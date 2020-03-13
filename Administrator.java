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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class Administrator extends AppCompatActivity {

    Spinner Members;//the members dropdown

    //Spinner sMenu;//the menu dropdown
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

        //putting the arrays into each adapter and put the adapter into the spinners
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,MenuItems);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Members.setAdapter(adapter);
    }


    /*
     * FUNCTION:	 onCreateOptionsMenu
     * DESCRIPTION:	This function is event handler for whenever the options menu is created
     * PARAMETERS:	Menu menu - this parameter indicates what menu is created
     * RETURNS:	    bool - this function returns a boolean value based on it's operation
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //inflating the specified menu for the administrator screen
        getMenuInflater().inflate(R.menu.activity_administrator, menu);
        return true;
    }



    /*
     * FUNCTION:	 onOptionsItemSelected
     * DESCRIPTION:	This function is event handler for whenever an option within the menu has been selected
     * PARAMETERS:	MenuItem item - this parameter indicates what item has been selected
     * RETURNS:	    bool - this function returns a boolean value based on it's operation
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_activity2:
                Intent intent = new Intent(Administrator.this, MembersScreen.class);
                //putting the Data object into the extras
                intent.putExtra("data", info);
                //starting the new activity
                startActivity(intent);
                Log.i("Admin","Going to members screen.");
                finish();
                return true;
            case R.id.menu_activity3:
                //creating a new intent for the view screen
                Intent intent2 = new Intent(Administrator.this, ViewScreen.class);
                //putting the Data object into the extras
                intent2.putExtra("data", info);
                //starting the new activity
                startActivity(intent2);
                Log.i("Admin","Going to view screen.");
                finish();
                return true;
            case R.id.menu_activity4:
                //creating a new intent for the job assignment screen
                Intent intent3 = new Intent(Administrator.this, JobAssignment.class);
                //putting the Data object into the extras
                intent3.putExtra("data", info);
                //starting the new activity
                startActivity(intent3);
                Log.i("Admin","Going to the job assignment screen.");
                finish();
                return true;
            case R.id.menu_activity5:
                Intent intent4 = new Intent(Administrator.this, NewsFeed.class);
                intent4.putExtra("data", info);
                startActivity(intent4);
                Log.i("Admin","Going to news feed screen.");
                finish();
                return true;
            case R.id.menu_activity6:
                //creating a new intent for the login screen
                Intent intent5 = new Intent (Administrator.this, MainActivity.class);
                //putting the Data object into the extras
                intent5.putExtra("data", info);
                //starting the new activity
                startActivity(intent5);
                Log.i("Admin","Going to login screen.");
                finish();
                return true;
            default:
                return false;
        }
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
