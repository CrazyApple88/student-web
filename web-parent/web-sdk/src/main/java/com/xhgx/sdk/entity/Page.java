package com.xhgx.sdk.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName Page
 * @Description 分页信息
 * @author zohan(inlw@sina.com)
 * @date 2017-03-21 8:58
 * @vresion 1.0
 */
public class Page implements Serializable, Cloneable {
    
	/**数据总页数*/
    private int pageTotal;
    /**数据条数*/
    private int pageCount;
    /**当前页，默认第一页 */
    private int pageNo = 1;
    /**每页多少条，默认10条*/
    private int pageSize = 10;
    /**返回的对象数据*/
    private List<?> result = new ArrayList();

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<?> getResult() {
        return result;
    }

    public void setResult(List<?> result) {
        this.result = result;
    }

	public int getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}
}
