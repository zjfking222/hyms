package com.hy.dto;

/**
 * @Auther: 沈超宇
 * @Date: 2018/12/22 15:40
 * @Description:bipicture表DTO
 */
public class BiPictureDto {
    private int id;
    private String title;
    private String url;
    private String path;
    private String type;

    public BiPictureDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
