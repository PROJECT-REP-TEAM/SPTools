package com.tools.module.app.web;

import com.overzealous.remark.Remark;
import com.tools.common.model.Result;
import com.tools.module.app.util.JsoupUtils;
import io.swagger.annotations.Api;
import org.jsoup.nodes.Document;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 博客
 * @author 小柒2012
 */
@Api(tags ="博客管理")
@RestController
@RequestMapping("app/blog")
public class BlogController {

    /**
     * 爬取
     * @param url
     * @return
     */
    @RequestMapping("crawl")
    public Result crawl(String url) {
        try {
            if(url.startsWith("https://www.cnblogs.com")){
                Document document = JsoupUtils.getDocument(url);
                String title = document.select("#cb_post_title_url").text();
                String content = document.select("#cnblogs_post_body").html();
                Remark remark = new Remark();
                String markdown = remark.convertFragment(content);
                System.out.println(title);
                System.out.println(markdown);
            }else{
                return Result.error("目前仅支持博客园");
            }
        } catch (Exception e) {
            return Result.error("抓取异常");
        }
        return Result.ok();
    }

    public static void main(String[] args) throws IOException {
        String url = "https://www.cnblogs.com/lifengdi/p/13827262.html";
        Document document = JsoupUtils.getDocument(url);
        String title = document.select("#cb_post_title_url").text();
        String content = document.select("#cnblogs_post_body").html();
        Remark remark = new Remark();
        String markdown = remark.convertFragment(content);
        System.out.println(title);
        System.out.println(markdown);
    }
}
