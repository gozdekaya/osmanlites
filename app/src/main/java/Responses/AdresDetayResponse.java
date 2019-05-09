package Responses;

import Models.Address;

public class AdresDetayResponse {

    private String status;
    private Address data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Address getData() {
        return data;
    }

    public void setData(Address data) {
        this.data = data;
    }
}
