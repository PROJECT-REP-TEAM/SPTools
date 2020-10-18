package com.tools.module.blog.service.impl;

import com.tools.common.dynamicquery.DynamicQuery;
import com.tools.common.model.PageBean;
import com.tools.common.model.Result;
import com.tools.common.util.DateUtils;
import com.tools.module.blog.entity.AppBlog;
import com.tools.module.blog.entity.AppBlogCategory;
import com.tools.module.blog.repository.AppBlogCategoryRepository;
import com.tools.module.blog.service.AppBlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppBlogCategoryServiceImpl implements AppBlogCategoryService {

    @Autowired
    private DynamicQuery dynamicQuery;
    @Autowired
    private AppBlogCategoryRepository blogTypeRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result save(AppBlogCategory category) {
        category.setGmtCreate(DateUtils.getTimestamp());
        category.setGmtModified(DateUtils.getTimestamp());
        blogTypeRepository.saveAndFlush(category);
        return Result.ok("保存成功");
    }

    @Override
    public Result get(Long id) {
        AppBlogCategory type = blogTypeRepository.getOne(id);
        return Result.ok(type);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result delete(Long id) {
        blogTypeRepository.deleteById(id);
        return Result.ok("删除成功");
    }

    @Override
    public Result list(AppBlogCategory category) {
        String nativeSql = "SELECT COUNT(*) FROM app_blog_category";
        Long count = dynamicQuery.nativeQueryCount(nativeSql);
        PageBean<AppBlogCategory> data = new PageBean<>();
        if(count>0){
            nativeSql = "SELECT * FROM app_blog_category ORDER BY gmt_create desc";
            Pageable pageable = PageRequest.of(category.getPageNo(),category.getPageSize());
            List<AppBlogCategory> list =
                    dynamicQuery.nativeQueryPagingList(AppBlogCategory.class,pageable,nativeSql);
            data = new PageBean(list,count);
        }
        return Result.ok(data);
    }
}
