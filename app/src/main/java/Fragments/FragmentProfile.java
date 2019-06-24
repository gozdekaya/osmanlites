package Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gozde.osmanlitapp.R;
import com.gozde.osmanlitapp.SharedPrefManager;

public class FragmentProfile extends Fragment {
    TextView siparisler, adresler, ayarlar, cikisyap, card;
    Context mContext;
    ImageView goback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ayarlar = (TextView) view.findViewById(R.id.settings);
        goback=(ImageView)view.findViewById(R.id.back);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentAyarlar()).commit();
            }
        });
        ayarlar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentProfilAyar()).addToBackStack(null).commit();
            }
        });
        adresler = (TextView) view.findViewById(R.id.address);
        adresler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentAdresler()).addToBackStack(null).commit();
            }
        });
        card = (TextView) view.findViewById(R.id.card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentCreditCards()).addToBackStack(null).commit();
            }
        });

        siparisler = (TextView) view.findViewById(R.id.orders);
        siparisler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new FragmentSiparisler()).addToBackStack(null).commit();
            }
        });


        cikisyap = (TextView) view.findViewById(R.id.logout);
        cikisyap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(new ContextThemeWrapper(mContext, R.style.AlertDialogCustom));

                adb.setTitle(R.string.cikis_yapilsin_mi);
                adb.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logout();
                    }
                });
                adb.setNegativeButton(R.string.iptal, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                adb.show();

                // logout();
            }
        });

        return view;
    }

    private void logout() {
        SharedPrefManager.getInstance(getActivity()).clear();
        getFragmentManager().beginTransaction().replace(R.id.container, new FragmentHome()).commit();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;


    }

}
