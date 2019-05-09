package Responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCreditCardResponse {


    @SerializedName("cardAlias")
    @Expose
    private String cardAlias;
    @SerializedName("binNumber")
    @Expose
    private String binNumber;
    @SerializedName("lastFourDigits")
    @Expose
    private String lastFourDigits;
    @SerializedName("cardType")
    @Expose
    private String cardType;
    @SerializedName("cardAssociation")
    @Expose
    private String cardAssociation;
    @SerializedName("cardFamily")
    @Expose
    private String cardFamily;
    @SerializedName("cardBankName")
    @Expose
    private String cardBankName;

    public String getCardAlias() {
        return cardAlias;
    }

    public void setCardAlias(String cardAlias) {
        this.cardAlias = cardAlias;
    }

    public String getBinNumber() {
        return binNumber;
    }

    public void setBinNumber(String binNumber) {
        this.binNumber = binNumber;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardAssociation() {
        return cardAssociation;
    }

    public void setCardAssociation(String cardAssociation) {
        this.cardAssociation = cardAssociation;
    }

    public String getCardFamily() {
        return cardFamily;
    }

    public void setCardFamily(String cardFamily) {
        this.cardFamily = cardFamily;
    }

    public String getCardBankName() {
        return cardBankName;
    }

    public void setCardBankName(String cardBankName) {
        this.cardBankName = cardBankName;
    }
}
