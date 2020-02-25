/*
 * FILE:              NewMember.java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Alex Palmer
 * OTHER MEMBERS:    Alex MacCumber, David Obeda, Stephen Perrin, Marissa Schmitt
 * FIRST VERSION:    February 6th, 2020
 * DESCRIPTION:      This file describes the functionality of the NewMember screen. This screen adds
 *                   new member to the data class
 */
package com.example.crewmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class NewMember extends AppCompatActivity {


    Spinner sMenu = null;//the menu dropdown
    private EditText UserName;
    private EditText Name;
    private EditText Age;
    private EditText Job;
    private EditText Date;
    private Button Enter;
    private Data data;


    /*
     * FUNCTION:	onCreate
     * DESCRIPTION:	creates the NewMember screen
     * PARAMETERS:	Bundle savedInstanceState
     * RETURNS:	void - this function returns nothing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_member);
        Intent intent = getIntent();
        Bundle info = intent.getExtras();
        data = (Data)info.getSerializable("data");
        UserName = (EditText)findViewById(R.id.Username);
        Name = (EditText)findViewById(R.id.Name);
        Age = (EditText)findViewById(R.id.Age);
        Job = (EditText)findViewById(R.id.Job);
        Date = (EditText)findViewById(R.id.DateofHire);
        Enter = (Button)findViewById(R.id.pbtn);

        sMenu = (Spinner)findViewById(R.id.newMemSpinner);//retrieving the view by specifying its id and casting it as a spinner
        ArrayList<String> MenuItems = new ArrayList<String>();//creating an array list


//Adding items to menu for navigation
        MenuItems.add("Administration Screen");//adding Administration Screen to the menu
        MenuItems.add("Members Screen");//adding Members Screen to the menu
        MenuItems.add("Progress Screen");//adding Progress Screen to the menu
        MenuItems.add("Job Assignment Screen");//adding Job Assignment Screen to the menu
        MenuItems.add("News Feed Screen");//adding News Feed Screen to the menu
        MenuItems.add("Log out");//adding log out to the menu

//putting the arrays into each adapter and put the adapter into the spinners
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,MenuItems);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sMenu.setAdapter(mAdapter);



        sMenu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            /*
             * FUNCTION:   onItemSelected
             * DESCRIPTION:    Whenever an item is selected
             * PARAMETERS: AdapterView<?> parent, View view, int position, long id
             * RETURNS:    void - this function returns nothing
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position >= 0)//ensuring the user didn't select the screen they're currently on
                {
                    if(position == 0)//seeing if the user chose the first option
                    {
                        //creating a new intent for the members screen
                        Intent intent = new Intent(NewMember.this, Administrator.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("NewMember","Going to the administrator screen.");
                    }
                    else if(position == 1)//seeing if the user chose the second option
                    {
                        //creating a new intent for the progress screen
                        Intent intent = new Intent(NewMember.this, MembersScreen.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("NewMember","Going to the members screen.");
                    }
                    else if(position == 2)//seeing if the user chose the second option
                    {
                        //creating a new intent for the progress screen
                        Intent intent = new Intent(NewMember.this, ProgressScreen.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("NewMember","Going to the progress screen.");
                    }
                    else if(position == 3)//seeing if the user chose the third option
                    {
                        //creating a new intent for the job assignment screen
                        Intent intent = new Intent(NewMember.this, JobAssignment.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("NewMember","Going to the job assignment screen.");
                    }
                    else if(position == 4)//seeing if the user chose the fourth option
                    {
                        //creating a new intent for the news feed screen
                        Intent intent = new Intent(NewMember.this, NewsFeed.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the activity
                        startActivity(intent);
                        Log.i("NewMember","Going to the news feed screen.");
                    }
                    else if(position == 5)//seeing if the user chose the fifth option
                    {
                        //creating a new intent for the login screen
                        Intent intent = new Intent (NewMember.this, MainActivity.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("NewMember","Going to the login screen.");
                    }
                }

            }
            /*
             * FUNCTION:   onNothingSelected
             * DESCRIPTION:    Whenever an item is not selected
             * PARAMETERS: AdapterView<?> parent
             * RETURNS:    void - this function returns nothing
             */

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    /*
     * FUNCTION:	newMember
     * DESCRIPTION:	When the button to create a new member in the list is pressed
     * PARAMETERS:	amdroid.View.view v
     * RETURNS:	void - this function returns nothing
     */
    public void newMember(android.view.View v){
        if(UserName.getText().toString().equals(""))
        {
            UserName.setText("None");
        }
        if(Name.getText().toString().equals(""))
        {
            Name.setText("unknown");
        }
        if(Age.getText().toString().equals(""))
        {
            Age.setText("0");
        }
        if(Job.getText().toString().equals(""))
        {
            Job.setText("None");
        }
        if(Date.getText().toString().equals(""))
        {
            Date.setText("Unknown");
        }
        String un = UserName.getText().toString();
        String pas = un;
        String name = Name.getText().toString();
        String age = Age.getText().toString();
        String date = Date.getText().toString();
        String job = Job.getText().toString();
        Log.i("NewMember","New member has been created.");
        data.addMember(un,name,Integer.parseInt(age),date,job);
        Intent intent = new Intent(NewMember.this, Administrator.class);
        intent.putExtra("data", data);
        startActivity(intent);


    }
}
