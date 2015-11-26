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
 * Created by Jordan on 11/16/2015.
 */
public class CreateWorkout extends Fragment implements View.OnClickListener {

    private Button cancel, save;
    private FloatingActionButton fab;
    //Dummy list, to show functionality
    String[] listOfRoutines = new String[] {"Routine 1", "Routine 2", "Routine 3", "Routine  4"};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Main view
        View view = inflater.inflate(R.layout.create_workout, container, false);

        ListView list = (ListView)view.findViewById(R.id.createWorkoutRoutineList);
        //List is displaying dummy values to show list in action.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, listOfRoutines);
        list.setAdapter(adapter);

        TextView workout_name = (TextView) view.findViewById(R.id.workoutNameEditText);
        //Set text to actually retrieve the Workout id's Name from DB
        workout_name.setText("Get_From_Database_Name");


        //Save Button
        save = (Button) view.findViewById(R.id.saveWorkoutButton);
        save.setOnClickListener(this);
        //Cancel Button
        cancel = (Button) view.findViewById(R.id.cancelCreateWorkout);
        cancel.setOnClickListener(this);

        return view;
    }



    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction transaction;

        switch (v.getId()) {
            //Move to a new FRAGMENT for each button
            case R.id.saveWorkoutButton:
                //todo: Check list to see which routines are selected.
                //todo: Get routine by ID (FK to Workout PK?)
                //todo: Send to be saved as JSON object

                break;

            case R.id.cancelCreateWorkout:

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
