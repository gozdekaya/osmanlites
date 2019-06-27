package Responses;

import android.content.ClipData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import Models.Item;
import Models.Siparis;

public class SiparisResponse {


    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private List<Siparis> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Siparis> getData() {
        return data;
    }

    public void setData(List<Siparis> data) {
        this.data = data;
    }
}
