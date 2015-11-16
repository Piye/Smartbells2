package teameleven.smartbells2;

/**
 * CreateRoutine class makes a Routine of Exercises(Set Group)
 * Created  by Jarret on 2015-10-05.
 * Update   by WonKyoung on 2015-10-10
 * Update   by WonKyoung on 2015-10-25
 * Update   by WonKyoung On 2015-11-05
 * updated: Oct 8th, 2015 : added methods to interact with Exercise and Routine for Routine saving and creation.
 */


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

//import teameleven.smartbells_prototype0001.BusinessLayer.localdatabase.DatabaseAdapter;
//import teameleven.smartbells_prototype0001.BusinessLayer.tableclasses.Exercise;
//import teameleven.smartbells_prototype0001.BusinessLayer.tableclasses.Routine;
//import teameleven.smartbells_prototype0001.BusinessLayer.tableclasses.SetGroup;

public class CreateRoutine extends Fragment {

    //private DatabaseAdapter database;
    //private Routine routine;
    private Spinner exerciseSpinner;
    private Boolean isPublic = false;
    private String exerciseName;// exerciseId is used as the key of calling JSon
    private int exerciseId;
    private int setsNum;
    private int repsNum;
    private String exercise;
    // private ArrayList<Exercise> exercises = new ArrayList<>();
    private ArrayList<String> exerciseList = new ArrayList<>();
    private RadioGroup radioGroup;
    private RadioButton publicButton;
    int numberOfGroups = 1;

    /**
     * onCreate
     *
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Main view
        View view = inflater.inflate(R.layout.create_routine, container, false);
        return view;
       // addListenerOnSpinnerExerciseSelection();

        //Set up the exercise Spinner
        //   database = new DatabaseAdapter(this);

      /*  try {
            database.openLocalDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Get list of Exercises from the database
        ArrayList<String> exerciseList = database.getExercisesAsStrings();
*/

/*        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, exerciseList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSpinner.setAdapter(adapter);*/
   /* }


    //Add Routine Name
    public String addRoutineName() {
        TextView name = (TextView) findViewById(R.id.editNameText);
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
                RadioButton publicRoutine = (RadioButton) findViewById(R.id.publicTextView);
                // Comparison selectedId and buttonId
                if (selectedId == publicRoutine.getId()) {
                    isPublic = true;
                } else {
                    isPublic = false;
                }
                Toast.makeText(CreateRoutine.this,
                        publicButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return isPublic;
    }


    //Set Groups
    public void setGroups(View view) {
        //Set the Number of Exercise Groups you want to create in this routine
        numberOfGroups++;//When clicked the plus button, it should be added 1.
        for (int i = 1; i <= numberOfGroups; i++) {

            TextView setsNum = (TextView) findViewById(R.id.editSetsText);
            TextView repsNum = (TextView) findViewById(R.id.editRepsText);
            //Add ArrayList exercises
            *//*
            exercises.add("1");//test : need to restCall get exercise ID.
            exercises.add(setsNum.getText().toString());
            exercises.add(repsNum.getText().toString());
            *//*
        }
    }

    //Add Exercise
    public String addListenerOnSpinnerExerciseSelection() {
        exerciseSpinner = (Spinner) findViewById(R.id.exerciseSpinner);
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
        TextView setsText = (TextView) findViewById(R.id.editSetsText);
        //call set method in routine class
        //exercise.setSets(Integer.getInteger(setsText.toString()));
        return Integer.parseInt(setsText.getText().toString());
    }


    //Add Reps Per Set
    public int addRepsPerSet() {
        TextView repsText = (TextView) findViewById(R.id.editRepsText);
        //call set method in routine class
        //exercise.setReps(Integer.getInteger(repsText.toString()));
        return Integer.parseInt(repsText.getText().toString());
    }


    //SAVE new Routine to database
    public void saveRoutine(View view) {
        // todo ADD Everything - need some way to ensure that number of sets and reps per set are both filled in.
        //todo i think its better practice to build the entire exercise object here. we can also do verification and such here?
        //todo long story short, need input verification here

*//*

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
            //database.insertRoutine(routine);
            routine.RestPutRoutine(database);


        //todo routine.save()
        //todo or some variation - would save the current routine to the database


        //Close the database
        database.closeLocalDatabase();

        //Back to menu
        Toast.makeText(this, "routine " + routine.getName() + " created!!.", Toast.LENGTH_LONG).show();

            CreateRoutine.this.finish();
        }
    }

    private boolean validate() {
        return true;
    }

*//*
    //Cancel - back to menu
    public void cancelCreateRoutine(View view) {

        CreateRoutine.this.finish();
    }*/

    }
}
