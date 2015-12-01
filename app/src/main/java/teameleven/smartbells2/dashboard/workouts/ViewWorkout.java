package teameleven.smartbells2.dashboard.workouts;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import teameleven.smartbells2.R;
import teameleven.smartbells2.dashboardfragmenttabs.Workouts_Fragment;

/**
 * Created by Jarret on 2015-11-30.
 */
public class ViewWorkout extends AppCompatActivity {

    private String nameValue;
    private String routineNameValue;
    private String exerciseNameValue;
    private String resistanceValue;
    private String setsValue;
    private String repsValue;

    private TextView nameView;
    private TextView routineName;
    private TextView exerciseName;
    private TextView resistance;
    private TextView sets;
    private TextView reps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_workout);

        //Set values passed into this activity
        nameValue = getIntent().getStringExtra(Workouts_Fragment.WORKOUT_ITEM_NAME);
        routineNameValue = getIntent().getStringExtra(Workouts_Fragment.WORKOUT_ROUTINE_NAME);
        exerciseNameValue = getIntent().getStringExtra(Workouts_Fragment.ROUTINE_EXERCISE_NAME);
        resistanceValue = getIntent().getStringExtra(Workouts_Fragment.ROUTINE_EXERCISE_RESISTANCE);
        setsValue = getIntent().getStringExtra(Workouts_Fragment.ROUTINE_EXERCISE_SETS);
        repsValue = getIntent().getStringExtra(Workouts_Fragment.ROUTINE_EXERCISE_REPS);

        //Associate text Views with Id in the layout xml
        nameView = (TextView) findViewById(R.id.workoutViewNameValue);
        routineName = (TextView) findViewById(R.id.routineNameSetValue);
        exerciseName = (TextView) findViewById(R.id.workoutExerciseSetValue);
        resistance = (TextView) findViewById(R.id.workoutResistanceSetValue);
        sets = (TextView) findViewById(R.id.workoutSetsSetValue);
        reps = (TextView) findViewById(R.id.workoutRepsSetValue);

        //Set values
        nameView.setText(nameValue.toString());
        routineName.setText(routineNameValue.toString());
        exerciseName.setText(exerciseNameValue.toString());
        resistance.setText(resistanceValue.toString());
        sets.setText(setsValue.toString());
        reps.setText(repsValue.toString());


    }


    public void recordWorkout(View view) {


    ////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //                              RECORD THIS WORKOUT
    //
    // /////////////////////////////////////////////////////////////////////////////////////////////

    }

    public void goBack(View view) {
        ViewWorkout.this.finish();
    }

}
