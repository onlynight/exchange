package io.github.onlynight.exchange.plugin.sdk;

public interface Translator {

    String PLUGIN_TYPE_TRANSLATE_PLATFORM = "translatePlatform";
    String PLUGIN_TYPE_DOCUMENT = "documentHandler";

    /**
     * return typed text type
     *
     * @return typed text
     */
    String typedTextType();

    /**
     * 翻译统一调用入口
     *
     * @param srcLanguage    原语言
     * @param targetLanguage 要翻译的目标语言
     */
    void translate(String srcLanguage, String targetLanguage);

    PlatformHandlerPlugin getTranslatePlatformPlugin();

    DocumentHandlerPlugin getDocumentHandlerPlugin();

}
