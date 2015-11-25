package teameleven.smartbells2.businesslayer.synchronization;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import teameleven.smartbells2.businesslayer.RESTCall;
import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Exercise;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;
import teameleven.smartbells2.businesslayer.tableclasses.SetGroup;
import teameleven.smartbells2.businesslayer.tableclasses.WorkoutSession;
import teameleven.smartbells2.businesslayer.tableclasses.WorkoutSetGroup;

/**
 * This class treats to sync database of Smartbells with AbstractThreadedSyncAdapter
 * Created by Andrew Rabb on 2015-11-12.
 */
public class SyncAdaptor extends AbstractThreadedSyncAdapter {

    DatabaseAdapter database;
    static int syncCount;
    /**
     * Open the database
     * @param context : Context
     * @param autoInitialize : Boolean of initialization where Auto or not
     */
    public SyncAdaptor(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        try {
            database = new DatabaseAdapter(context).openLocalDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Synck with context,autoInitialize and allowParalellSyncs
     * @param context : Context
     * @param autoInitialize : Boolean of automaical initialization
     * @param allowParalellSyncs : Boolean of Allow the paralellSyncs
     */
    public SyncAdaptor(Context context, boolean autoInitialize, boolean allowParalellSyncs){
        super(context, autoInitialize, allowParalellSyncs);
        try{
            database = new DatabaseAdapter(context).openLocalDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Perform the sysnc
     * @param account : Account
     * @param extras : Bundle of extras
     * @param authority : Authority
     * @param provider : ContentProviderClient
     * @param syncResult : SyncResult
     */
    @Override
    public void onPerformSync(
                Account account,
                Bundle extras,
                String authority,
                ContentProviderClient provider,
                SyncResult syncResult) {
        Log.d("SmartBells.OnPerformSync -", " checking for updates");
        if (database.hasUpdates()){
            Log.d("SmartBells has Updates", "");
            //table name
            String tableID;
            //type of call (GET, UPDATE, DELETE)
            String httpType;
            //Object, if required
            String object = null;
            String modifier = "";
            //id of object to be changed
            //update table records
            ArrayList<int[]> updates = database.readUpdateRecord();

            for (int[] x : updates){
                modifier = "/" + String.valueOf(x[0]);
                tableID = setRestID(x[1]);
                httpType = setHTTPType(x[2]);
                if (x[2] == 0){
                    modifier = "";
                }else if(x[2] != 2){
                    object = getChangedObject(x[0], x[1]);
                }
                AsyncTask result = new RESTCall()
                        .execute(tableID + modifier, httpType, object, database.getTokenAsString());
                try {
                    JSONObject json = (JSONObject) result.get();
                    saveToDatabase(json, x[1]);

                    writeRecordLog(json.toString(4));


                } catch (InterruptedException | ExecutionException | JSONException e) {
                    e.printStackTrace();
                }
            }
            syncCount++;
            if (syncCount == 15){
                Log.d("SmartBells recreating Database", "");
                initialDatabaseSync();
                syncCount = 0;
            }
        }
    }

    /**
     * Write logs of records
     * @param record : A record for writing a log
     */
    private void writeRecordLog(String record) {
        try{
            OutputStreamWriter outputStreamWriter =
                    new OutputStreamWriter(getContext().openFileOutput
                            ("SyncTracker.txt", getContext().MODE_PRIVATE));
            outputStreamWriter.write(record + "\n\n\n");
            outputStreamWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Boolean whether it is exernal strorage writable status of environment
     * @return boolean true or false
     */
    private boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }
    /**
     * Save a record into a table as the parameter
     * @param json : JSONObject
     * @param table : Table Number
     */
    private void saveToDatabase(JSONObject json, int table) {
        switch (table){
            case(0)://exercises
                Exercise exercise = new Exercise(json);
                database.insertExercise(exercise);
            break;
            case(1)://set groups
                SetGroup setGroup = new SetGroup(json);
                database.insertSetGroup(setGroup);
            break;
            case(2)://routine
                Routine routine = new Routine(json);
                database.insertRoutine(routine);
            break;
            case(3)://workout session
                WorkoutSession session = new WorkoutSession(json);
                database.insertWorkoutSession(session);
            break;
            case(4)://workout set group
                WorkoutSetGroup workoutSetGroup = new WorkoutSetGroup(json);
                database.insertWorkoutSetGroup(workoutSetGroup);
            break;
        }
    }
    /**
     * Select table records by table id and table number
     * @param id : table's id - Primary key
     * @param table : Table name
     * @return Records of the table
     */
    private String getChangedObject(int id, int table) {
        try {
            switch (table) {
                case (0)://exercises
                    return database.getExercise(id);
                case (1)://set groups
                    return database.getSet_Group(id);
                case (2)://routines
                    return database.getRoutine(id);
                case (3)://workout session
                    return database.getWorkoutSession(id);
                case (4)://workout set group
                    return database.getWorkoutSetGroup(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    /**
     * Sett the HTTP type
     * @param type : Http type number
     * @return : Http type name
     */
    private String setHTTPType(int type) {
        switch (type){
            case(0):
                return "POST";
            case(1):
                return "PUT";
            case(2):
                return "DELETE";
        }
        return null;
    }
    /**
     * Set the Rest Id
     * @param table : table number
     * @return table name
     */
    private String setRestID(int table) {

        switch (table){
            case(0)://exercises
                return "exercises";
            case(1)://set groups
                return "set_groups";
            case(2)://routines
                return "routines";
            case(3)://workout session
                return "workout_sessions";
            case(4)://workout set group
                return "workout_set_groups";
        }
        return null;
    }
    /**
     * Initialization of database Sysn
     */
    private void initialDatabaseSync() {
        database.updateDB();
        long x = System.currentTimeMillis();
        long y;


        ArrayList<Exercise> exercise = Exercise.restGetAll();
        Log.d("LoginActivity.initialDatabaseSync - Exercise row count = ", String.valueOf(exercise.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        database.loadAllExercises(exercise);


        ArrayList<Routine> routines = Routine.restGetAll(database.getUserIDForSession());
        Log.d("LoginActivity.initialDatabaseSync - Routine row count = ", String.valueOf(routines.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        database.loadAllRoutines(routines);


        ArrayList<WorkoutSession> workoutSessions = WorkoutSession.
                restGetAll(database.getUserIDForSession());
        Log.d("LoginActivity.initialDatabaseSync - WorkoutSession row count = ", String.valueOf(workoutSessions.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        database.loadAllWorkoutSessions(workoutSessions);
    }

}