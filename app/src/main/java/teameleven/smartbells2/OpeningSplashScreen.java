package teameleven.smartbells2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Jare on 2015-11-20.
 */
public class OpeningSplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_opening_screen);

        //Run splash screen on a seperate thread to avoid interupting the main program.
        Thread timedThread = new Thread(){
            public void run(){
                try{
                    //Allow this to run for 4 seconds
                    sleep(4000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    //Start the next Activity
                    Intent intent = new Intent(OpeningSplashScreen.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        };
        timedThread.start();
    }

    //Destroy the thread when it's finished running
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
