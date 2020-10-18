package com.tools.module.blog.service;

import com.tools.common.model.Result;
import com.tools.module.blog.entity.AppBlog;

public interface AppBlogService {

    Result get(Long id);

    Result save(AppBlog blog);

    Result delete(Long id);

    Result list(AppBlog blog);

}
