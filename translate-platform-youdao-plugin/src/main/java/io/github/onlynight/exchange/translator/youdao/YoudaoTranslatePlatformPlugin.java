package io.github.onlynight.exchange.translator.youdao;

import com.google.gson.Gson;
import io.github.onlynight.exchange.plugin.sdk.impl.BaseTranslatePlatformPlugin;
import io.github.onlynight.exchange.plugin.sdk.utils.MD5Utils;
import io.github.onlynight.exchange.translator.youdao.result.YouDaoTranslateResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

public class YoudaoTranslatePlatformPlugin extends BaseTranslatePlatformPlugin {

    public YoudaoTranslatePlatformPlugin(String appId, String appKey, String apiUrl, List<String> others) {
        super(appId, appKey, apiUrl, others);
    }

    @Override
    public String onGenerateUrl(String content, String fromLanguage, String toLanguage) {
        Random random = new Random();
        int salt = random.nextInt();
        String sign = sign(content, salt);
        try {
            return String.format("%s?q=%s&from=%s$to=%s&appKey=%s&salt=%s&sign=%s",
                    apiUrl, URLEncoder.encode(content, "utf-8"), convertLanguage(fromLanguage),
                    convertLanguage(toLanguage), appId, salt, sign);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String onTranslateFinished(String result) {
        YouDaoTranslateResult res = new Gson().fromJson(result, YouDaoTranslateResult.class);
        if (res != null && res.getTranslation() != null && res.getTranslation().size() > 0) {
            return res.getTranslation().get(0);
        }

        return null;
    }

    private String sign(String content, int salt) {
        return MD5Utils.md5(appId + content + salt + appKey);
    }

}
