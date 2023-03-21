package com.modern.model;

import java.util.List;

public class ResultDTO {
    private List<UserDTO> data;
    private int page;


    private int perPage;
    private int total;
    private int totalPages;

    public ResultDTO() {
        super();
    }

    public ResultDTO(int page, int perPage, int total, int totalPages, List<UserDTO> data) {
        this.page = page;
        this.perPage = perPage;
        this.total = total;
        this.totalPages = totalPages;
        this.data = data;
    }


    public List<UserDTO> getData() {
        return data;
    }

    public void setData(List<UserDTO> data) {
        this.data = data;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<UserDTO> getUsers() {
        return getData();
    }


}
