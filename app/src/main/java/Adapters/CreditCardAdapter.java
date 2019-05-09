package Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;

import java.util.List;

import Models.cardDetails;

public class CreditCardAdapter extends RecyclerView.Adapter<CreditCardAdapter.ViewHolder> {
  private List<cardDetails> cards;


    public CreditCardAdapter(List<cardDetails> cards) {
        this.cards = cards;
    }

    @NonNull
    @Override
    public CreditCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.credit_card_cardview,viewGroup,false);
        return new CreditCardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreditCardAdapter.ViewHolder viewHolder, int i) {
        String kname = cards.get(i).getCardAlias();
        String num =  cards.get(i).getBinNumber();
         viewHolder.name.setText(kname + "\n "+ num);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=(TextView)itemView.findViewById(R.id.name_card);
        }
    }
}
