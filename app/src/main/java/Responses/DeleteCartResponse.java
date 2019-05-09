package Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import Models.DeleteData;

public class DeleteCartResponse {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private DeleteData data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DeleteData getData() {
        return data;
    }

    public void setData(DeleteData data) {
        this.data = data;
    }
}
