package Responses;

import java.util.List;

import Models.Product;

public class SearchResponse {
    String status;
    List<Product> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}
