package Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gozde.osmanlitapp.R;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.List;

import Adapters.ExploreAdapter;
import Models.Cart;
import Models.Product;
import Responses.ProductResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentKesfet extends Fragment implements DiscreteScrollView.OnItemChangedListener,View.OnClickListener{
 private List<Product> products;
 Cart mCart;
 Product mProducts;
 ProgressBar mProgressBar;

 ImageButton buy;
Context mContext;
ExploreAdapter adapter;
    Boolean isConnected = false;
private DiscreteScrollView itemPicker;
InfiniteScrollAdapter scrollAdapter;
    @SuppressLint("WrongViewCast")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_kesfet,container,false);
        buy=(ImageButton) view.findViewById(R.id.button_buy);


    /*    display=(Button)view.findViewById(R.id.button_display);
        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentUrunDetay()).commit();
            }
        });*/
        itemPicker=(DiscreteScrollView)view.findViewById(R.id.picker);
        itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
        mProgressBar=(ProgressBar)view.findViewById(R.id.progressBar1);
        mProgressBar.setVisibility(View.VISIBLE);
        itemPicker.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

            }
        });

        if (isConnected){
            Call<ProductResponse> call= ApiClient.getInstance(mContext).getApi().urunler("application/json");
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {

                    mProgressBar.setVisibility(View.GONE);
                    products=response.body().getData().getProducts();

                    adapter=new ExploreAdapter(products);
                    scrollAdapter=InfiniteScrollAdapter.wrap(adapter);

                    itemPicker.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.8f).build());
                    itemPicker.setAdapter(scrollAdapter);
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    Log.d("failure",t.getMessage().toString());
                }
            });
        }else {
            Toast.makeText(mContext, "internet bağlantını kontrol et", Toast.LENGTH_LONG).show();
        }
        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        if (checkConnection()) isConnected = true;
        else isConnected = false;
    }


    private boolean checkConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (!(networkInfo != null && networkInfo.isConnected() ))
        {
            return false;
            //
        } else return true;

    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

    }



}
