package com.tools.module.app.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import com.alibaba.fastjson.JSONObject;
import com.baidu.aip.speech.AipSpeech;
import com.baidu.aip.speech.TtsResponse;
import com.baidu.aip.util.Base64Util;
import com.baidu.aip.util.Util;
import com.tools.common.constant.SystemConstant;
import com.tools.common.util.FileUtils;
import com.tools.common.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * 百度智能AI
 * @author 小柒2012
 */
@Component
@Configuration
@EnableConfigurationProperties({BaiDuProperties.class,BaiDuImageProperties.class})
public class BaiDuUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(BaiDuUtils.class);

    private BaiDuProperties baiDu;

    private BaiDuImageProperties baiDuImage;

    public BaiDuUtils(BaiDuProperties baiDu,BaiDuImageProperties baiDuImage) {
        this.baiDu = baiDu;
        this.baiDuImage = baiDuImage;
    }

    private AipSpeech instance;

    @Value("${file.path}")
    private String filePath;

    @PostConstruct
    public void init() {
        try {
            FileUtil.mkdir(filePath+SystemConstant.SF_FILE_SEPARATOR+"voice");
            instance = new AipSpeech(baiDu.getAppId(), baiDu.getApiKey(), baiDu.getAccessKeySecret());
            // 可选：设置网络连接参数
            instance.setConnectionTimeoutInMillis(2000);
            instance.setSocketTimeoutInMillis(60000);
        } catch (Exception e) {
            LOGGER.error("百度智能AI初始化失败,{}", e.getMessage());
        }
    }

    /**
     * 语音合成
     * 本地测试可能会出现https认证的问题 调用一下 ignoreSsl 方法即可
     * @param text 合成的文本，使用UTF-8编码，请注意文本长度必须小于1024字节
     */
    public String text2Voice(String text,Boolean per) {
        SslUtils.ignoreSsl();
        HashMap<String, Object> options = new HashMap<>();
        if(per){
            options.put("per", "3");
        }else{
            options.put("per", "4");
        }
        TtsResponse res = instance.synthesis(text, "zh", 1, options);
        byte[] data = res.getData();
        if (data != null) {
            try {
                String file = "voice"+SystemConstant.SF_FILE_SEPARATOR+UUID.randomUUID()+".mp3";
                Util.writeBytesToFileSystem(data, filePath + SystemConstant.SF_FILE_SEPARATOR + file);
                return file;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取车牌
     * @param filePath
     * @return
     */
    public String licensePlate(String filePath){
        String url = baiDuImage.getUrl();
        try {
            // 本地文件路径
            byte[] imgData = FileUtils.readFileByBytes(filePath);
            String imgStr = Base64Util.encode(imgData);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");
            String param = "image=" + imgParam +"&accessToken="+getAccessToken();
            String result = HttpUtils.post(url,param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
     * @return
     * @throws Exception
     */
    public String getAccessToken() throws Exception {
        String authUrl = baiDuImage.getAuthUrl();
        String param = "grant_type=client_credentials"
                        + "&client_id=" + baiDuImage.getApiKey()
                        + "&client_secret=" + baiDuImage.getAccessKeySecret();
        String result = HttpUtils.post(authUrl,param);
        JSONObject json = JSONObject.parseObject(result);
        String accessToken = json.getString("access_token");
        return accessToken;
    }

    public static void main(String[] args) throws Exception {
        String authHost = "https://aip.baidubce.com/oauth/2.0/token";
        String param =
                // 1. grant_type为固定参数
                "grant_type=client_credentials"
                // 2. 官网获取的 API Key
                + "&client_id=*****"
                // 3. 官网获取的 Secret Key
                + "&client_secret=*****";
        String result = HttpUtils.post(authHost,param);
        System.out.println(result);
        JSONObject json = JSONObject.parseObject(result);
        String access_token = json.getString("access_token");
        System.out.println(access_token);
        // 本地文件路径
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/license_plate";
        byte[] imgData = FileUtils.readFileByBytes("D:\\car.png");
        String imgStr = Base64Util.encode(imgData);
        String imgParam = URLEncoder.encode(imgStr, "UTF-8");
        param = "image=" + imgParam +"&accessToken="+access_token;
        result = HttpUtils.post(url,param);
        System.out.println(result);
    }
}
