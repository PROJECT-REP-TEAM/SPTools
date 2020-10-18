package com.tools.module.blog.web;

import com.overzealous.remark.Remark;
import com.tools.common.config.AbstractController;
import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.Result;
import com.tools.module.app.util.JsoupUtils;
import com.tools.module.blog.entity.AppBlog;
import com.tools.module.blog.entity.AppBlogCrawl;
import com.tools.module.blog.service.AppBlogService;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequestMapping("/blog/posts")
public class AppBlogController extends AbstractController {

    @Autowired
    private AppBlogService blogService;
    @Autowired
    private DynamicQuery dynamicQuery;

    /**
     * 列表
     */
    @PostMapping("/list")
    public Result list(AppBlog blog){
        return blogService.list(blog);
    }

    /**
     * 查询
     */
    @PostMapping("/get")
    public Result get(Long id){
        return blogService.get(id);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody AppBlog blog){
        return blogService.save(blog);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long id){
        return blogService.delete(id);
    }

    /**
     * 爬取
     * @param url
     * @return
     */
    @RequestMapping("crawl")
    public Result crawl(String url) {
        try {
            String domain = JsoupUtils.getDomain(url);
            String nativeSql = "SELECT * FROM app_blog_crawl WHERE url = ?";
            AppBlogCrawl crawl =
                    dynamicQuery.nativeQuerySingleResult(AppBlogCrawl.class,nativeSql,new Object[]{domain});
            if(crawl!=null){
                Document document = JsoupUtils.getDocument(url);
                String title = document.select(crawl.getTitle()).text();
                String content = document.select(crawl.getContent()).html();
                Remark remark = new Remark();
                String markdown = remark.convertFragment(content);
                AppBlog blog = new AppBlog();
                blog.setTitle(title);
                blog.setContent(markdown);
                blog.setUrl(url);
                return Result.ok(blog);
            }else{
                return Result.error("目前暂不支持此网站抓取");
            }
        } catch (Exception e) {
            return Result.error("抓取异常");
        }
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
        System.out.println(url.split("/")[0]+"//"+url.split("/")[2]);
    }
}
