package Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

import Adapters.CreditCardAdapter;
import Models.cardDetails;
import Responses.CreditCardResponse;
import Utils.ApiClient;
import Utils.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentCreditCards extends Fragment {
    List<cardDetails> cards=new ArrayList<>();
    ProgressBar mProgressBar;
    RecyclerView recyclerView;
    ImageButton back;
    Button addcard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_credit_cards,container,false);
        back=(ImageButton)view.findViewById(R.id.back);
        mProgressBar=(ProgressBar)view.findViewById(R.id.progressBar1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        addcard=(Button)view.findViewById(R.id.add_card);
        addcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentDialogKartEkle()).commit();
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_kart);
        recyclerView.setLayoutManager(layoutManager);
        mProgressBar.setVisibility(View.VISIBLE);
        ApiInterface apiInterface= ApiClient.getInstance(getContext()).getApi();
        Call<CreditCardResponse> call=apiInterface.kartlarim("Bearer " + SharedPrefManager.getInstance(getContext()).getToken(), "application/json");
        call.enqueue(new Callback<CreditCardResponse>() {
            @Override
            public void onResponse(Call<CreditCardResponse> call, Response<CreditCardResponse> response) {

                cards=response.body().getCardDetails();
                mProgressBar.setVisibility(View.GONE);
                CreditCardAdapter adapter=new CreditCardAdapter(cards);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<CreditCardResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });



        return view;
    }
}
