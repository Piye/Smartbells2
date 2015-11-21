package teameleven.smartbells2.create;
//// TODO: 08/11/2015 add database connectivity - also get exerciseId from spinner and pass to restCall

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import teameleven.smartbells2.BeginWorkout2;
import teameleven.smartbells2.R;

/**
 * Created by Jare on 2015-10-06.
 *
 */
public class CreateCustomSession extends Fragment implements View.OnClickListener {

    //private String exerciseName = "curls";
    private int numberOfGroups = 1;
    //private DatabaseAdapter database;
    private Spinner exerciseSpinner;
    private String exerciseName;
    private EditText mName;
    private EditText mNumOfSets;
    private EditText mRepsPerSet;
    //WorkoutSession session = new WorkoutSession();

    private Button cancel;
    private FloatingActionButton fab;

    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Main view
        View view = inflater.inflate(R.layout.create_routine, container, false);
        //CancelButton
        cancel = (Button) view.findViewById(R.id.cancelCreateRoutine);
        cancel.setOnClickListener(this);

        return view;
/*
        //Set up the exercise Spinner
        database = new DatabaseAdapter(this);
        try {
            database.openLocalDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Get list of Exercises from the database
        ArrayList<String> exerciseList = database.getExercisesAsStrings();
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, exerciseList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(adapter);
*/
    }

    //Add Name
    public String addName() {
       // mName = (EditText) findViewById(R.id.editCustomRoutineName);
        //Call setName() in Routine class
        return mName.getText().toString();
    }

    //Set Groups

    public void setGroups(View view) {
        //Set the Number of Exercise Groups you want to create in this routine

        numberOfGroups++;

        for (int i = 1; i <= numberOfGroups; i++) {

        }

    }

    //Add Exercise
    public String addListenerOnSpinnerExerciseSelection() {
        //exerciseSpinner = (Spinner) findViewById(R.id.exerciseSpinner);
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                exerciseName = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });
        return exerciseName;
    }

    //Add Reps Per Set
    public String addRepsPerSet() throws NumberFormatException {
        //mRepsPerSet = (EditText) findViewById(R.id.editRepsText);
        //call set method in routine class
        return mRepsPerSet.getText().toString();
    }

    //Add Number of Sets
    public String addNumberOfSets() throws NumberFormatException {
        //mNumOfSets = (EditText) findViewById(R.id.editSetsText);
        //call set method in routine class
        return mNumOfSets.getText().toString();
    }

    public boolean validate(){
        boolean valid = true;
        if(addNumberOfSets().isEmpty()){
            mNumOfSets.setError("Please enter the number of sets for your session");
            valid = false;
        }
        if(addName().isEmpty()){
            mName.setError("Please enter the name of your session");
            valid = false;
        }
        if(addRepsPerSet().isEmpty()){
            mRepsPerSet.setError("Please enter the number of reps per set");
            valid = false;
        }
        return valid;
    }

    //SAVE new Routine to database
    public void saveSession(View view) {
/*
        if (!validate()) {

        } else {
            String name = addName();
            int sets = Integer.parseInt(addNumberOfSets());
            int reps = Integer.parseInt(addRepsPerSet());
            int exerciseId = database.getExerciseIdByName(exerciseName);
            String token = database.getTokenAsString();
            //session.restCreateCustomSession(name, exerciseId, sets, reps, token);
            WorkoutSession session = new WorkoutSession(name, sets, reps, exerciseId);
            session.restCreateCustomSession(name, sets, reps, exerciseId,token);
            database.insertWorkoutSession(session);

            //Log.d("TEST", session.restGetAllWorkoutSession());
            //Log.d("TEST", session.restGetSpecificWorkoutSession(39));

            //Back to menu
            Toast.makeText(this, "Session saved!", Toast.LENGTH_LONG).show();
            CreateCustomSession.this.finish();
        }
*/
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction transaction;

        switch (v.getId()) {
            //START NEW INTENT for each button
            case R.id.add_setgroup:
                //Pop set group dialog window
                /*
                fragment = new CreateSetGroupDialog();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                */
                break;
            case R.id.design_routine:
                //Add new workout
                break;
            case R.id.cancelCreateRoutine:

                fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                fragment = new BeginWorkout2();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                //Kill the fragment
                //getFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }

}
