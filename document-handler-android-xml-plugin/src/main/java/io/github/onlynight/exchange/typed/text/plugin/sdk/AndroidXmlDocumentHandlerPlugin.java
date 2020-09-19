package io.github.onlynight.exchange.typed.text.plugin.sdk;

import io.github.onlynight.exchange.plugin.sdk.impl.BaseDocumentHandlerPlugin;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AndroidXmlDocumentHandlerPlugin extends BaseDocumentHandlerPlugin<Document> {

    private static final String ANDROID_FILE_SUB = ".xml";

    public AndroidXmlDocumentHandlerPlugin(String documentPath) {
        super(documentPath);
    }

    @Override
    public String typedTextType() {
        return TEXT_TYPE_ANDROID;
    }

    @Override
    public List<TranslatorDocument<Document>> getDocument() {
        File file = new File(documentPath);
        File[] files = file.listFiles();
        if (files != null) {
            List<TranslatorDocument<Document>> documents = new ArrayList<>();
            for (File doc : files) {
                if (doc.isFile() && doc.getName().endsWith(ANDROID_FILE_SUB)) {
                    TranslatorDocument<Document> res = convertDocument(openDocument(doc),
                            doc.getAbsolutePath());
                    if (res != null) {
                        documents.add(res);
                    }
                }
            }
            return documents;
        }
        return null;
    }

    @Override
    public TranslatorDocument<Document> convertDocument(Document document, String path) {
        if (document != null) {
            Element rootElement = document.getRootElement();
            Iterator iterator = rootElement.elementIterator();
            List<String> lines = new ArrayList<>();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                lines.add(element.getText());
            }
            return new TranslatorDocument<>(path, document, lines);
        }
        return null;
    }

    @Override
    public void writeNewDocument(TranslatorDocument<Document> document, String targetLanguage) {
        Document doc = document.getSourceDocument();
        Element rootElement = doc.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        int index = 0;
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext() && document.getDestStrings() != null) {
            Element element = (Element) iterator.next();
            element.setText(document.getDestStrings().get(index));
            index++;
        }
        sb.append(doc.asXML());
        writeNewDocument(document.getPath(), sb, targetLanguage);
    }

    @Override
    public String getResultFolderName(String targetLanguage) {
        String folderName = super.getResultFolderName(targetLanguage);
        if (folderName == null) {
            folderName = "values-" + targetLanguage;
        }
        return folderName;
    }

    private Document openDocument(File fileName) {
        if (fileName.getName().endsWith(ANDROID_FILE_SUB)) {
            String xmlFileName = fileName.getAbsolutePath();
            String xmlContent = loadXmlFile(xmlFileName);
            return getDocument(xmlContent);
        } else {
            return null;
        }
    }

    private Document getDocument(String xml) {
        if (xml != null) {
            try {
                return DocumentHelper.parseText(xml);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private String loadXmlFile(String path) {
        try {
            FileInputStream fis = new FileInputStream(new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line).append("\n");
            }
            fis.close();

            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
