package io.github.onlynight.exchange.typed.text.plugin.sdk;

import io.github.onlynight.exchange.plugin.sdk.LanguageFolderMapper;
import io.github.onlynight.exchange.plugin.sdk.TranslatorHandler;
import io.github.onlynight.exchange.plugin.sdk.TranslatorPlugin;
import io.github.onlynight.exchange.plugin.sdk.utils.ClassHelper;
import io.github.onlynight.exchange.typed.text.plugin.sdk.doc.DocumentIOS;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public abstract class IosStringsTranslator<Handler extends TranslatorHandler> implements TranslatorPlugin {

	//ios文本后缀
	private static final String IOS_STRINGS_SUB = ".strings";

	private static final String PATH = "plugins/sdk/ios/";

	public static final String IOS_VALUES_SUBFIX = ".lproj";
	public static final String IOS_VALUES_BASE = "Base" + IOS_VALUES_SUBFIX;
	public static final String IOS_VALUES_ZH_HANS = "zh-Hans" + IOS_VALUES_SUBFIX;
	public static final String IOS_VALUES_EN = "en" + IOS_VALUES_SUBFIX;

	protected Handler handler;

	private String translatePath;
	private LanguageFolderMapper languageFolderMapper;

	public IosStringsTranslator() {
		handler = (Handler) ClassHelper.createHandler(getClass());
		languageFolderMapper = new LanguageFolderMapper();
		languageFolderMapper.loadDefaultMapper(getPluginRelativePath());
	}

	@Override
	public String pluginName() {
		return getClass().getSimpleName();
	}

	@Override
	public String getPluginRelativePath() {
		return PATH;
	}

	@Override
	public void setTranslatePath(String translatePath) {
		this.translatePath = translatePath;
	}

	@Override
	public String innerTranslate(String sourceString, String src, String target) {
		if (sourceString == null) {
			return null;
		}

		String url = onGenerateUrl(sourceString, src, target);
//        System.out.println("REQUEST URL IS : " + url);

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

	@Override
	public void translate(String srcLanguage, String targetLanguage) {
		File file = new File(translatePath);
		File[] files = file.listFiles();
		if (files != null) {
			for (File doc : files) {
				if (doc.getName().endsWith(IOS_STRINGS_SUB)) {
					String filePath = doc.getAbsolutePath();
					DocumentIOS document = new DocumentIOS(filePath);
					document.parse();
					translate(document, srcLanguage, targetLanguage);
					writeDocument(filePath, document, targetLanguage);
				}
			}
		}
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

	private void translate(DocumentIOS document, String srcLanguage, String targetLanguage) {
		List<DocumentIOS.IOSStringLine> lines = document.getLines();
		if (lines != null) {
			for (DocumentIOS.IOSStringLine line : lines) {
				if (line.isValidLine()) {
					String result = innerTranslate(line.getValue(), srcLanguage, targetLanguage);
					System.out.println(line.getValue() + " TRANSLATE TO " + " ====> " + result);
					line.setValue(result);
				}
			}
		}
	}

	private void writeDocument(String docPath, DocumentIOS document, String target) {
		if (document == null) {
			return;
		}

		File dir = new File(new File(docPath).getParent() + File.separator + getValuesFolderName(target));
		if (!dir.exists()) {
			dir.mkdirs();
		}

		try {
			FileOutputStream fos = new FileOutputStream(new File(dir.getAbsolutePath() +
					File.separator + new File(docPath).getName()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));
			for (DocumentIOS.IOSStringLine line : document.getLines()) {
				if (line.isValidLine()) {
					writer.write(line.getKey() + " = \"" + line.getValue() + "\";\n");
				} else {
					writer.write(line.getContent() + "\n");
				}
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getValuesFolderName(String targetLanguage) {
		String folderName = languageFolderMapper.get(targetLanguage);
		if (folderName == null) {
			folderName = targetLanguage + IOS_VALUES_SUBFIX;
		}
		return folderName;
	}

}
