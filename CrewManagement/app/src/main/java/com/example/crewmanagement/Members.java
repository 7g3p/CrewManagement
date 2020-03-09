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
