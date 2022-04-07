package com.weilaios.iqxceqhnhubt.utils;

import org.apache.commons.lang3.ObjectUtils;

import java.util.Objects;

/**
 * 未来OS 数据分页信息类
 */
public class WlosPagination {

    private static final long serialVersionUID = 1L;
    public static final int PAGE_SIZE = 15;
    /**
     * 当前页码
     */
    private Integer current_page = 1;
    /**
     * 条数
     */
    private Integer page_size = 15;
    /**
     * 总条数
     */
    private Integer total_record = 0;
    /**
     * 总页数
     */
    private Integer total_page = 0;
    /**
     * 是否有上一页
     */
    private Boolean hasPre;
    /**
     * 是否有下一页
     */
    private Boolean hasNext;
    /**
     * 是否是首页
     */
    private Boolean isFirst;
    /**
     * 是否是尾页
     */
    private Boolean isLast;
    /**
     * 是否分页
     */
    private boolean page_able;


    public boolean isPage_able() {
        return this.page_able;
    }

    public void setPage_able(boolean page_able) {
        this.page_able = page_able;
    }

    public Integer getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(Integer current_page) {
        this.current_page = current_page;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }

    public Integer getTotal_page() {
        return this.total_page;
    }

    public void setTotal_page(Integer total_page) {
        this.total_page = total_page;
    }

    public Integer getTotal_record() {
        return this.total_record;
    }

    public void setTotal_record(Integer totalRecord) {

        this.total_record = totalRecord;
        if (Objects.equals(current_page, 0) || ObjectUtils.isEmpty(current_page)) {
            this.current_page = 1;

        }
        if (Objects.equals(page_size, 0) || ObjectUtils.isEmpty(page_size)) {
            this.page_size = 15;
        }

        int totalPage = totalRecord % this.page_size == 0 ? totalRecord / this.page_size : totalRecord / this.page_size + 1;
        this.total_page = totalPage;
    }

    public Boolean getHasPre() {
        return hasPre;
    }

    public void setHasPre(Boolean hasPre) {
        this.hasPre = hasPre;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }

    public Boolean getFirst() {
        return isFirst;
    }

    public void setFirst(Boolean first) {
        isFirst = first;
    }

    public Boolean getLast() {
        return isLast;
    }

    public void setLast(Boolean last) {
        isLast = last;
    }

    @Override
    public String toString() {
        return "WlosPagination{" +
                "current_page=" + current_page +
                ", page_size=" + page_size +
                ", total_record=" + total_record +
                ", total_page=" + total_page +
                ", hasPre=" + hasPre +
                ", hasNext=" + hasNext +
                ", isFirst=" + isFirst +
                ", isLast=" + isLast +
                ", page_able=" + page_able +
                '}';
    }
}
