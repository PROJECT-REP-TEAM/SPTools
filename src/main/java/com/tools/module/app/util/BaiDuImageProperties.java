package com.tools.module.app.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 百度智能AI
 * @author 小柒2012
 */
@Data
@ConfigurationProperties(prefix = "bai-du.image")
public class BaiDuImageProperties {

    private String url;
    private String authUrl;
    private String appId;
    private String apiKey;
    private String accessKeySecret;

}
