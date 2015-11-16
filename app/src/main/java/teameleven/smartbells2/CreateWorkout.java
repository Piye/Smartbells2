package teameleven.smartbells2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import teameleven.smartbells2.R;

/**
 * Created by Jordan on 11/16/2015.
 */
public class CreateWorkout extends Fragment{

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //Main view
        View view = inflater.inflate(R.layout.create_workout, container, false);
        return view;
    }
}
