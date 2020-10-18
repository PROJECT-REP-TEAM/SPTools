package com.tools.module.blog.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tools.common.model.PageBean;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 博客分类
 */
@Data
@Entity
@Table(name = "app_blog_category")
public class AppBlogCategory extends PageBean implements Serializable {
   /**
    * 自增主键
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 20)
    private Long id;
    /**
    * 代码
    */
    @Column(name = "code")
    private String code;
    /**
    * 名称
    */
    @Column(name = "name")
    private String name;
    /**
    * 创建时间
    */
    @Column(name = "gmt_create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp gmtCreate;
    /**
    * 修改时间
    */
    @Column(name = "gmt_modified")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp gmtModified;
}