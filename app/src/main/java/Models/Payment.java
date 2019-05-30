package Models;

public class Payment {
   Card card;
   String shippingAddressId;
   String billingAddressId;

    public Payment(Card card, String shippingAddressId, String billingAddressId) {
        this.card = card;
        this.shippingAddressId = shippingAddressId;
        this.billingAddressId = billingAddressId;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public String getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(String shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public String getBillingAddressId() {
        return billingAddressId;
    }

    public void setBillingAddressId(String billingAddressId) {
        this.billingAddressId = billingAddressId;
    }
}
