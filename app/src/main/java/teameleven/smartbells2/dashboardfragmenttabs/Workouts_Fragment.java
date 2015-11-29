package teameleven.smartbells2.dashboardfragmenttabs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
<<<<<<< HEAD
=======
        //Tells main activity ADD button what type of item to add (WORKOUT)
        //Refer to ONCLICK- SmartBellsMainActivity
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(0);
>>>>>>> origin/master

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

    /**
     * Override the view when the list item clicked
     * @param lv ListView of Routine name
     * @param view View
     * @param position postion of the item
     * @param id Id of the clicked item
     */
    @Override
    public void onListItemClick(ListView lv, View view, int position, long id) {
        final int pos = position;

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getActivity(), "Delete", Toast.LENGTH_LONG).show();
            }
        });
        builder.setPositiveButton("View", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface arg0, int arg1) {

                //Do something
                Toast.makeText(getActivity(), "View", Toast.LENGTH_LONG).show();
            }

        });

        Dialog mydialog = builder.setView(new View(getActivity())).create();

        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.copyFrom(mydialog.getWindow().getAttributes());
        params.width = 600;
        params.height = 250;
        mydialog.show();
        mydialog.getWindow().setAttributes(params);

    }

}
