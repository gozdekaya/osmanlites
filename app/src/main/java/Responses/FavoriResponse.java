package Responses;

import java.util.List;

import Models.Favori;

public class FavoriResponse {
    String status;
    List<Favori> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Favori> getData() {
        return data;
    }

    public void setData(List<Favori> data) {
        this.data = data;
    }
}
