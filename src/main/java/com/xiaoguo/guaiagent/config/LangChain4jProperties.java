package com.xiaoguo.guaiagent.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * LangChain4j 运行参数。
 */
@ConfigurationProperties(prefix = "langchain4j")
public class LangChain4jProperties {

    private boolean enabled;
    private String apiKey;
    private String modelName = "deepseek-v4-flash-0731";
    private int memoryMessages = 20;
    private final Mcp mcp = new Mcp();

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

    public int getMemoryMessages() {
        return memoryMessages;
    }

    public void setMemoryMessages(int memoryMessages) {
        this.memoryMessages = memoryMessages;
    }

    public Mcp getMcp() {
        return mcp;
    }

    public static class Mcp {
        private boolean enabled;
        private String imageSseUrl = "http://localhost:8127/sse";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getImageSseUrl() {
            return imageSseUrl;
        }

        public void setImageSseUrl(String imageSseUrl) {
            this.imageSseUrl = imageSseUrl;
        }
    }
}
