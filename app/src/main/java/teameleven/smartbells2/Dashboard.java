package teameleven.smartbells2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import teameleven.smartbells2.dashboardfragmenttabs.Achievement_Fragment;
import teameleven.smartbells2.dashboardfragmenttabs.Records_Fragment;
import teameleven.smartbells2.dashboardfragmenttabs.Routines_Fragment;
import teameleven.smartbells2.dashboardfragmenttabs.Workouts_Fragment;


public class Dashboard extends Fragment {

    private FragmentTabHost dashboardTabHost;
    private int checkTabPage;

    //Create the view and setup the tab host to view
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab_fragment_dashboard, container, false);

        dashboardTabHost = (FragmentTabHost) rootView.findViewById(android.R.id.tabhost);
        dashboardTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);

        //Creates a listener, and handles fragment manager to swap between list fragments
        TabHost.OnTabChangeListener tabChangeListener = new TabHost.OnTabChangeListener() {
            //Inner method to control tab functions
            @Override
            public void onTabChanged(String tabId) {

                android.support.v4.app.FragmentManager fragManager = getActivity().getSupportFragmentManager();

                Workouts_Fragment workout_Fragment = (Workouts_Fragment) fragManager.findFragmentByTag("workouts");
                Routines_Fragment routine_Fragment = (Routines_Fragment) fragManager.findFragmentByTag("routines");
                Achievement_Fragment achievement_Fragment = (Achievement_Fragment) fragManager.findFragmentByTag("achievements");
                Records_Fragment records_Fragment = (Records_Fragment) fragManager.findFragmentByTag("records");

                android.support.v4.app.FragmentTransaction fragTransaction = fragManager.beginTransaction();

                //Detach Workout_Fragment if it exists
                if (workout_Fragment != null) {
                    fragTransaction.detach(workout_Fragment);
                }


                //Detach Routine_Fragment if it exists
                if (routine_Fragment != null) {
                    fragTransaction.detach(routine_Fragment);
                }

                //Detach Achievement_Fragment if it exists
                if (achievement_Fragment != null) {
                    fragTransaction.detach(achievement_Fragment);
                }


                //Detach Records_Fragment if it exists
                if (records_Fragment != null) {
                    fragTransaction.detach(records_Fragment);
                }


                //If the current tab is on workouts, show only workouts
                if (tabId.equalsIgnoreCase("workouts")) {
                    setCheckTabPage(0);
                    if (workout_Fragment == null) {
                        fragTransaction.add(R.id.realtabcontent, new Workouts_Fragment(), "workouts");
                    } else {
                        fragTransaction.attach(workout_Fragment);
                    }
                }

                //If the current tab is on routines, show only routines
                if (tabId.equalsIgnoreCase("routines")) {
                    setCheckTabPage(1);
                    if (routine_Fragment == null) {
                        fragTransaction.add(R.id.realtabcontent, new Routines_Fragment(), "routines");
                    } else {
                        fragTransaction.attach(routine_Fragment);
                    }
                }

                //If the current tab is on achievements show only achieves
                if (tabId.equalsIgnoreCase("achievements")) {
                    setCheckTabPage(2);
                    if (achievement_Fragment == null) {
                        fragTransaction.add(R.id.realtabcontent, new Achievement_Fragment(), "achievements");
                    } else {
                        fragTransaction.attach(achievement_Fragment);
                    }
                }

                //If the current tab is on records show only records
                if (tabId.equalsIgnoreCase("records")) {
                    setCheckTabPage(3);
                    if (records_Fragment == null) {
                        fragTransaction.add(R.id.realtabcontent, new Records_Fragment(), "records");
                    } else {
                        fragTransaction.attach(records_Fragment);
                    }
                }

                fragTransaction.commit();

            }
        };


        //Setting up the tab change listener

        dashboardTabHost.setOnTabChangedListener(tabChangeListener);

        //Setting up builders for each tabs

        //Workout Tab creation
        dashboardTabHost.addTab(dashboardTabHost.newTabSpec("workouts").setIndicator("Workouts"), Workouts_Fragment.class, null);

        //Routine Tab creation
        dashboardTabHost.addTab(dashboardTabHost.newTabSpec("routines").setIndicator("Routines"), Routines_Fragment.class, null);

        //Achievements Tab creation
        dashboardTabHost.addTab(dashboardTabHost.newTabSpec("achievements").setIndicator("Achievements"), Achievement_Fragment.class, null);

        //Records Tab creation
        dashboardTabHost.addTab(dashboardTabHost.newTabSpec("records").setIndicator("Records"), Records_Fragment.class, null);


        //Adjusting the text within the tabs to properly show titles
        for (int i = 0; i < 4; i++)
        {
            TextView x = (TextView) dashboardTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            if (i == 2)
            {
                x.setTextSize(7);
            } else {x.setTextSize(10);}


        }

        //Return the view
        return rootView;
    }


    public int getCheckTabPage() {
        return checkTabPage;
    }

    public void setCheckTabPage(int checkTabPage) {
        this.checkTabPage = checkTabPage;
        //Use for debugging - Want to make sure variable is changing with tab clicks.
        System.err.print(checkTabPage);
    }


}