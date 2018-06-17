package io.github.onlynight.exchange.plugin.sdk;

import io.github.onlynight.exchange.plugin.sdk.utils.ClassHelper;

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
		return handler.handleJsonString(result);
	}

	@Override
	public List<String> getSupportLanguage() {
		return handler.getSupportLanguage();
	}

	protected abstract String getValuesFolderName(String targetLanguage);

}
