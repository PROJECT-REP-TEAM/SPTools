package com.tools.module.blog.service;

import com.tools.common.model.Result;
import com.tools.module.blog.entity.AppBlog;
import com.tools.module.blog.entity.AppBlogCrawl;

public interface AppBlogCrawlService {

    Result get(Long id);

    Result save(AppBlogCrawl crawl);

    Result delete(Long id);

    Result list(AppBlogCrawl crawl);
}
