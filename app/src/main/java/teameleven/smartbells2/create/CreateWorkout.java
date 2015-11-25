package teameleven.smartbells2.create;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

import teameleven.smartbells2.Dashboard;
import teameleven.smartbells2.R;

/**
 * This class creates a workout
 * Created by Jordan on 11/16/2015.
 */
public class CreateWorkout extends Fragment implements View.OnClickListener {
    /**
     * Cancel button
     */
    private Button cancel;
    /**
     * FloatingActionButton
     */
    private FloatingActionButton fab;

    /**
     * The create set group page
     * @param inflater LayoutInflater
     * @param container ViewGroup
     * @param savedInstanceState  Bundle
     * @return view
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Main view
        View view = inflater.inflate(R.layout.edit_workout, container, false);
        //Button
        cancel = (Button) view.findViewById(R.id.cancelCreateWorkout);
        cancel.setOnClickListener(this);

        return view;
    }

    /**
     * The screen of add_setgoup,design_routine and cancel create workout
     * @param v View
     */
    @Override
    public void onClick(View v) {
        Fragment fragment;
        FragmentTransaction transaction;

        switch (v.getId()) {
            //Move to a new FRAGMENT for each button
            case R.id.add_setgroup:
                //Pop set group dialog window
                /*
                fragment = new CreateSetGroupDialog();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                */
                break;
            case R.id.design_routine:
                //Add new Routine
                break;
            case R.id.cancelCreateWorkout:

                fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
                fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                fragment = new Dashboard();
                transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.content_main, fragment);
                transaction.commit();
                //Kill the fragment
                //getFragmentManager().beginTransaction().remove(this).commit();
                break;
        }
    }
}
