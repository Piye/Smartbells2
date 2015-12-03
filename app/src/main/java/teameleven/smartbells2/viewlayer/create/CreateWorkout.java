package teameleven.smartbells2.viewlayer.create;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.viewlayer.Dashboard;
import teameleven.smartbells2.R;
import teameleven.smartbells2.viewlayer.SmartBellsMainActivity;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;
import teameleven.smartbells2.businesslayer.tableclasses.SetGroup;
import teameleven.smartbells2.businesslayer.tableclasses.WorkoutSession;
import teameleven.smartbells2.businesslayer.tableclasses.WorkoutSetGroup;

/**
 * This class creates a workout
 * Created by Jordan on 11/16/2015.
 */
public class CreateWorkout extends Fragment implements View.OnClickListener {
    /**
     * Cancel button
     */
    //private Button cancelButton;
    /***
     * save button
     */
    private Button saveButton;
    /**
     * FloatingActionButton
     */
    private FloatingActionButton fab;
    DatabaseAdapter database;
    /***
     * Textview for workout name
     */
    private TextView workoutName;
    private String routineName;
    private ListView routineList;

    ArrayList<String> routines;
    private ArrayList<String> clickedRoutines;

    /**
     * The create set group page
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState  Bundle
     * @return view
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        database = new DatabaseAdapter(this.getContext());
        try {
            database.openLocalDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Main view SmartBellsMainActivity.dashboardTab.setCheckTabPage(4)
        View view = inflater.inflate(R.layout.create_workout, container, false);
        // SmartBellsMainActivity.dashboardTab.getCheckTabPage()== 4 : Display Private Routine
        // dSmartBellsMainActivity.dashboardTab.getCheckTabPage()== 5 : Display Public Routine
        if(SmartBellsMainActivity.dashboardTab.getCheckTabPage() == 4) {
            routines = database.getMyRoutinesAsStrings(database.getUserIDForSession());
        }else{
            routines = database.getRoutinesAsStrings();
        }//WON
        System.out.println(" beginWorkout.getCheckTabPage()" +
                             SmartBellsMainActivity.dashboardTab.getCheckTabPage());

        routineList = (ListView) view.findViewById(R.id.createWorkoutRoutineList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_multiple_choice, routines);
        routineList.setAdapter(adapter);
        clickedRoutines = addListenerOnItems();

        workoutName = (TextView) view.findViewById(R.id.workoutNameEditText);
        //Set text to actually retrieve the Workout id's Name from DB
        //workoutName.setText("Get_From_Database_Name");
        //Button
        Button cancelButton;
        cancelButton = (Button) view.findViewById(R.id.cancelCreateWorkoutButton);
        saveButton = (Button) view.findViewById(R.id.saveCreateWorkoutButton);

        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
        return view;
    }

    /**
     * Adds a listener to the ListView
     * @return ArrayList of Strings
     */
    private ArrayList<String> addListenerOnItems() {
        final ArrayList<String> routineNames = new ArrayList<>();
        routineList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            SparseBooleanArray sp;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SparseBooleanArray sp = routineList.getCheckedItemPositions();

                for (int i = 0; i < sp.size(); i++) {
                    if (sp.valueAt(i)) {
                        routineNames.add((String) routineList.getItemAtPosition(i));
                    } else {
                        routineNames.remove(i);
                    }
                }
                routineName = parent.getItemAtPosition(position).toString();
                //Log.d("routineName " + routineName, "has been selected, its position is " + position);
            }
        });
        return routineNames;
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
            case R.id.saveCreateWorkoutButton:      //SAVING IS GOING TO JUST EXIT TO DASHBOARD AT THE MOMENT
                //Pop set group dialog window
                long id = 0;
                for (String routineName : clickedRoutines) {
                    id = database.getRoutineIDByName(routineName);
                }
                WorkoutSession session = new WorkoutSession();

                session.setName(workoutName.getText().toString());
                try {
                    Routine routine = new Routine(database.getRoutine(id));
                    for (SetGroup setGroup : routine.getSetGroups()) {
                        session.getSetGroups().add(new WorkoutSetGroup(setGroup));
                    }
                    database.insertWorkoutSession(session, true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                database.closeLocalDatabase();
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
                break;
        }
    }
}