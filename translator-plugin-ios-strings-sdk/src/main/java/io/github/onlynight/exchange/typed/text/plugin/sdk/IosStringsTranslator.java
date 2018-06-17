package io.github.onlynight.exchange.typed.text.plugin.sdk;

import io.github.onlynight.exchange.plugin.sdk.BaseTranslatorPlugin;
import io.github.onlynight.exchange.plugin.sdk.TranslatorHandler;
import io.github.onlynight.exchange.typed.text.plugin.sdk.doc.DocumentIOS;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public abstract class IosStringsTranslator<Handler extends TranslatorHandler> extends BaseTranslatorPlugin<Handler> {

	//ios文本后缀
	private static final String IOS_STRINGS_SUB = ".strings";

	private static final String PATH = "plugins/sdk/ios/";

	public static final String IOS_VALUES_SUBFIX = ".lproj";
	public static final String IOS_VALUES_BASE = "Base" + IOS_VALUES_SUBFIX;
	public static final String IOS_VALUES_ZH_HANS = "zh-Hans" + IOS_VALUES_SUBFIX;
	public static final String IOS_VALUES_EN = "en" + IOS_VALUES_SUBFIX;

	@Override
	public String getPluginRelativePath() {
		return PATH;
	}

	@Override
	public String textType() {
		return TEXT_TYPE_IOS;
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
	protected String getValuesFolderName(String targetLanguage) {
		String folderName = languageFolderMapper.get(targetLanguage);
		if (folderName == null) {
			folderName = targetLanguage + IOS_VALUES_SUBFIX;
		}
		return folderName;
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

}
