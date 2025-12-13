package com.club.campusclubmanager.common;

import lombok.Data;

import java.util.List;

/**
 * 分页查询结果
 */
@Data
public class PageResult<T> {
    /**
     * 总记录数
     */
    private long total;

    /**
     * 每页记录数
     */
    private long size;

    /**
     * 当前页码
     */
    private long current;

    /**
     * 总页数
     */
    private long pages;

    /**
     * 记录列表
     */
    private List<T> records;
}