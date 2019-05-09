package Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import Models.Product;

public class UrunDetayResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Product data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Product getData() {
        return data;
    }

    public void setData(Product data) {
        this.data = data;
    }
}
