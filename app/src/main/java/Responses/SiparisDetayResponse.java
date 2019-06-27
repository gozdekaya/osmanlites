package Responses;

import Models.Siparis;

public class SiparisDetayResponse {
    String status;
    Siparis data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Siparis getData() {
        return data;
    }

    public void setData(Siparis data) {
        this.data = data;
    }
}
