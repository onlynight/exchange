package io.github.onlynight.exchange;

import com.google.gson.Gson;
import io.github.onlynight.exchange.plugin.sdk.TranslatorPlugin;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TranslatorPluginManager {

    private HashMap<String, List<TranslatorPlugin>> plugins;

    private static TranslatorPluginManager instance;

    public static TranslatorPluginManager getInstance() {
        if (instance == null) {
            instance = new TranslatorPluginManager();
        }
        return instance;
    }

    private TranslatorPluginManager() {
        plugins = new HashMap<>();
    }

    public void loadPlugins() {
        File currentFolder = new File("");
        File pluginFolder = new File(currentFolder.getAbsolutePath(), "plugins");
        File[] files = pluginFolder.listFiles();
        if (files != null && files.length > 0) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".jar")) {
                    loadPlugin(file);
                }
            }
        }
    }

    private void loadPlugin(File pluginFile) {
        System.out.println("LOAD PLUGIN ===> " + pluginFile.getName());
        try {
            URL fileUrl = new URL("file:" + pluginFile.getAbsolutePath());
            URLClassLoader classLoader = new URLClassLoader(new URL[]{fileUrl},
                    ClassLoader.getSystemClassLoader());
            InputStream pluginConfigStream = classLoader.getResourceAsStream("plugin.json");
            TranslatorPluginConfig config = loadConfig(pluginConfigStream);
            if (config != null) {
                if (config.getPlugins() != null) {
                    List<TranslatorPlugin> instances = new ArrayList<>();
                    for (TranslatorPluginConfig.Plugin plugin : config.getPlugins()) {
                        Class<?> clazz = classLoader.loadClass(plugin.getEntryPoint());
                        TranslatorPlugin instance = (TranslatorPlugin) clazz.newInstance();
                        System.out.println("PLUGIN ===> " + instance.pluginName() + " ===> LOADED");
                        instances.add(instance);
                    }

                    List<TranslatorPlugin> loadedPlugins = plugins.get(config.getPlatform());
                    if (loadedPlugins != null) {
                        loadedPlugins.addAll(instances);
                    } else {
                        plugins.put(config.getPlatform(), instances);
                    }
                }
            } else {
                System.err.println("DON'T FIND \"plugin.json\" file in " + pluginFile.getName());
            }
        } catch (MalformedURLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private TranslatorPluginConfig loadConfig(InputStream inputStream) {
        if (inputStream != null) {
            String line;
            StringBuilder sb = new StringBuilder();
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(inputStream, "utf-8"));
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                reader.close();
                return new Gson().fromJson(sb.toString(), TranslatorPluginConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public HashMap<String, List<TranslatorPlugin>> getPlugins() {
        return plugins;
    }

    public TranslatorPlugin getTranslator(String platform, String textType) {
        if (plugins != null) {
            List<TranslatorPlugin> temps = plugins.get(platform);
            for (TranslatorPlugin plugin : temps) {
                if (plugin.textType().equals(textType)) {
                    return plugin;
                }
            }
        }

        return null;
    }

}
