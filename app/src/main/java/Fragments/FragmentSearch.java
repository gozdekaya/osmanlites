package Fragments;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.gozde.osmanlitapp.ProductMediaType;
import com.gozde.osmanlitapp.R;
import com.yarolegovich.discretescrollview.DSVOrientation;
import com.yarolegovich.discretescrollview.DiscreteScrollView;
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter;
import com.yarolegovich.discretescrollview.transform.ScaleTransformer;

import java.util.ArrayList;
import java.util.List;

import Adapters.ExploreAdapter;
import Adapters.KategoriAdapter;
import Adapters.KesfetAdapter;
import Adapters.RecentAdapter;
import Adapters.SearchAdapter;
import Adapters.SearchDiscreteAdapter;
import Models.Categorie;
import Models.DisProduct;
import Models.Product;
import Responses.CategorieResponse;
import Responses.DisProductResponse;
import Responses.ProductResponse;
import Responses.SearchResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearch extends Fragment {
    private RecyclerView recyclerViews;
    LinearLayoutManager layoutManager1;
    RecyclerView recyclerView;
    RecyclerView recyclerView1;
    KategoriAdapter kategoriAdapter;
    private LinearLayoutManager layoutManager;
    SearchAdapter searchAdapter;
    List<Product> items;
    TextView popular, lastviews, noproduct;
    ImageButton searchbutton;
    Context mContext;
    String keyword;
    KesfetAdapter adapterk;
    SearchDiscreteAdapter discreteAdapter;
    private DiscreteScrollView itemPicker;
    InfiniteScrollAdapter scrollAdapter;
    private List<Product> products;
    ApiClient client;
    RecyclerView recyclerViewkat;
    private List<DisProduct> disProducts;
    private List<Categorie> mKategoriler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        itemPicker = (DiscreteScrollView) view.findViewById(R.id.picker);
        layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewkat = view.findViewById(R.id.recycler_kat);
        itemPicker.setOrientation(DSVOrientation.HORIZONTAL);
        itemPicker.addOnItemChangedListener(new DiscreteScrollView.OnItemChangedListener<RecyclerView.ViewHolder>() {
            @Override
            public void onCurrentItemChanged(@Nullable RecyclerView.ViewHolder viewHolder, int adapterPosition) {

            }
        });
//Populer ürünler
        Call<ProductResponse> call2 = ApiClient.getInstance(mContext).getApi().populerurunler("application/json");
        call2.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                products = response.body().getData().getProducts();
                layoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recyclerView = view.findViewById(R.id.recycler_kesfet);
                recyclerView.setLayoutManager(layoutManager1);
                adapterk = new KesfetAdapter(products);
                recyclerView.setAdapter(adapterk);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                t.printStackTrace();
                Log.d("err", "err");
            }
        });

//Kategorileri getir
        client = ApiClient.getInstance(mContext);

        Call<CategorieResponse> call = client.getApi().kategoriler("application/json");
        call.enqueue(new Callback<CategorieResponse>() {
            @Override
            public void onResponse(Call<CategorieResponse> call, Response<CategorieResponse> response) {
                mKategoriler = response.body().getData();

                kategoriAdapter = new KategoriAdapter(mKategoriler, mContext);
                recyclerViewkat.setAdapter(kategoriAdapter);
                recyclerViewkat.setLayoutManager(layoutManager);
            }

            @Override
            public void onFailure(Call<CategorieResponse> call, Throwable t) {

            }
        });

//Discrete Ürünleri getir
        Call<ProductResponse> call1 = ApiClient.getInstance(mContext).getApi().urunler("application/json");
        call1.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {


                products = response.body().getData().getProducts();

                discreteAdapter = new SearchDiscreteAdapter(products);
                scrollAdapter = InfiniteScrollAdapter.wrap(discreteAdapter);

                itemPicker.setItemTransformer(new ScaleTransformer.Builder().setMinScale(0.6f).build());
                itemPicker.setAdapter(scrollAdapter);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {
                Log.d("failure", t.getMessage());
            }
        });


//Arama Kısmı

        recyclerViews = (RecyclerView) view.findViewById(R.id.search_recyclerview);
        EditText editText = view.findViewById(R.id.search);
        noproduct = view.findViewById(R.id.noproduct);
        popular = view.findViewById(R.id.popular);
        lastviews = view.findViewById(R.id.lastviews);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerViews.setLayoutManager(manager);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                keyword = editText.getText().toString();
                if (keyword.length() > 3) {
                    Call<SearchResponse> call = ApiClient.getInstance(getContext()).getApi().urunara("application/json", keyword);
                    call.enqueue(new Callback<SearchResponse>() {
                        @Override
                        public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                            items = response.body().getData();
                            if (items.size() != 0) {
                                recyclerViewkat.setVisibility(View.GONE);
                                itemPicker.setVisibility(View.GONE);
                                recyclerViews.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                                recyclerView1.setVisibility(View.GONE);
                                popular.setVisibility(View.GONE);
                                lastviews.setVisibility(View.GONE);
                                searchAdapter = new SearchAdapter(items);
                                recyclerViews.setAdapter(searchAdapter);

                            } else {

                                recyclerViews.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.GONE);
                                recyclerView1.setVisibility(View.GONE);
                                popular.setVisibility(View.GONE);
                                lastviews.setVisibility(View.GONE);
                                noproduct.setVisibility(View.VISIBLE);
                            }

                        }

                        @Override
                        public void onFailure(Call<SearchResponse> call, Throwable t) {
                            t.printStackTrace();
                        }
                    });
                } else {
                    noproduct.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (keyword.length() == 0) {
                    recyclerViewkat.setVisibility(View.VISIBLE);
                    itemPicker.setVisibility(View.VISIBLE);
                    recyclerViews.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    recyclerView1.setVisibility(View.VISIBLE);
                    popular.setVisibility(View.VISIBLE);
                    lastviews.setVisibility(View.VISIBLE);

                }
            }
        });
//İndirimli ürünleri görüntüle
        Call<ProductResponse> call3 = ApiClient.getInstance(mContext).getApi().indurun("application/json");
        call3.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(Call<ProductResponse> call, Response<ProductResponse> response) {
                products = response.body().getData().getProducts();
                LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
                recyclerView1 = view.findViewById(R.id.recycler_display);
                recyclerView1.setLayoutManager(layoutManager1);
                RecentAdapter adapter1 = new RecentAdapter(products);
                recyclerView1.setAdapter(adapter1);
            }

            @Override
            public void onFailure(Call<ProductResponse> call, Throwable t) {

            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;


    }


}
