package com.tools.module.blog.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.blog.entity.AppBlog;
import com.tools.module.blog.repository.AppBlogRepository;
import com.tools.module.blog.service.AppBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppBlogServiceImpl implements AppBlogService {

    @Autowired
    private DynamicQuery dynamicQuery;
    @Autowired
    private AppBlogRepository blogRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(AppBlog blog) {
        blog.setGmtCreate(DateUtils.getTimestamp());
        blog.setGmtModified(DateUtils.getTimestamp());
        blogRepository.saveAndFlush(blog);
        return Result.ok("保存成功");
    }

    @Override
    public Result get(Long id) {
        AppBlog blog = blogRepository.getOne(id);
        return Result.ok(blog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        blogRepository.deleteById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result list(AppBlog blog) {
        String nativeSql = "SELECT COUNT(*) FROM app_blog";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<AppBlog> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM app_blog ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(blog.getPageNo(),blog.getPageSize());
            List<AppBlog> list =  dynamicQuery.nativeQueryPagingList(AppBlog.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }
}
