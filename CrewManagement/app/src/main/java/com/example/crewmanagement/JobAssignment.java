/*
 * FILE:             JobAssignment.Java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Alex MacCumber
 * OTHER MEMBERS:    Alex Palmer, David Obeda, Stephen Perrin, Marissa Schmitt
 * FIRST VERSION:    March 10th, 2020
 * DESCRIPTION:      This file describes the functionality of the Job Assignment screen.
 */
package com.example.crewmanagement;

import android.content.Context;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.widget.Toast;

import com.example.crewmanagement.ui.jobmanagement.fragment_AssignMemberToJob;
import com.example.crewmanagement.ui.jobmanagement.fragment_AssignMemberToTask;
import com.example.crewmanagement.ui.jobmanagement.fragment_AssignTaskToJob;
import com.example.crewmanagement.ui.jobmanagement.fragment_CreateJob;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crewmanagement.ui.jobmanagement.SectionsPagerAdapter;
import com.example.crewmanagement.ui.jobmanagement.fragment_CreateTask;
import com.google.android.material.tabs.TabLayout;

public class JobAssignment extends AppCompatActivity
    implements  fragment_CreateJob.MyJobListener,
                fragment_CreateTask.MyTaskListener,
                fragment_AssignTaskToJob.MyAddTaskListener,
                fragment_AssignMemberToJob.MyMemToJobListener,
                fragment_AssignMemberToTask.MyMemToTaskListener {


    private DBAdapter dbAdapter;
    private Data data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_assignment);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        Intent intent = new Intent();
        Bundle info = intent.getExtras();
        if(info != null)//ensuring that the bundle isn't null
        {
            data = (Data)info.getSerializable("data");
        }
        else
        {
            dbAdapter = new DBAdapter(this);
            data = dbAdapter.GetData();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_job_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_activity1:
                Intent intent1 = new Intent(JobAssignment.this, Administrator.class);
                //putting the Data object into the extras
                intent1.putExtra("data", data);
                //starting the new activity
                startActivity(intent1);
                Log.i("View","Going to adminstrator screen.");
                finish();
                return true;
            case R.id.menu_activity2:
                Intent intent2 = new Intent(JobAssignment.this, MembersScreen.class);
                //putting the Data object into the extras
                intent2.putExtra("data", data);
                //starting the new activity
                startActivity(intent2);
                Log.i("View","Going to members screen.");
                finish();
                return true;
            case R.id.menu_activity5:
                Intent intent4 = new Intent(JobAssignment.this, NewsFeed.class);
                intent4.putExtra("data", data);
                startActivity(intent4);
                Log.i("View","Going to news feed screen.");
                finish();
                return true;
            case R.id.menu_activity6:
                //creating a new intent for the login screen
                Intent intent5 = new Intent (JobAssignment.this, MainActivity.class);
                //putting the Data object into the extras
                intent5.putExtra("data", data);
                //starting the new activity
                startActivity(intent5);
                Log.i("View","Going to login screen.");
                finish();
                return true;
            case R.id.menu_activity7:
                //declaring the tech support number
                String telephoneNum = "519-555-1234";
                //parsing the number and appending the telephone identifier to it
                Uri viewUri = Uri.parse("tel:" + telephoneNum);
                //creating a new intent to make an action_dial
                Intent callIntent = new Intent(Intent.ACTION_DIAL, viewUri);
                //starting the dial activity
                startActivity(callIntent);
                Log.i("View", "Calling tech support.");
                return true;
            case R.id.menu_activity3:
                //creating a new intent for the login screen
                Intent intent3 = new Intent (JobAssignment.this, ViewScreen.class);
                //putting the Data object into the extras
                intent3.putExtra("data", data);
                //starting the new activity
                startActivity(intent3);
                Log.i("View","Going to view screen.");
                finish();
                return true;

            default:
                return false;
        }
    }

    /*
     * FUNCTION:
     *		CreateJob()
     * DESCRIPTION:
     *		Utilizes the DBAdapter to Add a Job to the database
     * PARAMETERS:
     *			Bundle args : Contains the user entered Job Information
     * RETURNS:
     *			VOID
     */
    public void CreateJob(Bundle args)
    {
        long retcode = 0;
        retcode = dbAdapter.InsertNewJob(args.getString("jName"), args.getString("jDate"), args.getInt("jDuration"), args.getInt("jActual"), args.getInt("jStatus"));
        if (retcode >= 0)
        {
            // Insertion of new job was successful
            Context context = getApplicationContext();
            CharSequence toastMsg = "Job WAS Created!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastMsg, duration);
            toast.show();
            Log.i("CreateJob","Job Creation Successful");
        }
        else
        {
            // Insertion failed
            Context context = getApplicationContext();
            CharSequence toastMsg = "Job NOT created";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastMsg, duration);
            toast.show();
            Log.e("CreateJob","Job Creation failed. Retcode was "+ retcode);
        }
    }



    /*
     * FUNCTION:
     *		CreateTask()
     * DESCRIPTION:
     *		Utilizes the DBAdapter to Add a task to the database
     * PARAMETERS:
     *			Bundle args : Contains the user entered task description
     * RETURNS:
     *			VOID
     */
    public void CreateTask(Bundle args)
    {
        long retcode = -1;
        retcode = dbAdapter.InsertNewTask(args.getString("tDescription"), null);
        if (retcode >= 0)
        {
            // Insertion of new task was successful
            Context context = getApplicationContext();
            CharSequence toastMsg = "Task was created!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastMsg, duration);
            toast.show();
            Log.i("CreateJob","Task Creation Successful");
        }
        else
        {
            // Insertion failed
            Context context = getApplicationContext();
            CharSequence toastMsg = "Task was not created!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastMsg, duration);
            toast.show();
            Log.e("CreateTask","Task Creation failed with retcode "+ retcode);
        }
    }

    /*
     * FUNCTION:
     *		TaskToJob()
     * DESCRIPTION:
     *		Utilizes the DBAdapter to Assign a Task to a Job
     * PARAMETERS:
     *			Bundle args : Contains the user selected job and task from the AssignTaskToJob fragment screen
     * RETURNS:
     *			VOID
     */
    public void TaskToJob(Bundle args)
    {
        long retcode = 0;
        retcode = dbAdapter.AssignTaskToJob(args.getString("TaskDescription"), args.getString("JobName"));
        if (retcode >= 0)
        {
            // Insertion of new task was successful
            Context context = getApplicationContext();
            CharSequence toastMsg = args.getString("MemberToAdd") + "was assigned to " + args.getString("JobToAssign");
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastMsg, duration);
            toast.show();
            Log.i("MemberToJob","Job Assignment Successful");
        }
        else
        {
            // Insertion failed
            Context context = getApplicationContext();
            CharSequence toastMsg = args.getString("MemberToAdd") + "was not assigned to " + args.getString("JobToAssign");
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastMsg, duration);
            toast.show();
            Log.e("MemberToJob","Job Assignment failed with retcode "+ retcode);
        }
    }

    /*
     * FUNCTION:
     *		MemberToJob()
     * DESCRIPTION:
     *		Utilizes the DBAdapter to Assign a Member to a job
     *      Currently not working, as the AssignMemberToTask method requires a username that was not accessible
     * PARAMETERS:
     *			Bundle args : Contains the user selected Job and Member from the AssignTaskToJob fragment screen
     * RETURNS:
     *			VOID
     */
    public void MemberToJob(Bundle args)
    {
        long retcode = 0;
        retcode = dbAdapter.AssignJobToMember(args.getString("MemberToAdd"), args.getString("jobToAssign"));
        if (retcode >= 0)
        {
            // Insertion of new task was successful
            Context context = getApplicationContext();
            CharSequence toastMsg = args.getString("MemberToAdd") + "was assigned to " + args.getString("JobToAssign");
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastMsg, duration);
            toast.show();
            Log.i("MemberToJob","Job Assignment Successful");
        }
        else
    {
        // Insertion failed
        Context context = getApplicationContext();
        CharSequence toastMsg = args.getString("MemberToAdd") + "was not assigned to " + args.getString("JobToAssign");
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, toastMsg, duration);
        toast.show();
        Log.e("MemberToJob","Job Assignment failed with retcode "+ retcode);
    }
}

    /*
     * FUNCTION:
     *		MemberToTask()
     * DESCRIPTION:
     *		Utilizes the DBAdapter to Assign a Member to a Task
     *      Currently not working, as the AssignMemberToTask method requires a username that was not accessible
     * PARAMETERS:
     *			Bundle args : Contains the user selected Member and Job from the AssignMemberToTask fragment screen
     * RETURNS:
     *			VOID
     */
    public void MemberToTask(Bundle args)
    {
        long retcode = 0;
        retcode = dbAdapter.AssignMemberToTask(args.getString("TaskToAssign"), args.getString("MemberToAssign"));
        if (retcode >= 0)
        {
            // Insertion of new task was successful
            Context context = getApplicationContext();
            CharSequence toastMsg = args.getString("MemberToAssign") + "was assigned to " + args.getString("TaskToAssign");
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastMsg, duration);
            toast.show();
            Log.i("MemberToTask","Task Assignment Successful");
        }
        else
        {
            // Insertion failed
            Context context = getApplicationContext();
            CharSequence toastMsg = args.getString("MemberToAssign") + "was not assigned to " + args.getString("TaskToAssign");
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, toastMsg, duration);
            toast.show();
            Log.e("MemberToTask","Task Assignment failed with retcode "+ retcode);
        }
    }
}