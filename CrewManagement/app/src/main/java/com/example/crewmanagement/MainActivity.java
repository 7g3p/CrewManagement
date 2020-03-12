/*
 * FILE:              MainActivity.Java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Alex Palmer
 * OTHER MEMBERS:    Alex MacCumber, David Obeda, Stephen Perrin, Marissa Schmitt
 * FIRST VERSION:    February 6th, 2020
 * DESCRIPTION:      This file describes the functionality of the admin screen. This screen adds
 *                   new members to the data class and more jobs
 */
package com.example.crewmanagement;

import androidx.appcompat.app.AppCompatActivity;
import com.example.crewmanagement.Data;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText UserName;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private Data myData;
    private Integer counter = 5;
    Intent intent;
    /*
     * FUNCTION:   onCreate
     * DESCRIPTION:    creates the loginscreen
     * PARAMETERS: Bundle savedInstanceState
     * RETURNS:    void - this function returns nothing
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();//
        Bundle check = intent.getExtras();
        UserName = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.bLogin);
        if(check != null)//ensuring that the bundle isn't null
        {
            myData = (Data)check.getSerializable("data");
        }
        else
        {
            myData = new Data("admin","1234", "Alex", 20, "Febuary 4th", "Administrator", null, null);//creating a new data object
        }
    }



    /*
     * FUNCTION:   onClick
     * DESCRIPTION:    Whenever the user presses a button
     * PARAMETERS: android.view.View v
     * RETURNS:    void - this function returns nothing
     */
    public void onClick(android.view.View v)
    {
        check(UserName.getText().toString(),Password.getText().toString(), myData);
        startActivity(intent);
        Log.i("Login","Logging in...");
    }
    /*
     * FUNCTION:   btnChangepass
     * DESCRIPTION:    Check if the user has the correct username and password
     * PARAMETERS: String userName, String userPassword, Data data
     * RETURNS:    void - this function returns nothing
     */
    private void check(String userName, String userPassword, Data info){
        Data newData = info;
        Integer number = 0;
        Integer location = -1;
        Integer admin = 0;
// checks each password and user
        while(number != newData.getNumberOfMembers())
        {
            String nam = newData.getUsername(number);
            String pas = newData.getPassword(number);
            if(userName.equals(nam) && userPassword.equals(pas))
            {
                location = number;
                //check if admin
                if(location.equals(0))
                {
                    admin = 1;
                }
            }
            number = number + 1;
        }
        if(location != -1)
        {
            // if admin
            if(admin.equals(1))
            {
                newData.changeAdmin(1);
                intent = new Intent(MainActivity.this, Administrator.class);
                intent.putExtra("data", newData);
            }
            else
            {
                newData.changeAdmin(0);
                //if username is the same as password
                if(userName.equals(userPassword))
                {
                    intent = new Intent(MainActivity.this, ChangePassword.class);
                    intent.putExtra("location", location.intValue());
                    intent.putExtra("data", newData);

                }
                else {
                    //intent = new Intent(MainActivity.this, (memberlistname).class);
                    //intent.putExtra("data", newData);
                }
            }
        }
        else
        {
            // reduces the amount of attempts left
            counter--;
            Info.setText("Number of attempts: " + String.valueOf(counter));
            if(counter.equals(0))
            {
                Login.setEnabled(false);
            }
        }
    }
}




