/*
 * FILE:             Fragment_AssignMemberToTask.Java
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


public class fragment_AssignMemberToTask extends Fragment
    implements View.OnClickListener {

    View view = null;
    Spinner taskList;
    Spinner memberList;

    ArrayAdapter taskSpinnerAdapter;
    ArrayAdapter memberSpinnerAdapter;
    Button btn_addToTask;
    Button btn_clearForm;

    List<String> taskNames = new ArrayList<>();
    List<String> memberNames = new ArrayList<>();

    String selectedTask;
    String selectedMember;

    DBAdapter dbAdapter;
    Data data;

    MyMemToTaskListener amt;

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

        dbAdapter = new DBAdapter(getContext());
        data = dbAdapter.GetData();

        // Load the Job List and the Member list with values
        LoadLists();
        taskSpinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, taskNames);
        memberSpinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_list_item_1, memberNames);
        taskList = (Spinner) view.findViewById(R.id.spnr_amtTaskList);
        memberList = (Spinner) view.findViewById(R.id.spnr_amtMemberList);

        taskList.setAdapter(taskSpinnerAdapter);
        memberList.setAdapter(memberSpinnerAdapter);
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

        btn_clearForm = (Button)view.findViewById(R.id.btnClearAMTForm);
        btn_clearForm.setOnClickListener(this);
        btn_addToTask = (Button)view.findViewById(R.id.btnAddMemberToTask);
        btn_addToTask.setOnClickListener(this);

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyMemToTaskListener) {
            amt = (MyMemToTaskListener) context;
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
                String taskToAssign = selectedTask;
                bundle.putString("MemberToAssign", memberToAdd);
                bundle.putString("TaskToAssign", taskToAssign);
                amt.MemberToTask(bundle);
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
        String taskName;
        String memberName;

        // Grab the number of Jobs and Number of Members in the Database
        Integer numJobs = dbAdapter.GetUnassignedTasks().size();
        Integer numMembers = data.getNumberOfMembers();

        while (counter < numJobs)
        {
            taskName = data.getJobList(counter);
            taskNames.add(taskName);
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
        taskList.setSelection(0);
        memberList.setSelection(0);
    }


    public interface MyMemToTaskListener {
        public void MemberToTask(Bundle bundle);
    }
}
