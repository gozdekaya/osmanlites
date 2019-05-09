package Adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import com.gozde.osmanlitapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import Models.Media;

public class MainHorzAdapter extends RecyclerView.Adapter<MainHorzAdapter.ViewHolder> {
    private List<Media> mediaList;
    private LayoutInflater inflater;

    public MainHorzAdapter(List<Media> mediaList, Context context) {
        this.mediaList = mediaList;
        this.mediaList.add(new Media("http://api.osmanli.app-xr.com/storage/osm-m3u8/index.m3u8", 2));
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.mainpage_cardview_horizontal, viewGroup, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MainHorzAdapter.ViewHolder viewHolder, int i) {
        Media myMedia = mediaList.get(i);
        viewHolder.setData(myMedia, i);
    }

    @Override
    public int getItemCount() {
        return mediaList.size();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        VideoView v = holder.itemView.findViewById(R.id.videoView);
        if (v != null)
            v.start();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        VideoView v = holder.itemView.findViewById(R.id.videoView);
        if (v != null)
            v.pause();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        VideoView videoView;
        FrameLayout placeholder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            videoView = itemView.findViewById(R.id.videoView);
            placeholder = itemView.findViewById(R.id.placeholder);
        }

        public void setData(Media selectedMedia, int position) {
            if (selectedMedia.getType() == 2) {
                videoView.setVisibility(View.VISIBLE);
                Uri uri = Uri.parse(selectedMedia.getUrl());
                //     Uri uri = Uri.parse("http://184.72.239.149/vod/smil:BigBuckBunny.smil/playlist.m3u8");
                videoView.setVideoURI(uri);
                videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        placeholder.setVisibility(View.INVISIBLE);

                    }
                });
                //videoView.start();
            } else {
                placeholder.setVisibility(View.INVISIBLE);
                imageView.setVisibility(View.VISIBLE);
                Picasso.get().load(Uri.parse(selectedMedia.getUrl())).into(imageView);
            }
        }
    }
}

