package Responses;

import Models.Country;

import java.util.List;

public class CountryResponse {

    String status;
    List<Country> data;

    public CountryResponse(String status, List<Country> data) {
        this.status = status;
        this.data = data;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Country> getData() {
        return data;
    }

    public void setData(List<Country> data) {
        this.data = data;
    }
}
