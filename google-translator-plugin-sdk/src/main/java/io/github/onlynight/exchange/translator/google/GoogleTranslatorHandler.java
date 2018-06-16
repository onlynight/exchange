package io.github.onlynight.exchange.translator.google;

import com.google.gson.Gson;
import io.github.onlynight.exchange.plugin.sdk.BaseTranslatorHandler;
import io.github.onlynight.exchange.translator.google.result.GoogleTranslateResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class GoogleTranslatorHandler extends BaseTranslatorHandler {

	// https://www.googleapis.com/language/translate/v2?
	private static final String PATH = "plugins/sdk/google/";

	@Override
	public String getConfigFileRelativePath() {
		return PATH;
	}

	@Override
	public String onGenerateUrl(String content, String src, String target) {
		try {
			return String.format("%s?key=%s&source=%s&target=%s&q=%s",
					apiUrl, appKey, src, target, URLEncoder.encode(content, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String handleJsonString(String result) {
		GoogleTranslateResult json = new Gson().fromJson(result, GoogleTranslateResult.class);
		if (json != null && json.getData() != null &&
				json.getData().getTranslations() != null &&
				json.getData().getTranslations().size() > 0) {
			return json.getData().getTranslations().get(0).getTranslatedText();
		}

		return null;
	}

}
