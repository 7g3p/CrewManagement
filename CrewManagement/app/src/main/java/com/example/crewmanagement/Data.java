/*
 * FILE:              Data.Java
 * PROJECT:          CrewManagement
 * PROGRAMMER:       Alex Palmer
 * OTHER MEMBERS:    Alex MacCumber, David Obeda, Stephen Perrin, Marissa Schmitt
 * FIRST VERSION:    February 5th, 2020
 * DESCRIPTION:      This file describes the functionality and events of the progress screen within
 *                   the CrewManagement application. The Data class that holds and stores the information
 *                   needed for the CrewManagement application
 */
package com.example.crewmanagement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*
 * NAME:     Data
 * PURPOSE:  The purpose of the Data class is to define the attributes and the corresponding accessors/mutators
 *           of the attributes within the Data class. This class inherits(extends) characteristics from Serializable
 */
public class Data implements Serializable {

    List<String> username; //this will hold the members username(in constructor first (variable = 0) variable must be the admins)
    List<String> password; //this will hold the members password(in constructor first (variable = 0)variable must be the admins)
    List<String> eName; //this will hold the members name(in constructor first (variable = 0)variable must be the admins)
    List<Integer> pAge; //this will hold the members age(in constructor first (variable = 0)variable must be the admins)
    List<String> dateOfHire; //this will hold the members date of hire(in constructor first (variable = 0)variable must be the admins)
    List<String> pJob; //this will hold the members job(in constructor first (variable = 0)variable must be the admins)
    List<String> pAssignedJob;//this will hold the members Assignedjob(in constructor first (variable = 0)variable must be the admins)
    List<String> jobList;// this shows the list of jobs
    List<String> jobStatus;// this shows the status of the job
    Integer uncompletedJobs;// this will track how many jobs are left
    Integer completedJobs;// this will track how many jobs are completed
    Integer numberOfMembers; // how many members there are (including admin so when using this has a source do -1 when accessing data
    Integer Admin; // to identify if the admin is logged in 0 is a member while 1 is a admin


    /*
     * FUNCTION:	Data
     * DESCRIPTION:	This is a constructor for the Data class.
     * PARAMETERS:	String un - represents the username entered by the user
     *		String pw - represents the password entered by the user
     *		String name - represents the name of the employee
     *		Integer age - represents the age of the employee
     *		String doh - represents the date of hire of the employee
     *		String job - represents the job title of the employee
     * RETURNS:	N/A - the constructor doesn't have a return value.
     */
    public Data(String un, String pw, String name, Integer age, String doh, String job)
    {
        this.username = new ArrayList<String>();//initializing the data class' username to a new array list of strings
        this.password = new ArrayList<String>();//initializing the data class' password to a new array list of strings
        this.eName = new ArrayList<String>();//initializing the data class' employee name to a new array list of strings
        this.pAge = new ArrayList<Integer>();//initializing the data class' age to a new array list of ints
        this.pJob = new ArrayList<String>();//initializing the data class' jobs to a new array list of strings
        this.dateOfHire = new ArrayList<String>();//initializing the data class' date of hire to a new array list of strings
        this.pAssignedJob = new ArrayList<String>();//initializing the data class' assigned job to a new array list of strings
        this.jobList = new ArrayList<String>();//initializing the data class' job list to a new array list of strings
        this.jobStatus = new ArrayList<String>();//initializing the data class' job status to a new array list of strings
        username.add(un);//adding the user's username to the username list
        password.add(pw);//adding the user's password to the password list
        eName.add(name);//adding the employee's name to the employee name list
        pAge.add(age);//adding the employee's age to the age list
        dateOfHire.add(doh);//adding the date of hire to the specified list
        pJob.add(job);//adding the job to the specified list
        pAssignedJob.add("none");//adding "none" to the list of the jobs
        uncompletedJobs = 0;//initializing the uncompleted jobs int to zero
        completedJobs = 0;//initializing the completed jobs int to zero
        numberOfMembers = 1;//initializing the number of members to one
    }


