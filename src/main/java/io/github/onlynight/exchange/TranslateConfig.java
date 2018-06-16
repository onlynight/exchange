package io.github.onlynight.exchange;

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

}
