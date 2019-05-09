package Fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import Adapters.CartAdapter;
import Models.DataSepet;
import Responses.CartResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCart extends Fragment {
    Context mContext;
    TextView bosText;
    TextView totalprice;
    DataSepet items;
    Boolean isConnected = false;
    RecyclerView recyclerView;
    CartAdapter adapter;
    ProgressBar mProgressBar;
    Button button,startshop;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        adapter = new CartAdapter();
        adapter.setUpdIns(()->{
            getUpdatedItems();
        });
        startshop=view.findViewById(R.id.startshop);
        bosText = view.findViewById(R.id.bosText);
       recyclerView = view.findViewById(R.id.recycler_sepet);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(false);
        totalprice=view.findViewById(R.id.total);
        button=(Button)view.findViewById(R.id.button_payment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentPayment()).commit();
            }
        });
        mProgressBar=(ProgressBar)view.findViewById(R.id.progressBar1);
        mProgressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
        getUpdatedItems();
         startshop.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 getFragmentManager().beginTransaction().replace(R.id.container,new FragmentHome()).commit();
             }
         });

        return view;
    }

    private void getUpdatedItems() {
        if (isConnected){ Call<CartResponse> call = ApiClient
                .getInstance(mContext).getApi().sepeturunler("Bearer " + SharedPrefManager.getInstance(mContext).getToken(), "application/json");
            call.enqueue(new Callback<CartResponse>() {
                @Override
                public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                    Log.d("msg", String.valueOf(response.body()));
                      mProgressBar.setVisibility(View.GONE);
                      button.setVisibility(View.VISIBLE);
                    if (response.errorBody() == null) {
                        items = response.body().getData();
                        if (items.getCartList().size() == 0) {
                            bosText.setVisibility(View.VISIBLE);
                            startshop.setVisibility(View.VISIBLE);
                            totalprice.setVisibility(View.INVISIBLE);
                            button.setVisibility(View.INVISIBLE);
                        } else {
                            bosText.setVisibility(View.INVISIBLE);
                        }
                        adapter.setItems(items.getCartList());
                        adapter.notifyDataSetChanged();
                        totalprice.setText("Toplam: "+items.getTotalPrice());

                        recyclerView.setAdapter(adapter);
                    } else {
                        button.setVisibility(View.GONE);
                        Toast.makeText(mContext, "Sepeti görmek için giriş yapmalısınız.", Toast.LENGTH_LONG).show();
                    }


                }


                @Override
                public void onFailure(Call<CartResponse> call, Throwable t) {
                    t.printStackTrace();
                    Log.d("err", "err");
                }
            });
        }else {
            Toast.makeText(mContext, "Lütfen internet bağlantınızı kontrol edin ", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext=context;
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


}