    /*
     * FUNCTION:	changeUsername
     * DESCRIPTION:	This is a mutator for the username attribute within the Data class.
     * PARAMETERS:	String newUsername - represents the username entered by the user
     *		int location - represents the users role, whether they're an admin(0) or an employee(1).
     * RETURNS:	void - this mutator returns nothing
     */
    public void changeUsername(String newUsername, int location)
    {
        username.set(location, newUsername);//changing the username of the specified employee
    }


    /*
     * FUNCTION:	changePassword
     * DESCRIPTION:	This is a mutator for the password attribute within the Data class.
     * PARAMETERS:	String newPassword - represents the password entered by the user
     *		int location - represents the role of the user(zero = admin, non-zero = employee)
     * RETURNS:	void - this mutator returns nothing
     */
    public void changePassword(String newPassword, int location)
    {
        password.set(location, newPassword);//changing the password of the specified employee
    }


    /*
     * FUNCTION:	changeName
     * DESCRIPTION:	This is a mutator for the name attribute within the Data class.
     * PARAMETERS:	String newName - represents the name entered by the user
     *		int location - represents the role of the user(zero = admin, non-zero = employee)
     * RETURNS:	void - this mutator returns nothing
     */
    public void changeName(String newName, int location)
    {
        eName.set(location, newName);//changing the name of the specified employee
    }



    /*
     * FUNCTION:	changeAge
     * DESCRIPTION:	This is a mutator for the age attribute within the Data class.
     * PARAMETERS:	integer newAge - represents the age entered by the user
     *		int location - represents the role of the user(zero = admin, non-zero = employee)
     * RETURNS:	void - this mutator returns nothing
     */
    public void changeAge(Integer newAge, int location)
    {
        pAge.set(location, newAge);//changing the age of the specified employee
    }



    /*
     * FUNCTION:	changeJob
     * DESCRIPTION:	This is a mutator for the job attribute within the Data class.
     * PARAMETERS:	String newJob - represents the job entered by the user
     *		int location - represents the role of the user(zero = admin, non-zero = employee)
     * RETURNS:	void - this mutator returns nothing
     */
    public void changeJob(String newJob, int location)
    {
        pJob.set(location, newJob);//changing the job of the specified employee
    }



    /*
     * FUNCTION:	changeAssignedJob
     * DESCRIPTION:	This is a mutator for the assigned job attribute within the Data class.
     * PARAMETERS:	String newAssignedJob - represents the assigned job entered by the user
     *		int location - represents the role of the user(zero = admin, non-zero = employee)
     * RETURNS:	void - this mutator returns nothing
     */
    public void changeAssignedJob(String newAssignedJob, int location)
    {
        pAssignedJob.set(location, newAssignedJob);//adjusting the assigned job at the specified location to the new job
    }



    /*
     * FUNCTION:	addMember
     * DESCRIPTION:	This is a method is used to add a member of the crew within the Data class.
     * PARAMETERS:	String un - represents the username entered by the user
     *		String name - represents the name entered by the user
     *		Integer age - represents the age entered by the user
     *		String doh - represents the date of hire of the employee
     *		String job - represents the job entered by the user
     * RETURNS:	void - this method returns nothing
     */
    public void addMember(String un, String name, Integer age, String doh, String job)
    {
        username.add(un);//adding the user's username to the username list
        password.add(un);//adding the user's username to the password list
        eName.add(name);//adding the user's name to the name list
        pAge.add(age);//adding the user's age to the age list
        dateOfHire.add(doh);//adding the user's date of hire to the date of hire list
        pJob.add(job);//adding the user's job to the job list
        pAssignedJob.add("None");//adding the user's assigned job, defaulted to none
        numberOfMembers = numberOfMembers + 1;//adjusting the number of members to indicate one has been added

    }



