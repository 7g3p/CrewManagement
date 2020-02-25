/*
 * FILE : NewsFeed.java
 * PROJECT : PROG3150 - Assignment #1
 * PROGRAMMER : Stephen Perrin, Alex Palmer, Alex MacCumber, David Obeda, Marissa Schmitt
 * FIRST VERSION : 2020-02-08
 * DESCRIPTION :
 * The news feed page is to be a yay to provide communication to the workforce, updating them on
 * issues or developments, without having to message every individual. Includes timestamps!
 */

package com.example.crewmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


//Class         : NewsFeed
//Description   : Supports the news feed page, where the user can post information for other users
//Members       : userMessage - a EditText widget to allow the user to write their posts
//Members       : MessageHistory - Textview widget to allow the user to view post history
public class NewsFeed extends AppCompatActivity {

    private EditText userMessage;   //Compose message box
    private TextView MessageHistory;//View posted messages
    Spinner sMenu = null;//the menu dropdown
    Data data = null;//initializing the Data object to null (not containing the appropriate widget yet)

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
        setContentView(R.layout.activity_news_feed);
        userMessage = findViewById(R.id.composeMessage);
        MessageHistory = findViewById(R.id.messageview);
        sMenu = (Spinner)findViewById(R.id.newsFeedMenu);//retrieving the view by specifying its id and casting it as a spinner
        ArrayList<String> MenuItems = new ArrayList<String>();//creating an array list

        setTitle("News Feed");
        Intent intent = getIntent();//getting the intent and storing it in a local instance
        Bundle info = intent.getExtras();//getting the intent and storing it in a local instance
        data = (Data) info.getSerializable("data");//retrieving the data and casting it as a Data object

        //Adding items to menu for navigation
        MenuItems.add("Administrator Screen");//adding Administrator Screen to the menu
        MenuItems.add("Members Screen");//adding Members Screen to the menu
        MenuItems.add("Progress Screen");//adding Progress Screen to the menu
        MenuItems.add("Job Assignment Screen");//adding Job Assignment Screen to the menu
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
                        //creating a new intent for the members screen
                        Intent intent = new Intent(NewsFeed.this, Administrator.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("NewsFeedScreen","Going to the administrator screen.");
                    }
                    else if(position == 1)//seeing if the user chose the second option
                    {
                        //creating a new intent for the progress screen
                        Intent intent = new Intent(NewsFeed.this, MembersScreen.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("NewsFeedScreen","Going to the members screen.");
                    }
                    else if(position == 2)//seeing if the user chose the third option
                    {
                        //creating a new intent for the progress screen
                        Intent intent = new Intent(NewsFeed.this, ProgressScreen.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("NewsFeedScreen","Going to the job assignment screen.");
                    }
                    else if(position == 3)//seeing if the user chose the fourth option
                    {
                        //creating a new intent for the job assignment screen
                        Intent intent = new Intent(NewsFeed.this, JobAssignment.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("NewsFeedScreen","Going to the job assignment screen.");
                    }
                    else if(position == 4)//seeing if the user chose the fifth option
                    {
                        //creating a new intent for the login screen
                        Intent intent = new Intent (NewsFeed.this, MainActivity.class);
                        //putting the Data object into the extras
                        intent.putExtra("data", data);
                        //starting the new activity
                        startActivity(intent);
                        Log.i("NewsFeedScreen","Going to the login screen.");
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

    //Function      : sendMessage
    //Description   : Posts the message to the news feed
    //Parameters    : android.view.View v
    //Returns       : Void
    public void sendMessage(android.view.View v)
    {
        //Grabs a timestamp to append to the message
        SimpleDateFormat timePattern = new SimpleDateFormat("h:mm a");
        Date currentTime = new Date();
        String sTime = timePattern.format(currentTime);

        //append the message to the display box
        MessageHistory.append('\t' + userMessage.getText().toString() +"\n@ " + sTime + "\n\n");
        userMessage.setText("");
    }
}
