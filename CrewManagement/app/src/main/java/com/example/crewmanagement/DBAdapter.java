package com.example.crewmanagement;

import com.example.crewmanagement.Data;

import java.io.File;
import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter
{
    // RetCode Meanings
    public static final int SUCCESS = 1;
    public static final int FAILURE = 0;
    public static final int INVALID_USERNAME = -1;
    public static final int INVALID_PASSWORD = -2;
    public static final int INVALID_NAME = -3;
    public static final int AGE_NOT_IN_RANGE = -4;
    public static final int INVALID_DATE_FORMAT = -5;
    public static final int INVALID_JOB_TITLE = -6;

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

    /*
     * FUNCTION:
     *		getNumberOfMembers()
     * DESCRIPTION:
     *		Queries the database to find the number of members (including admin)
     * PARAMETERS:
     *			N/A
     * RETURNS:
     *			int : Returns the number of members in the database (this value can double as the new MemberID)
     */
    private int getNumberOfMembers()
    {
        // Variables
        Cursor cursor;
        int count = 0;

        this.openReadableDB();

        cursor = db.rawQuery("SELECT MemberID FROM Members", null);

        while(cursor.moveToNext())
        {
            count++;
        }

        return count;
    }

    // Public Methods
    /*
     * FUNCTION:
     *		getData()
     * DESCRIPTION:
     *		Queries the database to get all information related to the variables in the Data class to be filled and returned
     * PARAMETERS:
     *			N/A
     * RETURNS:
     *			Data : Returns a populated Data class object with ALL database data
     */
    public Data GetData()
    {
        // Variables
        int counter = 0;
        Cursor cursor;
        Data newData = new Data();
        ArrayList<String> memberIDs = new ArrayList<String>();
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

        this.closeCursor(cursor);
        this.closeDB();

        return newData;
    }

    /*
     * FUNCTION:
     *		InsertNewMember(String user, String pass, String name, Integer age, String doh, Integer access, String phone, byte[] profilePic)
     * DESCRIPTION:
     *		Takes Data from the parameters and inserts them into the User and Members Tables.
     * PARAMETERS:
     *			String user       : The new member's username (Must not contain a space, ',', or ''')
     *          String pass       : The new member's password (Must not contain a space)
     *          String name       : The new member's name (Is parsed into a first and last name; first and last name must not have additional spaces; last name can be blank)
     *          Integer age       : The new member's age (Age must be between 15 and 80)
     *          String doh        : The new member's DateOfHire (******NOTE: DateOfHire can be set to null in parameters to enter default value (Current Date in YYYY-MM-DD format) into database*****)
     *          Integer access    : The new member's access (*****NOTE: access can be set to null in parameters to enter default value (0) into database*****)
     *          String phone      : The new member's phone (*****NOTE: phone can be set to null in parameters to enter default value ("000-000-0000") into database*****)
     *          byte[] profilePic : The new member's profile picture (*****NOTE: ProfilePicture can be set to null in parameters to enter default value (null) into database*****)
     * RETURNS:
     *			long : Returns the RowID of the newly inserted row on success, else returns one of the class designated retValues
     */
    public long InsertNewMember(String user, String pass, String name, Integer age, String doh, Integer access, String phone, byte[] profilePic)
    {
        // Variables
        long retValue = SUCCESS;
        int memberID = getNumberOfMembers();
        ContentValues cv = new ContentValues();
        String fName = "";
        String lName = "";

        // Open writeable database
        this.openWriteableDB();

        // Check that the name is valid (for security of database)
        if (name.contains("1") || name.contains("2") || name.contains("3") || name.contains("4") || name.contains("5") || name.contains("6") || name.contains("7") || name.contains("8") || name.contains("9") || name.contains("0"))
        {
            return INVALID_NAME;
        }
        else if (name.contains(" "))
        {
            fName = name.substring(0, name.indexOf(' '));
            lName = name.substring(name.indexOf(' '), name.length());

            if (fName.contains(" ") || lName.contains(" "))
            {
                return INVALID_NAME;
            }
            else
            {
                cv.put("FirstName", fName);
                cv.put("LastName", lName);
            }
        }
        else
        {
            fName = name;

            cv.put("FirstName", fName);
            cv.put("LastName", lName);
        }

        // Check that the age is within acceptable range
        if (age <= 15 || age >= 80)
        {
            return AGE_NOT_IN_RANGE;
        }
        else
        {
            cv.put("Age", age);
        }

        // Check that the date of hire is in the correct format else if null/"Unknown" then set to today's date (in format YYYY-MM-DD)
        if (!doh.matches("^((19|2[0-9])[0-9]{2})-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$"))
        {
            return INVALID_DATE_FORMAT;
        }
        else if (doh == "Unknown" || doh == null)
        {
            Date currDate = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-DD");

            doh = df.format(currDate);
        }

        // Store the DateOfHire
        cv.put("DateOfHire", doh);

        // Check if phone is null then set to default format with Zeros
        if (phone == null)
        {
            phone = "000-000-0000";
        }
        else
        {
            cv.put("Phone", phone);
        }

        // Store profile pic
        cv.put("ProfilePicture", profilePic);

        // Store the memberID
        cv.put("MemberID", memberID);

        // Insert into members table
        retValue = db.insert("Members", null, cv);

        // If an error occurred during insert return failure code
        if (retValue == -1)
        {
            return FAILURE;
        }


        // Reset the contentValue variable for new data
        cv = new ContentValues();
        ///////////////////////////////////////////////

        // Check that the username is valid (for security of database)
        if (user.contains(" ") || user.contains("'") || user.contains(","))
        {
            return INVALID_USERNAME;
        }
        else
        {
            cv.put("Username", user);
        }

        // Check that the password is valid (for security of database)
        if (pass.contains(" ") || pass.contains("'") || pass.contains(","))
        {
            return INVALID_PASSWORD;
        }
        else
        {
            cv.put("Password", pass);
        }

        // Check that the job title is valid else if null set to default employee value (0)
        if (!(access >= 0 && access <= 1))
        {
            return INVALID_JOB_TITLE;
        }
        else if (access == null)
        {
            access = 0;
        }
        else
        {
            cv.put("Access", access);
        }

        // Put the memberID foreign key
        cv.put("MemberID", memberID);

        // Insert into the users table
        retValue = db.insert("Users", null, cv);

        // If an error occurred during insert return failure code
        if (retValue == -1)
        {
            return FAILURE;
        }

        // Return the RowID of the newly inserted row
        return retValue;
    }

}
