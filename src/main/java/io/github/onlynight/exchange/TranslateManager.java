package io.github.onlynight.exchange;

import io.github.onlynight.exchange.plugin.sdk.TranslatorPlugin;

import java.util.List;

/**
 * Created by lion on 2016/10/28.
 * 翻译管理器，统一调度管理翻译器
 */
public class TranslateManager {

	private static TranslateManager instance;

	public static TranslateManager getInstance() {
		if (instance == null) {
			instance = new TranslateManager();
		}
		return instance;
	}

	public void translate(String srcLanguage, String destLanguage, String platform, String textType,
	                      String translatePath, String appId, String appKey, String apiUrl, List<String> others) {
		TranslatorPlugin plugin = TranslatorPluginManager.getInstance().getTranslator(platform, textType);
		if (plugin != null) {
			plugin.setTranslatePath(translatePath);
			plugin.setTranslatePlatformInfo(appId, appKey, apiUrl, others);
			if (destLanguage == null) {
				List<String> languages = plugin.getSupportLanguage();
				for (String language : languages) {
					if (!language.equals(srcLanguage)) {
						plugin.translate(srcLanguage, language);
					}
				}
			} else {
				plugin.translate(srcLanguage, destLanguage);
			}
		} else {
			System.err.println("CAN'T FIND <platform>=" + platform + " <textType>=" + textType + " TRANSLATORS");
		}
	}

	private void translate(String srcLanguage, List<String> destLanguages, String platform, String textType,
	                       String translatePath, String appId, String appKey, String apiUrl, List<String> others) {
		if (destLanguages != null) {
			if (destLanguages.size() == 1 && destLanguages.get(0).equals("all")) {
				translate(srcLanguage, (String) null, platform, textType,
						translatePath, appId, appKey, apiUrl, others);
			} else {
				for (String destLanguage : destLanguages) {
					translate(srcLanguage, destLanguage, platform, textType,
							translatePath, appId, appKey, apiUrl, others);
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
