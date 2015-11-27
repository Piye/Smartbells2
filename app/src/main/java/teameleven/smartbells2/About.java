package teameleven.smartbells2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

/**
 * Explanation about the team eleven
 * Created by Jarret on 2015-11-10.
 */
public class About extends Fragment {
    /**
     * View for inflater of the about_page
     */
    private TextView link;
    /**
     * FloatingActionButton
     */
    private FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Hide the FAB
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.animate().translationY(fab.getHeight() + 50).setInterpolator(
                new AccelerateInterpolator(2)).start();

        View rootView = inflater.inflate(R.layout.about_page, container, false);

        //link = (TextView) getActivity().findViewById(R.id.webAppLink);
        //link.setMovementMethod(LinkMovementMethod.getInstance());

        return rootView;
    }

}
