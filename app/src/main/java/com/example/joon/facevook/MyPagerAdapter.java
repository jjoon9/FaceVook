package com.example.joon.facevook;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import layout.ArticleListFragment;
import layout.FriendListFragment;


public class MyPagerAdapter extends FragmentStatePagerAdapter {

    private static final int PAGE_NUMBER = 2;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0: // 글목록
                return ArticleListFragment.newInstance();
            case 1: // 친구
                return FriendListFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount(){
       return PAGE_NUMBER;
    }
}
