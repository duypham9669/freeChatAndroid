package com.example.chatapp2.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.chatapp2.R;
import com.example.chatapp2.frag1;
import com.example.chatapp2.frag2;
import com.example.chatapp2.frag3;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment= new frag1();
                break;
            case 1:
                fragment=new frag2();
                break;
            case 2:
                fragment=new frag3();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
    switch (position) {
        case 0:
            return "Tin nhắn";
        case 1:
            return "Bạn bè";
        case 2:
            return "Thông tin";
    }
    return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 3;
    }
}