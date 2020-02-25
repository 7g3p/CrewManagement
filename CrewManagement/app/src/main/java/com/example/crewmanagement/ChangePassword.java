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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import java.util.ArrayList;

/*
 * FUNCTION:	Change password
 * DESCRIPTION:	THis creates the change password screen
 * PARAMETERS:	String newAssignedJob - represents the assigned job entered by the user
 *		Bundle savedInstanceState
 * RETURNS:	void - this function returns nothing
 */
public class ChangePassword extends AppCompatActivity {

    Integer Location;
    Data data;
    EditText Password;
    Button Enter;

    Spinner sMenu = null;//the menu dropdown
    ArrayList<String> MenuItems = new ArrayList<String>();//creating an array list

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        Intent intent = getIntent();
        Location = new Integer(intent.getIntExtra("location", -1));
        data = (Data)intent.getExtras().getSerializable("data");
        Password = (EditText)findViewById(R.id.passchng);
        Enter = (Button)findViewById(R.id.chngpassbtn);
        sMenu = (Spinner)findViewById(R.id.passwordMenu);//retrieving the view by specifying its id and casting it as a spinner


//Adding items to menu for navigation
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
                if(position > 0)//ensuring the user didn't select the screen they're currently on
                {
                    if(position == 0)//seeing if the user chose the first option
                    {
                        //creating a new intent for the members screen
                        Intent intent = new Intent(ChangePassword.this, MembersScreen.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("ChangePassword","Going to members screen.");
                    }
                    else if(position == 1)//seeing if the user chose the second option
                    {
                        //creating a new intent for the progress screen
                        Intent intent = new Intent(ChangePassword.this, ProgressScreen.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("ChangePassword","Going to progress screen.");
                    }
                    else if(position == 2)//seeing if the user chose the third option
                    {
                        //creating a new intent for the job assignment screen
                        Intent intent = new Intent(ChangePassword.this, JobAssignment.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("ChangePassword","Going to the job assignment screen.");
                    }
                    else if(position == 3)//seeing if the user chose the fourth option
                    {
                        //creating a new intent for the news feed screen
                        Intent intent = new Intent(ChangePassword.this, NewsFeed.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("ChangePassword","Going to news feed screen.");
                    }
                    else if(position == 4)//seeing if the user chose the fifth option
                    {
                        //creating a new intent for the login screen
                        Intent intent = new Intent (ChangePassword.this, MainActivity.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("ChangePassword","Going to login screen.");
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
     * FUNCTION:	btnChangepass
     * DESCRIPTION:	Whenever the user presses a button
     * PARAMETERS:	android.view.View v
     * RETURNS:	void - this function returns nothing
     */
    public void btnchangepass(android.view.View v) {
        if (Password.getText().toString().equals("")) {
            Password.setText("None");
        }

        data.changePassword(Password.getText().toString(),Location);
        //Intent intent = new Intent(ChangePassword.this, (MemberScreenName).class);
        //intent.putExtra("data", data);
        //startActivity(intent);
        Log.i("ChangePassword","Password has been changed.");

    }
}
