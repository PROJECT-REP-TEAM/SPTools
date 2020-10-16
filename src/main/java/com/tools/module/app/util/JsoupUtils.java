package com.tools.module.app.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * 工具类
 */
public class JsoupUtils {

    /**
     * 获取 document
     * @param url
     * @return
     * @throws IOException
     */
    public static Document getDocument(String url) throws IOException {
        Document document = Jsoup.connect(url)
                .timeout(100000)
                .ignoreContentType(true)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36")
                .get();
        return document;
    }

}
