package com.example.crewmanagement.ui.jobmanagement;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.crewmanagement.Data;
import com.example.crewmanagement.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class fragment_CreateJob extends Fragment
    implements View.OnClickListener {

    private final String job = "J";
    private final String datePattern = "yyyy-MM-dd";

    // Set default value to be sent for Job Actual duration to 1 day
    final Integer jobActualDefault = 1;
    // Set default value to be sent for Job Status to 0, represents not complete
    final Integer jobStatusDefault = 0;


    View view = null;
    Button btn_CreateJob = null;
    Button btn_ClearForms = null;
    EditText txt_JobName = null;
    EditText txt_JobDuration = null;
    DatePicker startDate = null;

    Integer currentYear = 0;
    Integer currentMonth = 0;
    Integer currentDay = 0;

    MyJobListener mjl = null;

    public fragment_CreateJob() {
        // Required empty public constructor
    }

    public static fragment_CreateJob newInstance() {
        fragment_CreateJob fragment = new fragment_CreateJob();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_job, container, false);
        btn_ClearForms = (Button)view.findViewById(R.id.btnClearForm);
        btn_ClearForms.setOnClickListener(this);
        btn_CreateJob = (Button)view.findViewById(R.id.btnNewJob);
        btn_CreateJob.setOnClickListener(this);
        txt_JobName = view.findViewById(R.id.txtJobName);
        txt_JobDuration = view.findViewById(R.id.txtJobDuration);
        startDate = view.findViewById(R.id.JobStartDate);
        currentYear = startDate.getYear();
        currentMonth = startDate.getMonth();
        currentDay = startDate.getDayOfMonth();
        startDate.setMinDate(System.currentTimeMillis());
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyJobListener) {
            mjl = (MyJobListener) context;
        }
    }



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
    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnNewJob:
                Bundle bundle = new Bundle();
                String jobDateString = null;
                String jobName = null;
                Integer jobYear = null;
                Integer jobMonth = null;
                Integer jobDay = null;
                Integer jobDuration = null;

                jobName = txt_JobName.getText().toString();
                jobYear = startDate.getYear();
                jobMonth = startDate.getMonth();
                jobDay = startDate.getDayOfMonth();

                Calendar calendar = Calendar.getInstance();
                calendar.set(jobYear, jobMonth, jobDay);

                SimpleDateFormat dateFormat = new SimpleDateFormat(datePattern);
                jobDateString = dateFormat.format(calendar.getTime());

                jobDuration = Integer.parseInt(txt_JobDuration.getText().toString());

                bundle.putString("jName", jobName);
                bundle.putString("jDate", jobDateString);
                bundle.putInt("jDuration", jobDuration);
                bundle.putInt("jActual", jobActualDefault);
                bundle.putInt("jStatus", jobStatusDefault);

                mjl.CreateJob(bundle);
                break;

            case R.id.btnClearForm:
                ClearForms(v);

            default:
                break;
        }
    }


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
    public void ClearForms(View v)
    {
        txt_JobName.setText("");
        txt_JobDuration.setText("");
        startDate.updateDate(currentYear,currentMonth,currentDay);
    }


    public interface MyJobListener {
        public void CreateJob(Bundle bundle);
    }

}
