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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;

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
    private ListView recyclerView;
    ArrayList<String> names= new ArrayList<>();
    ArrayList<String> images= new ArrayList<>();
    ArrayList<String> names1= new ArrayList<>();
    ArrayList<String> images1= new ArrayList<>();
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_search,container,false);
        recyclerView=(ListView) view.findViewById(R.id.search_recyclerview);
        EditText editText=view.findViewById(R.id.search);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_kesfet);
        recyclerView.setLayoutManager(layoutManager);
        KesfetAdapter adapter = new KesfetAdapter(images,names);
        recyclerView.setAdapter(adapter);

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
        RecyclerView recyclerView1 = view.findViewById(R.id.recycler_display);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.searchview_menu,menu);
        final SearchView searchView=(SearchView)menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager=(SearchManager)getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                        Call<SearchResponse> call=ApiClient.getInstance(getContext()).getApi().urunara("application/json",s);
                        call.enqueue(new Callback<SearchResponse>() {
                            @Override
                            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                                List<Product> items = (List<Product>) response.body();
                                SearchAdapter searchAdapter= new SearchAdapter(getContext(),items);
                                recyclerView.setAdapter(searchAdapter);

                            }

                            @Override
                            public void onFailure(Call<SearchResponse> call, Throwable t) {
                               t.printStackTrace();
                            }
                        });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }
}
