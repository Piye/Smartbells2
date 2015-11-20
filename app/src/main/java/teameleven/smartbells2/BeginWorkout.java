package teameleven.smartbells2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import teameleven.smartbells2.create.CreateCustomSession;

/**
 * Created by Jare on 2015-10-06.
 */
public class BeginWorkout extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    Button createCustomSession;
    //private DatabaseAdapter db;
    //value to be passed to RecordWorkout Activity
    //public static final String ITEM_NAME = DatabaseAdapter.ROUTINE_NAME;

    private ArrayList<String> list;
    private ArrayList<String> plist;

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

        /*/Open Database
        db = new DatabaseAdapter(getActivity());
        try {
            db.openLocalDatabase();
            //insert more routines
            //db.insertTESTRoutines();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Two ArrayLists for each ListView
        list = db.getRoutinesAsStrings();
        plist = db.getRoutinesAsStrings();

        //close the database
        db.closeLocalDatabase();
                                                                      //The custom layout,     the textview id,    the list
        routinelist.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item_routine, R.id.routineNameValueDetail, list));
        publiclist.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item_routine,R.id.routineNameValueDetail, plist));
        */
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_session:
                Intent intent = new Intent(getActivity(), CreateCustomSession.class);
                startActivity(intent);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Start an intent when a list item is clicked

        //Intent intent = new Intent(getActivity(), RecordWorkoutRoutine.class);
        //When we start the new intent we want to pass the name of the Routine from the list
        //intent.putExtra(ITEM_NAME, list.get(position));
        //startActivity(intent);
    }
}
