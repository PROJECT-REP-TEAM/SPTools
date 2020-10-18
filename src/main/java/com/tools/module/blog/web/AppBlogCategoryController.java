package com.tools.module.blog.web;

import com.tools.common.config.AbstractController;
import com.tools.common.model.Result;
import com.tools.module.blog.entity.AppBlogCategory;
import com.tools.module.blog.service.AppBlogCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/blog/categories")
public class AppBlogCategoryController extends AbstractController {

    @Autowired
    private AppBlogCategoryService blogTypeService;

    /**
     * 列表
     */
    @PostMapping("/list")
    public Result list(AppBlogCategory category){
        return blogTypeService.list(category);
    }

    /**
     * 查询
     */
    @PostMapping("/get")
    public Result get(Long id){
        return blogTypeService.get(id);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public Result save(@RequestBody AppBlogCategory category){
        return blogTypeService.save(category);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public Result delete(Long id){
        return blogTypeService.delete(id);
    }

}
