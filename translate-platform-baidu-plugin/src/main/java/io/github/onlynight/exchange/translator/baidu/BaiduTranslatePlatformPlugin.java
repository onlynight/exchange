package io.github.onlynight.exchange.translator.baidu;

import com.google.gson.Gson;
import io.github.onlynight.exchange.plugin.sdk.impl.BaseTranslatePlatformPlugin;
import io.github.onlynight.exchange.plugin.sdk.utils.MD5Utils;
import io.github.onlynight.exchange.translator.baidu.result.BaiduTranslateResult;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Random;

/**
 * Created by lion on 2016/10/30.
 */
public class BaiduTranslatePlatformPlugin extends BaseTranslatePlatformPlugin {

    public BaiduTranslatePlatformPlugin(String appId, String appKey, String apiUrl, List<String> others) {
        super(appId, appKey, apiUrl, others);
    }

    @Override
    public String onGenerateUrl(String content, String fromLanguage, String toLanguage) {
        Random random = new Random();
        int randomInt = random.nextInt();

        String sign = sign(content, randomInt);
        try {
            return String.format("%s?from=%s&to=%s&appid=%s&salt=%d&q=%s&sign=%s",
                    apiUrl, convertLanguage(fromLanguage), convertLanguage(toLanguage), appId,
                    randomInt, URLEncoder.encode(content, "utf-8"), sign);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String onTranslateFinished(String result) {
        BaiduTranslateResult jsonResult = new Gson().fromJson(result, BaiduTranslateResult.class);
        if (jsonResult != null &&
                jsonResult.getTrans_result() != null &&
                jsonResult.getTrans_result().get(0) != null) {
            return jsonResult.getTrans_result().get(0).getDst();
        }
        return null;
    }

    private String sign(String content, int randomInt) {
        return MD5Utils.md5(appId + content + randomInt + appKey);
    }

}
