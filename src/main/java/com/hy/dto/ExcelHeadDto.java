package com.hy.dto;

/**
 * @Auther: 钱敏杰
 * @Date: 2019/1/31 15:05
 * @Description:Excel标题列对象
 */
public class ExcelHeadDto {

    //当前列对应的数据的字段名称
    private String key;
    //当前列的标题名称
    private String name;
    //当前列的序号
    private int sort;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
