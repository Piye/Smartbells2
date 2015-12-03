package teameleven.smartbells2.businesslayer.synchronization;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONObject;

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
 * Synchronizes with the Remote Database, allowing data transfer between multiple two devices
 * Created by Andrew Rabb on 2015-11-12.
 */
public class SyncAdaptor extends AbstractThreadedSyncAdapter {
    /**
     * Database link, to allow access
     */
    DatabaseAdapter database;
    /**
     * counts the number of times a sync has occurred. forces a sync with the remote database
     * every 15 regular syncs, which occur about once every 24 hours.
     */
    static int syncCount;

    /**
     * Main Constructor
     * @param context        - Context of the Application, used to open the Database
     * @param autoInitialize - AutoInitialize boolean
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
     * Secondary Constructor for backwards compatibility
     *
     * @param context            - Context of the Application, used to open the Database
     * @param autoInitialize     - AutoInitialize boolean
     * @param allowParallelSync - backwards Compatibility variable
     */
    @SuppressWarnings("unused")
    public SyncAdaptor(Context context, boolean autoInitialize, boolean allowParallelSync) {
        super(context, autoInitialize, allowParallelSync);
        try {
            database = new DatabaseAdapter(context).openLocalDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSyncCanceled() {
        super.onSyncCanceled();
        database.closeLocalDatabase();
    }

    /**
     * Method that performs the actual synchronization. Pulls update table from database,
     * and based on the values performs required operations upon the remote database.
     * @param account - dummy account used for syncAdapter
     * @param extras - extras pushed. used to determine whether a database recreation is required
     * @param authority - Authority for the SyncAdapter
     * @param provider - Provider for the SyncAdapter - dummy class
     * @param syncResult - Result of the Sync Callback
     */
    @Override
    public void onPerformSync(
            Account account,
            Bundle extras,
            String authority,
            ContentProviderClient provider,
            SyncResult syncResult) {
        Log.d("Syncing", "- SmartBells.OnPerformSync - checking for updates");
        if (extras.getBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED)) {
            Log.d("SmartBells Syncing", " - New User            - Recreating Database");
            initialDatabaseSync();
            syncCount = 0;
        } else {
            Log.d("SmartBells Syncing", " - Old user, checking for updates!");
            if (database.hasUpdates()) {
                Log.d("Syncing", " - SmartBells has Updates");

                String tableID;//id of the table to be changed

                String httpType; //type of call (GET, UPDATE, DELETE)

                String object = null;//Object, if required

                String modifier = ""; //id of object to be changed

                //update table records
                ArrayList<int[]> updates = database.readUpdateRecord();
                for (int[] UpdateRecord : updates) {//iterates through Update Records
                    tableID = setRestID(UpdateRecord[1]);//sets the table id
                    httpType = setHTTPType(UpdateRecord[2]);//sets the http call type


                    //if insert (POST) callType, nullify modifier
                    if (UpdateRecord[2] == 0) {
                        modifier = "";
                        object = getChangedObject(UpdateRecord[0], UpdateRecord[1]);
                        database.deleteObject(UpdateRecord[0], UpdateRecord[1]);
                        //delete old record -ensures no duplicates
                        }


                    //if call type update, obtain new object, and old object id (modifier)
                    else if (UpdateRecord[2] == 1) {
                        //sets the id of the object to be altered
                        modifier = "/" + String.valueOf(UpdateRecord[0]);
                        object = getChangedObject(UpdateRecord[0], UpdateRecord[1]);
                        database.deleteObject(UpdateRecord[0], UpdateRecord[1]);
                        //delete old record -ensures no duplicates
                    }


                    //if call type is delete, object remains null
                    else if (UpdateRecord[2] == 2){
                        object = "";
                    }

                    //perform the RESTCall, based upon parameters from update table
                    AsyncTask result = new RESTCall()
                            .execute(tableID + modifier, httpType, object,
                                    database.getTokenAsString());
                    try {//if not delete, retrieve object from RestCall
                        if (UpdateRecord[2] != 2) {
                            JSONObject json = (JSONObject) result.get();
                            if (json != null) {
                                saveToDatabase(json, UpdateRecord[1]);
                            }
                        }

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    database.clearUpdateTable();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                if (syncCount == 15) {
                    //recreates database every 15 synchronization cycles
                    Log.d("Syncing", " - SmartBells recreating Database");
                    initialDatabaseSync();
                    syncCount = 0;
                }
                syncCount++;
            }
        }
    }

    /**
     * Saves an object into the database, after retrieving it from the database.
     * @param json - JsonObject result from RESTCall.
     * @param table - Table to add the object to
     */
    private void saveToDatabase(JSONObject json, int table) {
        switch (table) {
            case (0)://exercises
                database.insertExercise(new Exercise(json), false);
                break;
            case (1)://set groups
                database.insertSetGroup(new SetGroup(json), false);
                break;
            case (2)://routine
                database.insertRoutine(new Routine(json), false);
                break;
            case (3)://workout session
                database.insertWorkoutSession(new WorkoutSession(json), false);
                break;
            case (4)://workout set group
                database.insertWorkoutSetGroup(new WorkoutSetGroup(json), false);
                break;
        }
    }
    /**
     * accepts the id and table, to return the database object in question
     *
     * @param id - id of the object to be retrieved
     * @param table - table of the object to be retrieved
     * @return object in question, String format
     */
    private String getChangedObject(int id, int table) {
        try {
            switch (table) {
                case (0)://exercises
                    //Log.d("attempting to retrieve exercise - ", String.valueOf(id) +" -  "+table);
                    return database.getExercise(id).toString();
                case (1)://set groups
                    return database.getSetGroup(id).toString();
                case (2)://routines
                    return database.getExercise(id).toString();
                case (3)://workout session
                    return database.getWorkoutSession(id).toString();
                case (4)://workout set group
                    return database.getWorkoutSetGroup(id).toString();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     *  returns the type of HTTP call to use. Matches DatabaseAdapter.InsertUpdateRecords()
     * @param type -
     * @return -
     */
    private String setHTTPType(int type) {
        switch (type) {
            case (0):
                return "POST";
            case (1):
                return "PUT";
            case (2):
                return "DELETE";
        }
        return null;
    }

    /**
     * sets the table to be called in REST, Matches DatabaseAdapter.InsertUpdateRecord()
     * @param table - int id of the table
     * @return the table to be called in REST
     */
    private String setRestID(int table) {

        switch (table) {
            case (0)://exercises
                return "exercises";
            case (1)://set groups
                return "set_groups";
            case (2)://routines
                return "routines";
            case (3)://workout session
                return "workout_sessions";
            case (4)://workout set group
                return "workout_set_groups";
        }
        return null;
    }

    /**
     * loads the database from the remote server. Loads the three main tables,
     * which loads the secondary tables as well.
     * Exercise
     * Routine + Set Group
     * WorkoutSessions + Workout Set Group
     */
    private void initialDatabaseSync() {
        database.updateDB();
        long x = System.currentTimeMillis();
        long y;


        ArrayList<Exercise> exercise = Exercise.restGetAll();
        assert exercise != null;
        Log.d("LoginActivity.initialDatabaseSync - Exercise row count = ",
                String.valueOf(exercise.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        database.loadAllExercises(exercise);


        ArrayList<Routine> routines = Routine.restGetAll(database.getUserIDForSession());
        assert routines != null;
        Log.d("LoginActivity.initialDatabaseSync - Routine row count = ",
                String.valueOf(routines.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        database.loadAllRoutines(routines);


        ArrayList<WorkoutSession> workoutSessions = WorkoutSession.
                restGetAll(database.getUserIDForSession());
        assert workoutSessions != null;
        Log.d("LoginActivity.initialDatabaseSync - WorkoutSession row count = ",
                String.valueOf(workoutSessions.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        database.loadAllWorkoutSessions(workoutSessions);
    }
}