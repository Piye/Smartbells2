package teameleven.smartbells2.dashboardfragmenttabs;

import android.content.Context;
import android.view.View;
import android.widget.TabHost.TabContentFactory;

/**
 * Created by Jordan on 10/18/2015.
 * This class will render individual tabs
 */
public class Homepage_TabContent implements TabContentFactory {

private Context thisContext;

    public Homepage_TabContent(Context context){
        thisContext = context;
    }

    //
    @Override
    public View createTabContent(String tabtag){
        View view = new View(thisContext);
        return view;
    }
}
