package Responses;

import Models.Cart;

public class AddCartResponse {
    private String status;
    private Cart data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Cart getData() {
        return data;
    }

    public void setData(Cart data) {
        this.data = data;
    }
}
