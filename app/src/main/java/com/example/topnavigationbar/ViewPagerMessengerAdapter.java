package com.example.topnavigationbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerMessengerAdapter extends FragmentPagerAdapter {
    public ViewPagerMessengerAdapter( FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new PhotoFragment();
        } else if (position == 1) {
            return new VideoFragment();
        } else if (position==2) {
            return new saveFragment();
        }else {
            return new MusicsFragment();
        }
    }

    @Override
    public int getCount() {return 4;}


    @Override
    public CharSequence getPageTitle(int position) {if (position == 0) {return "Photo";} else if (position == 1) {return "Video";} else if (position==2){return "Save";}else {return "Music";}}
}