package com.example.crewmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;

public class Navigation extends AppCompatActivity {

    PopupWindow popup;
    LinearLayout layout;
    Boolean click;
    TextView text;
    LinearLayout.LayoutParams params;
    Button button;
    Button but_Admin;
    Button but_Members;
    Button but_Jobs;
    Button but_Progress;
    Button but_Newsfeed;
    Button but_Profile;
    Data info;
    DBAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        but_Admin = (Button)findViewById(R.id.but_Admin);
        but_Members = (Button)findViewById(R.id.but_Member);
        but_Jobs = (Button)findViewById(R.id.but_Job);
        but_Progress = (Button)findViewById(R.id.but_Progress);
        but_Newsfeed = (Button)findViewById(R.id.but_Newsfeed);
        but_Profile = (Button)findViewById(R.id.but_Profile);
        Intent intent = getIntent();//creating a new intent
        Bundle data = intent.getExtras();//getting extras from the intent
        if(data != null)//ensuring that the bundle isn't null
        {
            info = (Data)data.getSerializable("data");
        }
        else {
            adapter = new DBAdapter(this);
            info = adapter.GetData();
        }
        but_Admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.crewmanagement.Navigation.this, Administrator.class);
                intent.putExtra("data", info);
                startActivity(intent);
            }
        });
        but_Members.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.crewmanagement.Navigation.this, MembersScreen.class);
                intent.putExtra("data", info);
                startActivity(intent);
            }
        });
        but_Jobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.crewmanagement.Navigation.this, JobAssignment.class);
                intent.putExtra("data", info);
                startActivity(intent);
            }
        });
        but_Progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.crewmanagement.Navigation.this, ViewScreen.class);
                intent.putExtra("data", info);
                startActivity(intent);
            }
        });
        but_Newsfeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.crewmanagement.Navigation.this, NewsFeed.class);
                intent.putExtra("data", info);
                startActivity(intent);
            }
        });
        but_Profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(com.example.crewmanagement.Navigation.this, Profile.class);
                intent.putExtra("data", info);
                startActivity(intent);
            }
        });
        Integer ID = getIntent().getIntExtra("ID", 0);
        if( !ID.equals(0))
        {
            but_Admin.setEnabled(false);
        }
    }
    public Byte[] newMember(){
        Drawable d = getResources().getDrawable(R.drawable.ic_launcher_foreground);
        Bitmap bitmap = ((BitmapDrawable)d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bimage = stream.toByteArray();
        Byte[] image = new Byte[bimage.length];
        for (int i = 0; i < bimage.length; i++)
        {
            image[i] = Byte.valueOf(bimage[i]);
        }
        return image;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        popup = new PopupWindow(this);
        layout = new LinearLayout(this);
        click = true;
        text = new TextView(this);
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        button = new Button(this);
        button.setText("OK");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup.dismiss();
            }
        });
        layout.setOrientation(LinearLayout.VERTICAL);
        text.setText("This screen is to help you navigate throughout the application");
        layout.setBackgroundColor(Color.WHITE);
        layout.addView(text,params);
        layout.addView(button, params);
        popup.setContentView(layout);

        switch(item.getItemId())
        {
            case R.id.about:
                popup.showAtLocation(layout, Gravity.BOTTOM, 10, 10);
                popup.update(50,900,300,400);
                break;
            case R.id.sign_out:
                Intent intent = new Intent(Navigation.this, MainActivity.class);
                startActivity(intent);
                break;





        }
        return super.onOptionsItemSelected(item);
    }

}
