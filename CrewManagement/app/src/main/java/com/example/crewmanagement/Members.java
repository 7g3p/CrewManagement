/*
 * FILE              Members.java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Marissa Schmitt
 * OTHER MEMBERS:    Alex MacCumber, David Obeda, Alex Palmer, Stephen Perrin
 * FIRST VERSION:    March 5th, 2020
 * DESCRIPTION:      This file describes the functionality and events of the members fragment
 *                      within the view screen.
 */
package com.example.crewmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.crewmanagement.ui.members.MembersFragment;

public class Members extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.members_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MembersFragment.newInstance())
                    .commitNow();
        }
    }
}
