package teameleven.smartbells2.dashboardfragmenttabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.BeginWorkout;
import teameleven.smartbells2.SmartBellsMainActivity;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/**
 * Created by Jare on 2015-11-19.
 */
public class MyRoutines_Fragment extends ListFragment {

    BeginWorkout dashboard;
    public static final String ROUTINE_ITEM_NAME = DatabaseAdapter.ROUTINE_NAME;
    //Temporary string array to populate list
    private ArrayList<String> myroutines;
    private DatabaseAdapter db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Tells main activity ADD button what type of item to add (ACHIEVEMENTS)
        SmartBellsMainActivity.bw2.setCheckTabPage(0);

        //Open Database
        db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
            //insert more routines
            //db.insertTESTRoutines();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Two ArrayLists for each ListView
        myroutines = db.getMyRoutinesAsStrings(db.getUserIDForSession());

        //close the database
        db.closeLocalDatabase();

        //Change adapter type to handle objects instead of strings later
        //Set the adapter to show in application
        ArrayAdapter<String> mylist = new ArrayAdapter<String>(getActivity().getBaseContext(),  android.R.layout.simple_list_item_1, myroutines);
        setListAdapter(mylist);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //run list code on tab select
    @Override
    public void onStart()
    {
        super.onStart();
        getListView();
    }

    @Override
    public void onListItemClick(ListView lv, View view, int position, long id) {
        //Start an intent when a list item is clicked

        Intent intent = new Intent(getActivity(), RecordWorkoutRoutine.class);
        //When we start the new intent we want to pass the name of the Routine from the list
        intent.putExtra(ROUTINE_ITEM_NAME, myroutines.get(position));

//        Pull Ispublic, reps, sets from DB, pass to proper view area, below is not how to implement. Just a reference for myself - Jordan
//        intent.putExtra(ROUTINE_ISPUBLIC, list.get(position));
//        intent.putExtra(ROUTINE_REPS, list.get(position));
//        intent.putExtra(ROUTINE_SETS, list.get(position));

        startActivity(intent);
    }
}
