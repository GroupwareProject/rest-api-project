package com.project.groupware.common;

public class Criteria {

    private int pageNum;
    private int amount;

    private String searchValue;

    public Criteria(){
        this(1, 10);
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    @Override
    public String toString() {
        return "Criteria{" +
                "pageNum=" + pageNum +
                ", amount=" + amount +
                ", searchValue='" + searchValue + '\'' +
                '}';
    }


}
