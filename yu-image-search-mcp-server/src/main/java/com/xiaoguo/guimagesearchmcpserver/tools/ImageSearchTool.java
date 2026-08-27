package com.xiaoguo.guimagesearchmcpserver.tools;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 调用 Pexels API 搜索图片。
 */
@Service
public class ImageSearchTool {

    private static final String API_URL = "https://api.pexels.com/v1/search";
    private final String apiKey = System.getenv().getOrDefault("PEXELS_API_KEY", "");

    public String searchImage(String query) {
        if (StrUtil.isBlank(apiKey)) {
            throw new IllegalStateException("请配置环境变量 PEXELS_API_KEY");
        }
        return String.join(",", searchMediumImages(query));
    }

    public List<String> searchMediumImages(String query) {
        Map<String, String> headers = Map.of("Authorization", apiKey);
        Map<String, Object> params = new HashMap<>();
        params.put("query", query);
        params.put("per_page", 5);

        String response = HttpUtil.createGet(API_URL)
                .addHeaders(headers)
                .form(params)
                .execute()
                .body();

        return JSONUtil.parseObj(response)
                .getJSONArray("photos")
                .stream()
                .map(JSONObject.class::cast)
                .map(photo -> photo.getJSONObject("src").getStr("medium"))
                .filter(StrUtil::isNotBlank)
                .toList();
    }
}
