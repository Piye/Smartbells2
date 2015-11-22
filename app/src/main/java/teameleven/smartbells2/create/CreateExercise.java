package teameleven.smartbells2.create;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;

import teameleven.smartbells2.Dashboard;
import teameleven.smartbells2.R;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Exercise;

/**
 * Created by Jarret on 2015-10-07.
 * This Activity Handles the background functionality for creating a new Exercise. Its content is
 * presented in create_exercise.xml
 */
public class CreateExercise extends Fragment implements View.OnClickListener {

    private DatabaseAdapter database;
    private Exercise exercise;
    private Button cancel;
    private Button save;
    private FloatingActionButton fab;

    /**
     * onCreate
     * @param savedInstanceState
     */
    //@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Main view
        View view = inflater.inflate(R.layout.create_exercise, container, false);

        //Save Button
        save = (Button) view.findViewById(R.id.saveExercise);
        save.setOnClickListener(this);
        //CancelButton
        cancel = (Button) view.findViewById(R.id.cancelCreateExercise);
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
     * @return name
     */
    public String addExerciseName() {
        TextView name = (TextView) getActivity().findViewById(R.id.editExNameText);
        //Call setName() in Routine class
        return null; //(name.getText().toString());
    }

    /**
     * Add Increase Per Session
     * @return increasePS.getText().toString()
     */
    public String addIncreasePerSession() {
        TextView increasePS = (TextView) getActivity().findViewById(R.id.editIncreasePerSessionText);
        //call set method in exercise class
        return null; //(increasePS.getText().toString());

    }

    private boolean validate() {
        return true;
    }

    /************************************** BUTTONS ***********************************************/
    /**
     * Cancel Button handler. Finish this activity and go back to Main
     * @param view
     */
    @Override
    public void onClick(View view) {
        Fragment fragment;
        FragmentTransaction transaction;
        switch (view.getId()) {
            // for each button
            case R.id.saveExercise:
                //Add new Exercise
                if(validate()){

                    if (addExerciseName() != null && addIncreasePerSession() != null) {
                        try {
                            Exercise.restGetExercise(1);
                            exercise = new Exercise();
                            exercise.setName(this.addExerciseName());
                            exercise.setIncrease_Per_Session(Integer.parseInt(addIncreasePerSession()));

                            //loads either rest or the database. Rest also loads database
                            //exercise.restPutExercise(database);//both
                            database.insertExercise(exercise);//database

                            //todo this is currently blocked while the data is being retrieved. we can work with
                            //this later, however for the time being it is good enough for testing purposes.
                        } catch (Exception ex) {
                            Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Please fill in all fields.", Toast.LENGTH_LONG).show();
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
                fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
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
