package io.github.onlynight.exchange.translator.google;

import io.github.onlynight.exchange.typed.text.plugin.sdk.IosStringsTranslator;

import java.util.List;

public class GoogleIosStringsTranslator extends IosStringsTranslator<GoogleTranslatorHandler> {

	@Override
	public String textType() {
		return TEXT_TYPE_IOS;
	}

}
