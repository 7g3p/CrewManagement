/*
 * FILE:             Fragment_AssignMemberToJob.Java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Alex MacCumber
 * OTHER MEMBERS:    Alex Palmer, David Obeda, Stephen Perrin, Marissa Schmitt
 * FIRST VERSION:    March 10th, 2020
 * DESCRIPTION:      This file describes the functionality of the AssignMemberToJob screen.
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


public class fragment_AssignMemberToJob extends Fragment
        implements View.OnClickListener {

    private View view = null;
    private Spinner jobList;
    private Spinner memberList;

    private ArrayAdapter jobSpinnerAdapter;
    private ArrayAdapter memberSpinnerAdapter;
    private Button btn_addToJob;
    private Button btn_clearForm;

    private List<String> jobNames = new ArrayList<>();
    private List<String> memberNames = new ArrayList<>();

    private String selectedJob;
    private String selectedMember;

    private DBAdapter dbAdapter;
    private Data data;

    private MyMemToJobListener mtj;

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
        dbAdapter = new DBAdapter(getContext());
        data = dbAdapter.GetData();

        // Load the Job List and the Member list with values
        LoadLists();
        jobSpinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, jobNames);
        memberSpinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, memberNames);
        jobList = (Spinner)view.findViewById(R.id.spnr_jobChoice);
        memberList = (Spinner)view.findViewById(R.id.spnr_MemberChoice);

        jobList.setAdapter(jobSpinnerAdapter);
        memberList.setAdapter(memberSpinnerAdapter);
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
        memberList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                Object item = parent.getItemAtPosition(pos);
                selectedMember = item.toString();
            }

            public void onNothingSelected(AdapterView<?> parent)
            {
                // Do nothing
            }
        });

        btn_clearForm = (Button)view.findViewById(R.id.btnClearMTJForm);
        btn_clearForm.setOnClickListener(this);
        btn_addToJob = (Button)view.findViewById(R.id.btnAddMemberToJob);
        btn_addToJob.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof fragment_AssignMemberToJob.MyMemToJobListener) {
            mtj = (fragment_AssignMemberToJob.MyMemToJobListener) context;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnAddMemberToJob:

                Bundle bundle = new Bundle();
                String memberToAdd = selectedMember;
                String jobToAssign = selectedJob;
                //TODO Get Username of Selected Member's name, need to implement a database method
                bundle.putString("memberToAdd", memberToAdd);
                bundle.putString("jobToAssign", jobToAssign);
                mtj.MemberToJob(bundle);
                break;

            case R.id.btnClearForm:
                ClearForms(v);

            default:
                break;
        }
    }

    public void LoadLists()
    {
        Integer counter = 0;
        String jobName;
        String memberName;

        // Grab the number of Jobs and Number of Members in the Database
        Integer numJobs = (data.getCompletedJobs() + data.getUncompletedJobs());
        Integer numMembers = data.getNumberOfMembers();

        while (counter < numJobs)
        {
            jobName = data.getJobList(counter);
            jobNames.add(jobName);
            counter++;
        }

        // reset counter
        counter = 0;
        while (counter < numMembers)
        {
            memberName = data.getName(counter);
            memberNames.add(memberName);
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
        jobList.setSelection(0);
        memberList.setSelection(0);
    }


    public interface MyMemToJobListener {
        public void MemberToJob(Bundle bundle);
    }
}
