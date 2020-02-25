/*
 * FILE : HashmapListAdapter.java
 * PROJECT : PROG3150 - Assignment #1
 * PROGRAMMER : Stephen Perrin, Alex Palmer, Alex MacCumber, David Obeda, Marissa Schmitt
 * FIRST VERSION : 2020-02-08
 * DESCRIPTION :
 * The functions in this file are built off of the expandableListAdapter base and are used to convert
 * a hashmap's data into an expandableList as well as connect the formatting of the data on screen
 * when the child data is expanded/collapsed
 */
package com.example.crewmanagement;

import java.util.HashMap;
import java.util.List;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;



public class HashmapListAdapter extends BaseExpandableListAdapter
{
    // Class Variables
    private Context context;
    private List<String> listTitles;
    private HashMap<String, List<String>> listDetails;

    /*
     * FUNCTION:
     *		HashmapListAdapter(Context context, List<String> lt, HashMap<String, List<String>> ld)
     * DESCRIPTION:
     *		Assigns the inputted data to the private class data members
     * PARAMETERS:
     *			Context context : The expandableList that is being adjusted
     *			List<String> lt : The list titles that will be initially displayed when list is in collapsed state
     *			HashMap<String, List<String>> ld : The list details that will be displayed with it's corresponding list title when expanded
     * RETURNS:
     *			void - this function returns nothing
     */
    public HashmapListAdapter(Context context, List<String> lt, HashMap<String, List<String>> ld)
    {
        // Set the list title, details, and current expandableList to the appropriate objects
        this.context = context;
        this.listTitles = lt;
        this.listDetails = ld;
    }

    /*
     * FUNCTION:
     *		getChild(int listPosition, int expandedListPosition)
     * DESCRIPTION:
     *		Returns the specified child according to the parameters
     * PARAMETERS:
     *			int listPosition : The parent position of the specified child
     *			int expandedListPosition : The position within the child's parent's list
     * RETURNS:
     *			Object - this function returns the specified child's data
     */
    @Override
    public Object getChild(int listPosition, int expandedListPosition)
    {
        // Return the specified detail according to the positions in the parameters
        return this.listDetails.get(this.listTitles.get(listPosition)).get(expandedListPosition);
    }

    /*
     * FUNCTION:
     *		getChildId(int listPosition, int expandedListPosition)
     * DESCRIPTION:
     *		Returns the specified child's position as the ID
     * PARAMETERS:
     *			int listPosition : The parent position of the specified child
     *			int expandedListPosition : The position within the child's parent's list
     * RETURNS:
     *			Object - this function returns the specified child's Position
     */
    @Override
    public long getChildId(int listPosition, int expandedListPosition)
    {
        // Returns the expandedListPosition
        return expandedListPosition;
    }

    /*
     * FUNCTION:
     *		getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent)
     * DESCRIPTION:
     *		Returns the view of the specified child
     * PARAMETERS:
     *			int listPosition : The parent position of the specified child
     *			final int expandedListPosition : The position within the child's parent's list
     *			boolean isLastChild : Determines if the child is the last one in its parent's group
     *			View convertView : Allows for the conversion between expandableList child to textView
     *			ViewGroup parent : the parent view group used to locate the child view
     * RETURNS:
     *			View - this function returns the specified child's view
     */
    @Override
    public View getChildView(int listPosition, final int expandedListPosition, boolean isLastChild, View convertView, ViewGroup parent)
    {
        // Variable to hold the string for the designated detail's string
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);

        // If the details are not already assigned to a view then assign to a view
        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_items, null);
        }

        TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
        expandedListTextView.setText(expandedListText);

        return convertView;
    }

    /*
     * FUNCTION:
     *		getChildrenCount(int listPosition)
     * DESCRIPTION:
     *		Returns the number of children within a parent group designated by the parameter
     * PARAMETERS:
     *			int listPosition : The parent group position
     * RETURNS:
     *			int - this function returns the number of children within a parent group
     */
    @Override
    public int getChildrenCount(int listPosition)
    {
        return this.listDetails.get(this.listTitles.get(listPosition)).size();
    }

    /*
     * FUNCTION:
     *		getGroup(int listPosition)
     * DESCRIPTION:
     *		Returns the specified parent group
     * PARAMETERS:
     *			int listPosition : The parent position
     * RETURNS:
     *			Object - this function returns the specified parent group
     */
    @Override
    public Object getGroup(int listPosition)
    {
        return this.listTitles.get(listPosition);
    }

    /*
     * FUNCTION:
     *		getGroupCount()
     * DESCRIPTION:
     *		Returns the number of groupings by counting the number of list titles
     * PARAMETERS:
     *			N/A
     * RETURNS:
     *			int - this function returns the number of list titles
     */
    @Override
    public int getGroupCount()
    {
        return this.listTitles.size();
    }

    /*
     * FUNCTION:
     *		getGroupId(int listPosition)
     * DESCRIPTION:
     *		Returns the position as the ID
     * PARAMETERS:
     *			int listPosition : The parent group position
     * RETURNS:
     *			long - this function returns the position as the ID
     */
    @Override
    public long getGroupId(int listPosition)
    {
        return listPosition;
    }

    /*
     * FUNCTION:
     *		getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent)
     * DESCRIPTION:
     *		Returns the view of the specified child
     * PARAMETERS:
     *			int listPosition : The parent group position
     *			boolean isExpanded : Determines if the parent group is expanded
     *			View convertView : Allows for the conversion between expandableList group to textView
     *			ViewGroup parent : the parent view group
     * RETURNS:
     *			View - this function returns the specified parent group view
     */
    @Override
    public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent)
    {
        // Variable to hold the title for the detail views
        String listTitle = (String) getGroup(listPosition);

        if (convertView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_grouping, null);
        }

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);

        return convertView;
    }

    /*
     * FUNCTION:
     *		hasStableIds()
     * DESCRIPTION:
     *		Returns false to indicate that there is no stable ID setup
     * PARAMETERS:
     *			N/A
     * RETURNS:
     *			boolean - this function returns false to indicate that there is no stable ID setup
     */
    @Override
    public boolean hasStableIds()
    {
        return false;
    }

    /*
     * FUNCTION:
     *		isChildSelectable(int listPosition, int expandedListPosition)
     * DESCRIPTION:
     *		Returns true to indicate that each child can be selected
     * PARAMETERS:
     *			int listPosition : The parent position of the specified child
     *			final int expandedListPosition : The position within the child's parent's list
     * RETURNS:
     *			boolean - this function returns true to indicate that each child can be selected
     */
    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition)
    {
        return true;
    }
}
