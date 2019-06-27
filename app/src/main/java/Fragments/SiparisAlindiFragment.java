package Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.gozde.osmanlitapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SiparisAlindiFragment extends Fragment {
Button buton;

    public SiparisAlindiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_siparis_alindi,container,false);
        buton=view.findViewById(R.id.gohome);
        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container,new FragmentHome()).commit();
            }
        });
        return view;
    }

}
