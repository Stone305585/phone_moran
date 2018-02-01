package com.phone.moran.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.phone.moran.tools.SLogger;

import java.util.List;

/**
 * 首页pager的adapter
 * Created by stone on 2016/3/7.
 */
public class MainPagerAdapter extends FragmentPagerAdapter {
    String[] urlTags = null;
    List<Fragment> fragments;

    public MainPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments.size() != 0 && fragments.get(position) != null) {
            return fragments.get(position);
        } else
            return null;
    }

    @Override
    public int getCount() {
        return (fragments != null) ? fragments.size() : 0;
    }

}

