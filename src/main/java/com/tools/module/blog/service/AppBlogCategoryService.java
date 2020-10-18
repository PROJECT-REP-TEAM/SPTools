package com.tools.module.blog.service;

import com.tools.common.model.Result;
import com.tools.module.blog.entity.AppBlogCategory;

public interface AppBlogCategoryService {

    Result get(Long id);

    Result save(AppBlogCategory category);

    Result delete(Long id);

    Result list(AppBlogCategory category);
}
