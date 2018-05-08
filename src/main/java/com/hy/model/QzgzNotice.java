package com.hy.model;

public class QzgzNotice {
    private Integer id;
    private String title;
    private String content;
    private String nodifiedPerson;
    private Integer creater;
    private String created;
    private Integer modifier;
    private String modified;
    private Integer state;
    private Integer countPage;

    public QzgzNotice(Integer id ,String title, Integer creater, String created, String content, String nodifiedPerson,Integer modifier) {
        this.id=id;
        this.title = title;
        this.creater = creater;
        this.created = created;
        this.content = content;
        this.nodifiedPerson=nodifiedPerson;
        this.modifier=modifier;
    }
    public QzgzNotice(){}

    public String getNodifiedPerson() {
        return nodifiedPerson;
    }

    public void setNodifiedPerson(String nodifiedPerson) {
        this.nodifiedPerson = nodifiedPerson;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content =content;
    }

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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCountPage() {
        return countPage;
    }

    public void setCountPage(Integer countPage) {
        this.countPage = countPage;
    }
}

