package io.github.onlynight.exchange;

import com.google.gson.Gson;
import io.github.onlynight.exchange.bean.DocumentHandlerPluginConfig;
import io.github.onlynight.exchange.bean.TranslatePlatformPluginConfig;
import io.github.onlynight.exchange.utils.JarLoaderUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Set;

public class TranslatorPluginManager {

    private HashMap<String, DocumentHandlerPluginConfig> docHandlerPlugins;
    private HashMap<String, TranslatePlatformPluginConfig> translatePlatformPlugins;

    private static TranslatorPluginManager instance;

    public static TranslatorPluginManager getInstance() {
        if (instance == null) {
            instance = new TranslatorPluginManager();
        }
        return instance;
    }

    private TranslatorPluginManager() {
        docHandlerPlugins = new HashMap<>();
        translatePlatformPlugins = new HashMap<>();
    }

    public void loadPlugins() {
        File currentFolder = new File("");
        File pluginFolder = new File(currentFolder.getAbsolutePath(), "plugins");
        scanFolder(pluginFolder.getAbsolutePath());
        System.err.println(translatePlatform(
                translatePlatformPlugins.keySet(), "translatePlatform"));
        System.err.println(translatePlatform(
                docHandlerPlugins.keySet(), "textType"));
        JarLoaderUtil.loadJarPath(pluginFolder.getAbsolutePath());
    }

    private void scanFolder(String path) {
        File currentFolder = new File(path);
        File[] files = currentFolder.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".jar")) {
                        loadPlugin(file);
                    }
                } else {
                    scanFolder(file.getAbsolutePath());
                }
            }
        }
    }

    private void loadPlugin(File pluginFile) {
        System.out.println("LOAD PLUGIN ===> " + pluginFile.getName());
        URL fileUrl = null;
        try {
            fileUrl = new URL("file:" + pluginFile.getAbsolutePath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (fileUrl != null) {
            URLClassLoader classLoader = new URLClassLoader(new URL[]{fileUrl},
                    ClassLoader.getSystemClassLoader());
            try {
                InputStream pluginConfigStream = classLoader.getResourceAsStream("doc_plugin.json");
                DocumentHandlerPluginConfig docConfig = loadDocConfig(pluginConfigStream);
                if (docConfig != null) {
                    docHandlerPlugins.put(docConfig.getTypedTextType(), docConfig);
                }

                pluginConfigStream = classLoader.getResourceAsStream("platform_plugin.json");
                TranslatePlatformPluginConfig platformConfig = loadPlatformConfig(pluginConfigStream);
                if (platformConfig != null) {
                    translatePlatformPlugins.put(platformConfig.getPlatform(), platformConfig);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private DocumentHandlerPluginConfig loadDocConfig(InputStream inputStream) {
        String content = loadStreamContent(inputStream);
        if (content != null) {
            return new Gson().fromJson(content, DocumentHandlerPluginConfig.class);
        }
        return null;
    }

    private TranslatePlatformPluginConfig loadPlatformConfig(InputStream inputStream) {
        String content = loadStreamContent(inputStream);
        if (content != null) {
            return new Gson().fromJson(content, TranslatePlatformPluginConfig.class);
        }
        return null;
    }

    private String loadStreamContent(InputStream inputStream) {
        String line;
        StringBuilder sb = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, "utf-8"));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();
            return sb.toString();
        } catch (Exception ignore) {
            return null;
        }

    }

    public HashMap<String, DocumentHandlerPluginConfig> getDocHandlerPlugins() {
        return docHandlerPlugins;
    }

    public void setDocHandlerPlugins(HashMap<String, DocumentHandlerPluginConfig> docHandlerPlugins) {
        this.docHandlerPlugins = docHandlerPlugins;
    }

    public HashMap<String, TranslatePlatformPluginConfig> getTranslatePlatformPlugins() {
        return translatePlatformPlugins;
    }

    public void setTranslatePlatformPlugins(HashMap<String, TranslatePlatformPluginConfig> translatePlatformPlugins) {
        this.translatePlatformPlugins = translatePlatformPlugins;
    }

    private String translatePlatform(Set<String> keys, String namespace) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (String plat : keys) {
            sb.append(plat).append(",");
        }
        sb.append("]");
        String res = sb.toString();
        int index = res.lastIndexOf(',');
        String plats = res.substring(0, index) + res.substring(index + 1, res.length());
        return namespace + ": " + plats;
    }

}