    /*
     * FUNCTION:	removeMember
     * DESCRIPTION:	This is a method is used to remover a member of the crew within the Data class.
     * PARAMETERS:	int location - represents the employee's identifier
     * RETURNS:	void - this method returns nothing
     */
    public void removeMember(int location)
    {
        username.remove(location);//removing the username from the specified location in the list
        password.remove(location);//removing the password from the specified location in the list
        eName.remove(location);//removing the name from the specified location in the list
        pAge.remove(location);//removing the age from the specified location in the list
        dateOfHire.remove(location);//removing the date of hire from the specified location in the list
        pJob.remove(location);//removing the job from the specified location in the list
        pAssignedJob.remove(location);//removing the assigned job from the specified location in the list
        numberOfMembers = numberOfMembers - 1;//adjusting the number of members to indicate one has been removed
    }



    /*
     * FUNCTION:	getUsername
     * DESCRIPTION:	This is an accessor for the username attribute within the Data class.
     * PARAMETERS:	int location - represents the employee's identifier.
     * RETURNS:	String - this method returns the username of the specified employee
     */
    public String getUsername(int location)
    {
        String info = username.get(location);//retrieving the specified username
        return info;//returning the username
    }


    /*
     * FUNCTION:	getPassword
     * DESCRIPTION:	This is an accessor for the password attribute within the Data class.
     * PARAMETERS:	int location - represents the employee's identifier.
     * RETURNS:	String - this method returns the password of the specified employee
     */
    public String getPassword(int location)
    {
        String info = password.get(location);//retrieving the specified password
        return info;//returning the password
    }


    /*
     * FUNCTION:	getName
     * DESCRIPTION:	This is an accessor for the name attribute within the Data class.
     * PARAMETERS:	int location - represents the employee's identifier.
     * RETURNS:	String - this method returns the name of the specified employee
     */
    public String getName(int location)
    {
        String info = eName.get(location);//retrieving the specified employee name
        return info;//returning the employee's name
    }



    /*
     * FUNCTION:	getAge
     * DESCRIPTION:	This is an accessor for the age attribute within the Data class.
     * PARAMETERS:	int location - represents the employee's identifier.
     * RETURNS:	Integer - this method returns the age of the specified employee
     */
    public Integer getAge(int location)
    {
        Integer info = pAge.get(location);//retrieving the specified age
        return info;//returning the age
    }


    /*
     * FUNCTION:	getJob
     * DESCRIPTION:	This is an accessor for the job attribute within the Data class.
     * PARAMETERS:	int location - represents the employee's identifier.
     * RETURNS:	String - this method returns the job of the specified employee
     */
    public String getJob(int location)
    {
        String info = pJob.get(location);//retrieving the specified job
        return info;//returning the job
    }



    /*
     * FUNCTION:	getAssignedJob
     * DESCRIPTION:	This is an accessor for the assigned job attribute within the Data class.
     * PARAMETERS:	int location - represents the employee's identifier.
     * RETURNS:	String - this method returns the assigned job of the specified employee
     */
    public String getAssignedJob(int location)
    {
        String info = pAssignedJob.get(location);//retrieving the specified assigned job
        return info;//returning the assigned job
    }



    /*
     * FUNCTION:	addJob
     * DESCRIPTION:	This is a method is used to add a job within the Data class.
     * PARAMETERS:	String job - represents the job entered by the user
     * RETURNS:	void - this method returns nothing
     */
    public void addJob(String job)
    {
        jobList.add(job);//adding the new job to the job list
        jobStatus.add("incomplete");//marking it as incomplete when it's added
        uncompletedJobs = uncompletedJobs + 1;//adjusting the number of incomplete jobs
    }


    /*
     * FUNCTION:	jobComplete
     * DESCRIPTION:	This is a method is mark a job as complete within the Data class.
     * PARAMETERS:	int location - represents the job identifier
     * RETURNS:	void - this method returns nothing
     */
    public void jobComplete(int location)
    {
        if(jobStatus.get(location) == "incomplete")//ensuring that the job status is currently marked as incomplete
        {
            jobStatus.set(location, "Complete");//marking the job status as complete
            uncompletedJobs = uncompletedJobs - 1;//adjusting the number of incomplete jobs
            completedJobs = completedJobs + 1;//adjusting the number of completed jobs to represent the job no longer being incomplete
        }
    }


