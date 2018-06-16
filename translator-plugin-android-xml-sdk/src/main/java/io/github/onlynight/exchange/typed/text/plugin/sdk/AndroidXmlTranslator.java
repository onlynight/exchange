package io.github.onlynight.exchange.typed.text.plugin.sdk;

import io.github.onlynight.exchange.plugin.sdk.LanguageFolderMapper;
import io.github.onlynight.exchange.plugin.sdk.TranslatorHandler;
import io.github.onlynight.exchange.plugin.sdk.TranslatorPlugin;
import io.github.onlynight.exchange.plugin.sdk.utils.ClassHelper;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

public abstract class AndroidXmlTranslator<Handler extends TranslatorHandler> implements TranslatorPlugin {

	private static final String ANDROID_FILE_SUB = ".xml";

	private static final String PATH = "plugins/sdk/android/";

	protected Handler handler;

	@Override
	public String pluginName() {
		return getClass().getSimpleName();
	}

	private String translatePath;
	private LanguageFolderMapper languageFolderMapper;

	public AndroidXmlTranslator() {
		handler = (Handler) ClassHelper.createHandler(getClass());
		languageFolderMapper = new LanguageFolderMapper();
		languageFolderMapper.loadDefaultMapper(getPluginRelativePath());
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
	public void translate(String srcLanguage, String targetLanguage) {
		File currentPath = new File(translatePath);
		File[] files = currentPath.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isFile()) {
					Document document = openDocument(file);
					if (document != null) {
						parseXmlAndTranslate(document, srcLanguage, targetLanguage);
						writeDocument(file, document, targetLanguage);
					}
				}
			}
		}
	}

	@Override
	public String innerTranslate(String sourceString, String srcLanguage, String targetLanguage) {
		if (sourceString == null) {
			return null;
		}

		String url = onGenerateUrl(sourceString, srcLanguage, targetLanguage);
		System.out.println(sourceString + " REQUEST URL IS : " + url);

		try {
			URLConnection connection = new URL(url).openConnection();
			InputStream is = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
			StringBuilder stringBuilder = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
			}
			reader.close();

			String result = stringBuilder.toString();
			System.out.println(sourceString + " REQUEST RESULT ===> " + result);
			return onTranslateFinished(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
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

	private Document openDocument(File currentPath) {
		if (currentPath.getName().endsWith(ANDROID_FILE_SUB)) {
			String xmlFileName = currentPath.getAbsolutePath();
			String xmlContent = loadXmlFile(xmlFileName);
			return getDocument(xmlContent);
		} else {
			return null;
		}
	}

	private void writeDocument(File translateFile, Document document, String targetLanguage) {
		if (document == null) {
			return;
		}

		File dir = new File(translateFile.getParent(), getValuesFolderName(targetLanguage));
		if (!dir.exists()) {
			dir.mkdirs();
		}

		String xml = document.asXML();
		try {
			FileOutputStream fos = new FileOutputStream(new File(dir.getAbsolutePath(), translateFile.getName()));
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, "utf-8"));
			writer.write(xml);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getValuesFolderName(String targetLanguage) {
		String folderName = languageFolderMapper.get(targetLanguage);
		if (folderName == null) {
			folderName = "values-" + targetLanguage;
		}
		return folderName;
	}

	private Document getDocument(String xml) {
		if (xml != null) {
			try {
				return DocumentHelper.parseText(xml);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	private String loadXmlFile(String path) {
		try {
			FileInputStream fis = new FileInputStream(new File(path));
			BufferedReader reader = new BufferedReader(new InputStreamReader(fis, "utf-8"));
			StringBuilder result = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line).append("\n");
			}
			fis.close();

			return result.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void parseXmlAndTranslate(Document document, String srcLanguage, String targetLanguage) {
		if (document == null) {
			System.out.println("document is null");
			return;
		}
		Element rootElement = document.getRootElement();
		Iterator iterator = rootElement.elementIterator();
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();
			String result = innerTranslate(element.getText(), srcLanguage, targetLanguage);
			if (result != null) {
				System.out.println(element.getText() + " TRANSLATE TO ====> " + result);
				element.setText(result);
			}
		}
	}

}
