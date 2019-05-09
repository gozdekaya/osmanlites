package Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DeleteData {

    @SerializedName("shoppingCards")
    @Expose
    private List<Product> shoppingCards ;
    @SerializedName("totalPrice")
    @Expose
    private String totalPrice;

    public List<Product> getShoppingCards() {
        return shoppingCards;
    }

    public void setShoppingCards(List<Product> shoppingCards) {
        this.shoppingCards = shoppingCards;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }


}
