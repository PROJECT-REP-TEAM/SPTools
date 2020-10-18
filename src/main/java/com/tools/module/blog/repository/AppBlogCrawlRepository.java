package com.tools.module.blog.repository;

import com.tools.module.blog.entity.AppBlogCrawl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppBlogCrawlRepository extends JpaRepository<AppBlogCrawl, Long> {

}