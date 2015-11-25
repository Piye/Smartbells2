package teameleven.smartbells2;

import android.content.ContentResolver;
import android.os.Bundle;


/**
 * Created by Jordan on 11/16/2015.
 * Splash Screen handler
 */

import android.app.Activity;
import android.content.Intent;

public class LoadingSplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_loading_screen);

        //Run splash screen on a seperate thread to avoid interupting the main program.
        Thread timedThread = new Thread(){
            public void run(){
                try{
                    //Allow this to run for 3 seconds
                    sleep(7000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{
                    Intent intent = new Intent(LoadingSplashScreen.this, SmartBellsMainActivity.class);
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