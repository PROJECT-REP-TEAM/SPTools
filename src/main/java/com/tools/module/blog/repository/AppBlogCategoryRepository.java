package com.tools.module.blog.repository;

import com.tools.module.blog.entity.AppBlogCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppBlogCategoryRepository extends JpaRepository<AppBlogCategory, Long> {

}