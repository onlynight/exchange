package io.github.onlynight.exchange.plugin.sdk.impl;

import com.github.onlynight.fastini.FastIni;
import com.github.onlynight.fastini.IniDocument;
import io.github.onlynight.exchange.plugin.sdk.PlatformHandlerPlugin;
import io.github.onlynight.exchange.plugin.sdk.utils.JarUtils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTranslatePlatformPlugin implements PlatformHandlerPlugin {

    protected List<String> languages;
    protected List<IniDocument.KeyValue> diffLanguageMapper;

    protected String appId;
    protected String appKey;
    protected String apiUrl;
    protected List<String> others;

    public BaseTranslatePlatformPlugin(String appId, String appKey, String apiUrl, List<String> others) {
        this.appId = appId;
        this.appKey = appKey;
        this.apiUrl = apiUrl;
        this.others = others;

        languages = loadSupportLanguage();
        if (languages == null) {
            languages = new ArrayList<>();
        }

        diffLanguageMapper = loadDiffLangMapper();
        if (diffLanguageMapper == null) {
            diffLanguageMapper = new ArrayList<>();
        }
    }

    private List<String> loadSupportLanguage() {
        try {
            InputStream inputStream = new FileInputStream(
                    getConfigFileInSameFolder("support_language.txt"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            List<String> languages = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                languages.add(line);
            }
            reader.close();
            return languages;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected String convertLanguage(String language) {
//        switch (language) {
//            case "zh-CN":
//                return "zh";
//            case "zh-TW":
//                return "cht";
//            case "ja":
//                return "jp";
//            default:
//                return language;
//        }
        for (IniDocument.KeyValue kv : diffLanguageMapper) {
            if (kv.getKey().equals(language)) {
                if (kv.getValue() != null && kv.getValue().size() > 0) {
                    return kv.getValue().get(0);
                }
            }
        }

        return language;
    }

    private List<IniDocument.KeyValue> loadDiffLangMapper() {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(
                    getConfigFileInSameFolder("diff_language_mapper.ini"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new FastIni().fromStream(inputStream);
    }

    @Override
    public List<String> getSupportLanguage() {
        return languages;
    }

    @Override
    public String translate(String sourceString, String srcLanguage, String targetLanguage) {
        String res = requestApi(sourceString, srcLanguage, targetLanguage);
        System.out.println("TRANSLATE " + sourceString + " ===> " + res);
        return res;
    }

    @Override
    public String requestApi(String sourceString, String srcLanguage, String targetLanguage) {
        if (sourceString == null) {
            return null;
        }

        String url = onGenerateUrl(sourceString, srcLanguage, targetLanguage);
        System.out.println("REQUEST " + sourceString + " ===> " + url);

        try {
            URLConnection connection = new URL(url).openConnection();
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            StringBuilder result = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            System.out.println("RESULT STRING ====> " + result.toString());

            return onTranslateFinished(result.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected File getConfigFileInSameFolder(String fileName) {
        return new File(JarUtils.getJarAbsPath(getClass()), fileName);
    }

}
