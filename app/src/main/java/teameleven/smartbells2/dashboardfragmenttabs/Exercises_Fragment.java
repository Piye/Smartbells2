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

/**
 * This class shows the list of Excercise
 * Created by Jare on 2015-11-17.
 */
public class Exercises_Fragment extends ListFragment {
    /**
     * Dashboard of Smartbells(Workout,Routine,Excercise)
     */
    Dashboard dashboard;
    //Temporary string array to populate list
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        /**
         * Refer to ONCLICK- SmartBellsMainActivity
         */
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(2);
        /**
         * DB adaptoer
         */
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
            //insert more routines
        } catch (SQLException e) {
            e.printStackTrace();
        }
        /**
         * List of Exercise from Database
         */
        ArrayList<String> listOfExercises = (ArrayList<String>) db.getExercisesAsStrings();

        /**
         * Change adapter type to handle objects instead of strings later
         * Set the adapter to show in application
         */

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getBaseContext(), android.R.layout.simple_list_item_1, listOfExercises);
        setListAdapter(adapter);

        //close the database
        db.closeLocalDatabase();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //run list code on tab select
//    @Override
//    public void onStart()
//    {
//        super.onStart();
//        getListView();
//    }
}
