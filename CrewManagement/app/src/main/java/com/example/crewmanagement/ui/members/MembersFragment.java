/*
 * FILE              MembersFragment.java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Marissa Schmitt
 * OTHER MEMBERS:    Alex MacCumber, David Obeda, Alex Palmer, Stephen Perrin
 * FIRST VERSION:    March 5th, 2020
 * DESCRIPTION:      This file describes the functionality and events of the members screen fragment.
 */
package com.example.crewmanagement.ui.members;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crewmanagement.R;

public class MembersFragment extends Fragment {

    private MembersViewModel mViewModel;

    public static MembersFragment newInstance() {
        return new MembersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.members_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MembersViewModel.class);
        // TODO: Use the ViewModel
    }

}
