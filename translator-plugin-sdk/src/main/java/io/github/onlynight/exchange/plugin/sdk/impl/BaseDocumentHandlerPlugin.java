package io.github.onlynight.exchange.plugin.sdk.impl;

import io.github.onlynight.exchange.plugin.sdk.DocumentHandlerPlugin;
import io.github.onlynight.exchange.plugin.sdk.utils.JarUtils;
import io.github.onlynight.exchange.plugin.sdk.utils.LanguageFolderMapper;

import java.io.*;

/**
 * document plugin base
 */
public abstract class BaseDocumentHandlerPlugin<T> implements DocumentHandlerPlugin<T> {

    protected String documentPath;

    protected LanguageFolderMapper languageFolderMapper;

    public BaseDocumentHandlerPlugin(String documentPath) {
        this.documentPath = documentPath;
        this.languageFolderMapper = new LanguageFolderMapper();
        this.languageFolderMapper.loadDefaultMapper(JarUtils.getJarAbsPath(getClass()));
    }

    @Override
    public String getResultFolderName(String targetLanguage) {
        if (languageFolderMapper != null) {
            return languageFolderMapper.get(targetLanguage);
        }
        return null;
    }

    protected void writeNewDocument(String docPath, StringBuilder document, String targetLanguage) {
        if (document == null) {
            return;
        }

        File dir = new File(new File(docPath).getParent() +
                File.separator + getResultFolderName(targetLanguage));

        if (!dir.exists()) {
            dir.mkdirs();
        }

        try {
            FileOutputStream fos = new FileOutputStream(new File(dir.getAbsolutePath() +
                    File.separator + new File(docPath).getName()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
            writer.write(document.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
