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

import com.gozde.osmanlitapp.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.KesfetAdapter;
import Adapters.RecentAdapter;
import Adapters.SearchAdapter;
import Models.Product;
import Responses.SearchResponse;
import Utils.ApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentSearch extends Fragment {
    private RecyclerView recyclerViews;
    RecyclerView recyclerView1;
    SearchAdapter searchAdapter;
    List<Product> items ;
    TextView popular,lastviews,noproduct;
    ImageButton searchbutton;
    ArrayList<String> names= new ArrayList<>();
    ArrayList<String> images= new ArrayList<>();
    ArrayList<String> names1= new ArrayList<>();
    ArrayList<String> images1= new ArrayList<>();
    Context mContext;
    String keyword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search,container,false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        recyclerViews=(RecyclerView) view.findViewById(R.id.search_recyclerview);
        EditText editText=view.findViewById(R.id.search);
        noproduct=view.findViewById(R.id.noproduct);
        popular=view.findViewById(R.id.popular);
        lastviews=view.findViewById(R.id.lastviews);
        LinearLayoutManager manager=new LinearLayoutManager(mContext);
        recyclerViews.setLayoutManager(manager);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_kesfet);
        recyclerView.setLayoutManager(layoutManager);
        KesfetAdapter adapter = new KesfetAdapter(images,names);
        recyclerView.setAdapter(adapter);
        searchbutton=view.findViewById(R.id.searchitem);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                 keyword =editText.getText().toString();
                if (keyword.length()>3){
                    Call<SearchResponse> call=ApiClient.getInstance(getContext()).getApi().urunara("application/json",keyword);
                    call.enqueue(new Callback<SearchResponse>() {
                        @Override
                        public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                            items=response.body().getData();
                         if (items.size()!=0){
                             recyclerViews.setVisibility(View.VISIBLE);
                             recyclerView.setVisibility(View.GONE);
                             recyclerView1.setVisibility(View.GONE);
                             popular.setVisibility(View.GONE);
                             lastviews.setVisibility(View.GONE);
                             searchAdapter = new SearchAdapter(items);
                             recyclerViews.setAdapter(searchAdapter);

                         }else {
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
                    });}else {
                    noproduct.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            if (keyword.length()==0){
                recyclerViews.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                recyclerView1.setVisibility(View.VISIBLE);
                popular.setVisibility(View.VISIBLE);
                lastviews.setVisibility(View.VISIBLE);

            }
            }
        });
        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        names.add("Kehribar Tesbih");
        images.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names.add("Kehribar Tesbih");
        images.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names.add("Kehribar Tesbih");
        images.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names.add("Kehribar Tesbih");
        images.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names.add("Kehribar Tesbih");
        images.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names.add("Kehribar Tesbih");
        images.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names.add("Kehribar Tesbih");
        images.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names.add("Kehribar Tesbih");
        images.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names.add("Kehribar Tesbih");
        images.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1 = view.findViewById(R.id.recycler_display);
        recyclerView1.setLayoutManager(layoutManager1);
        RecentAdapter adapter1 = new RecentAdapter(images,names);
        recyclerView1.setAdapter(adapter1);

        names1.add("Abanoz Tesbih");
        images1.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names1.add("Abanoz Tesbih");
        images1.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names1.add("Abanoz Tesbih");
        images1.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names1.add("Abanoz Tesbih");
        images1.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");
        names1.add("Abanoz Tesbih");
        images1.add("https://s.eticaretbox.com/2043/pictures/KYSTDPQNMT17201716417_Gumus-Nokta-Puskul-Surmeli-Sikma-Kehribar-Tesbih-2.jpg");

        return view;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;


    }


}
