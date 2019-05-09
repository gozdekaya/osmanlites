package Responses;

import java.util.List;

import Models.Cart;
import Models.DataSepet;

public class CartResponse {
    private String status;
    DataSepet data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataSepet getData() {
        return data;
    }

    public void setData(DataSepet data) {
        this.data = data;
    }
}
