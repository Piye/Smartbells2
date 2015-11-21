package teameleven.smartbells2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Jare on 2015-11-21.
 */
public class ViewProfile extends Fragment implements View.OnClickListener {

    private FloatingActionButton fab;
    private Button edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.profile_page, container, false);

        //Save Button
        edit = (Button) view.findViewById(R.id.editProfile);
        edit.setOnClickListener(this);

        //Hide the FAB
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.animate().translationY(fab.getHeight() + 50).setInterpolator(
                new AccelerateInterpolator(2)).start();


        return view;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment;
        FragmentTransaction transaction;
        switch (view.getId()) {
            // for each button
            case R.id.editProfile:
                //Edit Profile
                Toast.makeText(getActivity(), "Edit your profile!", Toast.LENGTH_LONG).show();
        }
    }

}