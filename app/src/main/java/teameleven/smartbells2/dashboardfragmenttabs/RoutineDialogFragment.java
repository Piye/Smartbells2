package teameleven.smartbells2.dashboardfragmenttabs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import teameleven.smartbells2.R;

/**
 * Created by Jordan on 11/20/2015.
 */
public class RoutineDialogFragment extends DialogFragment{

    private Spinner exerciseSpinner;
    private String exerciseName;
    private Button addButton, cancelButton;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            //Establish Root view
            View rootView = getActivity().getLayoutInflater().inflate(R.layout.add_exercise_to_routine, new LinearLayout(getActivity()), false);

//            getDialog().setTitle("Add Exercise");

            //Set up the Spinner

            exerciseSpinner = (Spinner) rootView.findViewById(R.id.exerciseSpinner);
            addButton = (Button) rootView.findViewById(R.id.addExerciseFromPrompt);
            cancelButton = (Button) rootView.findViewById(R.id.cancelExerciseFromPrompt);


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

            //Setting up the Add Button
            addButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                //Todo: Add function to save the exercise to the list in CreateRoutine.
                    //LIST LOCATED IN CreateRoutine.java
                }
            });

            //Setting up cancel button
            cancelButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    getFragmentManager().popBackStackImmediate();

                }
            });

            // Build dialog
            Dialog builder = new Dialog(getActivity());
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            builder.getWindow().setBackgroundDrawable(new ColorDrawable(Color.LTGRAY));
            builder.setContentView(rootView);
            return builder;


        }
}
