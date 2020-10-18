package com.tools.module.blog.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.blog.entity.AppBlog;
import com.tools.module.blog.entity.AppBlogCrawl;
import com.tools.module.blog.repository.AppBlogCrawlRepository;
import com.tools.module.blog.repository.AppBlogRepository;
import com.tools.module.blog.service.AppBlogCrawlService;
import com.tools.module.blog.service.AppBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppBlogCrawlServiceImpl implements AppBlogCrawlService {

    @Autowired
    private DynamicQuery dynamicQuery;
    @Autowired
    private AppBlogCrawlRepository blogCrawlRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(AppBlogCrawl crawl) {
        crawl.setGmtCreate(DateUtils.getTimestamp());
        crawl.setGmtModified(DateUtils.getTimestamp());
        blogCrawlRepository.saveAndFlush(crawl);
        return Result.ok("保存成功");
    }

    @Override
    public Result get(Long id) {
        AppBlogCrawl crawl = blogCrawlRepository.getOne(id);
        return Result.ok(crawl);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        blogCrawlRepository.deleteById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result list(AppBlogCrawl crawl) {
        String nativeSql = "SELECT COUNT(*) FROM app_blog_crawl";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<AppBlogCrawl> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM app_blog_crawl ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(crawl.getPageNo(),crawl.getPageSize());
            List<AppBlogCrawl> list =
                    dynamicQuery.nativeQueryPagingList(AppBlogCrawl.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }
}
