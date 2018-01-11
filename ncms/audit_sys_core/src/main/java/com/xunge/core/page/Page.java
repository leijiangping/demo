package com.xunge.core.page;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 分页
 * @author: SongJL
 * Update: 2017-6-6
 */
public class Page<E> implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = -5970911398845316275L;
	
	private int pageNum;//第几页
    private int pageSize;//每页记录数
    private int startRow;
    private int endRow;
    private long total;//总记录数 
    private int pages;// 总页数
    private List<E> result;//结果集 
    private List<?> resultAny;//结果集 

    public Page(int pageNum, int pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.startRow = pageNum > 0 ? (pageNum - 1) * pageSize : 0;
        this.endRow = pageNum * pageSize;
    }
    /**
	 * activity 使用分页时使用
	 * SQL:LIMIT “getFirstResult” OFFSET getPageSize
	 */
	public int getFirstResult(){
		int firstResult = (pageNum - 1) * getPageSize();
		if (firstResult >= getTotal()) {
			firstResult = 0;
		}
		return firstResult;
	}
	
	public Page<?> cloneNoResult(Page<?> page2){ 
		Page<E> page1 = new Page<>(page2.getPageNum(), page2.getPageSize());
		page1.setTotal(page2.getTotal());
		page1.setPages(page2.getPages());
		return page1;
	}
	
    public List<E> getResult() {
        return result;
    }

    public void setResult(List<E> result) {
        this.result = result;
    }

    public List<?> getResultAny() {
        return resultAny;
    }
    public void setResultAny(List<?> resultAny) {
        this.resultAny = resultAny;
    }


    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Page{" +
                "pageNum=" + pageNum +
                ", pageSize=" + pageSize +
                ", startRow=" + startRow +
                ", endRow=" + endRow +
                ", total=" + total +
                ", pages=" + pages +
                '}';
    }
}
