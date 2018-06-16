package io.github.onlynight.exchange.plugin.sdk;

import java.util.List;

public interface TranslatorPlugin {

	String TEXT_TYPE_ANDROID = "android";
	String TEXT_TYPE_IOS = "ios";

	String pluginName();

	String textType();

	/**
	 * set translate file path
	 *
	 * @param translatePath translate file path
	 */
	void setTranslatePath(String translatePath);

	void setTranslatePlatformInfo(String appId, String appKey, String apiUrl, List<String> others);

	/**
	 * you should return the plugin's relative path in {./plugins/},
	 * this path will use to load conf file.
	 *
	 * @return
	 */
	String getPluginRelativePath();

	/**
	 * 翻译统一调用入口
	 *
	 * @param srcLanguage    原语言
	 * @param targetLanguage 要翻译的目标语言
	 */
	void translate(String srcLanguage, String targetLanguage);

	/**
	 * 在不同的平台中实现，返回最终的请求url
	 *
	 * @param content        要翻译的内容，utf8需要encode一次
	 * @param srcLanguage    原语言
	 * @param targetLanguage 要翻译的目标语言
	 * @return
	 */
	String onGenerateUrl(String content, String srcLanguage, String targetLanguage);

	/**
	 * 内部翻译函数
	 *
	 * @param sourceString   源字符串
	 * @param srcLanguage    源语言
	 * @param targetLanguage 目标语言
	 * @return
	 */
	String innerTranslate(String sourceString, String srcLanguage, String targetLanguage);

	/**
	 * 请求REST_API后返回string处理这个结果，
	 * 你需要在请求REST_API后调用该函数。
	 *
	 * @param result 请求REST_API返回string
	 * @return 最终处理翻译请求的结果
	 */
	String onTranslateFinished(String result);

	/**
	 * 这个函数中需要返回该平台所支持的语言
	 *
	 * @return 支持的翻译语言
	 */
	List<String> getSupportLanguage();

}
