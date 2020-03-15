/*
 * FILE : NewsFeed.java
 * PROJECT : PROG3150 - Assignment #1
 * PROGRAMMER : Stephen Perrin, Alex Palmer, Alex MacCumber, David Obeda, Marissa Schmitt
 * FIRST VERSION : 2020-02-08
 * DESCRIPTION :
 * The news feed page is to be a way to provide communication to the workforce, updating them on
 * issues or developments, without having to message every individual. Includes timestamps!
 */

package com.example.crewmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.ArrayList;



//Class         : NewsFeed
//Description   : Supports the news feed page, where the user can post information for other users
//Members       : userMessage - a EditText widget to allow the user to write their posts
//Members       : MessageHistory - Textview widget to allow the user to view post history
public class NewsFeed extends AppCompatActivity {


    Spinner sMenu = null;//the menu dropdown
    Data data = null;//initializing the Data object to null (not containing the appropriate widget yet)
    private WebView NewsFeed;
    private Button CBCbtn;
    private Button CTVbtn;
    private Button GBLbtn;


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
        ArrayList<String> MenuItems = new ArrayList<String>();//creating an array list

        setTitle("News Feed");
        Intent intent = getIntent();//getting the intent and storing it in a local instance
        Bundle info = intent.getExtras();//getting the intent and storing it in a local instance
        data = (Data) info.getSerializable("data");//retrieving the data and casting it as a Data object

        //build the web view
        NewsFeed = (WebView)findViewById(R.id.newsFeed);
        NewsFeed.setWebViewClient(new WebViewClient());
        NewsFeed.getSettings().setJavaScriptEnabled(true);
        NewsFeed.getSettings().setDomStorageEnabled(true);
        NewsFeed.loadUrl("https://www.cbc.ca/news/canada");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu_job_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menu_activity1:
                Intent intent1 = new Intent(NewsFeed.this, Administrator.class);
                //putting the Data object into the extras
                intent1.putExtra("data", data);
                //starting the new activity
                startActivity(intent1);
                Log.i("View","Going to adminstrator screen.");
                finish();
                return true;
            case R.id.menu_activity2:
                Intent intent2 = new Intent(NewsFeed.this, MembersScreen.class);
                //putting the Data object into the extras
                intent2.putExtra("data", data);
                //starting the new activity
                startActivity(intent2);
                Log.i("View","Going to members screen.");
                finish();
                return true;
            case R.id.menu_activity6:
                //creating a new intent for the login screen
                Intent intent5 = new Intent (NewsFeed.this, MainActivity.class);
                //putting the Data object into the extras
                intent5.putExtra("data", data);
                //starting the new activity
                startActivity(intent5);
                Log.i("View","Going to login screen.");
                finish();
                return true;
            case R.id.menu_activity4:
                //creating a new intent for the job assignment screen
                Intent intent7 = new Intent(NewsFeed.this, JobAssignment.class);
                //putting the Data object into the extras
                intent7.putExtra("data", data);
                //starting the new activity
                startActivity(intent7);
                Log.i("View","Going to the job assignment screen.");
                finish();
                return true;
            case R.id.menu_activity3:
                //creating a new intent for the login screen
                Intent intent3 = new Intent (NewsFeed.this, ViewScreen.class);
                //putting the Data object into the extras
                intent3.putExtra("data", data);
                //starting the new activity
                startActivity(intent3);
                Log.i("View","Going to view screen.");
                finish();
                return true;

            default:
                return false;
        }
    }


    /*
     * FUNCTION:	onClickCBC
     * DESCRIPTION:	Changes the Webviews target URL to CBC news
     * RETURNS:	void - this function returns nothing
     */
    public void onClickCBC(android.view.View v)
    {
        NewsFeed.loadUrl("https://www.cbc.ca/news/canada");
    }

    /*
     * FUNCTION:	onClickCTV
     * DESCRIPTION:	Changes the Webviews target URL to CTV news
     * RETURNS:	void - this function returns nothing
     */
    public void onClickCTV(android.view.View v)
    {
        NewsFeed.loadUrl("https://www.ctvnews.ca/");
    }

    /*
     * FUNCTION:	onClickGBL
     * DESCRIPTION:	Changes the Webviews target URL to Global news
     * RETURNS:	void - this function returns nothing
     */
    public void onClickGBL(android.view.View v)
    {
        NewsFeed.loadUrl("https://globalnews.ca/");
    }


}
