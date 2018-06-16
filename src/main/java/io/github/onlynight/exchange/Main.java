package io.github.onlynight.exchange;

import com.github.onlynight.fastini.FastIni;
import com.google.gson.Gson;
import io.github.onlynight.exchange.utils.JarLoaderUtil;

import java.io.File;

public class Main {

    private static final String EMPTY_STR = "";
    private static final String CONFIG_FILE_NAME = "translate_config.ini";
    private static final String LIBS_FOLDER_NAME = "libs";
    private static final String PLUGINS_FOLDER_NAME = "plugins";
    private static final String PLUGINS_SDK_FOLDER_NAME = PLUGINS_FOLDER_NAME + File.separator + "sdk";

    public static void main(String[] args) {
        loadLibs();
        loadSdk();
        loadPlugins();
        translate();
    }

    private static void loadLibs() {
        String sdkPath = new File(new File(EMPTY_STR).getAbsolutePath(),
                LIBS_FOLDER_NAME).getAbsolutePath();
        JarLoaderUtil.loadJarPath(sdkPath);
    }

    private static void loadSdk() {
        String sdkPath = new File(new File(EMPTY_STR).getAbsolutePath(),
                PLUGINS_SDK_FOLDER_NAME).getAbsolutePath();
        JarLoaderUtil.loadJarPath(sdkPath);
    }

    private static void loadPlugins() {
        TranslatorPluginManager.getInstance().loadPlugins();
    }

    private static void translate() {
        TranslateConfig config = new FastIni().fromPath(new File(new File(EMPTY_STR).getAbsolutePath(),
                CONFIG_FILE_NAME).getAbsolutePath(), TranslateConfig.class);
        System.out.println(new Gson().toJson(config));
        TranslateManager.getInstance().translate(config);
    }

}
