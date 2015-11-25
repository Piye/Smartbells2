package teameleven.smartbells2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.create.CreateCustomSession;
import teameleven.smartbells2.dashboardfragmenttabs.RecordWorkoutRoutine;

/**
 * This class displays the page of "BeginWorkout" of
 *                         the dashboard and connect to create a custom session
 * Created by Jare on 2015-10-06.
 */
public class BeginWorkout extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    /**
     * A butto for creating a custom session
     */
    Button createCustomSession;
    /**
     * Counter of Tab page
     */
    private int checkTabPage;
    /**
     * DatabaseAdapter
     */
    private DatabaseAdapter db;

    /**
     * Routine name of the table=value to be passed to RecordWorkout Activity
     */
    public static final String ITEM_NAME = DatabaseAdapter.ROUTINE_NAME;
    /**
     * Private Routine list
     */
    private ArrayList<String> list;
    /**
     * Public Routine list
     */
    private ArrayList<String> plist;

    /**
     * Display the "BeginWourout" page
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState Bundle
     * @return view of the page
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Main view
        View view = inflater.inflate(R.layout.tab_fragment_beginworkout, container, false);
        //Custom Session Button; when clicked it will call onClick
        createCustomSession = (Button) view.findViewById(R.id.custom_session);
        createCustomSession.setOnClickListener(this);

        //Get Routine list and fill up the list view
        ListView routinelist = (ListView) view.findViewById(R.id.myRoutinelistview);
        ListView publiclist = (ListView) view.findViewById(R.id.publicRoutinelistview);
        //Set On Item Click Listener; clicking on a list item will call onItemClick
        routinelist.setOnItemClickListener(this);
        publiclist.setOnItemClickListener(this);

        //Open Database
        db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
            //insert more routines
            //db.insertTESTRoutines();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Two ArrayLists for each ListView
        list = db.getMyRoutinesAsStrings(db.getUserIDForSession());
        plist = db.getRoutinesAsStrings();

        //close the database
        db.closeLocalDatabase();
                                                                      //The custom layout,     the textview id,    the list
        routinelist.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item_routine, R.id.routineNameValueDetail, list));
        publiclist.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item_routine,R.id.routineNameValueDetail, plist));

        return view;
    }

    /**
     * Call the CreateCustomSession when the custom_session is clicked
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_session:
                Intent intent = new Intent(getActivity(), CreateCustomSession.class);
                startActivity(intent);
        }
    }

    /**
     * Override when an item is clicked
     * @param parent AdapterView<?>
     * @param view View to get the position of clicked item
     * @param position Postion of the item
     * @param id id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Start an intent when a list item is clicked

        Intent intent = new Intent(getActivity(), RecordWorkoutRoutine.class);
        //When we start the new intent we want to pass the name of the Routine from the list
        intent.putExtra(ITEM_NAME, list.get(position));
        startActivity(intent);
    }

    /**
     * Get the value of CheckBWTabPage
     * @return TabPage number
     */
    public int getCheckBWTabPage() {
        return checkTabPage;
    }

    /**
     * Set the value of CheckTabPage
     * @param checkTabPage Tabpage number
     */
    public void setCheckTabPage(int checkTabPage) {
        this.checkTabPage = checkTabPage;
        //Use for debugging - Want to make sure variable is changing with tab clicks.
        System.err.print(checkTabPage);
    }
}
