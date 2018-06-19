package io.github.onlynight.exchange.plugin.sdk.impl;

import io.github.onlynight.exchange.plugin.sdk.DocumentHandlerPlugin;
import io.github.onlynight.exchange.plugin.sdk.PlatformHandlerPlugin;
import io.github.onlynight.exchange.plugin.sdk.Translator;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class TranslatorImpl implements Translator {

    private PlatformHandlerPlugin translatorHandlerPlugin;
    private DocumentHandlerPlugin<Object> documentHandlerPlugin;
    private String handlerClass;
    private String documentClass;
    protected String translatePath;

    private String appId;
    private String appKey;
    private String apiUrl;
    private List<String> others;

    public TranslatorImpl(String translatePlatformClass, String documentHandlerClass, String translatePath,
                          String appId, String appKey, String apiUrl, List<String> others) {
        this.handlerClass = translatePlatformClass;
        this.documentClass = documentHandlerClass;
        this.translatePath = translatePath;

        this.appId = appId;
        this.appKey = appKey;
        this.apiUrl = apiUrl;
        this.others = others;

        translatorHandlerPlugin = createTranslateHandler();
        documentHandlerPlugin = createDocumentPlugin();
    }

    private PlatformHandlerPlugin createTranslateHandler() {

        try {
            Class<?> clazz = Class.forName(handlerClass);
            Constructor<?> constructor = clazz.getConstructor(String.class, String.class,
                    String.class, List.class);
            return (PlatformHandlerPlugin) constructor.newInstance(appId, appKey, apiUrl, others);
        } catch (ClassNotFoundException | NoSuchMethodException |
                IllegalAccessException | InvocationTargetException |
                InstantiationException e) {
            e.printStackTrace();
        }

        checkPlatformHandlerPlugin();

        return null;

    }

    private DocumentHandlerPlugin<Object> createDocumentPlugin() {
        try {
            Class<?> clazz = Class.forName(documentClass);
            Constructor<?> constructor = clazz.getConstructor(String.class);
            return (DocumentHandlerPlugin<Object>) constructor.newInstance(translatePath);
        } catch (ClassNotFoundException | NoSuchMethodException |
                IllegalAccessException | InvocationTargetException |
                InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String typedTextType() {
        checkDocumentPlugin();
        return documentHandlerPlugin.typedTextType();
    }

    @Override
    public void translate(String srcLanguage, String targetLanguage) {
        checkPlatformHandlerPlugin();
        checkDocumentPlugin();
        List<DocumentHandlerPlugin.TranslatorDocument<Object>> docs = documentHandlerPlugin.getDocument();
        for (DocumentHandlerPlugin.TranslatorDocument<Object> doc : docs) {
            doc.setDestStrings(new ArrayList<>());
            for (String src : doc.getSrcStrings()) {
                String res = translatorHandlerPlugin.translate(src, srcLanguage, targetLanguage);
                doc.getDestStrings().add(res);
            }
            documentHandlerPlugin.writeNewDocument(doc, targetLanguage);
        }
    }

    @Override
    public PlatformHandlerPlugin getTranslatePlatformPlugin() {
        return translatorHandlerPlugin;
    }

    @Override
    public DocumentHandlerPlugin getDocumentHandlerPlugin() {
        return documentHandlerPlugin;
    }

    private void checkPlatformHandlerPlugin() {
        if (translatorHandlerPlugin == null) {
            throw new RuntimeException("HANDLER NOT FOUND: <" + handlerClass + ">");
        }
    }

    private void checkDocumentPlugin() {
        if (documentHandlerPlugin == null) {
            throw new RuntimeException("DOCUMENT PLUGIN IS NULL: <" + documentClass + ">");
        }
    }

}
