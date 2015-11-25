package teameleven.smartbells2.businesslayer.synchronization;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
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
 * Created by Andrew Rabb on 2015-11-12.
 */
public class SyncAdaptor extends AbstractThreadedSyncAdapter {

    DatabaseAdapter database;
    static int syncCount;
    /**
     *
     * @param context
     * @param autoInitialize
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
     *
     * @param context
     * @param autoInitialize
     * @param allowParalellSyncs
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
     *
     * @param account
     * @param extras
     * @param authority
     * @param provider
     * @param syncResult
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
            Log.d("Syncing", " - New User            - Recreating Database");
            initialDatabaseSync();
        }else{
            Log.d("Syncing", " - Old user, checking for updates! testing");
            if (database.hasUpdates()) {
                Log.d("Syncing", " - SmartBells has Updates");
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

                for (int[] x : updates) {
                    modifier = "/" + String.valueOf(x[0]);
                    tableID = setRestID(x[1]);
                    httpType = setHTTPType(x[2]);
                    if (x[2] == 0) {
                        modifier = "";
                    } else if (x[2] != 2) {
                        object = getChangedObject(x[0], x[1]);
                    }
                    AsyncTask result = new RESTCall()
                            .execute(tableID + modifier, httpType, object, database.getTokenAsString());
                    try {
                        JSONObject json = (JSONObject) result.get();
                        saveToDatabase(json, x[1]);

                    } catch (InterruptedException | ExecutionException e) {
                        e.printStackTrace();
                    }
                }
                if (syncCount == 15) {
                    Log.d("Syncing", " - SmartBells recreating Database");
                    initialDatabaseSync();
                    syncCount = 0;
                }
                syncCount++;
            }
        }
    }

    /**
     *
     * @param json
     * @param table
     */
    private void saveToDatabase(JSONObject json, int table) {
        switch (table){
            case(0)://exercises
                database.insertExercise(new Exercise(json));
            break;
            case(1)://set groups
                database.insertSetGroup(new SetGroup(json));
            break;
            case(2)://routine
                database.insertRoutine(new Routine(json));
            break;
            case(3)://workout session
                database.insertWorkoutSession(new WorkoutSession(json));
            break;
            case(4)://workout set group
                database.insertWorkoutSetGroup(new WorkoutSetGroup(json));
            break;
        }
    }
    /**
     *
     * @param id
     * @param table
     * @return
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
     *
     * @param type
     * @return
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
     *
     * @param table
     * @return
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
     *
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