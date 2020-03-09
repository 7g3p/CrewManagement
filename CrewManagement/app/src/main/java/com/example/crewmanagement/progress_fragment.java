/*
 * FILE              progress_fragment.java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Marissa Schmitt
 * OTHER MEMBERS:    Alex MacCumber, David Obeda, Alex Palmer, Stephen Perrin
 * FIRST VERSION:    March 5th, 2020
 * DESCRIPTION:      This file describes the functionality and events of the progress screen within
 *                      the view screen.
 */
package com.example.crewmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.crewmanagement.ui.progress.ProgressFragmentFragment;

public class progress_fragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.progress_fragment_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, ProgressFragmentFragment.newInstance())
                    .commitNow();
        }
    }
}
