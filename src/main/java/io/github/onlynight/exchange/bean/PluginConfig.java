package io.github.onlynight.exchange.bean;

public class PluginConfig extends BasePluginConfig {

	/**
	 * platform : youdao
	 */

	private String platform;

	/**
	 * typedTextType : android
	 */

	private String typedTextType;

	public String getTypedTextType() {
		return typedTextType;
	}

	public void setTypedTextType(String typedTextType) {
		this.typedTextType = typedTextType;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

}
