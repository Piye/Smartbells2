package teameleven.smartbells2.viewlayer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import teameleven.smartbells2.R;

/**
 * About Page Fragment
 * Created by Jarret on 2015-11-10.
 */
public class About extends Fragment {

    /**
     * onCreateView
     * inflate the view layout
     * @param inflater - Layout Inflater
     * @param container - View Container
     * @param savedInstanceState - Bundle saved instance state
     * @return - View
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the FAB button
        /*
      FloatingActionButton
     */
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.animate().translationY(fab.getHeight() + 50).setInterpolator(
                new AccelerateInterpolator(2)).start();

        //Set the view

        return inflater.inflate(R.layout.about_page, container, false);
    }

}
