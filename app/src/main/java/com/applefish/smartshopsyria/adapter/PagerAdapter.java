package com.applefish.smartshopsyria.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.applefish.smartshopsyria.fragments.LatestFragment;
import com.applefish.smartshopsyria.fragments.MallOffersFragment;
import com.applefish.smartshopsyria.fragments.SearchFragment;
import com.applefish.smartshopsyria.fragments.StoresFragment;

/**
 * Created by Ghiath on 1/6/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.numOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                LatestFragment tab1 = new LatestFragment();
                return tab1;
            case 1:
                MallOffersFragment tab2 = new MallOffersFragment();
                return tab2;
            case 2:
                SearchFragment tab3 = new SearchFragment();

                return tab3;
            case 3:
               StoresFragment tab4 = new StoresFragment();

                return tab4;
            default:
                return null;
        }
    }


    public int getItemPosition(Object item) {
            return POSITION_NONE;
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

}
