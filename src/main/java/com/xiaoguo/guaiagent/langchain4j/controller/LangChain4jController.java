package com.xiaoguo.guaiagent.langchain4j.controller;

import com.xiaoguo.guaiagent.langchain4j.app.LangChain4jLoveApp;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用于对比 LangChain4j 与现有 Spring AI 接口的独立接口。
 */
@RestController
@RequestMapping("/ai/langchain4j")
@ConditionalOnBean(LangChain4jLoveApp.class)
public class LangChain4jController {

    private final LangChain4jLoveApp loveApp;

    public LangChain4jController(LangChain4jLoveApp loveApp) {
        this.loveApp = loveApp;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        // 该接口只用于对比，不改变原有 Spring AI 接口。
        return loveApp.chat(message);
    }
}
