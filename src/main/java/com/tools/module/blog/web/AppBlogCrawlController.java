package com.tools.module.blog.web;

import com.tools.common.config.AbstractController;
import com.tools.common.model.Result;
import com.tools.module.blog.entity.AppBlogCrawl;
import com.tools.module.blog.service.AppBlogCrawlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/blog/crawl")
public class AppBlogCrawlController extends AbstractController {

    @Autowired
    private AppBlogCrawlService blogCrawlService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public Result list(AppBlogCrawl crawl){
        return blogCrawlService.list(crawl);
    }

    /**
     * 查询
     */
    @PostMapping("/get")
    public Result get(Long id){
        return blogCrawlService.get(id);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody AppBlogCrawl crawl){
        return blogCrawlService.save(crawl);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long id){
        return blogCrawlService.delete(id);
    }
}
