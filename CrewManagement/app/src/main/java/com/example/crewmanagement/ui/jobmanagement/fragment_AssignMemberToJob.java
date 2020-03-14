package com.example.crewmanagement.ui.jobmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crewmanagement.R;


public class fragment_AssignMemberToJob extends Fragment {

    View view = null;

    public fragment_AssignMemberToJob() {
        // Required empty public constructor
    }


    public static fragment_AssignMemberToJob newInstance() {
        fragment_AssignMemberToJob fragment = new fragment_AssignMemberToJob();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_add_member_to_job, container, false);
        return view;
    }
}
