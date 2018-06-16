package io.github.onlynight.exchange.translator.google;

import io.github.onlynight.exchange.typed.text.plugin.sdk.AndroidXmlTranslator;

import java.util.List;

public class GoogleAndroidXmlTranslator extends AndroidXmlTranslator<GoogleTranslatorHandler> {

	@Override
	public String textType() {
		return TEXT_TYPE_ANDROID;
	}

}
