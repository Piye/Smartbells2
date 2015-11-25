package teameleven.smartbells2.dashboardfragmenttabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import teameleven.smartbells2.AchievementDashboard;
import teameleven.smartbells2.SmartBellsMainActivity;

/**
 * Created by Jordan Medwid on 10/18/2015.
 * This class will handle an array of achievement objects to show them to the user
 * This class will be edited to accept JSON objects retrieved from a server- *Sprint 2
 */
public class Achievement_Fragment extends ListFragment {
        /**
         * AchievementDashboard
         */
        AchievementDashboard dashboard;

        /**
         * Temporary string array to populate list
         */
        String[] listOfAchievements = new String[] {"Walked 500 miles", "Walk 500 more", "running"};

        /**
         * Display the list of Achievements
         * @param inflater LayoutInflater
         * @param container ViewGroup
         * @param savedInstanceState Bundle
         * @return onCreateView of the listOfAchievements
         */
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            //Tells main activity ADD button what type of item to add (ACHIEVEMENTS)
            SmartBellsMainActivity.dashboardTab.setCheckTabPage(2);

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
