package teameleven.smartbells2.dashboardfragmenttabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import teameleven.smartbells2.BeginWorkout2;
import teameleven.smartbells2.SmartBellsMainActivity;

/**
 * Created by Jare on 2015-11-19.
 */
public class MyRoutines_Fragment extends ListFragment {

    BeginWorkout2 dashboard;
    //Temporary string array to populate list
    String[] myroutines = new String[] {"Kicking", "Punching", "Jumping",
                                        "Kicking", "Punching", "Jumping",
                                        "Kicking", "Punching", "Jumping",
                                        "Kicking", "Punching", "Jumping",
                                        "Kicking", "Punching", "Jumping",
                                        "Kicking", "Punching", "Jumping",
                                        "Kicking", "Punching", "Jumping",
                                        "Kicking", "Punching", "Jumping",
                                        "Kicking", "Punching", "Jumping"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Tells main activity ADD button what type of item to add (ACHIEVEMENTS)
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(2);

        //Change adapter type to handle objects instead of strings later
        //Set the adapter to show in application
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1, myroutines);
        setListAdapter(adapter);

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
