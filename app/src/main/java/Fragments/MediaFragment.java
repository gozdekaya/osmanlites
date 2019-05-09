package Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import com.gozde.osmanlitapp.R;
import com.squareup.picasso.Picasso;

import Models.Media;

public class MediaFragment extends Fragment {
    private String url;
    private int type;
    private VideoView videoView;


    public static MediaFragment newInstance(Media media){
        MediaFragment myFragment = new MediaFragment();
        Bundle args = new Bundle();
        args.putString("url",media.getUrl());
        args.putInt("type",media.getType());
        myFragment.setArguments(args);
        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.url=getArguments() != null ? getArguments().getString("url") : null;
        this.type=getArguments() !=null ? getArguments().getInt("type") : 0;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.layout_slider,container,false);

        if (type==2){
            videoView=view.findViewById(R.id.video_slide);
            if (videoView!=null){
                videoView.setVisibility(View.VISIBLE);
                Uri uri =Uri.parse(this.url);
                videoView.setVideoURI(uri);
                if (getUserVisibleHint()) videoView.start();
                //this.setUserVisibleHint(true);

            }
        }else {
            ImageView imageView = view.findViewById(R.id.image_slide);
            imageView.setVisibility(View.VISIBLE);
            Picasso.get().load(Uri.parse(url)).into(imageView);
        }
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (type==2 && this.isVisible()){

            if (isVisibleToUser){

                videoView.start();
            }
            else {
                videoView.pause();
            }

        }
    }
}
