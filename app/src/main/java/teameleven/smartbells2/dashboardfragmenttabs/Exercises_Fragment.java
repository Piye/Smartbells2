package teameleven.smartbells2.dashboardfragmenttabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teameleven.smartbells2.Dashboard;
import teameleven.smartbells2.SmartBellsMainActivity;

/**
 * Created by Jare on 2015-11-17.
 */
public class Exercises_Fragment extends ListFragment {

    Dashboard dashboard;
    //Temporary string array to populate list
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        //Refer to ONCLICK- SmartBellsMainActivity
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(2);

        /*
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
            //insert more routines
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ArrayList<String> listOfWorkouts = (ArrayList<String>) db.getExercisesAsStrings();
        */
        //Change adapter type to handle objects instead of strings later
        //Set the adapter to show in application
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, listOfWorkouts);
        //setListAdapter(adapter);

        //close the database
        //db.closeLocalDatabase();


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
