package io.github.onlynight.exchange.typed.text.plugin.sdk.generator;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * language and android value-xx folder map generator
 */
public class LanguageFolderMapperGenerator {

    public static void main(String args[]) {
        String currentPath = new File("").getAbsolutePath();
        generate(new File(currentPath, "android-xml-translator-plugin-sdk/support_language").getAbsolutePath());
    }

    public static void generate(String currentPath) {
        List<String> languages = loadSupportLanguage(currentPath +
                File.separator + "support_language.txt");
        genLanguagePlatform(currentPath, languages);
    }

    private static List<String> loadSupportLanguage(String path) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream(new File(path))));
            List<String> countries = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                countries.add(line);
            }
            reader.close();

            return countries;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void genLanguagePlatform(String path, List<String> languages) {
        try {

            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(new File(path +
                            File.separator + "LanguageFolderMapper.json")), "utf-8"));
            writer.write("[");
            writer.write("\n");

            int i = 0;
            for (String language : languages) {
                StringBuilder json = new StringBuilder();
                json.append("\t{\n")
                        .append("\t\t\"key\":").append("\"").append(language).append("\",\n")
                        .append("\t\t\"value\":");
                if (language.contains("-")) {
                    String temp[] = language.split("-");
                    json.append("\"values-").append(temp[0]).append("-r").append(temp[1]).append("\"");
                } else {
                    json.append("\"values-").append(language).append("\"");
                }
                json.append("\n\t}");

                writer.write(json.toString());
                if (i == languages.size() - 1) {
                    writer.write("\n");
                } else {
                    writer.write(",\n");
                }
                i++;
            }

            writer.write("]");

            writer.close();

            System.out.println("Generate Finish!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
