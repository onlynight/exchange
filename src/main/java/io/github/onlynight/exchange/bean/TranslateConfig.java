package io.github.onlynight.exchange.bean;

import java.util.List;

public class TranslateConfig {

    private String appId;
    private String appKey;
    private String apiUrl;
    private List<String> others;
    private String translatePlatform;
    private String textType;
    private String sourceLanguage;
    private List<String> destinationLanguage;
    private String sourceFilePath;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public List<String> getOthers() {
        return others;
    }

    public void setOthers(List<String> others) {
        this.others = others;
    }

    public String getTranslatePlatform() {
        return translatePlatform;
    }

    public void setTranslatePlatform(String translatePlatform) {
        this.translatePlatform = translatePlatform;
    }

    public String getTextType() {
        return textType;
    }

    public void setTextType(String textType) {
        this.textType = textType;
    }

    public String getSourceLanguage() {
        return sourceLanguage;
    }

    public void setSourceLanguage(String sourceLanguage) {
        this.sourceLanguage = sourceLanguage;
    }

    public List<String> getDestinationLanguage() {
        return destinationLanguage;
    }

    public void setDestinationLanguage(List<String> destinationLanguage) {
        this.destinationLanguage = destinationLanguage;
    }

    public String getSourceFilePath() {
        return sourceFilePath;
    }

    public void setSourceFilePath(String sourceFilePath) {
        this.sourceFilePath = sourceFilePath;
    }

    @Override
    public String toString() {
        StringBuilder languages = new StringBuilder();
        try {
            for (String lang : destinationLanguage) {
                languages.append(lang).append(",");
            }
            languages.deleteCharAt(languages.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "appId" + " = " + appId + "\n" +
                "appKey" + " = " + appKey + "\n" +
                "apiUrl" + " = " + apiUrl + "\n" +
                "others" + " = " + list2String(others) + "\n" +
                "translatePlatform" + " = " + translatePlatform + "\n" +
                "textType" + " = " + textType + "\n" +
                "sourceLanguage" + " = " + sourceLanguage + "\n" +
                "destinationLanguage" + " = " + list2String(destinationLanguage) + "\n" +
                "sourceFilePath" + " = " + sourceFilePath + "\n";
    }

    private static String list2String(List<String> list) {
        StringBuilder languages = new StringBuilder();

        if (list != null) {
            try {
                for (String data : list) {
                    languages.append(data).append(",");
                }
                languages.deleteCharAt(languages.length() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return languages.toString();
    }
}
