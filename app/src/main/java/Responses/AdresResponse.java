package Responses;

import Models.Address;

import java.util.List;

public class AdresResponse {

    private String status;
    private List<Address> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Address> getData() {
        return data;
    }

    public void setData(List<Address> data) {
        this.data = data;
    }
}
