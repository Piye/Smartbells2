package teameleven.smartbells2.viewlayer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

import teameleven.smartbells2.R;

/**
 * This class runs a splash screen on a separate thread to avoid interrupting the main program
 */
public class LoadingSplashScreen extends Activity {
    /**
     * Display the splash_loading_screen
     * @param savedInstanceState Bundle for creating a view
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
         * Save the bundle of the instance states
         */
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_loading_screen);

        /**
         * Run splash screen on a separate thread to avoid interrupting the main program.
         */
        Thread timedThread = new Thread(){
            public void run(){
                try{
                    //Allow this to run for 4 seconds
                    sleep(4000);
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

    /**
     * Destroy the thread when it's finished running
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}