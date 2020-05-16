package com.example.android.quakereport;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Provides the appropriate {@link Fragment} for a view pager.
 */
/**
 * {@link EarthquakeFragmentPagerAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for
 * each list item based on a data source which is a list of {@link Earthquake} objects.
 */
public class EarthquakeFragmentPagerAdapter extends FragmentPagerAdapter {

    private String tabTitles[] = new String[] { "Tab1", "Tab2"};

    /** Context of the app */
    private Context mContext;

    /**
     * Create a new {@link EarthquakeFragmentPagerAdapter} object.
     *
     * @param fm is the fragment manager that will keep each fragment's state in the adapter
     *           across swipes.
     */
    public EarthquakeFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }


    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new EarthquakeFragment();
        } else{
            return new ScaleFragment();
        }
    }

    @Override
    public CharSequence getPageTitle (int position) {
        // Generate title based on item position
        if (position == 0) {
            return mContext.getString(R.string.category_earthquakes);
        } else {
            return mContext.getString(R.string.category_levels);
        }
//        return super.getPageTitle(position);
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 2;
    }
}

