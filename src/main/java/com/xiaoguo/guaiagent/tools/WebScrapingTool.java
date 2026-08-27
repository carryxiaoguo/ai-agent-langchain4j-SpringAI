package com.xiaoguo.guaiagent.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

/**
 * 网页抓取工具
 */
public class WebScrapingTool {

    @Tool("Scrape the content of a web page")
    public String scrapeWebPage(@P("URL of the web page to scrape") String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document.html();
        } catch (Exception e) {
            return "Error scraping web page: " + e.getMessage();
        }
    }
}
