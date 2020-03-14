package com.example.crewmanagement.ui.jobmanagement;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.crewmanagement.R;


public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.create_Job, R.string.create_Task, R.string.addMemberToJob,
            R.string.addMemberToTask, R.string.addTaskToJob};

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        Fragment fragment = null;
        switch (position)
        {
            case 0:
                fragment = new fragment_CreateJob().newInstance();
                break;
            case 1:
                fragment = new fragment_CreateTask().newInstance();
                break;
            case 2:
                fragment = new fragment_AssignMemberToJob().newInstance();
                break;
            case 3:
                fragment = new fragment_AssignMemberToTask().newInstance();
                break;
            case 4:
                fragment = new fragment_AssignTaskToJob().newInstance();
                break;
            default:
                fragment = new fragment_AssignMemberToJob().newInstance();
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show number of total pages.
        return TAB_TITLES.length;
    }
}