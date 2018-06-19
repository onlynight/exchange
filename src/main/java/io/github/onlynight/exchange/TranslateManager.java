package io.github.onlynight.exchange;

import io.github.onlynight.exchange.bean.PluginConfig;
import io.github.onlynight.exchange.bean.TranslateConfig;
import io.github.onlynight.exchange.plugin.sdk.PlatformHandlerPlugin;
import io.github.onlynight.exchange.plugin.sdk.Translator;
import io.github.onlynight.exchange.plugin.sdk.impl.TranslatorImpl;

import java.util.List;

/**
 * Created by lion on 2016/10/28.
 * 翻译管理器，统一调度管理翻译器
 */
public class TranslateManager {

	private Translator translator;
	private static TranslateManager instance;

	public static TranslateManager getInstance() {
		if (instance == null) {
			instance = new TranslateManager();
		}
		return instance;
	}

	private void translate(String srcLanguage, String destLanguage) {
		if (destLanguage == null) {
			PlatformHandlerPlugin plugin = translator.getTranslatePlatformPlugin();
			if (plugin != null && plugin.getSupportLanguage() != null) {
				for (String lang : plugin.getSupportLanguage()) {
					translator.translate(srcLanguage, lang);
				}
			}
		} else {
			translator.translate(srcLanguage, destLanguage);
		}
	}

	private void createTranslator(String platform, String textType, String translatePath,
	                              String appId, String appKey, String apiUrl, List<String> others) {
		if (translator == null) {
			PluginConfig platformConfig = TranslatorPluginManager.getInstance()
					.getTranslatePlatformPlugins().get(platform);
			PluginConfig docConfig = TranslatorPluginManager.getInstance()
					.getDocHandlerPlugins().get(textType);

			if (platformConfig == null) {
				throw new RuntimeException("platformConfig load fail");
			}
			if (docConfig == null) {
				throw new RuntimeException("docConfig load fail");
			}

			translator = new TranslatorImpl(platformConfig.getName(), docConfig.getName(),
					translatePath, appId, appKey, apiUrl, others);
		}
	}

	private void translate(String srcLanguage, List<String> destLanguages, String platform, String textType,
	                       String translatePath, String appId, String appKey, String apiUrl, List<String> others) {
		createTranslator(platform, textType, translatePath, appId, appKey, apiUrl, others);
		if (destLanguages != null) {
			if (destLanguages.size() == 1 && destLanguages.get(0).equals("all")) {
				translate(srcLanguage, null);
			} else {
				for (String destLanguage : destLanguages) {
					translate(srcLanguage, destLanguage);
				}
			}
		}
	}

	public void translate(TranslateConfig config) {
		translate(config.getSourceLanguage(), config.getDestinationLanguage(), config.getTranslatePlatform(),
				config.getTextType(), config.getSourceFilePath(),
				config.getAppId(), config.getAppKey(), config.getApiUrl(), config.getOthers());
	}

}
