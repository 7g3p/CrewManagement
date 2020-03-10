/*
 * FILE : MembersScreen.java
 * PROJECT : PROG3150 - Assignment #1
 * PROGRAMMER : Stephen Perrin, Alex Palmer, Alex MacCumber, David Obeda, Marissa Schmitt
 * FIRST VERSION : 2020-02-08
 * DESCRIPTION :
 * The functions in this file are used to get and display the members data from the extras data class.
 * Aligning the data with each members' name to allow for simple display to the user. This file also
 * contains onClick events for when the member's details are expanded/collapsed and when an individual
 * child detail is selected (was planning to do some work on this part but circumstances didn't allow... sorry guys :(
 */
package com.example.crewmanagement;

import com.example.crewmanagement.Data;
import com.example.crewmanagement.HashmapListAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.content.Intent;
import android.widget.Toast;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;

public class MembersScreen extends AppCompatActivity
{
    // Class variables
    Intent intent;
    Data data;
    ExpandableListView listView;
    ExpandableListAdapter listViewAdapter;
    List<String> membersNames;
    HashMap<String, List<String>> listDetails;
    Bundle extras;

    /*
     * FUNCTION:
     *		onCreate(Bundle savedInstanceState)
     * DESCRIPTION:
     *		Creates and initializes the data needed in order to produce the dynamic activity screen
     * PARAMETERS:
     *			Bundle savedInstanceState : the previous state of the current activity screen
     * RETURNS:
     *			void - this function returns nothing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members_screen);

        // Setting title in actionbar
        setTitle("Member's Details");

        // Get intent and the member's data
        intent = getIntent();
        data = (Data)intent.getExtras().getSerializable("data");

        // Set list of members according to data
        listView = (ExpandableListView) findViewById(R.id.MembersList);
        // Variables
        List<String> memberDetails = new ArrayList<>(); // Local list of member's details
        HashMap<String, List<String>> details = new HashMap<>();    // Local hashmap of members' details


        // Loop through list of members (each list should be the same size)
        for(int i = 0; i < data.eName.size(); i++)
        {
            // Initialize a new instance of memberDetails list
            memberDetails = new ArrayList<>();

            // Gets all the information for the member at index i
            memberDetails.add("Age: \t\t\t" + data.pAge.get(i));
            memberDetails.add("Job: \t\t\t" + data.pJob.get(i));
            memberDetails.add("Assigned Job: \t" + data.pAssignedJob.get(i));
            memberDetails.add("Date of Hire: \t" + data.dateOfHire.get(i));


            // Get members' name
            details.put(data.eName.get(i), memberDetails);
        }
        listDetails = details;
        membersNames = new ArrayList<>(listDetails.keySet());
        listViewAdapter = new HashmapListAdapter(this, membersNames, listDetails);
        listView.setAdapter(listViewAdapter);

    // Setting the listener
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener()
        {

            /*
             * FUNCTION:
             *		onGroupExpand(int groupPosition)
             * DESCRIPTION:
             *		Displays a small message of which group was expanded on event
             * PARAMETERS:
             *			int groupPosition : The group that was expanded
             * RETURNS:
             *			void - this function returns nothing
             */
            @Override
            public void onGroupExpand(int groupPosition)
            {
                Toast.makeText(getApplicationContext(),membersNames.get(groupPosition) + "'s Info Expanded.", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener()
        {
            /*
             * FUNCTION:
             *		onGroupCollapse(int groupPosition)
             * DESCRIPTION:
             *		Displays a small message of which group was collapsed on event
             * PARAMETERS:
             *			int groupPosition : The group that was collapsed
             * RETURNS:
             *			void - this function returns nothing
             */
            @Override
            public void onGroupCollapse(int groupPosition)
            {
                Toast.makeText(getApplicationContext(),membersNames.get(groupPosition) + "'s Info Collapsed.", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            /*
             * FUNCTION:
             *		onChildClick(ExpandableListView parent, ViewScreen v, int groupPosition, int childPosition, long id)
             * DESCRIPTION:
             *		Displays a small message of which child was selected on event
             * PARAMETERS:
             *			ExpandableListView parent : the parent of the selected child
             *			ViewScreen v : the view
             *			int groupPosition : the position of the parent of the selected child
             *			int childPosition : the position within the parent group of the selected child
             *			long id : the child's ID
             * RETURNS:
             *			boolean - this function returns false
             */
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                Toast.makeText( getApplicationContext(),membersNames.get(groupPosition) + "'s " + listDetails.get( membersNames.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();

                return false;
            }
        });

    }

    /*
     * FUNCTION:
     *		getData()
     * DESCRIPTION:
     *		Takes the data recieved from the extras bundle and organizes it into groupings based on member and their details
     * PARAMETERS:
     *			N/A
     * RETURNS:
     *			HashMap<String, List<String>> - this function returns the finished grouped list of members and their details
     */
    private HashMap<String, List<String>> getData()
    {
        // Variables
        List<String> memberDetails = new ArrayList<>(); // Local list of member's details
        HashMap<String, List<String>> details = new HashMap<>();    // Local hashmap of members' details

        // Loop through list of members (each list should be the same size)
        for(int i = 0; i < data.eName.size(); i++)
        {
            // Initialize a new instance of memberDetails list
            memberDetails = new ArrayList<>();

            // Gets all the information for the member at index i
            memberDetails.add("Age: \t\t\t" + data.pAge.get(i));
            memberDetails.add("Job: \t\t\t" + data.pJob.get(i));
            memberDetails.add("Assigned Job: \t" + data.pAssignedJob.get(i));
            memberDetails.add("Date of Hire: \t" + data.dateOfHire.get(i));
            memberDetails.add("Job Status: \t" + data.jobStatus.get(i));

            // Get members' name
            details.put(data.eName.get(i), memberDetails);
        }

        // Return hashmap of members and their details
        return details;
    }


}
