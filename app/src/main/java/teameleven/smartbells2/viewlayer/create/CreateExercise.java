package teameleven.smartbells2.viewlayer.create;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import teameleven.smartbells2.viewlayer.Dashboard;
import teameleven.smartbells2.R;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Exercise;

/**
 * Created by Jarret on 2015-10-07.
 * Updated by Brian McMahon
 * This Activity Handles the background functionality for creating a new Exercise. Its content is
 * presented in create_exercise.xml
 */
public class CreateExercise extends Fragment implements View.OnClickListener {
    /**
     * DatabaseAdapter
     */
    private DatabaseAdapter database;

    /**
     * onCreate Create the view of the input page of exercise
     *
     * @param savedInstanceState - Bundle input
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Main view
        View view = inflater.inflate(R.layout.create_exercise, container, false);

        //Save Button
        /*
      Save Button
     */
        Button save = (Button) view.findViewById(R.id.saveExercise);
        save.setOnClickListener(this);
        //CancelButton
        /*
      Cancel Button
     */
        Button cancel = (Button) view.findViewById(R.id.cancelCreateExercise);
        cancel.setOnClickListener(this);

        database = new DatabaseAdapter(getActivity());
        try {
            database.openLocalDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return view;
    }

    /**
     * Add Exercise Name
     *
     * @return name
     */
    public String addExerciseName() {
        /*
      Text View fields of the name
     */
        TextView name = (TextView) getActivity().findViewById(R.id.editExNameText);
        //Call setName() in Routine class
        return (name.getText().toString());
    }

    /**
     * Add Increase Per Session
     *
     * @return increasePS.getText().toString()
     */
    public String addIncreasePerSession() {
        /*
      TextView fields of the inscreas per session
     */
        TextView increasePS = (TextView) getActivity().findViewById(R.id.editIncreasePerSessionText);
        //call set method in exercise class
        return (increasePS.getText().toString());

    }

    private boolean validate() {

        if (addExerciseName() != null && addIncreasePerSession() != null) {
            return true;
        }
        Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
        return false;
    }

    /************************************** BUTTONS ***********************************************/
    /**
     * Cancel Button handler. Finish this activity and go back to Main
     *
     * @param view - view of Fragment
     */
    @Override
    public void onClick(View view) {
        Fragment fragment;
        FragmentTransaction transaction;
        switch (view.getId()) {
            // for each button
            case R.id.saveExercise:
                //Add new Exercise
                if (validate()) {
                    try {
                        Exercise exercise = new Exercise();
                        exercise.setName(addExerciseName());
                        exercise.setIncrease_Per_Session(Integer.parseInt(addIncreasePerSession()));

                        //loads either rest or the database. Rest also loads database
                        //exercise.restPutExercise(database);//both
                        Log.d("CreateExercise.onClick - ", exercise.toString());
                        database.insertExercise(exercise, true);//database

                    } catch (Exception ex) {
                        Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    //Close the database
                    database.closeLocalDatabase();

                    //Back to menu
                    Toast.makeText(getActivity(), "Exercise Added!!!", Toast.LENGTH_LONG).show();

                    //Transfer back to the dashboard
                    fragment = new Dashboard();
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, fragment);
                    transaction.commit();
                }

                break;
            case R.id.cancelCreateExercise:
                //Show the FAB again

                //FloatingActionButton

                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                //Transfer back to the dashboard
                fragment = new Dashboard();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                break;
        }
    }
}
