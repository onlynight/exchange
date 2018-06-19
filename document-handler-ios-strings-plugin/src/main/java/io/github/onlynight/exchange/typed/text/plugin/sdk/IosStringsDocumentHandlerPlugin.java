package io.github.onlynight.exchange.typed.text.plugin.sdk;

import io.github.onlynight.exchange.plugin.sdk.impl.BaseDocumentHandlerPlugin;
import io.github.onlynight.exchange.typed.text.plugin.sdk.doc.DocumentIOS;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class IosStringsDocumentHandlerPlugin extends BaseDocumentHandlerPlugin<DocumentIOS> {

    //ios文本后缀
    private static final String IOS_STRINGS_SUB = ".strings";

    public static final String IOS_VALUES_SUFFIX = ".lproj";

    private DocumentIOS sourceDoc;

    public IosStringsDocumentHandlerPlugin(String documentPath) {
        super(documentPath);
    }

    @Override
    public String typedTextType() {
        return TEXT_TYPE_IOS;
    }

    @Override
    public String getResultFolderName(String targetLanguage) {
        String folderName = super.getResultFolderName(targetLanguage);
        if (folderName == null) {
            folderName = targetLanguage + IOS_VALUES_SUFFIX;
        }
        return folderName;
    }

    @Override
    public List<TranslatorDocument<DocumentIOS>> getDocument() {
        File file = new File(documentPath);
        File[] files = file.listFiles();
        if (files != null) {
            List<TranslatorDocument<DocumentIOS>> documents = new ArrayList<>();
            for (File doc : files) {
                if (doc.isFile() && doc.getName().endsWith(IOS_STRINGS_SUB)) {
                    TranslatorDocument<DocumentIOS> res = convertDocument(
                            new DocumentIOS(doc.getAbsolutePath()).parse(), doc.getAbsolutePath());
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
    public TranslatorDocument<DocumentIOS> convertDocument(DocumentIOS document, String path) {
        if (document != null) {
            List<DocumentIOS.IOSStringLine> iosLines = document.getLines();
            List<String> lines = new ArrayList<>();
            for (DocumentIOS.IOSStringLine line : iosLines) {
                lines.add(line.getValue());
            }
            return new TranslatorDocument<>(path, document, lines);
        }
        return null;
    }

    @Override
    public void writeNewDocument(TranslatorDocument<DocumentIOS> document, String targetLanguage) {
        DocumentIOS doc = document.getSourceDocument();
        List<DocumentIOS.IOSStringLine> lines = doc.getLines();
        StringBuilder sb = new StringBuilder();
        if (lines != null && document.getDestStrings() != null) {
            DocumentIOS.IOSStringLine line;
            for (int i = 0; i < lines.size(); i++) {
                line = lines.get(i);
                lines.get(i).setValue(document.getDestStrings().get(i));
                sb.append(line.getKey()).append(" = \"").append(line.getValue()).append("\";\n");
            }
        }
        writeNewDocument(document.getPath(), sb, targetLanguage);
    }

}
