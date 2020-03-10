/*
 * FILE              ProgressScreen.java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Marissa Schmitt
 * OTHER MEMBERS:    Alex MacCumber, David Obeda, Alex Palmer, Stephen Perrin
 * FIRST VERSION:    February 5th, 2020
 * DESCRIPTION:      This file describes the functionality and events of the progress screen within
 *                   the CrewManagement application.
 */
package com.example.crewmanagement;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

/*
 * NAME:     ProgressScreen
 * PURPOSE:  The purpose of the ProgressScreen class is to define the functionality and behaviour of
 *           found within the the ProgressScreen activity. This class inherits (extends) characteristics
 *           from the AppCompatActivity.
 */
public class ProgressScreen extends AppCompatActivity {

    TextView completeLabel = null;//initializing the TextView to null (not containing the appropriate widget yet)
    TextView incompleteLabel = null;//initializing the TextView to null (not containing the appropriate widget yet)
    ProgressBar percentageComplete = null;//initializing the ProgressBar to null (not containing the appropriate widget yet)
    Data data = null;//initializing the Data object to null (not containing the appropriate widget yet)
    TextView txtSummaryReport = null;
    String tempReport = "";
    Spinner sMenu = null;//the menu dropdown


    @Override
    /*
     * FUNCTION:     onCreate
     * DESCRIPTION:  This event handler is called whenever the activity/screen is being created. It is
     *               also used to fill the text-views with the appropriate information retrieved from
     *               an intent.
     * PARAMETERS:   Bundle savedInstanceState -
     * RETURNS:      void - this event handler returns nothing
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_screen);

        //retrieving the text-views by using their IDs
        completeLabel = findViewById(R.id.completeTasks);//finding a text-view with the id of 'completeTasks'
        incompleteLabel = findViewById(R.id.incompleteTasks);//finding a text-view with the id of 'incompleteTasks'
        percentageComplete = findViewById(R.id.percentComplete);
        txtSummaryReport = findViewById(R.id.txtSummary);
        sMenu = (Spinner)findViewById(R.id.progressMenu);//retrieving the view by specifying its id and casting it as a spinner
        ArrayList<String> MenuItems = new ArrayList<String>();//creating an array list

        setTitle("Progress Screen");
        Intent intent = getIntent();//getting the intent and storing it in a local instance
        Bundle info = intent.getExtras();//getting the intent and storing it in a local instance
        data = (Data) info.getSerializable("data");//retrieving the data and casting it as a Data object

        Integer tempComplete = 0;
        Integer tempIncomplete = 0;
        Integer totalNumOfJobs = 0;

        tempComplete = data.getCompletedJobs();//retrieving the number of completed tasks from the Data class
        tempIncomplete = data.getUncompletedJobs();//retrieving the number of incomplete tasks from the Data class
        totalNumOfJobs = tempComplete + tempIncomplete;//adding the number of completed and incomplete
        // tasks together to get the total number of task

        Log.i("ProgressScreen","Retrieving information from the Data class");//logging information about what's happening at this part in the program

        if(totalNumOfJobs > 0) {
            Integer percentage = tempComplete / totalNumOfJobs;//getting the percentage of completed jobs by dividing the number
            //of completed tasks with the total number of tasks

            completeLabel.setText(tempComplete);//setting the text that of the label that displays the amount of completed tasks
            incompleteLabel.setText(tempIncomplete);//setting the text that of the label that displays the amount of incomplete tasks
            percentageComplete.setProgress(percentage);//setting the progress of the status bar to indicate the percentage of
            // tasks complete
            tempReport = "The percentage of the "+ totalNumOfJobs  +" jobs complete is " + percentage;
            txtSummaryReport.setText(tempReport);
            Log.i("ProgressScreen", "Progress report was successfully generated." );

        }
        else {
            Log.i("ProgressScreen", "No progress report was made.");
            txtSummaryReport.setText("No report could be generated. There's no jobs.");
        }

        //Adding items to menu for navigation
        MenuItems.add("Administration Screen");//adding Administration Screen to the menu
        MenuItems.add("Members_Fragment Screen");//adding Members_Fragment Screen to the menu
        MenuItems.add("Job Assignment Screen");//adding Job Assignment Screen to the menu
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
             * PARAMETERS:	AdapterView<?> parent, ViewScreen view, int position, long id
             * RETURNS:	void - this function returns nothing
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position >= 0)//ensuring the user didn't select the screen they're currently on
                {
                    if(position == 0)//seeing if the user chose the first option
                    {
                        //creating a new intent for the members screen
                        Intent intent = new Intent(ProgressScreen.this, Administrator.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("ProgressScreen","Going to the administrator screen.");
                    }
                    else if(position == 1)//seeing if the user chose the second option
                    {
                        //creating a new intent for the progress screen
                        Intent intent = new Intent(ProgressScreen.this, MembersScreen.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("ProgressScreen","Going to the members screen.");
                    }
                    else if(position == 2)//seeing if the user chose the third option
                    {
                        //creating a new intent for the job assignment screen
                        Intent intent = new Intent(ProgressScreen.this, JobAssignment.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("ProgressScreen","Going to the job assignment screen.");
                    }
                    else if(position == 3)//seeing if the user chose the fourth option
                    {
                        //creating a new intent for the news feed screen
                        Intent intent = new Intent(ProgressScreen.this, NewsFeed.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("ProgressScreen","Going to the news feed screen.");
                    }
                    else if(position == 4)//seeing if the user chose the fifth option
                    {
                        //creating a new intent for the login screen
                        Intent intent = new Intent (ProgressScreen.this, MainActivity.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("ProgressScreen","Going to the login screen.");
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




}