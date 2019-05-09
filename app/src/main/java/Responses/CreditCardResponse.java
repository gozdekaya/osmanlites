package Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import Models.cardDetails;

public class CreditCardResponse {


    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("cardDetails")
    @Expose
    private List<cardDetails> cardDetails ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<cardDetails> getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(List<cardDetails> cardDetails) {
        this.cardDetails = cardDetails;
    }
}
