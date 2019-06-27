package Utils;

import com.google.gson.JsonObject;

import Responses.AddCartResponse;
import Responses.AddCreditCardResponse;
import Responses.AdresDetayResponse;
import Responses.AdresResponse;
import Responses.CartResponse;
import Responses.CategorieResponse;
import Responses.CountryResponse;
import Responses.CreditCardResponse;
import Responses.DeleteCartResponse;
import Responses.DisProductResponse;
import Responses.FavoriResponse;
import Responses.KategoriUrunResponse;
import Responses.LikeResponse;
import Responses.LoginResponse;
import Responses.PayJson;
import Responses.ProductResponse;
import Responses.RegisterResponse;
import Responses.SearchResponse;
import Responses.SiparisDetayResponse;
import Responses.SiparisResponse;
import Responses.UrunDetayResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    // LOGIN ICIN
    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> loginUser(
            @Header("Accept") String accept,
            @Field("email") String email,
            @Field("password") String password

    );

    //REGISTER ICIN
    @FormUrlEncoded
    @POST("register")
    Call<RegisterResponse> registerUser(
            @Header("Accept") String accept,
            @Field("name") String name,
            @Field("surname") String surname,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone") String phone,
            @Field("identityNumber") String identityNumber

    );
    //ADDRESS EKLE ICIN
    @FormUrlEncoded
    @POST ("addresses")
    Call<AdresDetayResponse> adresEkle(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept,
            @Field("title") String title,
            @Field("description") String description,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("postCode") String post,
            @Field("city") String city,
            @Field("town") String town,
            @Field("countryId") int countryid,
            @Field("isDefault") int deafault


    );

    @FormUrlEncoded
    @PUT ("addresses/{addressId}")
    Call<AdresDetayResponse> adresGuncelle(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept,
            @Field("title") String title,
            @Field("description") String description,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("postCode") String post,
            @Field("city") String city,
            @Field("town") String town,
            @Field("countryId") int countryid,
            @Field("isDefault") int deafault,
            @Path("addressId") String addressid


    );

    //ADRESLERİM

  @GET ("addresses")
    Call<AdresResponse> adreslerim(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept
  );

  @GET("addresses/{addressId}")
    Call<AdresDetayResponse>adresgor(
          @Header("Authorization") String authorization,
          @Header("Accept") String accept,
          @Path("addressId") String addressid
  );

  @GET("categories")

    Call<CategorieResponse> kategoriler(
          @Header("Accept") String accept
  );


  @GET("categories/{categoryId}")
    Call<KategoriUrunResponse> kategoriUrunler(
          @Header("Accept") String accept,
            @Path("categoryId") int pathNo
  );

  //ULKE
   @GET("countries")
    Call<CountryResponse> ulkeler(
           @Header("Accept") String accept
   );

   @GET("products")
    Call<ProductResponse> urunler(
           @Header("Accept") String accept
   );
@GET("products/{productId}")
    Call<UrunDetayResponse> urundetay(
        @Header("Accept") String accept,
        @Path("productId") String productId
);
@GET("products/{productId}")
Call<UrunDetayResponse>urundetaylogin(
        @Header("Authorization") String authorization,
        @Header("Accept") String accept,
        @Path("productId") String productId
);
    @GET("products")
    Call<ProductResponse>urunlogin(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept

    );

//Sepet

    @GET("shopping-cards")
    Call<CartResponse> sepeturunler(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept
    );

    @FormUrlEncoded
    @POST("shopping-cards")
    Call<AddCartResponse> sepeteekle(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept,
            @Field("productId") String productId,
            @Field("count") int count

    );

    //Kredi Kartı

    @GET("ipay-cards")
    Call<CreditCardResponse> kartlarim(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept
    );
    @FormUrlEncoded
    @POST("ipay-cards")
    Call<AddCreditCardResponse> kartekle(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept,
            @Field("cardAlias") String cardAlias,
            @Field("holderName") String holderName,
            @Field("cardNumber") String cardNumber,
            @Field("expireMonth") String expireMonth,
            @Field("expireYear") String expireYear
    );

//SEPETTEN URUN SİL
@DELETE("shopping-cards/{productId}")
    Call<DeleteCartResponse> urunsil(
        @Header("Authorization") String authorization,
        @Header("Accept") String accept,
        @Path("productId") String productId
);

    //SEARCH
    @FormUrlEncoded
    @POST("search-product")
    Call<SearchResponse> urunara(
            @Header("Accept") String accept,
            @Field("keyword") String keyword
    );

    @POST("payment")
    Call<PayJson> postRawJSON(
    @Header("Authorization") String authorization,
    @Header("Accept") String accept,
    @Body JsonObject PaymentJson

    );

    //popular urun
    @GET("popular-products")
    Call<ProductResponse> populerurunler(
            @Header("Accept") String accept
    );

    @GET("discounted-products")
    Call<ProductResponse> indurun(
            @Header("Accept") String accept
    );

    //Like
    @GET("products/{productId}/like")
    Call<LikeResponse> urunbegen(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept,
            @Path("productId") String productId
    );

    //Favoriler
    @GET("favorites")
    Call<FavoriResponse> favoriler(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept
    );

    //Siparislerim
    @GET("orders")
    Call<SiparisResponse> orders(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept
    );

    //Siparis Detay
    @GET("orders/{orderId}")
    Call<SiparisDetayResponse> siparisdetay(
            @Header("Authorization") String authorization,
            @Header("Accept") String accept,
            @Path("orderId") String orderId
    );
}
