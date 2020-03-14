package com.example.crewmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.crewmanagement.ui.jobmanagement.fragment_AssignTaskToJob;
import com.example.crewmanagement.ui.jobmanagement.fragment_CreateJob;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.example.crewmanagement.ui.jobmanagement.SectionsPagerAdapter;
import com.example.crewmanagement.ui.jobmanagement.fragment_CreateTask;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class JobAssignment extends AppCompatActivity
    implements  fragment_CreateJob.MyJobListener,
                fragment_CreateTask.MyTaskListener,
                fragment_AssignTaskToJob.MyAddTaskListener {

    private final String job = "J";
    private final String task = "T";
    private final String taskToJob = "TJ";
    private final String memberToJob = "MJ";
    private final String memberToTask = "MT";

    private DBAdapter dbAdapter;
    private Data data;

    private fragment_CreateJob.MyJobListener mjl = null;
    private fragment_CreateTask.MyTaskListener mtl = null;

    private Button btn_CreateJob = null;

    private ListView jobList;
    private ListView employeeList;

    private Integer numJobs = 0;
    private Integer numMembers = 0;

    private String jobName;


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
        btn_CreateJob = findViewById(R.id.btnNewJob);
        numJobs = data.jobList.size();
        numMembers = data.numberOfMembers;
    }

     /*
     * FUNCTION:
     *		CreateJob()
     * DESCRIPTION:
     *		Uses the DBAdapter class to insert a new job into the database using the user provided
     *      data from the Create Job fragment
     * PARAMETERS:
     *			Bundle args : Contains the data entered by the user on the fragment screen
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
     *		getData()
     * DESCRIPTION:
     *		Queries the database to get all information related to the variables in the Data class to be filled and returned
     * PARAMETERS:
     *			N/A
     * RETURNS:
     *			Data : Returns a populated Data class object with ALL database data
     */
    public void CreateTask(Bundle args)
    {
        //TODO Switch Statement For if a job was selected on create task screen or not

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
     *		getData()
     * DESCRIPTION:
     *		Queries the database to get all information related to the variables in the Data class to be filled and returned
     * PARAMETERS:
     *			N/A
     * RETURNS:
     *			Data : Returns a populated Data class object with ALL database data
     */
    public void TaskToJob(Bundle args)
    {

    }

    /*
     * FUNCTION:
     *		getData()
     * DESCRIPTION:
     *		Queries the database to get all information related to the variables in the Data class to be filled and returned
     * PARAMETERS:
     *			N/A
     * RETURNS:
     *			Data : Returns a populated Data class object with ALL database data
     */
    public void MemberToJob(Bundle args)
    {

    }

    /*
     * FUNCTION:
     *		getData()
     * DESCRIPTION:
     *		Queries the database to get all information related to the variables in the Data class to be filled and returned
     * PARAMETERS:
     *			N/A
     * RETURNS:
     *			Data : Returns a populated Data class object with ALL database data
     */
    public void MemberToTask(Bundle args)
    {

    }


    public void onClick()
    {

    }
}