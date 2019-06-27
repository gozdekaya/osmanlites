package Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.Field;

public class DataSepet {
    @SerializedName("shoppingCards")
   private List<Cart> cartList;
    private String totalPrice;
    int totalCount;

    public List<Cart> getCartList() {
        return cartList;
    }

    public void setCartList(List<Cart> cartList) {
        this.cartList = cartList;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
