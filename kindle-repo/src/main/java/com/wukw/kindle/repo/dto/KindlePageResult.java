package com.wukw.kindle.repo.dto;

import lombok.Data;

@Data
public class KindlePageResult<T> {

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
