package Responses;

import Models.Categorie;

import java.util.List;

public class CategorieResponse {

    private String status;

    private List<Categorie> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Categorie> getData() {
        return data;
    }

    public void setData(List<Categorie> data) {
        this.data = data;
    }
}
