package com.hy.dto;

public class NoticeInfoDto {
    private Integer id;
    private String title;
    private String notifiedPerson;
    private String  content;
    private Integer creater;
    private String created;

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
        this.title = title;
    }

    public String getNotifiedPerson() {
        return notifiedPerson;
    }

    public void setNotifiedPerson(String notifiedPerson) {
        this.notifiedPerson = notifiedPerson;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getCreater() {
        return creater;
    }

    public void setCreater(Integer creater) {
        this.creater = creater;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
