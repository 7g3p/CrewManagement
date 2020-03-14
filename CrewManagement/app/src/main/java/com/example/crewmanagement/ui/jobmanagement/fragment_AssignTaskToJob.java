package com.example.crewmanagement.ui.jobmanagement;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.crewmanagement.R;


public class fragment_AssignTaskToJob extends Fragment {

    View view = null;
    Spinner spnr_JobList;
    Spinner spnr_TaskList;

    SpinnerAdapter spnr_JobAdapter;
    SpinnerAdapter spnr_TaskAdapter;

    public fragment_AssignTaskToJob() {
        // Required empty public constructor
    }


    public static fragment_AssignTaskToJob newInstance() {
        fragment_AssignTaskToJob fragment = new fragment_AssignTaskToJob();
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
        view = inflater.inflate(R.layout.fragment_add_task_to_job, container, false);
        


        return view;
    }

    public interface MyAddTaskListener {
        public void TaskToJob(Bundle args);
    }
}
