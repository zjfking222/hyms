package com.hy.dto;

public class NoticeInfoDto {
    private Integer id;
    private String title;
    private String nodifiedPerson;
    private String  content;
    private Integer creater;
    private String created;
    private Integer modifier;
    private String modified;

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

    public String getNodifiedPerson() {
        return nodifiedPerson;
    }

    public void setNodifiedPerson(String notifiedPerson) {
        this.nodifiedPerson = notifiedPerson;
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

    public Integer getModifier() {
        return modifier;
    }

    public void setModifier(Integer modifier) {
        this.modifier = modifier;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }
}
