/*
 * FILE:              NewJob.Java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Alex MacCumber
 * OTHER MEMBERS:    Alex Palmer, David Obeda, Stephen Perrin, Marissa Schmitt
 * FIRST VERSION:    February 6th, 2020
 * DESCRIPTION:      This file describes the functionality of the Newjob screen. This screen adds
 *                   new jobs to the data class
 * External Resources: Utilized as a guide to populate the list views for jobs and employees https://windrealm.org/tutorials/android/android-listview.php
 */
package com.example.crewmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class JobAssignment extends AppCompatActivity {

    EditText jobInput;
    EditText employeeInput;
    ListView jobList;
    ListView employeeList;
    Button AssignJob;
    Button CreateJob;

    ArrayList<String> listOfJobs;
    ArrayList<String> listOfEmployees;

    String job;
    String name;

    ArrayAdapter<String> jobAdapter;
    ArrayAdapter<String> employeeAdapter;

    Spinner sMenu = null;//the menu dropdown

    Data data;
    /*
     * FUNCTION:	onCreate
     * DESCRIPTION:	creates the NewJob
     * PARAMETERS:	Bundle savedInstanceState
     * RETURNS:	void - this function returns nothing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_assignment);
        AssignJob = (Button)findViewById(R.id.btnAssignJob);
        CreateJob = (Button)findViewById(R.id.btnCreateJob);
        sMenu = (Spinner)findViewById(R.id.jobAssignMenu);//retrieving the view by specifying its id and casting it as a spinner
        ArrayList<String> MenuItems = new ArrayList<String>();//creating an array list
        Intent intent = getIntent();
        Bundle info = intent.getExtras();
        data = (Data)info.getSerializable("data");
        Integer count = 0;

        listOfJobs = new ArrayList<>();
        listOfEmployees = new ArrayList<>();

        data = (Data) info.getSerializable("data");//retrieving the data and casting it as a Data object

        setTitle("Job Assignment");

        // Find the ListView resources for jobs and employees to be displayed in
        jobList = (ListView) findViewById(R.id.listView_Jobs);
        employeeList = (ListView) findViewById(R.id.listView_Employees);

        Integer numMembers = data.getNumberOfMembers().intValue();

        while(count.intValue() < numMembers)
        {
            job = data.getJob(count);
            name = data.getName(count);

            listOfJobs.add(job);
            listOfEmployees.add(name);

            count++;
        }

        // Create two adapters
        jobAdapter = new ArrayAdapter<String>(this, R.layout.list_view_row_layout, listOfJobs);
        employeeAdapter = new ArrayAdapter<String>(this, R.layout.list_view_row_layout, listOfEmployees);

        // Add some default jobs and employees
        jobAdapter.add("Lumber Yard");
        jobAdapter.add("Sweeper");
        jobAdapter.add("Crane Operator");
        jobAdapter.add("Supervisor");
        jobAdapter.add("Sign Holder");
        jobAdapter.add("Human Resources");

        employeeAdapter.add("Chris");
        employeeAdapter.add("Joelle");
        employeeAdapter.add("Valerie");
        employeeAdapter.add("Logan");

        // Set adapters to each list view
        jobList.setAdapter(jobAdapter);
        employeeList.setAdapter(employeeAdapter);

        //Adding items to menu for navigation
        MenuItems.add("Administration Screen");//adding Administration Screen to the menu
        MenuItems.add("Members Screen");//adding Members Screen to the menu
        MenuItems.add("Progress Screen");//adding Job Assignment Screen to the menu
        MenuItems.add("News Feed Screen");//adding News Feed Screen to the menu
        MenuItems.add("Log out");//adding log out to the menu

        //putting the arrays into each adapter and put the adapter into the spinners
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,MenuItems);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMenu.setAdapter(mAdapter);

        sMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /*
             * FUNCTION:	onItemSelected
             * DESCRIPTION:	Whenever an item is selected
             * PARAMETERS:	AdapterView<?> parent, View view, int position, long id
             * RETURNS:	void - this function returns nothing
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position >= 0)//ensuring the user didn't select the screen they're currently on
                {
                    if(position == 0)//seeing if the user chose the first option
                    {
                        //creating a new intent for the admin screen
                        Intent intent = new Intent(JobAssignment.this, Administrator.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("Admin","Going to administrator screen.");
                    }
                    else if(position == 1)//seeing if the user chose the second option
                    {
                        //creating a new intent for the members screen
                        Intent intent = new Intent(JobAssignment.this, MembersScreen.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("Admin","Going to members screen.");
                    }
                    else if(position == 2)//seeing if the user chose the second option
                    {
                        //creating a new intent for the progress screen
                        Intent intent = new Intent(JobAssignment.this, ProgressScreen.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("Admin","Going to progress screen.");
                    }
                    else if(position == 3)//seeing if the user chose the third option
                    {
                        //creating a new intent for the job assignment screen
                        Intent intent = new Intent(JobAssignment.this, NewsFeed.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("Admin","Going to news feed screen.");
                    }
                    else if(position == 4)//seeing if the user chose the fourth option
                    {
                        //creating a new intent for the login screen
                        Intent intent = new Intent (JobAssignment.this, MainActivity.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("Admin","Going to login screen.");
                    }
                }

            }
            /*
             * FUNCTION:	onNothingSelected
             * DESCRIPTION:	Whenever an item is not selected
             * PARAMETERS:	AdapterView<?> parent
             * RETURNS:	void - this function returns nothing
             */

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    /*
     * FUNCTION:	newJob
     * DESCRIPTION:	When the button to create a new job in the list is pressed
     * PARAMETERS:	android.View.view v
     * RETURNS:	void - this function returns nothing
     */
    public void newJob(android.view.View v)
    {
        if (jobInput.getText().toString().equals("")) {
            jobInput.setText("None");
        }
        else if (!listOfJobs.contains(jobInput.getText().toString()))
        {
            // List of jobs does not contain the entered job
            // We will create it
            data.addJob(jobInput.getText().toString());
            Intent intent = new Intent(JobAssignment.this, Administrator.class);
            intent.putExtra("data", data);
            startActivity(intent);
        }
    }

    /*
     * FUNCTION:	assignJob
     * DESCRIPTION:	When the button to assign a new job is pressed, the job in the input field is
     *              assigned to is assigned to the employee in the employee input field
     * PARAMETERS:	android.View.view v
     * RETURNS:	void - this function returns nothing
     */
    public void assignJob(android.view.View v)
    {
        if (jobInput.getText().toString().equals("") || employeeInput.getText().toString().equals(""))
        {
            // One of the fields was left blank.  No job assigned
        }
        else if (listOfJobs.contains((jobInput.getText().toString())))
        {
            // Both fields have data, job assigned
            data.pAssignedJob.add(listOfEmployees.indexOf(employeeInput.getText().toString()), jobInput.getText().toString());
            Intent intent = new Intent(JobAssignment.this, Administrator.class);
            intent.putExtra("data", data);
            startActivity(intent);
        }
        else if(!listOfJobs.contains(jobInput.getText().toString()))
        {
            // Job entered does not exist. We will create it before assigning it
            newJob(v);
            assignJob(v);
        }
    }





}