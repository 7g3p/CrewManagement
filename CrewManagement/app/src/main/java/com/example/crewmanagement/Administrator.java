/*
 * FILE:              Administrator.Java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Alex Palmer
 * OTHER MEMBERS:    Alex MacCumber, David Obeda, Stephen Perrin, Marissa Schmitt
 * FIRST VERSION:    February 6th, 2020
 * DESCRIPTION:      This file describes the functionality of the admin screen. This screen adds
 *                   new members to the data class and more jobs
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
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class Administrator extends AppCompatActivity {

    Spinner Members;//the members dropdown

    Spinner sMenu;//the menu dropdown
    Button NewMember;//button for creating new members
    Button DelMember;// button to delete  members
    Data info;// data class to hold the information needed for the app
    Integer pointing;//location of selected members

    ArrayAdapter<String> adapter;// adapter for spinner
    ArrayList<String> names; // array of names

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);
        ArrayList<String> MenuItems = new ArrayList<String>();//creating an array list
        names = new ArrayList<String>();//creating an array list of names
        Intent intent = getIntent();//creating a new intent
        Bundle data = intent.getExtras();//getting extras from the intent
        info = (Data)data.getSerializable("data");
        Members = (Spinner)findViewById(R.id.ListofMembers);//retrieving the view by specifying its id
        sMenu = (Spinner)findViewById(R.id.sMenu);//retrieving the view by specifying its id
        NewMember = (Button)findViewById(R.id.newMemBtn);//retrieving the view by specifying its id
        DelMember = (Button)findViewById(R.id.delMemBtn);//retrieving the view by specifying its id
        Integer numMem = info.getNumberOfMembers();
        Integer numjob = info.getCompletedJobs() + info.getUncompletedJobs();
        Integer count2 = 0;
        Integer count = 1;
        String name;
        // adding names, jobs and menu options to the arrays
        while(count.intValue() != numMem.intValue())
        {
            name = info.getName(count++);
            names.add(name);
        }
        //Adding items to menu for navigation
        MenuItems.add("Administrator Screen");//adding Administrator Screen to the menu
        MenuItems.add("Members Screen");//adding Members Screen to the menu
        MenuItems.add("Progress Screen");//adding Progress Screen to the menu
        MenuItems.add("Job Assignment Screen");//adding Job Assignment Screen to the menu
        MenuItems.add("News Feed Screen");//adding News Feed Screen to the menu
        MenuItems.add("Log out");//adding log out to the menu

        //putting the arrays into each adapter and put the adapter into the spinners
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,MenuItems);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMenu.setAdapter(mAdapter);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Members.setAdapter(adapter);

        Members.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /*
             * FUNCTION:	onItemSelected
             * DESCRIPTION:	Whenever an item is selected
             * PARAMETERS:	AdapterView<?> parent, View view, int position, long id
             * RETURNS:	void - this function returns nothing
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                pointing = position + 1;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /*
             * FUNCTION:	onItemSelected
             * DESCRIPTION:	Whenever an item is selected
             * PARAMETERS:	AdapterView<?> parent, View view, int position, long id
             * RETURNS:	void - this function returns nothing
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0)//ensuring the user didn't select the screen they're currently on
                {
                    if(position == 1)//seeing if the user chose the first option
                    {
                        //creating a new intent for the members screen
                        Intent intent = new Intent(Administrator.this, MembersScreen.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", info);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("Admin","Going to members screen.");
                        finish();
                    }
                    else if(position == 2)//seeing if the user chose the second option
                    {
                        //creating a new intent for the progress screen
                        Intent intent = new Intent(Administrator.this, ProgressScreen.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", info);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("Admin","Going to progress screen.");
                        finish();
                    }
                    else if(position == 3)//seeing if the user chose the third option
                    {
                        //creating a new intent for the job assignment screen
                        Intent intent = new Intent(Administrator.this, JobAssignment.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", info);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("Admin","Going to the job assignment screen.");
                        finish();
                    }
                    else if(position == 4)//seeing if the user chose the fourth option
                    {
                        Intent intent = new Intent(Administrator.this, NewsFeed.class);
                        intent.putExtra("data", info);
                        startActivity(intent);
                        Log.i("Admin","Going to news feed screen.");
                        finish();
                    }
                    else if(position == 5)//seeing if the user chose the fifth option
                    {
                        //creating a new intent for the login screen
                        Intent intent = new Intent (Administrator.this, MainActivity.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", info);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("Admin","Going to login screen.");
                        finish();
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
     * FUNCTION:	btnNewMember
     * DESCRIPTION:	Whenever button is clicked
     * PARAMETERS:	android.view.View v
     * RETURNS:	void - this function returns nothing
     */
    public void btnNewMember(android.view.View v)
    {
        //creating a new intent for the new member screen
        Intent intent = new Intent(Administrator.this, NewMember.class);// points to new activity
        //putting the Data object into the extras
        intent.putExtra("data", info);// send information to activity
        //starting the new activity
        startActivity(intent);//start activity
        Log.i("Admin","Going to new member screen.");
    }
    /*
     * FUNCTION:	btnDelMember
     * DESCRIPTION:	Whenever button is clicked
     * PARAMETERS:	android.view.View v
     * RETURNS:	void - this function returns nothing
     */
    public void btnDelMember(android.view.View v)
    {
        info.removeMember(pointing);
        names.remove(pointing - 1);//remove array
        adapter.notifyDataSetChanged();// update adapter
        Log.i("Admin","Deleting selected member.");
    }

}
