package teameleven.smartbells2.viewlayer.dashboardfragmenttabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import teameleven.smartbells2.viewlayer.SmartBellsMainActivity;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/** Created by Jordan Medwid on 10/18/2015.
 * This class will handle an array of record objects to show them to the user
 * This class will be edited to accept JSON objects retrieved from a server- *Sprint 2
 */
public class New_Records_Fragment extends ListFragment {

    /**
     * Previous WorkoutSession name
     */
    private String previousWorkout = "";
    /**
     * Counter for Achievement
     */
    private int counter = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Tells main activity ADD button what type of item to add (RECORD)
        SmartBellsMainActivity.dashboardTab.setCheckTabPage(4);

        //Open Database
        /*
      Database Adapter
     */
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
            //insert more routines
            //db.insertTESTRoutines();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Get the list of WorkoutSession from the Database

        /*
      Variable for saving a list of the workouts name
     */
        ArrayList<String> myWorkoutList = db.getMyWorkoutsAsStrings(db.getUserIDForSession());

        //close the database
        db.closeLocalDatabase();


        //Sort the arrayList
        Collections.sort(myWorkoutList, new Comparator<String>() {

            @Override
            public int compare(String lhs, String rhs) {
                return lhs.compareToIgnoreCase(rhs);//Ascending order.
                //return (lhs.compareToIgnoreCase(rhs)*(-1));//Descending order.
            }
        });

        //Count the WorkoutSession
        /*
      Variable for count a list of the workouts name
     */
        ArrayList<String> myCountedWorkoutList = new ArrayList<>();
        for(String s: myWorkoutList){

            if ((s.equals(previousWorkout)) || (counter==0)){
                previousWorkout = s;
                counter++;
                System.out.println("S + previousWorkout " + s + "  " +
                        previousWorkout + " counter = " + counter);

            }else{
                CountedClass countedClass = new CountedClass(previousWorkout, counter);
                myCountedWorkoutList.add(countedClass.toString());

                System.out.println("2 S + previousWorkout " + s + "  " +
                        previousWorkout + " counter = " + counter);
                previousWorkout = s;
                counter = 1;
            }
        }
        /*
      ArrayAdapter for saving the list of WorkoutSessions
     */
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getBaseContext(),
                android.R.layout.simple_list_item_1, myCountedWorkoutList);
        setListAdapter(adapter);


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    //run list code on tab select
    // @Override
    public void onStart()
    {
        super.onStart();
        getListView();
    }

    /**
     * Counted Class
     */
    class CountedClass{
        String name;
        int cnt;

        CountedClass(String name, int cnt){
            this.name = name;
            this.cnt = cnt;
        }

        public String toString(){
            return name + "  : " + cnt + " Times";
        }
    }
}

