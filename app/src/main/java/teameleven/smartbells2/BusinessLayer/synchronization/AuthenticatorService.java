package teameleven.smartbells2.businesslayer.synchronization;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Andrew Rabb on 2015-11-13.
 */
public class AuthenticatorService extends Service {

    private Authenticator authenticator;

    public void onCreate(){
        authenticator = new Authenticator(this);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return authenticator.getIBinder();
    }
}
