package teameleven.smartbells2.dashboardfragmenttabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.Dashboard;
import teameleven.smartbells2.SmartBellsMainActivity;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/** Created by Jordan Medwid on 10/18/2015.
 * This class will handle an array of workout objects to show them to the user
 * This class will be edited to accept JSON objects retrieved from a server- *Sprint 2
 */

public class Workouts_Fragment extends ListFragment {

    Dashboard dashboard;
    //Temporary string array to populate list
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        //Tells main FAB button what type of item to add (WORKOUT)
        //Refer to ONCLICK- SmartBellsMainActivity
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(0);

/*
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
            //insert more routines
            //db.insertTESTRoutines();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Change adapter type to handle objects instead of strings later
        //Set the adapter to show in application
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, listOfWorkouts);
        //setListAdapter(adapter);

        *//**
         * Select Workouts of userId
         *//*
        ArrayList<String> listOfWorkouts = db.getMyWorkoutsAsStrings(db.getUserIDForSession());

        if (listOfWorkouts != null ){
            //Change adapter type to handle objects instead of strings later
            //Set the adapter to show in application
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(),
                    android.R.layout.simple_list_item_1, listOfWorkouts);
            setListAdapter(adapter);
        }else{
            System.out.println("listOfWorkouts null ");

        }
        //close the database
        db.closeLocalDatabase();*/


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //run list code on tab select
    @Override
    public void onStart()
    {
        super.onStart();
        getListView();
    }
}
