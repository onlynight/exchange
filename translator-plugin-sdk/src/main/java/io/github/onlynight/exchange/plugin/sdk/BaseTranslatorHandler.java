package io.github.onlynight.exchange.plugin.sdk;

import com.github.onlynight.fastini.FastIni;
import com.github.onlynight.fastini.IniDocument;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseTranslatorHandler implements TranslatorHandler {

	protected List<String> languages;
	protected List<IniDocument.KeyValue> diffLanguageMapper;

	protected String appId;
	protected String appKey;
	protected String apiUrl;
	protected List<String> others;

	public BaseTranslatorHandler() {
		languages = loadSupportLanguage();
		if (languages == null) {
			languages = new ArrayList<>();
		}
		diffLanguageMapper = loadDiffLangMapper();
		if (diffLanguageMapper == null) {
			diffLanguageMapper = new ArrayList<>();
		}
	}

	@Override
	public void setTranslatePlatformInfo(String appId, String appKey, String apiUrl, List<String> others) {
		this.appId = appId;
		this.appKey = appKey;
		this.apiUrl = apiUrl;
		this.others = others;
	}

	private List<String> loadSupportLanguage() {
		try {
			InputStream inputStream = new FileInputStream(
					new File(new File("").getAbsolutePath(), getConfigFileRelativePath() + "support_language.txt"));
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
					new File(new File("").getAbsolutePath(),
							getConfigFileRelativePath() + "diff_language_mapper.ini"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return new FastIni().fromStream(inputStream);
	}

	@Override
	public String handleJsonString(String result) {
		return null;
	}

	@Override
	public String handleXMLString(String result) {
		return null;
	}

	@Override
	public List<String> getSupportLanguage() {
		return languages;
	}

}
