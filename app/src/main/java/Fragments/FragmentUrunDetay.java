package Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import java.util.List;

import Adapters.SliderAdapter;
import Models.Cart;
import Models.Media;
import Models.Product;
import Responses.AddCartResponse;
import Responses.LikeResponse;
import Responses.UrunDetayResponse;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.facebook.FacebookSdk.getApplicationContext;

public class FragmentUrunDetay extends Fragment {

     TextView product_title,product_desc,product_price,addcart,dis_price,normalprice;
    ViewPager viewPager;
    SliderAdapter adapter;
    ProgressBar mProgressBar;
    TabLayout indicator;
    ImageButton backbutton;
    List<Media>images;
    Boolean isConnected = false;
   Context mContext;
   CheckBox cbfav;
   Boolean isFavorite;
   Cart mCart;
    LinearLayout layout;
   FragmentActivity fragmentActivity;
    private Snackbar snackbar;
    Product product;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view= inflater.inflate(R.layout.product_details,container,false);




normalprice=view.findViewById(R.id.normalprice);

        viewPager=(ViewPager)view.findViewById(R.id.slide_view);
        mProgressBar=(ProgressBar)view.findViewById(R.id.progressBar1);
        layout=(LinearLayout)view.findViewById(R.id.lin1);
        indicator=(TabLayout)view.findViewById(R.id.indicator);
        indicator.setupWithViewPager(viewPager, true);
        dis_price=view.findViewById(R.id.disprice);
        product_price=(TextView)view.findViewById(R.id.price);
        product_title=(TextView)view.findViewById(R.id.product_name);
        product_desc=(TextView)view.findViewById(R.id.aciklama_urun);
        backbutton=(ImageButton)view.findViewById(R.id.back);

        cbfav=(CheckBox)view.findViewById(R.id.checkBox1);

 cbfav.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
     @Override
     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

