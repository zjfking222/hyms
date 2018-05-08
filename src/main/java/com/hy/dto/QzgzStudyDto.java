package com.hy.dto;

import java.util.Date;

/**
 * 衢州公众号学习园地Dto
 */
public class QzgzStudyDto {
    private Integer id;

    private String title;

    private String content;

    private String creatername;

    private Date created;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getCreatername() {
        return creatername;
    }

    public void setCreatername(String name) {
        this.creatername = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}