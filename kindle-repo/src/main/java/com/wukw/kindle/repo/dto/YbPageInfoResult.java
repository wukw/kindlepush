package com.wukw.kindle.repo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 持久层查询结果带出分页信息
 * @param <T>
 */
@Data
public class YbPageInfoResult<T> implements Serializable {

    /**
     * 当前页码
     */
    private int pageNum;

    /**
     * 总页码
     */
    private int pageTotalNum;

    /**
     * 总记录数
     */
    private Long totalNum;

    /**
     * 查询结果
     */
    private T result;
}
