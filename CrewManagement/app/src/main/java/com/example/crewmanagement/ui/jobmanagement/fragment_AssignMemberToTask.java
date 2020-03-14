package com.example.crewmanagement.ui.jobmanagement;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.crewmanagement.R;


public class fragment_AssignMemberToTask extends Fragment {

    View view = null;

    public fragment_AssignMemberToTask() {
        // Required empty public constructor
    }

    public static fragment_AssignMemberToTask newInstance() {
        fragment_AssignMemberToTask fragment = new fragment_AssignMemberToTask();
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
        view = inflater.inflate(R.layout.fragment_add_member_to_task, container, false);
        return view;
    }
}
