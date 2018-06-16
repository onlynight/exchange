package io.github.onlynight.exchange;

import java.util.List;

public class TranslatorPluginConfig {

    /**
     * textType : baidu
     * plugins : [{"name":"BaiduTranslator","textType":"android","entryPoint":"io.github.onlynight.exchange.translator.baidu.BaiduTranslator"}]
     */

    private String platform;
    private List<Plugin> plugins;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public List<Plugin> getPlugins() {
        return plugins;
    }

    public void setPlugins(List<Plugin> plugins) {
        this.plugins = plugins;
    }

    public static class Plugin {
        /**
         * name : BaiduTranslator
         * textType : android
         * entryPoint : io.github.onlynight.exchange.translator.baidu.BaiduTranslator
         */

        private String name;
        private String textType;
        private String entryPoint;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTextType() {
            return textType;
        }

        public void setTextType(String textType) {
            this.textType = textType;
        }

        public String getEntryPoint() {
            return entryPoint;
        }

        public void setEntryPoint(String entryPoint) {
            this.entryPoint = entryPoint;
        }
    }
}
