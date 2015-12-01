package teameleven.smartbells2;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

//import teameleven.smartbells2.BusinessLayer.localdatabase.DatabaseAdapter;
//import teameleven.smartbells2.BusinessLayer.localdatabase.DatabaseAdapter;

/**
 * Created by Jordan on 11/20/2015.
 */
public class RoutineDialogFragment extends DialogFragment implements View.OnClickListener{

    private Spinner exerciseSpinner;
    private String exerciseName;
    private Button addButton, cancelButton;
    private EditText reps, sets;
    private DatabaseAdapter database;

    Fragment fragment;
    FragmentTransaction transaction;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            //Establish Root view
            View rootView = getActivity().getLayoutInflater().inflate(R.layout.add_exercise_to_routine, new LinearLayout(getActivity()), false);

//            getDialog().setTitle("Add Exercise");


            //Set up the buttons
            addButton = (Button) rootView.findViewById(R.id.addExerciseFromPrompt);
            cancelButton = (Button) rootView.findViewById(R.id.cancelExerciseFromPrompt);
            reps = (EditText) rootView.findViewById(R.id.editRepsText);
            sets = (EditText) rootView.findViewById(R.id.editSetsText);

            //Set up the Spinner
            exerciseSpinner = (Spinner) rootView.findViewById(R.id.exerciseSpinner);
            addListenerOnSpinnerExerciseSelection();

            //Open Database
            database = new DatabaseAdapter(getContext());
            try {
                database.openLocalDatabase();
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }

            //Get list of Exercises from the database
            ArrayList<String> exerciseList = database.getExercisesAsStrings();
//
//            //TODO: I CAN'T ACCESS THE DATABASE CLASS TO IMPORT THE EXERCISE LIST- Please fix
//            ArrayList<String> exerciseList = new ArrayList<String>();
            for(int i = 0; i < 10; i++) {
                exerciseList.add("thing " + i);
            }
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, exerciseList);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            exerciseSpinner.setAdapter(adapter);


            //Setting up the Add Button
            addButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                }
            });

            //Setting up cancel button
            cancelButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                getDialog().dismiss();
                }
            });

            // Build dialog
            Dialog builder = new Dialog(getActivity());
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
            builder.setContentView(rootView);
            return builder;


        }

    public String addListenerOnSpinnerExerciseSelection() {
        exerciseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //Get Set Groups(Exercise List)
                exerciseName = parent.getItemAtPosition(position).toString();
                //todo; sets = get exerciseName.position. Load saved set from DB
                //todo; reps = get exerciseName.position. Load saved reps from DB
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do Nothing
            }
        });

        return exerciseName;
    }

    public boolean validate(){
        boolean valid = true;
        if(sets.getText().toString().isEmpty()){
            sets.setError("Please enter the number of sets for your session");
            valid = false;
        }
        if(reps.getText().toString().isEmpty()){
            reps.setError("Please enter the resistance for your session");
            valid = false;
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction transaction;

        switch (v.getId()) {
            //Move to a new FRAGMENT for each button
            case R.id.addExerciseFromPrompt:
                //Pop set group dialog window

            case R.id.cancelExerciseFromPrompt:

                getDialog().dismiss();

                break;
        }
    }
}
