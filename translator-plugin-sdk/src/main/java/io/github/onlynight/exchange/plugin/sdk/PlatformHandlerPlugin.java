package io.github.onlynight.exchange.plugin.sdk;

import java.util.List;

/**
 * Created by lion on 2016/10/30.
 * 将rest_api处理和具体的translator分离方便拓展
 */
public interface PlatformHandlerPlugin {

    /**
     * 在不同的平台中实现，返回最终的请求url
     *
     * @param content      要翻译的内容，utf8需要encode一次
     * @param fromLanguage 原语言
     * @param toLanguage   要翻译的目标语言ss
     * @return
     */
    String onGenerateUrl(String content, String fromLanguage, String toLanguage);

    /**
     * get support translate language
     *
     * @return support language language set
     */
    List<String> getSupportLanguage();

    /**
     * 请求REST_API后返回string处理这个结果，
     * 你需要在请求REST_API后调用该函数。
     *
     * @param result 请求REST_API返回string
     * @return 最终处理翻译请求的结果
     */
    String onTranslateFinished(String result);

    /**
     * 翻译统一调用入口
     *
     * @param srcLanguage    原语言
     * @param targetLanguage 要翻译的目标语言
     */
    String translate(String sourceString, String srcLanguage, String targetLanguage);

    /**
     * request translate platform api, create a http request.
     *
     * @param sourceString   source string
     * @param srcLanguage    原语言
     * @param targetLanguage 要翻译的目标语言
     * @return
     */
    String requestApi(String sourceString, String srcLanguage, String targetLanguage);

}
