/*
 * FILE:             Fragment_AssignTaskToJob.Java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Alex MacCumber
 * OTHER MEMBERS:    Alex Palmer, David Obeda, Stephen Perrin, Marissa Schmitt
 * FIRST VERSION:    March 10th, 2020
 * DESCRIPTION:      This file describes the functionality of the AssignTaskToJob screen.
 */
package com.example.crewmanagement.ui.jobmanagement;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.crewmanagement.DBAdapter;
import com.example.crewmanagement.Data;
import com.example.crewmanagement.R;

import java.util.ArrayList;
import java.util.List;


public class fragment_AssignTaskToJob extends Fragment
    implements View.OnClickListener {

    View view = null;
    Spinner taskList;
    Spinner jobList;

    ArrayAdapter taskSpinnerAdapter;
    ArrayAdapter jobSpinnerAdapter;
    Button btn_addToTask;
    Button btn_clearForm;

    List<String> taskNames = new ArrayList<>();
    List<String> jobNames = new ArrayList<>();

    String selectedTask;
    String selectedJob;

    DBAdapter dbAdapter;
    Data data;

    MyAddTaskListener atl;

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

        dbAdapter = new DBAdapter(getContext());
        data = dbAdapter.GetData();

        // Load the Job List and the Member list with values
        LoadLists();
        taskSpinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, taskNames);
        jobSpinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, jobNames);
        taskList = (Spinner) view.findViewById(R.id.spnr_atjTaskList);
        jobList = (Spinner) view.findViewById(R.id.spnr_atjJobList);

        taskList.setAdapter(taskSpinnerAdapter);
        jobList.setAdapter(jobSpinnerAdapter);
        taskList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                Object item = parent.getItemAtPosition(pos);
                selectedTask = item.toString();
            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                // Do nothing
            }
        });


        jobList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                Object item = parent.getItemAtPosition(pos);
                selectedJob = item.toString();
            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                // Do nothing
            }
        });

        btn_clearForm = (Button)view.findViewById(R.id.btnClearSelections);
        btn_clearForm.setOnClickListener(this);
        btn_addToTask = (Button)view.findViewById(R.id.btnTaskToJob);
        btn_addToTask.setOnClickListener(this);

        return view;
}


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyAddTaskListener) {
            atl = (MyAddTaskListener) context;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnTaskToJob:
                Bundle bundle = new Bundle();
                String jobName = selectedJob;
                String taskToAssign = selectedTask;
                bundle.putString("TaskDescription", taskToAssign);
                bundle.putString("JobName", jobName);
                atl.TaskToJob(bundle);
                break;

            case R.id.btnClearSelections:
                ClearForms(v);

            default:
                break;
        }
    }

    public void LoadLists()
    {
        Integer counter = 0;
        String jobName;

        // Grab the number of Jobs and Number of Members in the Database
        Integer numTasks = dbAdapter.GetUnassignedTasks().size();
        Integer numJobs = (data.getCompletedJobs() + data.getUncompletedJobs());

        while (counter < numTasks)
        {
            taskNames = dbAdapter.GetUnassignedTasks();
            counter++;
        }

        // reset counter
        counter = 0;
        while (counter < numJobs)
        {
            jobName = data.getJobList(counter);
            jobNames.add(jobName);
            counter++;
        }
    }


    /*
     * FUNCTION:
     *		ClearForms()
     * DESCRIPTION:
     *		Resets the input forms to their default state
     * PARAMETERS:
     *			View : The view that the forms are in
     * RETURNS:
     *			VOID
     */
    public void ClearForms(View v)
    {
        taskList.setSelection(0);
        jobList.setSelection(0);
    }

    public interface MyAddTaskListener {
        public void TaskToJob(Bundle args);
    }
}
