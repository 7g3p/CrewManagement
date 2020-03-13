/*
 * FILE              progress_fragment.java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Marissa Schmitt
 * OTHER MEMBERS:    Alex MacCumber, David Obeda, Alex Palmer, Stephen Perrin
 * FIRST VERSION:    March 5th, 2020
 * DESCRIPTION:      This file describes the functionality and events of the progress fragment within
 *                      the view screen.
 */
package com.example.crewmanagement;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;


public class progress_fragment extends Fragment {

    TextView completeLabel = null;//initializing the TextView to null (not containing the appropriate widget yet)
    TextView incompleteLabel = null;//initializing the TextView to null (not containing the appropriate widget yet)
    ProgressBar percentageComplete = null;//initializing the ProgressBar to null (not containing the appropriate widget yet)
    Data data = null;//initializing the Data object to null (not containing the appropriate widget yet)

    @Override
    /*
     * FUNCTION:     onCreate
     * DESCRIPTION:  This event handler is called whenever the fragment is being created.
     * PARAMETERS:   Bundle savedInstanceState -
     * RETURNS:      void - this event handler returns nothing
     */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.activity_progress_screen);

        //retrieving the text-views by using their IDs
        completeLabel = getActivity().findViewById(R.id.completeTasks);//finding a text-view with the id of 'completeTasks'
        incompleteLabel = getActivity().findViewById(R.id.incompleteTasks);//finding a text-view with the id of 'incompleteTasks'
        percentageComplete = getActivity().findViewById(R.id.percentComplete);
        ArrayList<String> MenuItems = new ArrayList<String>();//creating an array list

        Intent intent = getActivity().getIntent();//getting the intent and storing it in a local instance
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
            Log.i("ProgressScreen", "Progress report was successfully generated." );

        }
        else {
            Log.i("ProgressScreen", "No progress report was made.");
            completeLabel.setText("0");//setting the text that of the label that displays the amount of completed tasks
            incompleteLabel.setText("0");//setting the text that of the label that displays the amount of incomplete tasks
            percentageComplete.setProgress(0);//setting the progress of the status bar to indicate the percentage of
        }

        //putting the arrays into each adapter and put the adapter into the spinners
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,MenuItems);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    /*
     * FUNCTION:     onCreateView
     * DESCRIPTION:  This event handler is called whenever the fragment view is created. The fragment
     *              layout is inflated here.
     * PARAMETERS:   LayoutInflater inflater
     *               ViewGroup container
                     Bundle savedInstanceState
     * RETURNS:      View  - this event handler returns the inflated view.
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.activity_progress_screen, container, false);
    }
}
