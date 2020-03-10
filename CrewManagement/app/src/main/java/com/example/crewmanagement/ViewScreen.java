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

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ViewScreen extends AppCompatActivity {
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

}
