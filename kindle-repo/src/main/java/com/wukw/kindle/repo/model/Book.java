package com.wukw.kindle.repo.model;

import java.util.Date;
import javax.persistence.*;

public class Book {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "local_url")
    private String localUrl;

    @Column(name = "origin_url")
    private String originUrl;

    /**
     * 类型 
     */
    private Integer type;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private String des;

    private String pic;

    private String author;

    /**
     * 获取id
     *
     * @return id - id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return local_url
     */
    public String getLocalUrl() {
        return localUrl;
    }

    /**
     * @param localUrl
     */
    public void setLocalUrl(String localUrl) {
        this.localUrl = localUrl;
    }

    /**
     * @return origin_url
     */
    public String getOriginUrl() {
        return originUrl;
    }

    /**
     * @param originUrl
     */
    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    /**
     * 获取类型 
     *
     * @return type - 类型 
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置类型 
     *
     * @param type 类型 
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return des
     */
    public String getDes() {
        return des;
    }

    /**
     * @param des
     */
    public void setDes(String des) {
        this.des = des;
    }

    /**
     * @return pic
     */
    public String getPic() {
        return pic;
    }

    /**
     * @param pic
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * @return author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author
     */
    public void setAuthor(String author) {
        this.author = author;
    }
}