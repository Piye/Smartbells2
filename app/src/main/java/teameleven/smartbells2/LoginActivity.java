package teameleven.smartbells2;
//TODO implement progress dialogs
//TODO implement new validation in signup for using existing user details.

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.businesslayer.SessionManager;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Exercise;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;
import teameleven.smartbells2.businesslayer.tableclasses.WorkoutSession;


/**
 * Created by Brian McMahon on 14/10/2015.
 */
public class LoginActivity extends Activity {

    private EditText mUserName;
    private EditText mPassword;
    private Button mLogInButton;
    private TextView mSignUp;
    String TAG = "DEBUGGING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";

    /*
    This is to set up a dummy account. if we had more time, we could easily change to
    an authenticator pattern for the Account. However, for the sake of this project,
     we'll keep it like this
     */
    private static final String AUTHORITY =
            "teameleven.smartbells_prototype0001.businesslayer.synchronization.provider";
    private static final String ACCOUNT_TYPE = "smart-bells-staging.herokuapp.com";
    private static final String ACCOUNT = "DefaultAccount";
    Account account;

    SessionManager session;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mUserName = (EditText) findViewById(R.id.editText1);
        mPassword = (EditText) findViewById(R.id.editText2);
        mLogInButton = (Button) findViewById(R.id.logInButton);
        mSignUp = (TextView) findViewById(R.id.sign_up_link);

        //login when user clicks button
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        //redirect to signup page for new users
        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //move to signup activity
                Intent intent = new Intent(v.getContext(), SignupActivity.class);
                startActivity(intent);
                //close activity
                finish();
            }
        });

    }

    //attempts to login to the account
    public void login() {
        if (!validate()) {
            mUserName.setText("");
            mPassword.setText("");
        } else {

            //temporary  //TODO ADD PROGRESS DIALOG
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();

            //instantiate the storage session
            session = new SessionManager(getApplicationContext());

            session.createLoginSession(getUsername(), getPassword());

            //Toast.makeText(getApplicationContext(), "User Login Status: " +
            // session.isLoggedIn(), Toast.LENGTH_LONG).show();



            //move to main activity
            //Intent intent = new Intent(this, SmartBellsMainActivity.class);

            /*
             * this block of code runs the Synchronization setup.
             * It will also run an initial Synchronization
             * As it runs this on a background thread, in theory
             * this should occur as the loading screen is showing
             */
            account = CreateSyncAccount(this);
            Bundle sync = new Bundle();
            sync.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
            sync.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
            ContentResolver.requestSync(account, AUTHORITY, sync);

            //move to the splashScreen instead
            Intent intent = new Intent(this, LoadingSplashScreen.class);
            startActivity(intent);
            //close activity
            finish();
        }

    }

    //todo review below
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed Called");

        //close app if back is pressed on login activity
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        LoginActivity.super.onBackPressed();
                        Intent intent = new Intent(Intent.ACTION_MAIN);

                        intent.addCategory(Intent.CATEGORY_HOME);

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).create().show();
    }

    public String getUsername() {
        return mUserName.getText().toString();
    }

    public String getPassword() {
        return mPassword.getText().toString();
    }

    //validates user entries
    public boolean validate() {
        boolean valid;

        //check if password is the required length and not empty and username is not empty
        if (getUsername().isEmpty() || getUsername().length() < 6 || getPassword().isEmpty() ||
                getPassword().length() < 6 || getPassword().length() > 12) {
            mUserName.setError("Please enter a valid Username or Email greater than 6 characters");
            mPassword.setError("Please enter a password containing between 6 and 12 characters");
            valid = false;
        } else {
            Authentication auth = new Authentication(getUsername(), getPassword());

            String authorized = auth.restAuthentication();

            if (authorized.equals("")) {
                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                valid = false;
            } else {
                auth.setAccessToken(authorized);
                DatabaseAdapter db = new DatabaseAdapter(getBaseContext());
                try {
                    db.openLocalDatabase();

                    JSONObject json = new JSONObject(authorized);

                    String accessToken = json.getString("authentication_token");
                    int user_id = json.getInt("id");
                    Log.d("LoginActivity.validate - token checking - ", user_id + "<-id token ->" +  accessToken);
                    db.insertToken(accessToken, user_id);
                    initialDatabaseSync(db);
                } catch (SQLException | JSONException e) {
                    e.printStackTrace();
                }
                db.closeLocalDatabase();
                valid = true;
            }

        }

        return valid;
    }

    private void initialDatabaseSync(DatabaseAdapter db) {
        db.updateDB();
        long x = System.currentTimeMillis();
        long y;


        ArrayList<Exercise> exercise = Exercise.restGetAll();
        Log.d("LoginActivity.initialDatabaseSync - Exercise row count = ", String.valueOf(exercise.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        db.loadAllExercises(exercise);


        ArrayList<Routine> routines = Routine.restGetAll(db.getUserIDForSession());
        Log.d("LoginActivity.initialDatabaseSync - Routine row count = ", String.valueOf(routines.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        db.loadAllRoutines(routines);


        ArrayList<WorkoutSession> workoutSessions = WorkoutSession.
                restGetAll(db.getUserIDForSession());
        Log.d("LoginActivity.initialDatabaseSync - Routine row count = ", String.valueOf(routines.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        db.loadAllWorkoutSessions(workoutSessions);
    }


    public static Account CreateSyncAccount(Context context){
        Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);

        AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);

        if (accountManager.addAccountExplicitly(newAccount, null, null)){
            Log.d("NEW ACCOUNT CREATED -- ", " - Returning new account");
            ContentResolver.setIsSyncable(newAccount, AUTHORITY, 1);
            ContentResolver.setSyncAutomatically(newAccount, AUTHORITY, true);

            return newAccount;
        }

        Log.d("No ACCOUNT CREATED -- ", " - Returning old account");
        return newAccount;
    }






}