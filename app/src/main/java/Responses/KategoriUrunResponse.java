package Responses;

import Models.KategoriUrun;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KategoriUrunResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private KategoriUrun data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public KategoriUrun getData() {
        return data;
    }

    public void setData(KategoriUrun data) {
        this.data = data;
    }
}