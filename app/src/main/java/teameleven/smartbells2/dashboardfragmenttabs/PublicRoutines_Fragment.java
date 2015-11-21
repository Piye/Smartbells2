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
public class PublicRoutines_Fragment extends ListFragment {

    BeginWorkout2 dashboard;
    //Temporary string array to populate list
    String[] listOfAchievements = new String[] {"not your routines", "---", "-----"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Tells main activity ADD button what type of item to add (ACHIEVEMENTS)
        SmartBellsMainActivity.bw2.setCheckTabPage(1);

        //Change adapter type to handle objects instead of strings later
        //Set the adapter to show in application
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1, listOfAchievements);
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
