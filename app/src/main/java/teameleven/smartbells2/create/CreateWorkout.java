package teameleven.smartbells2.create;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import teameleven.smartbells2.Dashboard;
import teameleven.smartbells2.R;

/**
 * This class creates a workout
 * Created by Jordan on 11/16/2015.
 */
public class CreateWorkout extends Fragment implements View.OnClickListener {
    /**
     * Cancel button
     */
    private Button cancelButton;
    /***
     * save button
     */
    private Button saveButton;
    /**
     * FloatingActionButton
     */
    private FloatingActionButton fab;

    /***
     * Textview for workout name
     */
    private TextView workoutName;
    /**
     * The create set group page
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState  Bundle
     * @return view
     */
    String[] listOfRoutines = new String[] {"Routine 1", "Routine 2", "Routine 3", "Routine  4"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Main view
        View view = inflater.inflate(R.layout.create_workout, container, false);

        ListView list = (ListView)view.findViewById(R.id.createWorkoutRoutineList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, listOfRoutines);
        list.setAdapter(adapter);

        TextView workoutName = (TextView) view.findViewById(R.id.workoutNameEditText);
        //Set text to actually retrieve the Workout id's Name from DB
        workoutName.setText("Get_From_Database_Name");
        //Button
        cancelButton = (Button) view.findViewById(R.id.cancelCreateWorkoutButton);
        saveButton = (Button) view.findViewById(R.id.saveCreateWorkoutButton);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);

        return view;
    }

    /**
     * The screen of add_setgoup,design_routine and cancel create workout
     * @param v View
     */
    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction transaction;

        switch (v.getId()) {
            //Move to a new FRAGMENT for each button
            case R.id.saveCreateWorkoutButton:
                //Pop set group dialog window
                fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                fragment = new Dashboard();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                break;
            case R.id.design_routine:
                //Add new Routine
                break;
            case R.id.cancelCreateWorkoutButton:

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
