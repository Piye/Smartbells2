package teameleven.smartbells2.viewlayer.create;

import android.app.FragmentManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.viewlayer.Dashboard;
import teameleven.smartbells2.R;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Exercise;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;
import teameleven.smartbells2.viewlayer.RoutineDialogFragment;
import teameleven.smartbells2.businesslayer.tableclasses.SetGroup;

/**
 * CreateRoutine class makes a Routine of Exercises(Set Group)
 * Created  by Jarret on 2015-10-05.
 * Update   by WonKyoung on 2015-10-10
 * Update   by WonKyoung on 2015-10-25
 * Update   by WonKyoung On 2015-11-05
 * Updated by Brian McMahon
 * updated: Oct 8th, 2015 : added methods to interact with Exercise and Routine for Routine saving and creation.
 */
public class CreateRoutine extends Fragment implements View.OnClickListener {
    /**
     * DatabaseAdapter for query
     */
    private DatabaseAdapter database;
    /**
     * Boolean where it is public or not
     */
    private Boolean isPublic = false;
    /**
     * List of excercises(Exercise attribute)
     */
    ArrayList<String> exerciseName = new ArrayList<>();
    private ArrayList<SetGroup> setGroups = new ArrayList<>();
    /**
     * Radio Group for choosing a radio button of Public or Private
     */
    private RadioGroup radioGroup;
    /**
     * Public radio button
     */
    private RadioButton publicButton;

    private RadioButton publicRoutine;
    private RadioButton privateRoutine;
    private ArrayAdapter<String> adapter;


    //@Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {


        BroadcastReceiver localBroadcastReceiver = new LocalBroadcastReceiver();
        LocalBroadcastManager.getInstance(getActivity().getApplicationContext())
                .registerReceiver(localBroadcastReceiver, new IntentFilter("DialogResult"));
        //Main view
        View view = inflater.inflate(R.layout.create_routine, container, false);
        //Save Button
        /*
      Save Button
     */
        Button save = (Button) view.findViewById(R.id.routineExerciseSaveButton);
        save.setOnClickListener(this);
        //CancelButton
        /*
      Cancel Button
     */
        Button cancel = (Button) view.findViewById(R.id.routineExerciseCancelButton);
        cancel.setOnClickListener(this);

        ListView routineSetGroups = (ListView) view.findViewById(R.id.routineSetGroups);

        /**
         Add Set Group Button
         */Button addSetGroup = (Button) view.findViewById(R.id.addExerciseListButton);
        addSetGroup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getFragmentManager();
//                CustomDialogFragment dialog = new CustomDialogFragment();
                RoutineDialogFragment dialog = new RoutineDialogFragment();

                dialog.show(fm, "SetGroupCreator");
            }
        });
        //Open Database
        database = new DatabaseAdapter(getActivity());
        try {
            database.openLocalDatabase();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }
        //ArrayList<String> exerciseList = database.getExercisesAsStrings();

        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, exerciseName);
        routineSetGroups.setAdapter(adapter);

        return view;
    }

    /**
     * Add Routine Name
     *
     * @return Routine name
     */
    public String addRoutineName() {
        /*
      onCreate - Create view of input screen for creating  set group

      */
        EditText mRoutineName = (EditText) getActivity().findViewById(R.id.editNameText);
        //Call setName() in Routine class
        //routine.setName(name.getText().toString());
        return mRoutineName.getText().toString();
    }

    /**
     * Return the boolean when a radio button clicked(Public or not(Private))
     *
     * @return isPublic
     */
    public Boolean addListenerOnRadioButton() {

        publicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                //int selectedId = radioGroup.getCheckedRadioButtonId();
                // declaring "public" radio button value
                publicRoutine = (RadioButton) getActivity().findViewById(R.id.publicTextView);
                privateRoutine = (RadioButton) getActivity().findViewById(R.id.privateTextView);
                // Comparison selectedId and buttonId
                //if (selectedId == publicRoutine.getId()) {
                if (publicRoutine.isChecked()) {
                    isPublic = true;
                } else if (privateRoutine.isChecked()) {
                    isPublic = false;
                }
                //Toast.makeText(CreateRoutine.this,
                //publicButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return isPublic;
    }

    /**
     * Create a routine with set group and cancel
     *
     * @param v View
     */
    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction transaction;

        switch (v.getId()) {
            //Move to a new FRAGMENT for each button
            case R.id.add_setgroup:
                //Pop set group dialog window
                /*
                fragment = new CreateSetGroupDialog();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                */
                break;
            case R.id.routineExerciseSaveButton:
                //SAVE new Routine to databasepiyep
                //if (validate()) {
                //SetGroup setGroup = new SetGroup(
                //database.getExerciseIdByName(exerciseName),
                //Integer.parseInt(addNumberOfSets()),
                //Integer.parseInt(addRepsPerSet()
                // );
                Routine routine = new Routine();
                //routine.getSetGroups().add(setGroup);
                routine.setName(addRoutineName());
                routine.setIs_Public(isPublic);
                routine.setSetGroups(setGroups);
                Log.d("CreateRoutine.saveRoutine - ", routine.toString());
                //database is called in restputroutine. both calls not necessary
                database.insertRoutine(routine, true);
                //routine.RestPutRoutine(database);//should not call rest here - data will be
                //input into update table for later synchronizing
                //Close the database
                database.closeLocalDatabase();

                //Back to menu
                Toast.makeText(getActivity(), "routine " + routine.getName() + " created!!.", Toast.LENGTH_LONG).show();

                /*
      FloatingActionButton
     */
                FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                fragment = new Dashboard();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                break;


            case R.id.routineExerciseCancelButton:

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

    private void refreshAdapter(int id) {
        try {
            exerciseName.add(new Exercise(database.getExercise(id)).getName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    private class LocalBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null || intent.getAction() == null) {
                return;
            }
            if (intent.getAction().equals("DialogResult")) {
                try {
                    SetGroup setGroup = new SetGroup(new JSONObject(intent.getStringExtra("setGroup")));
                    setGroups.add(setGroup);
                    refreshAdapter(setGroup.getExerciseId());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}