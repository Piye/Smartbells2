package teameleven.smartbells2.dashboardfragmenttabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/** Created by Jordan Medwid on 10/18/2015.
 * This class will handle an array of record objects to show them to the user
 * This class will be edited to accept JSON objects retrieved from a server- *Sprint 2
 */

public class Records_Fragment extends ListFragment {

    //Temporary string array to populate list
    String[] listOfRecords = new String[] {"10 Reps, 3 sets", "1000 Miles, 10 seconds", "Crazy Marathon"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        //Change adapter type to handle objects instead of strings later
        //Set the adapter to show in application
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getBaseContext(), android.R.layout.simple_list_item_1, listOfRecords);
        setListAdapter(adapter);

/*
        DatabaseAdapter db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
            //insert more routines
            //db.insertTESTRoutines();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //List of routines

        ArrayList<String> list = null; //db.getAchievements();
        //close the database
        db.closeLocalDatabase();
*/

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

