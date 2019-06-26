package Fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import Adapters.KategoriAdapter;
import Adapters.MainPageAdapter;
import Models.Data;
import Responses.CategorieResponse;
import Responses.ProductResponse;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import Models.Categorie;
import Models.Product;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;
;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {
    CheckBox cbfav;
    RelativeLayout katrel;
    ProgressBar mProgress;
    Data data,dataek;
    String nextPage;
    List<Product> datanm;
    private List<Categorie> mKategoriler ;
    KategoriAdapter kategoriAdapter;
    RecyclerView recyclerView1;
    Boolean isConnected = false;
    public int pageNumber;
    MainPageAdapter adapter;
    private ArrayList<Product> mProducts = new ArrayList<>();
    private View currentFocusedLayout, oldFocusedLayout;
    ApiClient client;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    Context mContext;
    public FragmentHome(){}
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         View view= inflater.inflate(R.layout.fragment_home,container,false);




        mProgress = (ProgressBar)view.findViewById(R.id.progressBar1);
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
           recyclerView=(RecyclerView)view.findViewById(R.id.katrecycler);
            katrel=(RelativeLayout)view.findViewById(R.id.relative_kategori);
            recyclerView1=view.findViewById(R.id.recycler_mainpage);


            mProgress.setVisibility(View.VISIBLE);
        ApiInterface apiInterface=ApiClient.getInstance(this.mContext).getApi();
        String bearer= SharedPrefManager.getInstance(getContext()).getToken();
        if (SharedPrefManager.getInstance(mContext).isLoggedIn()){
            Call<ProductResponse> call =apiInterface.urunlogin("Bearer " +bearer,"application/json");
            call.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    data=response.body().getData();


                    nextPage=(String) response.body().getData().getPagination().getNext_page_url();

                    adapter=new MainPageAdapter(data.getProducts(),mContext);
                    recyclerView1.setAdapter(adapter);
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setItemViewCacheSize(15);
                    mProgress.setVisibility(View.GONE);

                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });}else {
            Call<ProductResponse>call1=apiInterface.urunler("application/json");
            call1.enqueue(new Callback<ProductResponse>() {
                @Override
                public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                    data=response.body().getData();


                    nextPage=(String) response.body().getData().getPagination().getNext_page_url();

                    adapter=new MainPageAdapter(data.getProducts(),mContext);
                    recyclerView1.setAdapter(adapter);
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setItemViewCacheSize(15);
                    mProgress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<ProductResponse> call, Throwable t) {
                   t.printStackTrace();
                }
            });
        }


              final LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext);
              layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
              layoutManager1.setItemPrefetchEnabled(true);
              recyclerView1.setLayoutManager(layoutManager1);
              recyclerView1.setHasFixedSize(true);
              recyclerView1.setRecycledViewPool(new RecyclerView.RecycledViewPool());
              recyclerView.setNestedScrollingEnabled(false);
              recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
                  @Override
                  public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                      super.onScrolled(recyclerView, dx, dy);
                      LinearLayoutManager layoutManager2 =(LinearLayoutManager)recyclerView1.getLayoutManager();
                      int total = layoutManager2.getItemCount();
                      int currentLastItem = layoutManager2.findLastVisibleItemPosition();
                      if (currentLastItem==total - 1){
                          pageNumber++;
                          if (pageNumber<28)
                              requestNextPage();
                      }

                      int completelyVisibleItemPosition = layoutManager1.findFirstCompletelyVisibleItemPosition();
                      int firstVisibleItemPosition = layoutManager1.findFirstVisibleItemPosition();
                      int lastVisibleItemPosition = layoutManager1.findLastVisibleItemPosition();

                      View completelyView = layoutManager1.findViewByPosition(completelyVisibleItemPosition);
                      View firstVisibleView = layoutManager1.findViewByPosition(firstVisibleItemPosition);
                      View lastVisibleView = layoutManager1.findViewByPosition(lastVisibleItemPosition);

                      if (firstVisibleView != null){
                          if (layoutManager1.isViewPartiallyVisible(firstVisibleView, false, false)) {
                              VideoView fv = firstVisibleView.findViewById(R.id.videoView);
                              if (fv != null) fv.pause();
                          }
                          if (firstVisibleItemPosition >= 2){
                              katrel.setVisibility(View.GONE);

                          }else if (firstVisibleItemPosition < 1){
                              katrel.setVisibility(View.VISIBLE);
                          }
                      }
                      if (lastVisibleView != null){
                          if (layoutManager1.isViewPartiallyVisible(lastVisibleView, false, false)) {
                              VideoView lv = lastVisibleView.findViewById(R.id.videoView);
                              if (lv != null) lv.pause();
                          }
                      }
                      if (completelyView != null){
                          VideoView cv = completelyView.findViewById(R.id.videoView);
                          if (cv != null)
                              cv.start();
                      }
                  }
              });

         return view;

    }

    public void requestNextPage(){
        ApiInterface apiInterface =ApiClient.getInstance(mContext).getApi();
        Call<ProductResponse> call = apiInterface.urunler("application/json");
        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
               if (response.body() != null){
                   dataek=response.body().getData();
                   adapter.addMore(dataek.getProducts());
               }else {
                   Log.d("r_error", response.errorBody().toString());
               }

            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        new BackgroundTask().execute();

    }


    @Override
    public void onResume() {
        super.onResume();
        pageNumber = 1;
        new BackgroundTask().execute();
    }

    private boolean checkConnection(){

        ConnectivityManager connectivityManager = (ConnectivityManager) this.mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


        if (!(networkInfo != null && networkInfo.isConnected()))
        {
            return false;
            //
        } else return true;

    }
/*
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            return !ipAddr.equals("");

        } catch (Exception e) {
            return false;
        }
    }
*/
   private class BackgroundTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {

                InetAddress ipAddr = InetAddress.getByName("google.com");
                //You can replace it with your name
                Boolean result  = !ipAddr.equals("");
                if (checkConnection() && result) isConnected = true;
                else isConnected = false;
                if (isConnected){
                    client = ApiClient.getInstance(mContext);
                    Call<CategorieResponse> call=client.getApi().kategoriler("application/json");
                    call.enqueue(new Callback<CategorieResponse>() {
                        @Override
                        public void onResponse(Call<CategorieResponse> call, Response<CategorieResponse> response) {
                            mKategoriler=response.body().getData();

                            kategoriAdapter=new KategoriAdapter(mKategoriler,mContext);
                            recyclerView.setAdapter(kategoriAdapter);
                            recyclerView.setLayoutManager(layoutManager);
                        }

                        @Override
                        public void onFailure(Call<CategorieResponse> call, Throwable t) {

                        }
                    });
                }else {
                    Toast.makeText(mContext, "internet bağlantını kontrol et", Toast.LENGTH_LONG).show();
                }
                return result;

            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Boolean result) {
            super.onCancelled(result);
        }

    }


}
