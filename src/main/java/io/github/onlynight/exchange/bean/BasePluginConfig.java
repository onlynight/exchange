package io.github.onlynight.exchange.bean;

public class BasePluginConfig {

    /**
     * pluginType : documentHandler
     * name : io.github.onlynight.exchange.typed.text.plugin.sdk.AndroidXmlDocumentHandlerPlugin
     */

    private String pluginType;
    private String name;

    public String getPluginType() {
        return pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
