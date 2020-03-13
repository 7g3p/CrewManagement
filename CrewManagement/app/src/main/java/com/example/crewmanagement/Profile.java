package com.example.crewmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.ContextThemeWrapper;

public class Profile extends AppCompatActivity {


    EditText eName;
    EditText eUsername;
    EditText ePassword;
    EditText eAge;
    EditText ePhone;
    ImageView iPhoto;
    Button bName;
    Button bUsername;
    Button bPassword;
    Button bAge;
    Button bPhone;
    Data info;
    DBAdapter db;
    String fName;
    String lName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        eName = (EditText)findViewById(R.id.eName);
        eUsername = (EditText)findViewById(R.id.eUsername);
        ePassword = (EditText)findViewById(R.id.ePassword);
        eAge = (EditText)findViewById(R.id.eAge);
        ePhone = (EditText)findViewById(R.id.ePhone);
        bName = (Button)findViewById(R.id.bName);
        bUsername = (Button)findViewById(R.id.bUsername);
        bPassword = (Button)findViewById(R.id.bPassword);
        bAge = (Button)findViewById(R.id.bAge);
        bPhone = (Button)findViewById(R.id.bPhone);
        iPhoto = (ImageView)findViewById(R.id.iPhoto);
        Intent intent = getIntent();//creating a new intent
        Bundle data = intent.getExtras();//getting extras from the intent
        if(data != null)//ensuring that the bundle isn't null
        {
            info = (Data)data.getSerializable("data");
        }
        else {
            // info = getData();
            info = new Data("admin","1234", "Alex", 20, "Febuary 4th", "boss", "519-555-5555", newMember());//creating a new data object

        }
        eName.setText(info.getName(info.user));
        eUsername.setText(info.getUsername(info.user));
        ePassword.setText(info.getPassword(info.user));
        eAge.setText(info.getAge(info.user).toString());
        ePhone.setText((info.getPhonenumber(info.user)));
        Byte[] picture = info.getImage(info.user);
        byte[] bpicture = new byte[picture.length];
        int j=0;
        for(Byte b: picture)
        {
            bpicture[j++] = b.byteValue();
        }
        Drawable drawable = new BitmapDrawable(getResources(), BitmapFactory.decodeByteArray(bpicture, 0, bpicture.length));
        iPhoto.setImageDrawable(drawable);
        ContentValues cv = new ContentValues();
        fName = new String();
        lName = new String();
        bName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eName.getText().toString().contains("1") || eName.getText().toString().contains("2") || eName.getText().toString().contains("3") || eName.getText().toString().contains("4") || eName.getText().toString().contains("5") || eName.getText().toString().contains("6") || eName.getText().toString().contains("7") || eName.getText().toString().contains("8") || eName.getText().toString().contains("9") || eName.getText().toString().contains("0"))
                {
                }
                else if (eName.getText().toString().contains(" "))
                {
                    fName = eName.getText().toString().substring(0, eName.getText().toString().indexOf(' '));
                    lName = eName.getText().toString().substring(eName.getText().toString().indexOf(' ') + 1, eName.length());

                    if (fName.contains(" ") || lName.contains(" "))
                    {
                    }
                    else
                    {
                        db.UpdateName(null, fName, lName);
                    }
                }
                else
                {
                    String[] args = new String[3];
                    args[0] = fName;
                    args[1] = lName;
                    db.UpdateName(null, fName, lName);
                }
            }
        });
        bUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.UpdateUsername(info.username.toString(), eUsername.getText().toString());
            }
        });
        bPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.UpdatePassword(eName.getText().toString(), ePassword.getText().toString());
            }
        });
        bAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer age = Integer.parseInt(eAge.getText().toString());
                if (age <= 15 || age >= 80)
                {

                }
                else
                {
                    Integer[] args = new Integer[2];
                    db.UpdateAge(info.username.toString(), age);
                }
            }
        });
        bPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.UpdatePhone(info.username.toString(), ePhone.getText().toString());
            }
        });


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
}
