package teameleven.smartbells2.dashboardfragmenttabs;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import teameleven.smartbells2.SmartBellsMainActivity;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/**
 * Created by Jordan Medwid on 10/18/2015.
 * This class will handle an array of routine objects to show them to the user
 * This class will be edited to accept JSON objects retrieved from a server- *Sprint 2
 */


public class Routines_Fragment extends ListFragment {

    //public static final String ROUTINE_ITEM_NAME = DatabaseAdapter.ROUTINE_NAME;
    //public static final String ROUTINE_ISPUBLIC = DatabaseAdapter.ROUTINE_IS_PUBLIC;

    //Need to pull reps and sets from Database, added variables for Sprint 3 to Database
//    public static final String ROUTINE_REPS = DatabaseAdapter.ROUTINE_REPS;
//    public static final String ROUTINE_SETS = DatabaseAdapter.ROUTINE_SETS;

    public ArrayList<String> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Tells main activity ADD button what type of item to add (RECORD)
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(1);

        //List of routines
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
        } catch (java.sql.SQLException e) {
            e.printStackTrace();
        }

        list = db.getRoutinesAsStrings();

        //Set the adapter to show in application
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, list);
        setListAdapter(adapter);


        //close the database
        db.closeLocalDatabase();


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView lv, View view, int position, long id) {
        //Start an intent when a list item is clicked

        //Intent intent = new Intent(getActivity(), RecordWorkoutRoutine.class);
        //When we start the new intent we want to pass the name of the Routine from the list
        //intent.putExtra(ROUTINE_ITEM_NAME, list.get(position));

//        Pull Ispublic, reps, sets from DB, pass to proper view area, below is not how to implement. Just a reference for myself - Jordan
//        intent.putExtra(ROUTINE_ISPUBLIC, list.get(position));
//        intent.putExtra(ROUTINE_REPS, list.get(position));
//        intent.putExtra(ROUTINE_SETS, list.get(position));


        //startActivity(intent);
    }

    //run list code on tab select
    @Override
    public void onStart() {
        super.onStart();
        getListView();
    }


}

