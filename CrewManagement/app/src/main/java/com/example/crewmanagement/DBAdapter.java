package com.example.crewmanagement;

import com.example.crewmanagement.Data;

import java.io.File;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter
{
    // Database Constants
    private static final String DB_NAME = " CrewManagementDBVersion1";
    private static final int DB_VERSION = 1;
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // Create Tables Constants
    public static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS `Users` (" +
            " `MemberID` INTEGER DEFAULT NULL UNIQUE," +
            " `Username` TEXT DEFAULT NULL UNIQUE," +
            " `Password` TEXT DEFAULT NULL UNIQUE," +
            " `Access` INTEGER NOT NULL DEFAULT 0 CHECK(Access >= 0 AND Access <= 1)," +
            " FOREIGN KEY(`MemberID`) REFERENCES `Members`(`MemberID`)" +
            ")";
    public static final String CREATE_MEMBERS_TABLE = "CREATE TABLE IF NOT EXISTS `Members` (" +
            " `MemberID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
            " `FirstName` TEXT NOT NULL DEFAULT 'N/A'," +
            " `LastName` TEXT NOT NULL DEFAULT 'N/A'," +
            " `Age` INTEGER DEFAULT NULL," +
            " `Phone` TEXT DEFAULT NULL," +
            " `DateOfHire` TEXT NOT NULL DEFAULT '00-00-0000'," +
            " `ProfilePicture` BLOB DEFAULT NULL" +
            ")";
    public static final String CREATE_JOBS_TABLE = "CREATE TABLE IF NOT EXISTS `Jobs` (" +
            " `JobID` INTEGER DEFAULT NULL PRIMARY KEY AUTOINCREMENT UNIQUE, " +
            " `JobName` TEXT NOT NULL DEFAULT 'N/A', " +
            " `StartDate` TEXT NOT NULL DEFAULT '00-00-0000', " +
            " `ExpectedJobDuration` INTEGER NOT NULL DEFAULT 0, " +
            " `ActualJobDuration` INTEGER NOT NULL DEFAULT 0, " +
            " `IsCompleted` INTEGER NOT NULL DEFAULT 0 CHECK(IsCompleted >= 0 AND IsCompleted <= 1)" +
            ")";
    public static final String CREATE_TASKS_TABLE = "CREATE TABLE IF NOT EXISTS `Tasks` (" +
            " `TaskID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE," +
            " `JobID` INTEGER NOT NULL," +
            " `MemberID` INTEGER," +
            " `Description` TEXT NOT NULL DEFAULT 'N/A'," +
            " `IsCompleted`INTEGER NOT NULL DEFAULT 0 CHECK(IsCompleted <= 0 AND IsCompleted <= 1)" +
            ")";
    public static final String CREATE_TASKLIST_TABLE = "CREATE TABLE IF NOT EXISTS `TaskList` (" +
            " `TaskID` INTEGER UNIQUE," +
            " `IsCompleted` INTEGER NOT NULL DEFAULT 0 CHECK(IsCompleted >= 0 AND IsCompleted <= 1)" +
            ")";
    public static final String CREATE_JOBMEMBERLIST_TABLE = "CREATE TABLE IF NOT EXISTS `JobMemberList` (" +
            " `JobID` INTEGER," +
            " `MemberID` INTEGER," +
            " `Status` INTEGER NOT NULL DEFAULT 0 CHECK(Status >= 0 AND Status <= 1)," +
            " FOREIGN KEY(`MemberID`) REFERENCES `Members`(`MemberID`)," +
            " FOREIGN KEY(`JobID`) REFERENCES `Jobs`(`JobID`)" +
            ")";
    public static final String CREATE_NEWSFEEDPOSTS_TABLE = "CREATE TABLE IF NOT EXISTS `NewFeedPosts` (" +
            " `MemberID` INTEGER NOT NULL," +
            " `TimePosted` TEXT NOT NULL DEFAULT 'YYYY-MM-DD_HH:MM:SS'," +
            " `Message` TEXT DEFAULT NULL," +
            " FOREIGN KEY(`MemberID`) REFERENCES `Members`(`MemberID`)" +
            ")";

    // Drop Table Constants
    public static final String DROP_MEMBERS_TABLE = "DROP TABLE IF EXISTS `Members`";
    public static final String DROP_USERS_TABLE = "DROP TABLE IF EXISTS `Users`";
    public static final String DROP_JOBS_TABLE = "DROP TABLE IF EXISTS `Jobs`";
    public static final String DROP_JOBMEMBERLIST_TABLE = "DROP TABLE IF EXISTS `JobMemberList`";
    public static final String DROP_TASKS_TABLE = "DROP TABLE IF EXISTS `Tasks`";
    public static final String DROP_TASKLIST_TABLE = "DROP TABLE IF EXISTS `TaskList`";
    public static final String DROP_NEWSFEEDPOSTS_TABLE = "DROP TABLE IF EXISTS `NewFeedPosts`";

    //////////////////////////////////////////////////////////////////////////////////
    private static class DBHelper extends SQLiteOpenHelper
    {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            // Create the database tables
            db.execSQL(CREATE_MEMBERS_TABLE);
            db.execSQL(CREATE_USERS_TABLE);
            db.execSQL(CREATE_JOBS_TABLE);
            db.execSQL(CREATE_JOBMEMBERLIST_TABLE);
            db.execSQL(CREATE_TASKS_TABLE);
            db.execSQL(CREATE_TASKLIST_TABLE);
            db.execSQL(CREATE_NEWSFEEDPOSTS_TABLE);

            // Populate Members and Users Tables with default admin details
            db.execSQL("INSERT INTO Members (MemberID, FirstName, LastName, Age, DateOfHire) VALUES (0, 'Alex', 'Palmer', 20, '2020-03-10')");
            db.execSQL("INSERT INTO Users (MemberID, Username, Password, Access) VALUES (0, 'admin', '1234', 1)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            // Logging the upgrade
            Log.d("Crew Manager", "Upgrading Database From Version " + oldVersion + " to " + newVersion);

            // Drop all tables
            db.execSQL(DROP_JOBMEMBERLIST_TABLE);
            db.execSQL(DROP_JOBS_TABLE);
            db.execSQL(DROP_MEMBERS_TABLE);
            db.execSQL(DROP_NEWSFEEDPOSTS_TABLE);
            db.execSQL(DROP_TASKLIST_TABLE);
            db.execSQL(DROP_TASKS_TABLE);
            db.execSQL(DROP_USERS_TABLE);

            // Recreate with only default values
            onCreate(db);

        }

    }
    //////////////////////////////////////////////////////////////////////////////////

    public DBAdapter (Context context)
    {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    private void openReadableDB()
    {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB()
    {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB()
    {
        // Ensure there is a database to close before attempting to close it
        if (db != null)
        {
            db.close();
        }
    }

    private void closeCursor(Cursor cursor)
    {
        // Ensure there is a cursor (that has not already been closed) to close before attempting to close it
        if (cursor != null && !cursor.isClosed())
        {
            cursor.close();
        }
    }

    // Public Methods
    public Data GetData()
    {
        // Variables
        Cursor cursor;
        Data newData = new Data();
        ArrayList<String> memberIDs = new ArrayList<String>();
        int counter = 0;
        ArrayList<String> username = new ArrayList<String>();
        ArrayList<String> password = new ArrayList<String>();
        ArrayList<String> eName = new ArrayList<String>();
        ArrayList<Integer> pAge = new ArrayList<Integer>();
        ArrayList<String> pJob = new ArrayList<String>();
        ArrayList<String> dateOfHire = new ArrayList<String>();
        ArrayList<String> pAssignedJob = new ArrayList<String>();
        ArrayList<String> jobList = new ArrayList<String>();
        ArrayList<String> jobStatus = new ArrayList<String>();

        // Open the database to be read from
        openReadableDB();

        // Get the all members' data (including related data in other tables) present in the database
        cursor = db.rawQuery("SELECT u.MemberID, u.Username, u.Password, u.Access," +
                " m.Firstname, m.Lastname, m.Age, m.DateOfHire" +
                " FROM Users u" +
                " INNER JOIN Members m ON u.MemberID == m.MemberID", null);

        // Fill ArrayLists with members' data
        while (cursor.moveToNext())
        {
            // Get MemberIDs in the order they are added
            memberIDs.add(Integer.toString(cursor.getInt(0)));

            // Get the rest of the member's data
            username.add(cursor.getString(1));
            password.add(cursor.getString(2));
            if (cursor.getInt(3) == 1)
            {
                pJob.add("Administrator");
            }
            else
            {
                pJob.add("Employee");
                newData.numberOfMembers++;
            }
            eName.add(cursor.getString(4) + " " + cursor.getString(5));
            pAge.add(cursor.getInt(6));
            dateOfHire.add(cursor.getString(7));
            counter++;
        }

        // Temp string variable for the query's argument
        String[] args = new String[1];

        // Loop through the memberIDs in the order their details were added in
        for (int i = 0; i < memberIDs.size(); i++)
        {
            // Put the current MemberID into the temp argument string to be sent with the query
            args[i] = memberIDs.get(i);

            // Execute the query and get the cursor data
            cursor = db.rawQuery("SELECT j.JobName" +
                    " FROM Jobs j" +
                    " INNER JOIN JobMemberList jb ON jb.MemberID == ?" +
                    " WHERE j.JobID == jb.JobID", args);

            // Check if there were no rows returned (Signifies that the MemberID has no Job Associated With it) Else enter the job that was received with it (One Job per member)
            if (cursor.getCount() == 0)
            {
                pAssignedJob.add("N/A");
            }
            else
            {
                while (cursor.moveToNext())
                {
                    pAssignedJob.add(cursor.getString(0));
                }
            }
        }

        // Find the number of completed/uncompleted jobs
        cursor = db.rawQuery("SELECT IsCompleted FROM Jobs", null);

        // If no rows are returned then there are no jobs else check if the job is completed or not and add one to the appropriate counter
        if (cursor.getCount() == 0)
        {
            newData.completedJobs = 0;
            newData.uncompletedJobs = 0;
        }
        else
        {
            while (cursor.moveToNext())
            {
                if (cursor.getInt(0) == 0)
                {
                    newData.uncompletedJobs++;
                }
                else
                {
                    newData.completedJobs++;
                }
            }
        }


        // Put the newly received data into the newData variable to be returned
        newData.username = username;
        newData.password = password;
        newData.eName = eName;
        newData.pAge = pAge;
        newData.dateOfHire = dateOfHire;
        newData.pJob = pJob;
        newData.pAssignedJob = pAssignedJob;
        newData.jobList = jobList;
        newData.jobStatus = jobStatus;

        return newData;
    }

}

