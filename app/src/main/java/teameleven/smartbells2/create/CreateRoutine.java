package teameleven.smartbells2.create;

/**
 * CreateRoutine class makes a Routine of Exercises(Set Group)
 * Created  by Jarret on 2015-10-05.
 * Update   by WonKyoung on 2015-10-10
 * Update   by WonKyoung on 2015-10-25
 * Update   by WonKyoung On 2015-11-05
 * updated: Oct 8th, 2015 : added methods to interact with Exercise and Routine for Routine saving and creation.
 */


import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Exercise;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;
import teameleven.smartbells2.businesslayer.tableclasses.SetGroup;
import teameleven.smartbells2.Dashboard;
import teameleven.smartbells2.R;
import teameleven.smartbells2.dashboardfragmenttabs.RoutineDialogFragment;

//import teameleven.smartbells2.BusinessLayer.localdatabase.DatabaseAdapter;
//import teameleven.smartbells2.BusinessLayer.tableclasses.Exercise;
//import teameleven.smartbells2.BusinessLayer.tableclasses.Routine;
//import teameleven.smartbells2.BusinessLayer.tableclasses.SetGroup;

public class CreateRoutine extends Fragment implements View.OnClickListener {

    private DatabaseAdapter database;
    private Routine routine;
    private Spinner exerciseSpinner;
    private Boolean isPublic = false;
    private String exerciseName;// exerciseId is used as the key of calling JSon
    private int exerciseId;
    private int setsNum;
    private int repsNum;
    private String exercise;
    private ArrayList<Exercise> exercises = new ArrayList<>();
    private ArrayList<String> exerciseList = new ArrayList<>();
    private RadioGroup radioGroup;
    private RadioButton publicButton;
    int numberOfGroups = 1;

    private Button save, addExerciseButton;
    private Button cancel;
    private FloatingActionButton fab;


    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Main view
        View view = inflater.inflate(R.layout.create_routine, container, false);
        //Save Button
        save = (Button) view.findViewById(R.id.createNewRoutine);
        save.setOnClickListener(this);
        //CancelButton
        cancel = (Button) view.findViewById(R.id.cancelCreateRoutine);
        cancel.setOnClickListener(this);
        //Spinner
        exerciseSpinner = (Spinner) view.findViewById(R.id.exerciseSpinner);
        addListenerOnSpinnerExerciseSelection();
        //Add SetGroup "Add Exercise"
        addExerciseButton = (Button) view.findViewById(R.id.addExerciseListButton);

        //Open Database
        database = new DatabaseAdapter(getActivity());
        try {
            database.openLocalDatabase();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        //Get list of Exercises from the database
        ArrayList<String> exerciseList = database.getExercisesAsStrings();
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, exerciseList);

        //todo: If an available workout is pre-selected, show name, and routines saved to it.
        //Basic- Very veerrryy basic example below.
        TextView workout_name = (TextView) view.findViewById(R.id.editNameText);
        workout_name.setText("Get_From_Database_Name");


        return view;
    }


    //Add Routine Name
    public String addRoutineName() {
        TextView name = (TextView) getActivity().findViewById(R.id.editNameText);
        //Call setName() in Routine class
        //routine.setName(name.getText().toString());
        return name.getText().toString();
    }


    //Is public
    public Boolean addListenerOnRadioButton() {

        publicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                // declaring "public" radio button value
                RadioButton publicRoutine = (RadioButton) getActivity().findViewById(R.id.publicTextView);
                // Comparison selectedId and buttonId
                if (selectedId == publicRoutine.getId()) {
                    isPublic = true;
                } else {
                    isPublic = false;
                }
                //Toast.makeText(CreateRoutine.this,
                        //publicButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return isPublic;
    }


    //Set Groups
    public void setGroups(View view) {
        //Set the Number of Exercise Groups you want to create in this routine
        numberOfGroups++;//When clicked the plus button, it should be added 1.
        for (int i = 1; i <= numberOfGroups; i++) {

            TextView setsNum = (TextView) getActivity().findViewById(R.id.editSetsText);
            TextView repsNum = (TextView) getActivity().findViewById(R.id.editRepsText);
            //Add ArrayList exercises
            //exercises.add("1");//test : need to restCall get exercise ID.
            //exercises.add(setsNum.getText().toString());
            //exercises.add(repsNum.getText().toString());

        }
    }

    //Add Exercise
    public String addListenerOnSpinnerExerciseSelection() {
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Get Set Groups(Exercise List)
                exerciseName = (String) parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });

        return exerciseName;
    }

    //Add Number of Sets
    public int addNumberOfSets() {
        TextView setsText = (TextView) getActivity().findViewById(R.id.editSetsText);
        //call set method in routine class
        //exercise.setSets(Integer.getInteger(setsText.toString()));
        return Integer.parseInt(setsText.getText().toString());
    }


    //Add Reps Per Set
    public int addRepsPerSet() {
        TextView repsText = (TextView) getActivity().findViewById(R.id.editRepsText);
        //call set method in routine class
        //exercise.setReps(Integer.getInteger(repsText.toString()));
        return Integer.parseInt(repsText.getText().toString());
    }

    private boolean validate() {
        return true;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction transaction;

        switch (v.getId()) {
            //Move to a new FRAGMENT for each button
            case R.id.addExerciseListButton:

                    FragmentManager fm = getActivity().getFragmentManager();
                    RoutineDialogFragment dialog = new RoutineDialogFragment();
                    dialog.show(fm, "tag");
               //Pop set group dialog window
                /*
                fragment = new CreateSetGroupDialog();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                */
                break;
            case R.id.createNewRoutine:
                //SAVE new Routine to databasepiyep
                if (validate()) {
                    SetGroup setGroup = new SetGroup(
                            database.getExerciseIdByName(exerciseName),
                            addNumberOfSets(),
                            addRepsPerSet());
                    Routine routine = new Routine();
                    routine.getSetGroups().add(setGroup);
                    routine.setName(addRoutineName());
                    routine.setIs_Public(true);
                    //Log.d("CreateRoutine.saveRoutine - ", routine.toString());
                    //database is called in restputroutine. both calls not necessary
                    database.insertRoutine(routine, true);
                    //routine.RestPutRoutine(database);//should not call rest here - data will be
                    //input into update table for later synchronizing
                    //Close the database
                    database.closeLocalDatabase();

                    //Back to menu
                    Toast.makeText(getActivity(), "routine " + routine.getName() + " created!!.", Toast.LENGTH_LONG).show();
                    fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                    fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                    fragment = new Dashboard();
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, fragment);
                    transaction.commit();

                }
                break;

            case R.id.cancelCreateRoutine:

                fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                fragment = new Dashboard();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                //Kill the fragment
                //getFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }

}
