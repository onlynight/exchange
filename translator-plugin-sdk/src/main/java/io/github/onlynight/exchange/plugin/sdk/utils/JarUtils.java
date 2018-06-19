package io.github.onlynight.exchange.plugin.sdk.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;

public class JarUtils {

    public static String getJarAbsPath(Class clazz) {
        String jarWholePath = clazz.getProtectionDomain().getCodeSource().getLocation().getFile();
        try {
            jarWholePath = java.net.URLDecoder.decode(jarWholePath, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new File(jarWholePath).getParentFile().getAbsolutePath();
    }

}
