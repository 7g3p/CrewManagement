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
