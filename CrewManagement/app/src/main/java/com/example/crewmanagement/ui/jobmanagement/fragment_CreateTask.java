package com.example.crewmanagement.ui.jobmanagement;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.example.crewmanagement.DBAdapter;
import com.example.crewmanagement.Data;
import com.example.crewmanagement.R;
import java.util.ArrayList;
import java.util.List;


public class fragment_CreateTask extends Fragment
    implements View.OnClickListener {

    private final String task = "T";

    private View view = null;
    private Button btn_CreateTask = null;
    private Button btn_ClearTaskForm = null;
    private EditText txt_TaskDesc = null;

    private Spinner spnr_JobList = null;
    private ArrayList<String> jobNames = null;

    private Data data = null;
    private DBAdapter dbAdapter = null;

    private String jobName = null;

    private SpinnerAdapter spnr_JNameAdapter;


    private MyTaskListener mtl = null;


    public fragment_CreateTask() {
        // Required empty public constructor
    }


    public static fragment_CreateTask newInstance() {
        fragment_CreateTask fragment = new fragment_CreateTask();
        return fragment;
    }

    // TODO - Implement Loading of job names into spinner
    // TODO - Implement job name spinner for selection
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_create_task, container, false);
        btn_CreateTask = (Button)view.findViewById(R.id.btnNewTask);
        btn_CreateTask.setOnClickListener(this);
        btn_ClearTaskForm = (Button)view.findViewById(R.id.btnClearTaskForm);
        btn_ClearTaskForm.setOnClickListener(this);
        txt_TaskDesc = (EditText)view.findViewById(R.id.txtTaskDescription);
        dbAdapter = new DBAdapter(getActivity());
        data = dbAdapter.GetData();

        // Waiting on database methods / fixes to implement these
        LoadListOfJobs();
        //spnr_JobList = (Spinner)view.findViewById(R.id.spnr_jobNames);
        //spnr_JNameAdapter =


        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MyTaskListener) {
            this.mtl = (MyTaskListener) context;
        }
    }

    @Override
    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.btnNewTask:
                Context context = getActivity();
                Bundle bundle = new Bundle();
                String taskDescription = null;
                taskDescription = txt_TaskDesc.getText().toString();
                bundle.putString("tDescription", taskDescription);
                //  This line is currently breaking my functionality as mtl is null when this call occurs
                mtl.CreateTask(bundle);

                break;

            case R.id.btnClearTaskForm:
                ClearForms(v);

            default:
                break;
        }
    }

    public void ClearForms(View v)
    {
        // Clear the various inputs on the layout screen
        txt_TaskDesc.setText("");
    }


    public void LoadListOfJobs()
    {
        Integer counter = 0;
        List<String> unassignedTasks = new ArrayList<>();
        List<String> incompleteJobs = new ArrayList<>();
        Integer numJobs = (data.getCompletedJobs() + data.getUncompletedJobs());

        while (counter < numJobs)
        {
            String jobName = data.getJobList(counter);
            incompleteJobs.add(jobName);
            counter++;
        }
        unassignedTasks = dbAdapter.GetUnassignedTasks();

        Integer pause = 0;
    }

    public interface MyTaskListener {
        public void CreateTask(Bundle args);
    }
}
