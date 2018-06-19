package io.github.onlynight.exchange.translator.google;

import com.google.gson.Gson;
import io.github.onlynight.exchange.plugin.sdk.impl.BaseTranslatePlatformPlugin;
import io.github.onlynight.exchange.translator.google.result.GoogleTranslateResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class GoogleTranslatePlatformPlugin extends BaseTranslatePlatformPlugin {

    public GoogleTranslatePlatformPlugin(String appId, String appKey, String apiUrl, List<String> others) {
        super(appId, appKey, apiUrl, others);
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
    public String onTranslateFinished(String result) {
        GoogleTranslateResult json = new Gson().fromJson(result, GoogleTranslateResult.class);
        if (json != null && json.getData() != null &&
                json.getData().getTranslations() != null &&
                json.getData().getTranslations().size() > 0) {
            return json.getData().getTranslations().get(0).getTranslatedText();
        }

        return null;
    }

}
