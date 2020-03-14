/*
 * FILE              Members_Fragment.java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Marissa Schmitt
 * OTHER MEMBERS:    Alex MacCumber, David Obeda, Alex Palmer, Stephen Perrin
 * FIRST VERSION:    March 5th, 2020
 * DESCRIPTION:      This file describes the functionality and events of the members fragment
 *                      within the view screen.
 */
package com.example.crewmanagement;

import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Members_Fragment extends Fragment {



    Intent intent;
    ExpandableListView listView;
    ExpandableListAdapter listViewAdapter;
    List<String> membersNames;
    HashMap<String, List<String>> listDetails;
    Data data;

    @Override
    /*
     * FUNCTION:     onCreate
     * DESCRIPTION:  This event handler is called whenever the activity/screen is being created. It is
     *               also used to fill in the listview accordingly
     * PARAMETERS:   Bundle savedInstanceState -
     * RETURNS:      void - this event handler returns nothing
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        Log.i("MemberFrag","Inflating");;
        View membersView = inflater.inflate(R.layout.activity_members_screen, container, false);
        //membersView.findViewById();

        intent = getActivity().getIntent();
        data = (Data)intent.getExtras().getSerializable("data");
        // Set list of members according to data
        listView = (ExpandableListView) membersView.findViewById(R.id.MembersList);
        String tempPhoneNum = "519-123-4567";
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
            memberDetails.add("Phone Number:\t" + tempPhoneNum);

            // Get members' name
            details.put(data.eName.get(i), memberDetails);
        }
        listDetails = details;
        membersNames = new ArrayList<>(listDetails.keySet());
        listViewAdapter = new HashmapListAdapter(getContext(), membersNames, listDetails);
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
                Toast.makeText(getActivity().getApplicationContext(),membersNames.get(groupPosition) + "'s Info Expanded.", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getActivity().getApplicationContext(),membersNames.get(groupPosition) + "'s Info Collapsed.", Toast.LENGTH_SHORT).show();
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
                //retrieving the title of the information clicked on by the user (job, name, etc)
                String tempCheck = listDetails.get( membersNames.get(groupPosition)).get(childPosition).toString().substring(0,12);
                //checking if the string is equal to phone number
                if(tempCheck.compareTo("Phone Number") == 0)
                {
                    //getting the phone number and storing it within a string
                    String tempPhone = listDetails.get( membersNames.get(groupPosition)).get(childPosition).toString().substring(14);
                    //appending the tel: indicator to the phone number and placing it within a Uri
                    Uri viewUri = Uri.parse("tel:" + tempPhone);
                    //creating a new intent to make an action_dial
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, viewUri);
                    Log.i("Members Fragment", "Dialing the specified member's number.");
                    //starting the dial activity
                    startActivity(callIntent);
                }
                Toast.makeText(getActivity(). getApplicationContext(),membersNames.get(groupPosition) + "'s " + listDetails.get( membersNames.get(groupPosition)).get(childPosition), Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return membersView;
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
