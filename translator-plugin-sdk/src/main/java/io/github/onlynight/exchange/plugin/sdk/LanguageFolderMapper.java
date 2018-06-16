package io.github.onlynight.exchange.plugin.sdk;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.List;

public class LanguageFolderMapper {

	private static final String LANGUAGE_FOLDER_MAPPER = "language_folder_mapper.json";

	public List<LanguageFolderMapperBean> languageFolderMappers;

	public LanguageFolderMapper() {
//		if (languageFolderMappers == null) {
//			languageFolderMappers = loadDefaultMapper();
//		}
	}

	public String get(String key) {

		if (languageFolderMappers != null) {
			for (LanguageFolderMapperBean mapper : languageFolderMappers) {
				if (mapper.getKey().equals(key)) {
					return mapper.getValue();
				}
			}
		}

		return null;
	}

	public void add(String key, String value) {
		LanguageFolderMapperBean bean = new LanguageFolderMapperBean();
		bean.setKey(key);
		bean.setValue(value);
		languageFolderMappers.add(bean);
	}

	private List<LanguageFolderMapperBean> loadDefaultMapper() {
		InputStream inputStream = LanguageFolderMapper.class.getClassLoader()
				.getResourceAsStream(LANGUAGE_FOLDER_MAPPER);
		return languageFolderMappers = loadMapper(inputStream);
	}

	public void loadDefaultMapper(String relativePath) {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(new File(new File("").getAbsolutePath()
					+ File.separator + relativePath, LANGUAGE_FOLDER_MAPPER));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		loadDefaultMapper(inputStream);
	}

	public void loadDefaultMapper(InputStream inputStream) {
		languageFolderMappers = loadMapper(inputStream);
	}

	private List<LanguageFolderMapperBean> loadMapper(InputStream inputStream) {
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			reader.close();
			return new Gson().fromJson(sb.toString(), new TypeToken<List<LanguageFolderMapperBean>>() {
			}.getType());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public void addCustomerMapper(String mapperName) {
		InputStream inputStream = LanguageFolderMapper.class.getClassLoader()
				.getResourceAsStream(mapperName);
		List<LanguageFolderMapperBean> mappers = loadMapper(inputStream);
		if (mappers != null) {
			if (languageFolderMappers == null) {
				languageFolderMappers = mappers;
			} else {
				languageFolderMappers.addAll(mappers);
			}
		}
	}

	public static class LanguageFolderMapperBean {
		/**
		 * key : af
		 * value : values-af
		 */

		private String key;
		private String value;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}
}
