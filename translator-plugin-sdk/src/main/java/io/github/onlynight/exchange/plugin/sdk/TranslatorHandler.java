package io.github.onlynight.exchange.plugin.sdk;

import java.util.List;

/**
 * Created by lion on 2016/10/30.
 * 将rest_api处理和具体的translator分离方便拓展
 */
public interface TranslatorHandler {

	/**
	 * @param appId
	 * @param appKey
	 * @param apiUrl
	 * @param others
	 */
	void setTranslatePlatformInfo(String appId, String appKey, String apiUrl, List<String> others);

	/**
	 * you should return the plugin's relative path in {./plugins/},
	 * this path will use to load conf file.
	 *
	 * @return
	 */
	String getConfigFileRelativePath();

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
	 * REST_API调用返回结果
	 *
	 * @param result json字符串
	 * @return 翻译的最终结果
	 */
	String handleJsonString(String result);

	/**
	 * REST_API调用返回结果
	 *
	 * @param result xml字符串
	 * @return 翻译的最终结果
	 */
	String handleXMLString(String result);

	/**
	 * get support translate language
	 *
	 * @return support language language set
	 */
	List<String> getSupportLanguage();

}
