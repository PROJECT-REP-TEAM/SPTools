package com.tools.module.blog.repository;

import com.tools.module.blog.entity.AppBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppBlogRepository extends JpaRepository<AppBlog, Long> {

}