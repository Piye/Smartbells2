package teameleven.smartbells2;
//TODO implement progress dialogs
//TODO implement new validation in signup for using existing user details.

import android.app.Activity;
import android.app.AlertDialog;
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

import teameleven.smartbells2.businesslayer.SessionManager;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;


/**
 * Created by Brian McMahon on 14/10/2015.
 */
public class LoginActivity extends Activity {

    private EditText mUserName;
    private EditText mPassword;
    private Button mLogInButton;
    private TextView mSignUp;
    String TAG = "DEBUGGING!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!";

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

            //Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();

            //move to main activity
            //Intent intent = new Intent(this, SmartBellsMainActivity.class);

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
                    //recreateDatabase(db);


                    db.insertToken((String) new JSONObject(authorized).get("authentication_token"));
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                db.closeLocalDatabase();
                valid = true;
            }

        }

        return valid;
    }

    private void recreateDatabase(DatabaseAdapter db) {
        //db.updateDB();
        //ArrayList<Exercise> exercise = Exercise.getAllExercise(Exercise.restGetAll());
        //db.loadAllExercises(exercise);
        Routine.restGetAll();
        //SetGroup.restGetAll();
        //WorkoutSetGroup.restGetall();
        //WorkoutSession.restGetAll();


    }

}
