package Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import Fragments.MediaFragment;
import Models.Media;

public class SliderAdapter extends FragmentStatePagerAdapter {

    private List<Media> images;

    public SliderAdapter(FragmentManager fm, List<Media> images) {
        super(fm);
        this.images = images;
        this.images.add(new Media("http://api.osmanli.app-xr.com/storage/osm-m3u8/index.m3u8", 2));
    }

    @Override
    public Fragment getItem(int i) {
        return MediaFragment.newInstance(images.get(i));
    }

    @Override
    public int getCount() {
        return images.size();
    }
}
