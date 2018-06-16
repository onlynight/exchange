package io.github.onlynight.exchange.translator.baidu;

import io.github.onlynight.exchange.typed.text.plugin.sdk.AndroidXmlTranslator;

public class BaiduAndroidXmlTranslator extends AndroidXmlTranslator<BaiduTranslatorHandler> {

	@Override
	public String textType() {
		return TEXT_TYPE_ANDROID;
	}

}
