package io.github.onlynight.exchange.typed.text.plugin.sdk.doc;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lion on 2016/12/15.
 */
public class DocumentIOS {

    private static final String IGNORE_LINE_START_1 = "/*";
    private static final String IGNORE_LINE_START_2 = "*/";
    private static final String IGNORE_LINE_START_3 = "//";

    private List<String> srcLines = new ArrayList<>();
    private List<IOSStringLine> lines = new ArrayList<>();
    private String path;

    public DocumentIOS(String path) {
        this.path = path;
    }

    public DocumentIOS parse() {
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(new File(path)), "utf-8"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                srcLines.add(line);
            }
            reader.close();

            boolean isAnnotation = false;
            for (String tempLine : srcLines) {
                IOSStringLine iosStringLine = new IOSStringLine();
                iosStringLine.setContent(tempLine);

                // 过滤多行注释，单行
                if (tempLine.contains(IGNORE_LINE_START_1) &&
                        tempLine.contains(IGNORE_LINE_START_2)) {
                }

                // 过滤多行注释，多行
                if (tempLine.startsWith(IGNORE_LINE_START_1)) {
                    isAnnotation = true;
                }

                if (tempLine.startsWith(IGNORE_LINE_START_2)) {
                    isAnnotation = false;
                }

                // 过滤单行注释
                if (tempLine.startsWith(IGNORE_LINE_START_3)) {
                }

                // 识别关键行, pattern1
                if (tempLine.contains("=") &&
                        tempLine.contains(";") &&
                        !tempLine.startsWith(IGNORE_LINE_START_1) &&
                        !tempLine.startsWith(IGNORE_LINE_START_3)) {
                    String temp = tempLine.replace(" ", "");
                    int indexEqual = temp.indexOf("=");
                    int indexColon = temp.indexOf(";");
                    String key = temp.substring(0, indexEqual);
                    String value = temp.substring(indexEqual + 1, indexColon - 1).replace("\"", "");
                    iosStringLine.setKey(key);
                    iosStringLine.setValue(value);
                    lines.add(iosStringLine);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return this;
    }

    public List<IOSStringLine> getLines() {
        return lines;
    }

    public void setLines(List<IOSStringLine> lines) {
        this.lines = lines;
    }

    public static final class IOSStringLine {
        private String content;
        private String key;
        private String value;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
