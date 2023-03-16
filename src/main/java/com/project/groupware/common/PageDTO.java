package com.project.groupware.common;

public class PageDTO {

    private int pageStart;
    private int pageEnd;
    private boolean next, prev;
    private int total;
    private Criteria cri;


    public PageDTO(Criteria cri, int total) {


        this.cri = cri;
        this.total = total;

        this.pageEnd = (int) (Math.ceil(cri.getPageNum() / 10.0)) * 10;

        this.pageStart = this.pageEnd - 9;

        int realEnd = (int) (Math.ceil(total * 1.0 / cri.getAmount()));

        if (realEnd < pageEnd) {
            this.pageEnd = realEnd;
        }

        this.prev = this.pageStart > 1;

        this.next = this.pageEnd < realEnd;
    }

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart(int pageStart) {
        this.pageStart = pageStart;
    }

    public int getPageEnd() {
        return pageEnd;
    }

    public void setPageEnd(int pageEnd) {
        this.pageEnd = pageEnd;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public boolean isPrev() {
        return prev;
    }

    public void setPrev(boolean prev) {
        this.prev = prev;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Criteria getCri() {
        return cri;
    }

    public void setCri(Criteria cri) {
        this.cri = cri;
    }

    @Override
    public String toString() {
        return "PageDTO [pageStart=" + pageStart + ", pageEnd=" + pageEnd + ", next=" + next + ", prev=" + prev
                + ", total=" + total + ", cri=" + cri + "]";
    }
}
