package io.github.onlynight.exchange.translator.youdao.result;

import java.util.List;

public class YouDaoTranslateResult {
	/**
	 * tSpeakUrl : http://openapi.youdao.com/ttsapi?q=Please+set+share+content&langType=en&sign=DE105DDB1DEAE53A82D174BBF95CBDAB&salt=1529160270621&voice=4&format=mp3&appKey=2d33de3afc32ebfb
	 * query : 请设置分享内容
	 * translation : ["Please set share content"]
	 * errorCode : 0
	 * dict : {"url":"yddict://m.youdao.com/dict?le=eng&q=%E8%AF%B7%E8%AE%BE%E7%BD%AE%E5%88%86%E4%BA%AB%E5%86%85%E5%AE%B9"}
	 * webdict : {"url":"http://m.youdao.com/dict?le=eng&q=%E8%AF%B7%E8%AE%BE%E7%BD%AE%E5%88%86%E4%BA%AB%E5%86%85%E5%AE%B9"}
	 * l : zh-CHS2EN
	 * speakUrl : http://openapi.youdao.com/ttsapi?q=%E8%AF%B7%E8%AE%BE%E7%BD%AE%E5%88%86%E4%BA%AB%E5%86%85%E5%AE%B9&langType=zh-CHS&sign=88D6145AD3F9E4C3DED968244ECD7F41&salt=1529160270621&voice=4&format=mp3&appKey=2d33de3afc32ebfb
	 */

	private String tSpeakUrl;
	private String query;
	private String errorCode;
	private DictBean dict;
	private WebdictBean webdict;
	private String l;
	private String speakUrl;
	private List<String> translation;

	public String getTSpeakUrl() {
		return tSpeakUrl;
	}

	public void setTSpeakUrl(String tSpeakUrl) {
		this.tSpeakUrl = tSpeakUrl;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public DictBean getDict() {
		return dict;
	}

	public void setDict(DictBean dict) {
		this.dict = dict;
	}

	public WebdictBean getWebdict() {
		return webdict;
	}

	public void setWebdict(WebdictBean webdict) {
		this.webdict = webdict;
	}

	public String getL() {
		return l;
	}

	public void setL(String l) {
		this.l = l;
	}

	public String getSpeakUrl() {
		return speakUrl;
	}

	public void setSpeakUrl(String speakUrl) {
		this.speakUrl = speakUrl;
	}

	public List<String> getTranslation() {
		return translation;
	}

	public void setTranslation(List<String> translation) {
		this.translation = translation;
	}

	public static class DictBean {
		/**
		 * url : yddict://m.youdao.com/dict?le=eng&q=%E8%AF%B7%E8%AE%BE%E7%BD%AE%E5%88%86%E4%BA%AB%E5%86%85%E5%AE%B9
		 */

		private String url;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}

	public static class WebdictBean {
		/**
		 * url : http://m.youdao.com/dict?le=eng&q=%E8%AF%B7%E8%AE%BE%E7%BD%AE%E5%88%86%E4%BA%AB%E5%86%85%E5%AE%B9
		 */

		private String url;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}
	}
}
