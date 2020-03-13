/*
 * FILE              ViewScreen.java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Marissa Schmitt
 * OTHER MEMBERS:    Alex MacCumber, David Obeda, Alex Palmer, Stephen Perrin
 * FIRST VERSION:    March 5th, 2020
 * DESCRIPTION:      This file describes the functionality and events of the view screen containing
 *                      the members and progress fragments.
 */
package com.example.crewmanagement;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import java.net.URI;

public class ViewScreen extends AppCompatActivity {

    Data info;// data class to hold the information needed for the app

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
        setContentView(R.layout.view_screen);

        setTitle("View Screen");
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

        Intent intent = getIntent();//creating a new intent
        Bundle data = intent.getExtras();//getting extras from the intent
        info = (Data)data.getSerializable("data");
        //inflating the specified menu for the administrator screen
        getMenuInflater().inflate(R.menu.view_screen, menu);
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
            case R.id.menu_activity1:
                Intent intent1 = new Intent(ViewScreen.this, Administrator.class);
                //putting the Data object into the extras
                intent1.putExtra("data", info);
                //starting the new activity
                startActivity(intent1);
                Log.i("View","Going to adminstrator screen.");
                finish();
                return true;
            case R.id.menu_activity2:
                Intent intent2 = new Intent(ViewScreen.this, MembersScreen.class);
                //putting the Data object into the extras
                intent2.putExtra("data", info);
                //starting the new activity
                startActivity(intent2);
                Log.i("View","Going to members screen.");
                finish();
                return true;
            case R.id.menu_activity4:
                //creating a new intent for the job assignment screen
                Intent intent3 = new Intent(ViewScreen.this, JobAssignment.class);
                //putting the Data object into the extras
                intent3.putExtra("data", info);
                //starting the new activity
                startActivity(intent3);
                Log.i("View","Going to the job assignment screen.");
                finish();
                return true;
            case R.id.menu_activity5:
                Intent intent4 = new Intent(ViewScreen.this, NewsFeed.class);
                intent4.putExtra("data", info);
                startActivity(intent4);
                Log.i("View","Going to news feed screen.");
                finish();
                return true;
            case R.id.menu_activity6:
                //creating a new intent for the login screen
                Intent intent5 = new Intent (ViewScreen.this, MainActivity.class);
                //putting the Data object into the extras
                intent5.putExtra("data", info);
                //starting the new activity
                startActivity(intent5);
                Log.i("View","Going to login screen.");
                finish();
                return true;
            case R.id.menu_activity7:
                //declaring the tech support number
                String telephoneNum = "519-555-1234";
                //parsing the number and appending the telephone identifier to it
                Uri viewUri = Uri.parse("tel:" + telephoneNum);
                //creating a new intent to make an action_dial
                Intent callIntent = new Intent(Intent.ACTION_DIAL, viewUri);
                //starting the dial activity
                startActivity(callIntent);
                Log.i("View", "Calling tech support.");
                return true;

            default:
                return false;
        }
    }

}
