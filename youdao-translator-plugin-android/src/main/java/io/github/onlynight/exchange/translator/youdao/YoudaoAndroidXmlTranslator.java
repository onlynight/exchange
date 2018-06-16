package io.github.onlynight.exchange.translator.youdao;

import io.github.onlynight.exchange.typed.text.plugin.sdk.AndroidXmlTranslator;

import java.util.List;

public class YoudaoAndroidXmlTranslator extends AndroidXmlTranslator<YoudaoTranslatorHandler> {

	@Override
	public String textType() {
		return TEXT_TYPE_ANDROID;
	}

}