    /*
     * FUNCTION:	jobNotComplete
     * DESCRIPTION:	This is a method is mark a job as incomplete within the Data class.
     * PARAMETERS:	int location - represents the job identifier
     * RETURNS:	void - this method returns nothing
     */
    public void jobNotComplete(int location)
    {
        if(jobStatus.get(location) != "incomplete")//ensuring that the job status is not already incomplete
        {
            jobStatus.set(location, "incomplete");//marking the job status as incomplete
            uncompletedJobs = uncompletedJobs + 1;//adjusting the number of incomplete jobs to represent the job being marked as incomplete
            completedJobs = completedJobs - 1;//adjusting the number of completed jobs to represent the job no longer being completed
        }
    }



    /*
     * FUNCTION:	RemoveJob
     * DESCRIPTION:	This is a method is remove a job as complete within the Data class.
     * PARAMETERS:	int location - represents the job identifier
     * RETURNS:	void - this method returns nothing
     */
    public void Removejob(int location)
    {
        if(jobStatus.get(location) == "Complete")//seeind if the status of the task is complete
        {
            jobList.remove(location);//removing the specified task from the job list
            jobStatus.remove(location);//removing the specified task from the job status list
            completedJobs = completedJobs - 1;//adjusting the number of completed jobs to represent the job being removed
        }
        else
        {
            jobList.remove(location);//removing the specified task from the job list
            jobStatus.remove(location);//removing the specified task from the job status list
            completedJobs = uncompletedJobs - 1;//adjusting the number of incomplete jobs to represent the job being removed
        }
    }



    /*
     * FUNCTION:	getNumberOfMembers
     * DESCRIPTION:	This is an accessor for the number of members attribute within the Data class.
     * PARAMETERS:
     * RETURNS:	Integer - this method returns the number of members
     */
    public Integer getNumberOfMembers()
    {
        return numberOfMembers;//returning the number of members attributes
    }



    /*
     * FUNCTION:	getCompletedJobs
     * DESCRIPTION:	This is an accessor for the number of completed jobs attribute within the Data class.
     * PARAMETERS:
     * RETURNS:	Integer - this method returns the number of completed jobs
     */
    public Integer getCompletedJobs()
    {
        return completedJobs;//returning the number of completed jobs attributes
    }



    /*
     * FUNCTION:	getUncompletedJobs
     * DESCRIPTION:	This is an accessor for the number of uncompleted jobs attribute within the Data class.
     * PARAMETERS:
     * RETURNS:	Integer - this method returns the number of uncompleted jobs
     */
    public Integer getUncompletedJobs()
    {
        return uncompletedJobs;//returning the number of uncompleted jobs attributes
    }


    /*
     * FUNCTION:	ChangeAdmin
     * DESCRIPTION:	This is a mutator for the admin attribute within the Data class.
     * PARAMETERS:
     * RETURNS:	void - this mutator returns nothing
     */
    public void changeAdmin(Integer i)
    {
        Admin = i;
    }



    /*
     * FUNCTION:	getAdmin
     * DESCRIPTION:	This is an accessor for the Admin attribute within the Data class.
     * PARAMETERS:
     * RETURNS:	Integer - this method returns the Admin attribute
     */
    public Integer getAdmin()
    {
        return Admin;//returning the Variable Admin attribute
    }


    /*
     * FUNCTION:	getJobList
     * DESCRIPTION:	This is an accessor for the JobList attribute within the Data class.
     * PARAMETERS:
     * RETURNS:	Integer - this method returns the Admin attribute
     */
    public String getJobList(Integer id)
    {
        return jobList.get(id);
    }


}
