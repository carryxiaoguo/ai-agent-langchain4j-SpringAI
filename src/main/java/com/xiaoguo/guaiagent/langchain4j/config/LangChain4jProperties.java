package com.xiaoguo.guaiagent.langchain4j.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 独立 LangChain4j 实现的配置属性。
 */
@ConfigurationProperties(prefix = "langchain4j")
public class LangChain4jProperties {

    private boolean enabled;

    private String apiKey;

    private String modelName = "qwen-plus";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
