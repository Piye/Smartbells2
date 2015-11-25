package teameleven.smartbells2.dashboardfragmenttabs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import teameleven.smartbells2.R;
import teameleven.smartbells2.BeginWorkout;

/**
 * Created by Jare on 2015-11-03.
 */
public class RecordWorkoutRoutine extends AppCompatActivity {

    private String nameValue = null;
    private TextView nameView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_workout);

        //Get the name value passed and set it to the textview value
        nameValue = getIntent().getStringExtra(BeginWorkout.ITEM_NAME);
        nameView = (TextView) findViewById(R.id.nameTextValue);
        nameView.setText(nameValue);
    }

    //Save new workout
    public void saveSession(View view) {
        Toast.makeText(this, "Save a new Workout Session", Toast.LENGTH_LONG).show();
    }

    //Cancel - back to menu
    public void cancelCreateCustomSession(View view) {
        RecordWorkoutRoutine.this.finish();
    }
}