         if(SharedPrefManager.getInstance(mContext).isLoggedIn())
         {
             String bearer= SharedPrefManager.getInstance(mContext).getToken();
             String id = product.getId();

             Call<LikeResponse> call=ApiClient.getInstance(mContext).getApi().urunbegen("Bearer " +bearer,"application/json",id);
             call.enqueue(new Callback<LikeResponse>() {
                 @Override
                 public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {

                     if (response.body().getStatus().equals("success")) {
                         if (product.getIs_liked()) {
                             product.setIs_liked(false);
                             cbfav.setButtonDrawable(R.drawable.ic_favorite_black_24dp);
                         } else {
                             product.setIs_liked(true);
                             cbfav.setButtonDrawable(R.drawable.ic_favorite_red_24dp);
                         }
                     }

                 }

                 @Override
                 public void onFailure(Call<LikeResponse> call, Throwable t) {

                 }
             });


             //fragmentManager.beginTransaction().replace(R.id.container, new FragmentDialogSignup()).commit();

             //dialogSignup.show(getSupportFragmentManager(),"DialogSignup");

             //AppCompatActivity activity1 = (AppCompatActivity) buttonView.getContext();
             //activity1.getSupportFragmentManager().beginTransaction().replace(R.id.container,fragmentDialogSignup).addToBackStack(null).commit();
         }else {
             FragmentDialogSignup dialogSignup = new FragmentDialogSignup();
             dialogSignup.show(fragmentActivity.getSupportFragmentManager(),"DialogSignup");
         }
     }
 });


        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        addcart=(TextView)view.findViewById(R.id.add);
        addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart();
            }
        });

        final String id=getArguments().getString("ID",null);
        //final String title=getArguments().getString("title",null);
        //final String description=getArguments().getString("description",null);
        //final String media=getArguments().getString("media",null);

        mProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface = ApiClient.getInstance(mContext).getApi();

        if (SharedPrefManager.getInstance(mContext).isLoggedIn() ){
            String bearer= SharedPrefManager.getInstance(getContext()).getToken();
            Call<UrunDetayResponse> call=apiInterface.urundetaylogin("Bearer " +bearer,"application/json",id);
            call.enqueue(new Callback<UrunDetayResponse>() {
                @Override
                public void onResponse(Call<UrunDetayResponse> call, Response<UrunDetayResponse> response) {

                    product = response.body().getData();

                  if (product.getIs_liked()){
                      product.setIs_liked(true);
                        cbfav.setButtonDrawable(R.drawable.ic_favorite_red_24dp);

                    }else {
                      product.setIs_liked(false);
                        cbfav.setButtonDrawable(R.drawable.ic_favorite_black_24dp);
                    }
                    mProgressBar.setVisibility(View.GONE);

                          if (product.getDiscount()==null){
                              dis_price.setVisibility(View.GONE);
                              product_price.setVisibility(View.GONE);
                              normalprice.setText(product.getPrice());
                              normalprice.setVisibility(View.VISIBLE);
                          }else{
                              normalprice.setText(product.getPrice());
                             normalprice.setVisibility(View.GONE);
                              dis_price.setVisibility(View.VISIBLE);
                              product_price.setVisibility(View.VISIBLE);
                              product_price.setText(product.getPrice());
                              dis_price.setText(product.getDiscount().getDiscounted_price());
                          }






                    product_title.setText(product.getTitle());
                    product_desc.setText(product.getDescription());
                    images = product.getMedia();
                    viewPager.setAdapter(new SliderAdapter(getFragmentManager(), images));
                }

                @Override
                public void onFailure(Call<UrunDetayResponse> call, Throwable t) {
                 t.printStackTrace();
                }


            });

        }else {
            Call<UrunDetayResponse> call=apiInterface.urundetay("application/json",id) ;
            call.enqueue(new Callback<UrunDetayResponse>() {
                @Override
                public void onResponse(Call<UrunDetayResponse> call, Response<UrunDetayResponse> response) {
                    Product product = response.body().getData();

                    if (product.getDiscount()==null){
                        dis_price.setVisibility(View.GONE);
                        product_price.setVisibility(View.GONE);
                        normalprice.setText(product.getPrice());
                        normalprice.setVisibility(View.VISIBLE);
                    }else{
                        normalprice.setText(product.getPrice());
                        normalprice.setVisibility(View.GONE);
                        dis_price.setVisibility(View.VISIBLE);
                        product_price.setVisibility(View.VISIBLE);
                        product_price.setText(product.getPrice());
                        dis_price.setText(product.getDiscount().getDiscounted_price());
                    }
                    mProgressBar.setVisibility(View.GONE);
                    product_price.setText(product.getPrice());

                    product_title.setText(product.getTitle());
                    product_desc.setText(product.getDescription());
                    images = product.getMedia();
                    viewPager.setAdapter(new SliderAdapter(getFragmentManager(), images));
                }

                @Override
                public void onFailure(Call<UrunDetayResponse> call, Throwable t) {
                  t.printStackTrace();
                }
            });
            //Toast.makeText(mContext, R.string.internet_baglanti, Toast.LENGTH_LONG).show();
        }


        return view;

        }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.fragmentActivity = (FragmentActivity) context;

        if (checkConnection()) isConnected = true;
        else isConnected = false;
    }



    private boolean checkConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (!(networkInfo != null && networkInfo.isConnected() ))
        {
            return false;
            //
        } else return true;

    }
    private void addCart(){

        ApiInterface apiInterface=ApiClient.getInstance(mContext).getApi();
        String productId = getArguments().getString("ID",null);
        String bearer= SharedPrefManager.getInstance(mContext).getToken();
        int count=1;
        Call<AddCartResponse> call=apiInterface.sepeteekle("Bearer "+bearer,"application/json",productId,count);
        call.enqueue(new Callback<AddCartResponse>() {
            @Override
            public void onResponse(Call<AddCartResponse> call, Response<AddCartResponse> response) {
                if (SharedPrefManager.getInstance(getActivity()).isLoggedIn()){ snackbar = Snackbar.make(layout, "", Snackbar.LENGTH_LONG);
                    View view = snackbar.getView();
                    TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
                    tv.setTextColor(Color.parseColor("#2ecc71"));
                    tv.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_done_black_24dp, 0, 0, 0);
                    snackbar.setText(R.string.sepete_eklendi);
                    snackbar.show();}else {
                    Toast.makeText(mContext, R.string.sepete_urun_eklemek_icin, Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<AddCartResponse> call, Throwable t) {
t.printStackTrace();
            }
        });
    }
}

