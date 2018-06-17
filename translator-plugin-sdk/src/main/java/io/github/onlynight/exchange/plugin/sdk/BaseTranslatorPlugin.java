package io.github.onlynight.exchange.plugin.sdk;

import io.github.onlynight.exchange.plugin.sdk.utils.ClassHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public abstract class BaseTranslatorPlugin<Handler extends TranslatorHandler> implements TranslatorPlugin {

	protected Handler handler;
	protected String translatePath;
	protected LanguageFolderMapper languageFolderMapper;

	public BaseTranslatorPlugin() {
		handler = (Handler) ClassHelper.createHandler(getClass());
		languageFolderMapper = new LanguageFolderMapper();
		languageFolderMapper.loadDefaultMapper(getPluginRelativePath());
	}

	@Override
	public String pluginName() {
		return getClass().getSimpleName();
	}

	@Override
	public void setTranslatePath(String translatePath) {
		this.translatePath = translatePath;
	}

	@Override
	public void setTranslatePlatformInfo(String appId, String appKey, String apiUrl, List<String> others) {
		handler.setTranslatePlatformInfo(appId, appKey, apiUrl, others);
	}

	@Override
	public String onGenerateUrl(String content, String srcLanguage, String targetLanguage) {
		return handler.onGenerateUrl(content, srcLanguage, targetLanguage);
	}

	@Override
	public String onTranslateFinished(String result) {
		System.out.println(result);
		return handler.handleJsonString(result);
	}

	@Override
	public List<String> getSupportLanguage() {
		return handler.getSupportLanguage();
	}

	@Override
	public String innerTranslate(String sourceString, String src, String target) {
		if (sourceString == null) {
			return null;
		}

		String url = onGenerateUrl(sourceString, src, target);

		try {
			URLConnection connection = new URL(url).openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder result = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}

			return onTranslateFinished(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	protected abstract String getValuesFolderName(String targetLanguage);

}
