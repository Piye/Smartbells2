package teameleven.smartbells2.businesslayer.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.businesslayer.tableclasses.Exercise;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;
import teameleven.smartbells2.businesslayer.tableclasses.SetGroup;
import teameleven.smartbells2.businesslayer.tableclasses.WorkoutSession;
import teameleven.smartbells2.businesslayer.tableclasses.WorkoutSetGroup;

/**
 * This class is the Database Adapter that connects to JSON and SQLite3
 * Created by Jarret on 2015-10-24.
 * Updated by Andrew Rabb
 */
public class DatabaseAdapter {
    /**
     * TAG - Variable for debugging
     */
    public static final String TAG = "databaseAdapter";
    /**
     * Variable for getting an access token from API of SingularityXL SmartBells JSON
     */
    public static final String ACCESS_TOKEN = "token";
    /**
     * Variable for an id of an user who is using the system.
     */
    public static final String SESSION_USER_ID = "user_id";
    //Routine Table Columns
    /**
     * Primary key - Routine id
     */
    public static final String PK_ROUTINE_ID = "id";
    /**
     * User id of Routine
     */
    public static final String ROUTINE_USER_ID = "user_id";
    /**
     * User name of Routine
     */
    public static final String ROUTINE_NAME = "name";
    /**
     * Boolean whether a routine is public or not.
     */
    public static final String ROUTINE_IS_PUBLIC = "is_public";
    /**
     * A date of creating a routine
     */
    public static final String ROUTINE_CREATED_AT = "created_at";
    /**
     * A date of updating a routine
     */
    public static final String ROUTINE_UPDATED_AT = "updated_at";
    /**
     * Primary Key - An id of WorkoutSession
     */
    public static final String PK_WORKOUTSESSION_ID = "id";
    /**
     * User id of the WorkoutSession Table
     */
    public static final String FK_USER_ID = "user_id";
    /**
     * Session Name of the WorkoutSession Table
     */
    public static final String SESSION_NAME = "name";
    /**
     * A date of creating a workout session
     */
    public static final String SESSION_CREATED_AT = "created_at";
    /**
     * A date of updating a workout session
     */
    public static final String SESSION_UPDATED_AT = "updated_at";
    //Workout Set Group Table Columns
    /**
     * Primary Key - Id of the WorkoutSetGroup
     */
    public static final String PK_WORKOUTSETGROUP_ID = "id";
    /**
     * Id of Exercise
     */
    public static final String FK_WSG_EXERCISE_ID = "exercise_id";
    /**
     * Id of WorkoutSession
     */
    public static final String FK_WORKOUTSESSION_ID = "workout_session_id";
    //Set Group Table Columns
    /**
     * Primary Key - Id of a SetGroup
     */
    public static final String PK_SETGROUP_ID = "id";
    /**
     * Id of the exercise table
     */
    public static final String FK_SG_EXERCISE_ID = "exercise_id";
    public static final String SETGROUP_REPS = "reps_per_set";
    /**
     * A number of sets of a setGroup
     */
    public static final String SETGROUP_SETS = "number_of_sets";
    /**
     * A date of creating a setGroup
     */
    public static final String SETGROUP_CREATED_AT = "created_at";
    /**
     * A date of updating a setGroup
     */
    public static final String SETGROUP_UPDATED_AT = "updated_at";
    /**
     * Id of a routine in the SetGroup table
     */
    public static final String FK_SG_ROUTINE_ID = "routine_id";
    //Exercise Table Columns
    /**
     * Primary key - Id of exercise
     */
    public static final String PK_EXERCISE_ID = "id";
    /**
     * A name of exercise
     */
    public static final String EXERCISE_NAME = "name";
    /**
     * A number of increase per a session
     */
    public static final String INCREASE_PER_SESSION = "increase_per_session";
    /**
     * A date of creating a exercise record
     */
    public static final String EXERCISE_CREATED_AT = "created_at";
    /**
     * A date of updating Exercise
     */
    public static final String EXERCISE_UPDATED_AT = "updated_at";
    /**
     * Boolean whether an exercise is public or not
     */
    public static final String EXERCISE_IS_PUBLIC = "is_public";
    /**
     * User id of Exercise
     */
    public static final String EXERCISE_USER_ID = "user_id";
    /**
     * It has name of "Session".
     */
    protected static final String SESSION_TABLE = "Session";
    /**
     * It has name of "Routine".
     */
    protected static final String ROUTINE_TABLE = "Routine";
    /**
     * It has name of "WorkoutSession".
     */
    protected static final String WORKOUTSESSION_TABLE = "WorkoutSession";
    //Tables
    /**
     * It has name of "WorkoutSetGroup".
     */
    protected static final String WORKOUTSETGROUP_TABLE = "WorkoutSetGroup";
    /**
     * It has name of "SetGroup".
     */
    protected static final String SETGROUP_TABLE = "Setgroup";
    /**
     * It has name of "Exercise".
     */
    protected static final String EXERCISE_TABLE = "Exercise";
    /**
     * It has name of "UpdateTable".
     */
    protected static final String UPDATE_TABLE = "UpdateTable";
    /**
     * User id who updated a exercise record
     */
    private static final String UPDATE_ID = "update_id";
    /**
     * Table name for updating a table
     */
    private static final String UPDATE_TABLE_IDENTIFIER = "Table_name";
    /**
     * Type of updating
     */
    private static final String UPDATE_TYPE = "Update_Type";
    /**
     * A name of database
     */
    private static final String DATABASE_NAME = "smartbellsdata";
    /**
     * The version of database
     */
    private static final int DATABASE_VERSION = 14;
    /**
     * Final String to Create Table - SESSION_TABLE
     */
    private static final String CREATE_SESSION_TABLE =
            "CREATE TABLE if not exists " + SESSION_TABLE + " (" +
                    ACCESS_TOKEN + "," +
                    SESSION_USER_ID + " integer);";
    /**
     * Final String to Create Table - ROUTINE_TABLE
     */
    private static final String CREATE_ROUTINE_TABLE =
            "CREATE TABLE if not exists " + ROUTINE_TABLE + " (" +
                    PK_ROUTINE_ID + " integer PRIMARY KEY," +
                    ROUTINE_USER_ID + "," +
                    ROUTINE_NAME + "," +
                    ROUTINE_IS_PUBLIC + "," +
                    ROUTINE_CREATED_AT + "," +
                    ROUTINE_UPDATED_AT + "," +
                    " UNIQUE (" + PK_ROUTINE_ID + "));";
    /**
     * Final String to Create Table - WORKOUTSESSION_TABLE
     */
    private static final String CREATE_WORKOUTSESSION_TABLE =
            "CREATE TABLE if not exists " + WORKOUTSESSION_TABLE + " (" +
                    PK_WORKOUTSESSION_ID + " integer PRIMARY KEY," +
                    FK_USER_ID + "," +
                    SESSION_NAME + "," +
                    SESSION_CREATED_AT + "," +
                    SESSION_UPDATED_AT + "," +
                    " UNIQUE (" + PK_WORKOUTSESSION_ID + "));";
    /**
     * Final String to Create Table - SETGROUP_TABLE
     */
    private static final String CREATE_SETGROUP_TABLE =
            "CREATE TABLE if not exists " + SETGROUP_TABLE + " (" +
                    PK_SETGROUP_ID + " integer PRIMARY KEY," +
                    FK_SG_ROUTINE_ID + "," +
                    FK_SG_EXERCISE_ID + "," +
                    SETGROUP_SETS + "," +
                    SETGROUP_REPS + "," +
                    SETGROUP_CREATED_AT + "," +
                    SETGROUP_UPDATED_AT + "," +
                    " FOREIGN KEY (" + FK_SG_EXERCISE_ID + ") REFERENCES "
                    + EXERCISE_TABLE + "(" + PK_EXERCISE_ID + ")" +
                    " FOREIGN KEY (" + FK_SG_ROUTINE_ID + ") REFERENCES "
                    + ROUTINE_TABLE + "(" + PK_ROUTINE_ID + ")" +
                    " UNIQUE (" + PK_SETGROUP_ID + "));";
    /**
     * Final String to Create Table - WORKOUTSETGROUP_TABLE
     */
    private static final String CREATE_WORKOUTSETGROUP_TABLE =
            "CREATE TABLE if not exists " + WORKOUTSETGROUP_TABLE + " (" +
                    PK_WORKOUTSETGROUP_ID + " integer PRIMARY KEY," +
                    FK_WSG_EXERCISE_ID + "," +
                    FK_WORKOUTSESSION_ID + "," +
                    " FOREIGN KEY (" + FK_WSG_EXERCISE_ID + ") REFERENCES "
                    + EXERCISE_TABLE + "(" + PK_EXERCISE_ID + ")" +
                    " FOREIGN KEY (" + FK_WORKOUTSESSION_ID + ") REFERENCES "
                    + WORKOUTSESSION_TABLE + "(" + PK_WORKOUTSESSION_ID + ")" +
                    "  UNIQUE (" + PK_WORKOUTSETGROUP_ID + "));";
    /**
     * Final String to Create Table - EXERCISE_TABLE
     */
    private static final String CREATE_EXERCISE_TABLE =
            "CREATE TABLE if not exists " + EXERCISE_TABLE + " (" +
                    PK_EXERCISE_ID + " integer PRIMARY KEY," +
                    EXERCISE_NAME + "," +
                    INCREASE_PER_SESSION + "," +
                    EXERCISE_CREATED_AT + "," +
                    EXERCISE_UPDATED_AT + "," +
                    EXERCISE_IS_PUBLIC + "," +
                    EXERCISE_USER_ID + "," +
                    " UNIQUE (" + PK_EXERCISE_ID + "));";
    /**
     * Final String to Create Table - UPDATE_TABLE
     */
    private static final String CREATE_UPDATE_TABLE =
            "Create table if not exists " + UPDATE_TABLE + " (" +
                    UPDATE_ID + " integer," +
                    UPDATE_TABLE_IDENTIFIER + " integer," +
                    UPDATE_TYPE + " integer );";
    /**
     * Context for Database adapter
     */
    private final Context context;
    /**
     * Database Helper for using SQLiteOpenHelper
     */
    private DatabaseHelper databaseHelper;
    /**
     * SQLiteDatabase of Android.
     */
    private SQLiteDatabase database;

    /**
     * Constructor of DatabaseAdapter(this) with Context parameter
     *
     * @param context It is used for connecting SQLite3
     */
    public DatabaseAdapter(Context context) {
        this.context = context;
    }
    //********************************************************************************************//

    //Open database

    /**
     * Open Database - Writable
     *
     * @return DatabaseAdapter
     * @throws SQLException Throws exceptions of SQLite
     */
    public DatabaseAdapter openLocalDatabase() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        //insertTESTRoutines();
        return this;
    }

    /**
     * Close the Database
     */
    public void closeLocalDatabase() {
        if (databaseHelper != null) {
            databaseHelper.close();
        }
    }

    /*********************************Update Table*************************************************/
    /**
     * Creates a record in the update table. The update table is used to track all transactions that
     * occur within the database. the parameters are used to determine the different column values.
     *
     * @param id_num          - id of the column that was affected by the change in question.
     *                        As an example, if exercise_id = 1, then the id would be 1, and table defined by
     *                        tableIdentifier.
     * @param tableIdentifier - identifier of the table that was affected by the change.
     *                        Accepts a string, then changes to an integer for insertion
     *                        Identifiers are as follows
     *                        0 - Exercises
     *                        1 - SetGroups
     *                        2 - Routines
     *                        3 - WorkoutSession
     *                        4 - WorkoutSetGroup
     * @param transactionType - type of transaction that was performed, following CRUD (no R required)
     *                        0 - C - Insert statement
     *                        1 - U - Update Statement
     *                        2 - D - Delete Statement
     * @return row number of the insert, 0 if a delete, or no rows created
     * @throws SQLException SQL Exception, two rows created with the same Primary Key, Same table ID
     */
    private long insertUpdateRecord(Long id_num, String tableIdentifier, int transactionType)
            throws SQLException {
        /**
         * Get A number of table as the name of tables
         * 0= Exercise, 1 = SetGroup, 2 = Routine, 3= WorkoutSession, 4= WorkoutSetGroup
         */
        int tableNum = -1;
        switch (tableIdentifier) {
            case (EXERCISE_TABLE):
                tableNum = 0;
                break;
            case (SETGROUP_TABLE):
                tableNum = 1;
                break;
            case (ROUTINE_TABLE):
                tableNum = 2;
                break;
            case (WORKOUTSESSION_TABLE):
                tableNum = 3;
                break;
            case (WORKOUTSETGROUP_TABLE):
                tableNum = 4;
                break;
        }
        /**
         * Get an error of a previous query statement( Id number )
         */
        if (id_num == -1) throw new SQLException("previous statement failed");
        /**
         * Get an error of a previous query statement( Table number )
         */
        if (tableNum == -1) throw new SQLException("Improper table selection");
        /**
         * Check the transactionType : 0 = Select, 1 = Update, 2 = Delete
         */
        if (transactionType < 0 || transactionType > 2)
            throw new SQLException("Improper transaction type, should be between 0 and 2");
        /**
         * Integer ArrayList for read and update records.
         */
        ArrayList<int[]> records = readUpdateRecord();

        if (records.size() != 0) {//testing for multiple entries on same record
            for (int index = 0; index < records.size(); index++) {//run through table
                if (records.get(index)[0] == id_num && records.get(index)[1] == tableNum) {
                    // id num and table num equal
                    if (transactionType == 1) {                  //update statement
                        if (records.get(index)[2] == 0) {
                            /*
                            previous statement doesn't matter, because whether it was a create or an
                            update, either way the record can remain the same. this is assuming
                            the record already exists in this table. in which case, create and
                            update are equivalent. this is not the case when creating a change to a
                            record already in the remote database, only the local.
                             */
                            return 0;
                        }
                    } else if (transactionType == 2) {             //delete statement
                        if (records.get(index)[2] == 0) {        //previous statement was create
                            /*
                            In this case, if the previous statement was a create, then the record
                            is not in the database server. because of this, we must remove it from
                            the local database, and never add it to the remote server. this is done
                            by this method, deleting the record in question
                             */
                            return database.delete(UPDATE_TABLE, UPDATE_ID + " = ?", new String[]{String.valueOf(id_num)});
                        }
                    } else {
                        throw new SQLException("Create Statement on non-unique key");
                    }
                }
            }
        }
        ContentValues initialValues = new ContentValues();
        initialValues.put(UPDATE_ID, id_num);
        initialValues.put(UPDATE_TABLE_IDENTIFIER, tableNum);
        initialValues.put(UPDATE_TYPE, transactionType);
        //Log.d("Update table has - ", String.valueOf(id_num) + tableNum + transactionType);
        return database.insert(UPDATE_TABLE, null, initialValues);
    }

    /**
     * Delete the update table for clearing
     *
     * @throws SQLException Throws exceptions of SQLite
     */
    public void clearUpdateTable() throws SQLException {
        database.delete(UPDATE_TABLE, null, null);
        if (this.readUpdateRecord().size() != 0) throw new SQLException("update table not cleared");
    }

    /**
     * reads the update table, and returns an integer representation of the results.
     * This seemed the most lightweight way to accomplish this.
     *
     * @return - integer array
     * [0] = id# of the row changed
     * [1] = table that was changed (see insertUpdateRecord)
     * [2] = type of change (see insertUpdateRecord)
     */
    public ArrayList<int[]> readUpdateRecord() {
        Cursor myCursor = database.query(UPDATE_TABLE, null,
                null, null, null, null, null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        ArrayList<int[]> result = new ArrayList<>();
        assert myCursor != null;
        for (int index = 0; index < myCursor.getCount(); index++) {
            result.add(new int[myCursor.getColumnCount()]);
            for (int i = 0; i < myCursor.getColumnCount(); i++) {
                result.get(index)[i] = myCursor.getInt(i);
            }
        }
        myCursor.close();
        return result;
    }

    /**
     * Update the Update_Table
     *
     * @return boolean whether there are records what was updated or not
     */
    public boolean hasUpdates() {
        Cursor myCursor = database.query(UPDATE_TABLE, null, null, null, null, null, null);
        Log.d("SmartBells Database Adapter ", "checking for updates");
        if (myCursor.getCount() > 0) {
            return true;
        }
        myCursor.close();
        return false;
    }


    /*********************************SESSION TABLE***********************************************/
    //Insert Token

    /**
     * Insert an access token into the SESSION_TABLE
     *
     * @param accessToken - Access Token to create objects on the remote server
     * @param user_Id     - ID of the user, specified by remote server
     */
    public void insertToken(String accessToken, int user_Id) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(ACCESS_TOKEN, accessToken);
        initialValues.put(SESSION_USER_ID, user_Id);
        //insert
        database.delete(SESSION_TABLE, null, null);

        if (database.insert(SESSION_TABLE, null, initialValues) == -1) {
            try {
                throw new SQLException("Session table not inserted");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get an access token from SESSION_TABLE
     *
     * @return cursor.getString(token)
     */
    public String getTokenAsString() {
        String[] columns = new String[]{ACCESS_TOKEN};
        Cursor cursor = database.query(SESSION_TABLE, columns, null, null, null, null, null);
        int token = cursor.getColumnIndex(ACCESS_TOKEN);
        cursor.moveToLast();
        String result = cursor.getString(token);
        cursor.close();
        return result;
    }

    /**
     * Get an user id for now session
     *
     * @return cursor.getInt(token)
     */
    public int getUserIDForSession() {

        String[] columns = new String[]{SESSION_USER_ID};
        Cursor cursor = database.query(SESSION_TABLE, columns, null, null, null, null, null);
        int token = cursor.getColumnIndex(SESSION_USER_ID);
        cursor.moveToLast();

        int result = cursor.getInt(token);
        cursor.close();
        return result;
    }

    /*********************************ROUTINE TABLE***********************************************/
    /**
     * Load All Routines for syncing(SyncAdapter.java)
     *
     * @param routines - routine array to load into database
     */
    public void loadAllRoutines(ArrayList<Routine> routines) {
        database.beginTransaction();
        for (Routine i : routines) {
            //loads all values
            ContentValues initialValues = routineAttributes(i);

            //database insert
            database.insertWithOnConflict
                    (ROUTINE_TABLE, null, initialValues,
                            SQLiteDatabase.CONFLICT_IGNORE);
            for (SetGroup y : i.getSetGroups()) {
                //loads all values
                ContentValues initialValuesSetGroup = setGroupAttributes(y);

                //database insert
                database.insertWithOnConflict
                        (SETGROUP_TABLE, null, initialValuesSetGroup,
                                SQLiteDatabase.CONFLICT_IGNORE);
            }

        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    /**
     * Insert a routine
     *
     * @param routine A routine(Routine.java)
     * @param sync    boolean whether it is sync or not
     * @return result of the insert, routine id of inserted record
     */
    public long insertRoutine(Routine routine, boolean sync) {
        long result = insertRoutine(routine.getRoutineId(),
                routine.getName(),
                routine.getIsPublic(),
                routine.getCreated_At(),
                routine.getUpdated_At(),
                sync);
        for (SetGroup setGroup : routine.getSetGroups()) {
            setGroup.setRoutineId((int) result);
            insertSetGroup(setGroup, sync);
        }
        return result;
    }

    /**
     * Put the routine attributes
     *
     * @param routine A routine(Routine.java)
     * @return ContentValues
     */
    private ContentValues routineAttributes(Routine routine) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PK_ROUTINE_ID, routine.getRoutineId());
        initialValues.put(ROUTINE_USER_ID, routine.getUser_id());
        initialValues.put(ROUTINE_NAME, routine.getName());
        initialValues.put(ROUTINE_IS_PUBLIC, routine.getIsPublic());
        initialValues.put(ROUTINE_CREATED_AT, routine.getCreated_At());
        initialValues.put(ROUTINE_UPDATED_AT, routine.getUpdated_At());
        return initialValues;
    }

    //Insert Routine

    /**
     * Insert a routine
     *
     * @param routineId       An id of a routine
     * @param routineName     A name of a routine
     * @param routineIsPublic Boolean whether is public or not
     * @param createDate      A date of creating a routine record
     * @param updateDate      A date of updating a routine record
     * @param sync            Boolean If It is used to sync or not.
     * @return long result = database.insert(ROUTINE_TABLE, null, initialValues)
     */
    public long insertRoutine(long routineId,
                              String routineName,
                              Boolean routineIsPublic,
                              String createDate,
                              String updateDate,
                              boolean sync) {

        //This class is used to store a set of values that the ContentResolver can process.
        ContentValues initialValues = new ContentValues();
        if (routineId > -1) initialValues.put(PK_ROUTINE_ID, routineId);
        initialValues.put(ROUTINE_NAME, routineName);
        initialValues.put(ROUTINE_IS_PUBLIC, routineIsPublic);
        initialValues.put(ROUTINE_CREATED_AT, createDate);
        initialValues.put(ROUTINE_UPDATED_AT, updateDate);

        //insert
        long result = database.insert(ROUTINE_TABLE, null, initialValues);
        if (sync) {
            try {
                insertUpdateRecord(result, ROUTINE_TABLE, 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Delete a routine from routine table
     *
     * @param routineId Id of the routine
     * @param sync      boolean whether it is public routine or not
     * @return long v = database.delete(ROUTINE_TABLE, PK_ROUTINE_ID + "=" + routineId, null)
     */
    public long deleteRoutine(long routineId, boolean sync) {

        //Delete row from routine table
        long v = database.delete(ROUTINE_TABLE, PK_ROUTINE_ID + "=" + routineId, null);

        if (sync) {
            try {
                insertUpdateRecord(routineId, ROUTINE_TABLE, 2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return v;
    }

    /**
     * Select All Public Routines
      * @return ArrayList of Routines
     */
    public ArrayList<Routine> selectAllRoutines() {
        Cursor myCursor = database.query(ROUTINE_TABLE, new String[]{ROUTINE_NAME, PK_ROUTINE_ID},
                null, null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        ArrayList<Routine> routines = new ArrayList<>();
        Routine routine;

        assert myCursor != null;
        for (myCursor.moveToFirst(); !myCursor.isAfterLast(); myCursor.moveToNext()) {
            routine = new Routine(myCursor.getString(0), myCursor.getInt(1));
            routines.add(routine);
        }
        myCursor.close();
        return routines;
    }

    //Reference: http://stackoverflow.com/questions/9466380/
    //           how-to-get-data-from-my-database-and-put-it-on-a-listview-that-is-clickable
    // Get all Routines and return a string

    /**
     * Get routine name
     * @return ArrayList of routine names
     */
    public ArrayList<String> getRoutinesAsStrings() {
        String[] columns = new String[]{ROUTINE_NAME};
        Cursor cursor = database.query(ROUTINE_TABLE, columns, null, null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<>();
        //int id = cursor.getColumnIndex(PK_ROUTINE_ID);
        int name = cursor.getColumnIndex(ROUTINE_NAME);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result.add(cursor.getString(name));
        }
        cursor.close();
        return result;
    }
    //Reference: http://stackoverflow.com/questions/9466380/
    //           how-to-get-data-from-my-database-and-put-it-on-a-listview-that-is-clickable
    // Get private Routines of specific user and return a string

    /**
     * Get Routine names ArrayList
     *
     * @param userId user id
     * @return ArrayList of Routine names
     */
    public ArrayList<String> getMyRoutinesAsStrings(int userId) {
        String[] columns = new String[]{ROUTINE_NAME};
        Cursor cursor = database.query(ROUTINE_TABLE, columns, ROUTINE_USER_ID + "=" + userId,
                null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<>();
        int name = cursor.getColumnIndex(ROUTINE_NAME);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result.add(cursor.getString(name));
        }
        cursor.close();
        return result;
    }
    /******************************WORKOUTSESSION TABLE********************************************/

    /**
     * Load All WorkoutSessions to SQLite
     *
     * @param workoutSessions -
     */
    public void loadAllWorkoutSessions(ArrayList<WorkoutSession> workoutSessions) {
        database.beginTransaction();
        for (WorkoutSession session : workoutSessions) {
            ContentValues initialValues = new ContentValues();
            initialValues.put(PK_WORKOUTSESSION_ID, session.getId());
            initialValues.put(FK_USER_ID, session.getUser_Id());
            initialValues.put(SESSION_NAME, session.getName());
            initialValues.put(SESSION_CREATED_AT, session.getCreated_At());
            initialValues.put(SESSION_UPDATED_AT, session.getUpdated_At());

            //database insert
            database.insertWithOnConflict
                    (WORKOUTSESSION_TABLE, null, initialValues,
                            SQLiteDatabase.CONFLICT_IGNORE);
            for (WorkoutSetGroup wSetGroup : session.getSetGroups()) {
                ContentValues initialValuesSetGroup = new ContentValues();
                //Log.d("DBA - workout set group load into DB", wSetGroup.toString());

                initialValuesSetGroup.put(PK_WORKOUTSETGROUP_ID, wSetGroup.getSetGroupID());
                initialValuesSetGroup.put(FK_WORKOUTSESSION_ID, wSetGroup.getWorkoutSessionId());
                initialValuesSetGroup.put(FK_WSG_EXERCISE_ID, wSetGroup.getExerciseId());

                //database insert
                database.insertWithOnConflict
                        (WORKOUTSETGROUP_TABLE, null, initialValuesSetGroup,
                                SQLiteDatabase.CONFLICT_IGNORE);
            }
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }
    /**
     * Get private Workout name arrayList with user id
     *
     * @param userId User Id
     * @return ArrayList of Session names
     */
    public ArrayList<String> getMyWorkoutsAsStrings(int userId) {
        String[] columns = new String[]{SESSION_NAME};
        Cursor cursor = database.query(WORKOUTSESSION_TABLE, columns, FK_USER_ID + "=" + userId,
                null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<>();
        /**
         * Index of the Session name
         */
        int nameIndex = cursor.getColumnIndex(SESSION_NAME);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result.add(cursor.getString(nameIndex));
        }
        cursor.close();
        return result;
    }

    /**
     * Get private workout ids ArrayList
     *
     * @param userId User id
     * @return ArrayList of the WorkoutSession Id
     */
    public ArrayList<String> getMyWorkoutIds(int userId) {
        String[] columns = new String[]{PK_WORKOUTSESSION_ID};
        Cursor cursor = database.query(WORKOUTSESSION_TABLE, columns, FK_USER_ID + "=" + userId,
                null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<>();
        /**
         * Index of the WorkoutSessionId
         */
        int workoutIdIndex = cursor.getColumnIndex(PK_WORKOUTSESSION_ID);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result.add(cursor.getString(workoutIdIndex));
        }
        cursor.close();
        return result;
    }
    /**********************************************************************************************/
    /**
     * Insert a WorkoutSession record and sync
     *
     * @param session A WorkoutSession Record
     * @param sync    boolean whether it is sync or not
     * @return long result of inserting workoutSession
     */
    public long insertWorkoutSession(WorkoutSession session, boolean sync) {
        long result;
        result = insertWorkoutSession(
                session.getId(),
                session.getUser_Id(),
                session.getName(),
                session.getCreated_At(),
                session.getCreated_At(),
                sync);
        for (WorkoutSetGroup setGroup : session.getSetGroups()) {
            setGroup.setWorkoutSessionId((int) result);
            insertSetGroup(setGroup.getSet_group(), sync);
        }
        return result;
    }

    //Insert Workout Session

    /**
     * Insert a workoutSession record
     *
     * @param sessionId   Session Id
     * @param userId      User id
     * @param sessionName Session Name
     * @param createdAt   A date of creating a record
     * @param updatedAt   A date of updating a record
     * @param sync        boolean it is sync or not
     * @return long result after inserting a record
     */
    public long insertWorkoutSession(int sessionId,
                                     int userId,
                                     String sessionName,
                                     String createdAt,
                                     String updatedAt,
                                     boolean sync) {

        ContentValues initialValues = new ContentValues();

        if (sessionId > -1) initialValues.put(PK_WORKOUTSESSION_ID, sessionId);
        initialValues.put(FK_USER_ID, userId);
        initialValues.put(SESSION_NAME, sessionName);
        initialValues.put(SESSION_CREATED_AT, createdAt);
        initialValues.put(SESSION_UPDATED_AT, updatedAt);

        //insert
        long result;
        result = database.insert(WORKOUTSESSION_TABLE, null, initialValues);
        if (sync) {
            try {
                insertUpdateRecord(result, WORKOUTSESSION_TABLE, 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //Delete Workout Session

    /**
     * Delete a workoutSession record
     *
     * @param workoutSessionId WorkoutSession Id
     * @param sync             boolean it is sync or not
     * @return id of the deleted row
     */
    public long deleteWorkoutSession(long workoutSessionId, boolean sync) {

        //Delete row
        long result = database.delete(WORKOUTSESSION_TABLE, PK_WORKOUTSESSION_ID
                + "=" + workoutSessionId, null);
        if (sync) {
            try {
                insertUpdateRecord(workoutSessionId, WORKOUTSESSION_TABLE, 2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //Select All workout Sessions

    /**
     * Select all workoutSessions
     * @return ArrayList of all workoutSessions
     */
    public ArrayList<WorkoutSession> selectAllWorkoutSessions() {
        Cursor myCursor = database.query(WORKOUTSESSION_TABLE, new String[]{SESSION_NAME, PK_WORKOUTSESSION_ID},
                null, null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        ArrayList<WorkoutSession> sessions = new ArrayList<>();
        WorkoutSession session;

        assert myCursor != null;
        for (myCursor.moveToFirst(); !myCursor.isAfterLast(); myCursor.moveToNext()) {
            session = new WorkoutSession(myCursor.getString(0), myCursor.getInt(1));
            sessions.add(session);
        }
myCursor.close();
        return sessions;
    }

    /**********************************WORKOUT SETGROUP TABLE **************************************/
    /**
     * Insert a WorkoutSetGroup record
     *
     * @param workoutSetGroupId WorkoutSetGroup Id
     * @param exerciseId        Exercise Id
     * @param workoutSessionId  WorkoutSession Id
     * @param sync              boolean whether it is sync or not
     * @return long result
     */
    public long insertWorkoutSetGroup(int workoutSetGroupId,
                                      int exerciseId,
                                      int workoutSessionId,
                                      boolean sync) {

        ContentValues initialValues = new ContentValues();
        if (workoutSetGroupId >= 0) initialValues.put(PK_WORKOUTSETGROUP_ID, workoutSetGroupId);
        initialValues.put(FK_SG_EXERCISE_ID, exerciseId);
        initialValues.put(FK_WORKOUTSESSION_ID, workoutSessionId);


        //insert
        long result = database.insert(WORKOUTSETGROUP_TABLE, null, initialValues);
        if (sync) {
            try {
                insertUpdateRecord(result, WORKOUTSETGROUP_TABLE, 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
    /**********************************************************************************************/

    //Delete Workout Set Group

    /**
     * Delete a WorkoutSetGroup record
     *
     * @param workoutSetGroupId WorkoutSetGroup Id
     * @param sync              boolean whether it is sync or not
     * @return long v : a result of deleting a workoutSetGroup record
     */
    public long deleteWorkoutSetGroup(long workoutSetGroupId, boolean sync) {

        //Delete row
        long v = database.delete(WORKOUTSETGROUP_TABLE, PK_WORKOUTSETGROUP_ID
                + "=" + workoutSetGroupId, null);
        if (sync) {
            try {
                insertUpdateRecord(workoutSetGroupId, WORKOUTSETGROUP_TABLE, 2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return v;
    }
    /**
     * Get an ArrayList of WorkoutSession Ids with WorkoutSession Id
     * @param workoutSessionId Workout Session Id
     * @return ArrayList of Strings of WorkoutSession IDs
     */
    public ArrayList<String> getMyWorkoutSetGroupIds(String workoutSessionId) {
        String[] columns = new String[]{PK_WORKOUTSETGROUP_ID};
        Cursor cursor = database.query(WORKOUTSETGROUP_TABLE, columns,
                FK_WORKOUTSESSION_ID + "=" + workoutSessionId, null, null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<>();
        /**
         * Index of the WorkoutSessionId
         */
        int setGroupId = cursor.getColumnIndex(PK_WORKOUTSETGROUP_ID);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result.add(cursor.getString(setGroupId));
        }
        cursor.close();
        return result;
    }
    /**********************************SETGROUP TABLE**********************************************/
    /**
     * Insert a SetGroup record
     *
     * @param set_group SetGroup(SetGroup.java)
     * @param sync      boolean whether it is sync or not
     * @return long result after inserting an record
     */
    public long insertSetGroup(SetGroup set_group, boolean sync) {
        return insertSetGroup(
                set_group.getId(),
                set_group.getExercise().getId(),
                set_group.getRoutineId(),
                set_group.getNumberOfSets(),
                set_group.getRepsPerSet(),
                set_group.getCreationDate(),
                set_group.getLastUpdated(),
                sync);
    }

    /**
     * Set Group Attributes for set values
     *
     * @param setGroup SetGroup
     * @return ContentValues of SetGroup
     */
    private ContentValues setGroupAttributes(SetGroup setGroup) {
        ContentValues initialValuesSetGroup = new ContentValues();
        initialValuesSetGroup.put(PK_SETGROUP_ID, setGroup.getId());
        initialValuesSetGroup.put(FK_SG_ROUTINE_ID, setGroup.getRoutineId());
        initialValuesSetGroup.put(FK_SG_EXERCISE_ID, setGroup.getExerciseId());
        initialValuesSetGroup.put(SETGROUP_SETS, setGroup.getNumberOfSets());
        initialValuesSetGroup.put(SETGROUP_REPS, setGroup.getRepsPerSet());
        initialValuesSetGroup.put(SETGROUP_CREATED_AT, setGroup.getCreationDate());
        initialValuesSetGroup.put(SETGROUP_UPDATED_AT, setGroup.getLastUpdated());
        return initialValuesSetGroup;
    }
    //Insert Set Group

    /**
     * Insert a setGroup record
     *
     * @param setGroupId SetGroup Id
     * @param exerciseId Exercise Id
     * @param routineId  Routine Id
     * @param sets       A number of sets of Exercise
     * @param reps       A number of reps of sets
     * @param createdAt  A date of creating a record
     * @param updatedAt  A date of updating a record
     * @param sync       boolean whether it is sync or not
     * @return long result of inserting a record
     */
    public long insertSetGroup(int setGroupId,
                               int exerciseId,
                               int routineId,
                               int sets,
                               int reps,
                               String createdAt,
                               String updatedAt,
                               boolean sync) {

        ContentValues initialValues = new ContentValues();
        if (setGroupId > 0) initialValues.put(PK_SETGROUP_ID, setGroupId);
        initialValues.put(FK_SG_EXERCISE_ID, exerciseId);
        if (routineId > 0) initialValues.put(FK_SG_ROUTINE_ID, routineId);
        initialValues.put(SETGROUP_SETS, sets);
        initialValues.put(SETGROUP_REPS, reps);
        initialValues.put(SETGROUP_CREATED_AT, createdAt);
        initialValues.put(SETGROUP_UPDATED_AT, updatedAt);

        //insert
        long result;
        result = database.insert(SETGROUP_TABLE, null, initialValues);
        if (sync) {
            try {
                insertUpdateRecord(result, SETGROUP_TABLE, 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //Delete Set Groups

    /**
     * Delete a setGroup record
     *
     * @param setGroupId SetGroup Id
     * @param sync       boolean it is sync or not
     * @return long v : result of deleting
     */
    public long deleteSetGroup(long setGroupId, boolean sync) {

        //Delete row
        long v = database.delete(SETGROUP_TABLE, PK_SETGROUP_ID
                + "=" + setGroupId, null);
        if (sync) {
            try {
                insertUpdateRecord(setGroupId, SETGROUP_TABLE, 2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return v;
    }

    //Select All Set Groups

    /**
     * Select all SetGroup records
     * @return ArrayList of SetGroups
     */
    public ArrayList<SetGroup> selectAllSetGroups() {
        Cursor myCursor = database.query(SETGROUP_TABLE, new String[]{PK_SETGROUP_ID,
                        FK_SG_EXERCISE_ID, FK_SG_ROUTINE_ID, SETGROUP_REPS, SETGROUP_SETS},
                null, null, null, null, null, null);
        ArrayList<SetGroup> setGroups = new ArrayList<>();
        SetGroup setGroup;

        for (myCursor.moveToFirst(); !myCursor.isAfterLast(); myCursor.moveToNext()) {
            setGroup = new SetGroup(myCursor.getInt(0), myCursor.getInt(1), myCursor.getInt(2), myCursor.getInt(3), myCursor.getInt(4));
            setGroups.add(setGroup);
        }
        myCursor.close();
        return setGroups;
    }
    /**
     * Get private SetGroups by WorkoutSetGroup Id
     * @param workoutSetGroupId - id of the WorkoutSetGroup
     * @return ArrayList of SetGroup Exercise Ids.
     */
    public ArrayList<String> getMySetGroupIdsByWSG(String workoutSetGroupId) {
        Cursor cursor = database.query(SETGROUP_TABLE, new String[]{PK_SETGROUP_ID + "=" + workoutSetGroupId,
                FK_SG_EXERCISE_ID, SETGROUP_SETS, SETGROUP_REPS, SETGROUP_CREATED_AT,
                SETGROUP_UPDATED_AT}, null, null, null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<>();
        int id = cursor.getColumnIndex(FK_SG_EXERCISE_ID);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result.add(cursor.getString(id));
        }
        cursor.close();
        return result;
    }

    /**
     * Efficiently Loads all Exercises from the remote Database
     *
     * @param exercise - exercise array to be loaded
     */
    public void loadAllExercises(ArrayList<Exercise> exercise) {
        database.beginTransaction();
        for (Exercise i : exercise) {
            ContentValues initialValues = exerciseAttributes(i);
            database.insertWithOnConflict
                    (EXERCISE_TABLE, null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    /**********************************************************************************************/

    /**************************************EXERCISE TABLE******************************************/
    /**
     * Insert an exercise record
     *
     * @param exercise An record of exercise
     * @param sync     boolean whether it is sync or not
     * @return returns the id of the created Exercise
     */
    public long insertExercise(Exercise exercise, boolean sync) {
        return insertExercise(
                exercise.getId(),
                exercise.getName(),
                String.valueOf(exercise.getIncrease_Per_Session()),
                exercise.getCreated_At(),
                exercise.getUpdated_At(),
                exercise.getIsPublic(),
                exercise.getUser_Id(), sync);
    }

    /**
     * Insert an exercise record with its values
     *
     * @param exerciseID         Id number of the Exercise (-1 to generate one)
     * @param exerciseName       Exercise Name
     * @param increasePerSession A number of the IncreasePerSession
     * @param createdAt          A date of creating a record, String
     * @param updatedAt          A date of updating a record, String
     * @param is_Public          Boolean whether it is public or not
     * @param user_ID            User id
     * @param sync               Boolean whether it is sync or not
     * @return long result after inserting a record
     */
    public long insertExercise(int exerciseID,
                               String exerciseName,
                               String increasePerSession,
                               String createdAt,
                               String updatedAt,
                               boolean is_Public,
                               int user_ID,
                               boolean sync) {
        //Log.d("DatabaseAdaptor.insertExercise(name) - ", exerciseName);
        //Log.d("DatabaseAdaptor.insertExercise(increase) - ", increasePerSession);
        ContentValues initialValues = new ContentValues();

        initialValues.put(EXERCISE_NAME, exerciseName);
        if (exerciseID >= 0) initialValues.put(PK_EXERCISE_ID, exerciseID);
        initialValues.put(INCREASE_PER_SESSION, increasePerSession);
        initialValues.put(EXERCISE_CREATED_AT, createdAt);
        initialValues.put(EXERCISE_UPDATED_AT, updatedAt);
        initialValues.put(EXERCISE_IS_PUBLIC, is_Public);
        initialValues.put(EXERCISE_USER_ID, user_ID);

        //insert
        long result;
        result = database.insert(EXERCISE_TABLE, null, initialValues);
        if (sync) {
            try {
                insertUpdateRecord(result, EXERCISE_TABLE, 0);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * Set the exercise Attributes of the Exercise table
     *
     * @param exercise Exercise
     * @return ContentValues of The Exercise values
     */
    private ContentValues exerciseAttributes(Exercise exercise) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(PK_EXERCISE_ID, exercise.getId());
        initialValues.put(EXERCISE_NAME, exercise.getName());
        initialValues.put(INCREASE_PER_SESSION, exercise.getIncrease_Per_Session());
        initialValues.put(EXERCISE_CREATED_AT, exercise.getCreated_At());
        initialValues.put(EXERCISE_UPDATED_AT, exercise.getUpdated_At());
        initialValues.put(EXERCISE_IS_PUBLIC, exercise.getIsPublic());
        initialValues.put(EXERCISE_USER_ID, exercise.getUser_Id());
        return initialValues;
    }
    //Delete Exercises

    /**
     * Delete a exercise with its id
     *
     * @param exerciseId Exercise id
     * @param sync       boolean whether it is sync or not
     * @return long result after deleting
     */
    public long deleteExercise(long exerciseId, boolean sync) {

        //Delete row
        long result = database.delete(EXERCISE_TABLE, PK_EXERCISE_ID
                + " = " + exerciseId, null);
        if (sync) {
            try {
                insertUpdateRecord(exerciseId, EXERCISE_TABLE, 2);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    //Select All Exercises

    /**
     * Select All exercises
     * @return ArrayList of all exercises
     */
    public ArrayList<Exercise> selectAllExercises() {
        String[] columns = new String[]{EXERCISE_NAME, PK_EXERCISE_ID};
        Cursor cursor = database.query(EXERCISE_TABLE, columns, null, null, null, null, null);
        ArrayList<Exercise> exercises = new ArrayList<>();
        Exercise exercise;

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            exercise = new Exercise(cursor.getString(0), cursor.getInt(1));
            exercises.add(exercise);
        }
        cursor.close();
        //Log.d("DatabaseAdaptor.getExercises - ", exercises.toString());
        return exercises;
    }

    /**
     * Get list of name of Exercises
     *
     * @return ArrayList of Exercise Names
     */
    public ArrayList<String> getExercisesAsStrings() {
        String[] columns = new String[]{EXERCISE_NAME};
        Cursor cursor = database.query(EXERCISE_TABLE, columns, null, null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<>();
        int exName = cursor.getColumnIndex(EXERCISE_NAME);

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result.add(cursor.getString(exName));
        }
        cursor.close();
        return result;
    }

    /**
     * Get An id of Exercises by An exercise name
     *
     * @param name Exercise name
     * @return Exercise Id
     */
    public int getExerciseIdByName(String name) {
        String[] columns = new String[]{EXERCISE_NAME, PK_EXERCISE_ID};
        Cursor cursor = database.query
                (EXERCISE_TABLE, columns,
                        EXERCISE_NAME + " like \"%" + name + "%\"",
                        null, null, null, null);

        cursor.moveToFirst();
        int exerciseId = cursor.getInt(
                cursor.getColumnIndex(PK_EXERCISE_ID));
        cursor.close();
        return exerciseId;
    }

    /**
     * Update Database(All tables) of Smart Bells(SQLite3)
     */
    public void updateDB() {
        Log.d("DatabaseAdaptor.UpdateDB - ", "Clearing data from Database");
        database.execSQL("DROP TABLE IF EXISTS " + ROUTINE_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + WORKOUTSESSION_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + SETGROUP_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + WORKOUTSETGROUP_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);

        DatabaseHelper helper = new DatabaseHelper(context);
        helper.onCreate(database);
    }

    /**
     * Get Object with the below parameter
     *
     * @param table       Table name
     * @param primary_key Primary Key of the table
     * @param id          Each table's id
     * @return JSONObject
     * @throws SQLException treats exception of SQL Query
     */
    private JSONObject getObject(String table, String primary_key, long id) throws SQLException {
        Cursor query = database.query(table, null, primary_key + " =? ", new String[]{String.valueOf(id)}, null, null, null);
        if (query.getCount() != 1)
            throw new SQLException("Improper Key, " + query.getCount() + " records returned");
        query.moveToFirst();
        JSONObject json = new JSONObject();
        for (int x = 0; x < query.getColumnCount(); x++) {
            try {
                //Log.d("result from getObject ", query.getColumnName(x) + "  "+ query.getString(x));
                if (query.getColumnName(x).equals(EXERCISE_IS_PUBLIC)) {
                    if (query.getInt(x) == 0) {
                        json.put(query.getColumnName(x), false);
                    } else {
                        json.put(query.getColumnName(x), true);
                    }
                } else json.put(query.getColumnName(x), query.getString(x));
            } catch (JSONException e) {
                Log.d("Throwing error on json creation", " might not work actually");
                e.printStackTrace();
            }
        }
        //Log.d("returning following Json Object from get", json.toString(4));
        query.close();

        return json;
    }

    /**
     * Get an exercise by its id
     *
     * @param id Exercise id
     * @return JSONObject = A record of exercise
     * @throws SQLException Exception of SQL
     */
    public JSONObject getExercise(long id) throws SQLException {
        //should return a json representation of the object in question
        return getObject(EXERCISE_TABLE, PK_EXERCISE_ID, id);
    }

    /**
     * Get an SetGroup by its id
     *
     * @param id SetGroup id
     * @return SetGroup = A record of SetGroup
     * @throws SQLException Exception of SQL
     */
    public JSONObject getSetGroup(long id) throws SQLException {
        return getObject(SETGROUP_TABLE, PK_SETGROUP_ID, id);
    }

    /**
     * Get an Routine record by its id
     *
     * @param id Routine id
     * @return Routine = A record of Routine
     * @throws SQLException Exception of SQL
     */
    public JSONObject getRoutine(long id) throws SQLException {
        return getObject(ROUTINE_TABLE, PK_ROUTINE_ID, id);
    }

    /**
     * Get an WorkoutSession record by its id
     *
     * @param id WorkoutSession id
     * @return WorkoutSession = A record of WorkoutSession
     * @throws SQLException Exception of SQL
     */
    public JSONObject getWorkoutSession(long id) throws SQLException {
        return getObject(WORKOUTSESSION_TABLE, PK_WORKOUTSESSION_ID, id);
    }

    /**
     * Get an WorkoutSetGroup record by its id
     *
     * @param id WorkoutSetGroup id
     * @return WorkoutSetGroup = A record of WorkoutSetGroup
     * @throws SQLException Exception of SQL
     */
    public JSONObject getWorkoutSetGroup(long id) throws SQLException {
        JSONObject workoutSetGroup;
        workoutSetGroup = getObject(WORKOUTSETGROUP_TABLE, PK_WORKOUTSETGROUP_ID, id);
        //Log.d("workout set group is = ", workoutSetGroup.toString());
        return workoutSetGroup;

    }

    /**
     * Insert an WorkoutSetGroup record from the parameter
     * @param workoutSetGroup WorkoutSetGroup
     * @param sync true if sync needed, false otherwise
     * @return long result of inserting
     */
    public long insertWorkoutSetGroup(WorkoutSetGroup workoutSetGroup, boolean sync) {
        return insertWorkoutSetGroup(
                workoutSetGroup.getSetGroupID(),
                workoutSetGroup.getExerciseId(),
                workoutSetGroup.getWorkoutSessionId(),
                sync);
    }
    /**
     * Delete an Object by id and tableID(e.g. 0=Exercise,1=setGroups)
     *
     * @param id_num  Id of a record
     * @param tableID Id of a table
     * @param sync sync - chooses whether sync is necessary
     */
    public void deleteObject(int id_num, int tableID, boolean sync) {
        switch (tableID) {
            case (0)://exercises
                deleteExercise(id_num, sync);
                break;
            case (1)://set groups
                deleteSetGroup(id_num, sync);
                break;
            case (2)://routines
                deleteRoutine(id_num, sync);
                break;
            case (3)://workout session
                deleteWorkoutSession(id_num, sync);
                break;
            case (4)://workout set group
                deleteWorkoutSetGroup(id_num, sync);
                break;
        }
    }
    /**
     * Accepts old primary key, and changes any required fields, then returns new primary key if
     * successful
     * @param id primary key of old record
     * @param exercise exercise to be updated
     * @param sync true if sync needed, false otherwise
     * @return primary key of new record, if changed (otherwise will match id parameter
     * @throws SQLException - database error
     * @throws JSONException - JSON creation error
     */
    public long updateExercise(long id, Exercise exercise, boolean sync) throws SQLException, JSONException {
        long result;
        this.deleteExercise(id, false);
        result = this.insertExercise(exercise, false);
        if (sync) {
            insertUpdateRecord(result, EXERCISE_TABLE, 1);
        }
        return result;
    }

    /**
     * Update a Routine record by parameter
     * @param id      Routine id
     * @param routine Routine.java
     * @param sync    boolean it is sync or not
     * @return long result after inserting a routine record
     * @throws SQLException - database Error
     */
    public long updateRoutine(long id, Routine routine, boolean sync) throws SQLException {
        long result;
        this.deleteRoutine(id, false);
        result = this.insertRoutine(routine, false);
        if (sync) {
            insertUpdateRecord(result, ROUTINE_TABLE, 1);
        }
        return result;
    }

    /**
     * Update a workoutSession record by WorkoutSession
     *
     * @param id      WorkoutSession id
     * @param session WorkoutSession Attributes(WorkoutSession)
     * @param sync    boolean it is sync or not
     * @return long result of deleting
     * @throws SQLException - database error
     */
    public long updateWorkoutSession(long id, WorkoutSession session, boolean sync) throws SQLException {
        long result;
        this.deleteWorkoutSession(id, false);
        result = this.insertWorkoutSession(session, false);

        if (sync) {
            insertUpdateRecord(result, WORKOUTSESSION_TABLE, 1);
        }
        return result;
    }

    /**
     * Update a SetGroup by the parameters
     *
     * @param id       SetGroup ID
     * @param setGroup SetGroup(java)
     * @param sync     whether it is sync or not
     * @return long result of updating
     * @throws SQLException database Error
     */
    public long updateSetGroup(long id, SetGroup setGroup, boolean sync) throws SQLException {
        long result;
        this.deleteSetGroup(id, false);
        result = this.insertSetGroup(setGroup, false);

        if (sync) {
            insertUpdateRecord(result, SETGROUP_TABLE, 1);
        }
        return result;
    }

    /**
     * Update a WorkoutSetGroup record by the parameters
     *
     * @param id                     workoutSetGroup id
     * @param createdWorkoutSetGroup WorkoutSetGroup.java
     * @param sync                   whether it is sync or not
     * @return long result of inserting
     * @throws SQLException Exception of SQL
     */
    public long updateWorkoutSetGroup(long id, WorkoutSetGroup createdWorkoutSetGroup, boolean sync)
            throws SQLException {
        long result;
        //Log.d("id is", String.valueOf(id));
        this.deleteWorkoutSetGroup(id, false);
        result = this.insertWorkoutSetGroup(createdWorkoutSetGroup, false);
        if (sync) {
            insertUpdateRecord(result, WORKOUTSETGROUP_TABLE, 1);
        }
        return result;
    }

    /**
     * Get a routine id by its name
     *
     * @param routineName Routine Name
     * @return long routineId
     */
    public long getRoutineIDByName(String routineName) {
        String[] columns = new String[]{ROUTINE_NAME, PK_ROUTINE_ID};
        Cursor cursor = database.query
                (ROUTINE_TABLE, columns,
                        ROUTINE_NAME + " like \"%" + routineName + "%\"",
                        null, null, null, null);

        cursor.moveToFirst();
        int routineId = cursor.getInt(cursor.getColumnIndex(PK_ROUTINE_ID));
        cursor.close();
        return routineId;
    }

    /**
     * returns all workout set groups in the database table
     * @return ArrayList of Workout Set Groups
     */
    public ArrayList<WorkoutSetGroup> selectAllWorkoutSetGroups() {
        Cursor myCursor = database.query(WORKOUTSETGROUP_TABLE, new String[]{PK_WORKOUTSETGROUP_ID,
                        FK_WSG_EXERCISE_ID, FK_WORKOUTSESSION_ID},
                null, null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        ArrayList<WorkoutSetGroup> workoutSetGroups = new ArrayList<>();
        WorkoutSetGroup workoutSetGroup;

        assert myCursor != null;
        for (myCursor.moveToFirst(); !myCursor.isAfterLast(); myCursor.moveToNext()) {
            workoutSetGroup =
                    new WorkoutSetGroup(myCursor.getInt(0)
                    , myCursor.getInt(1)
                    , myCursor.getInt(2));
            workoutSetGroups.add(workoutSetGroup);
            //Log.d("DBA - select all workoutsetgroups  - ", workoutSetGroup.toString());
        }
        myCursor.close();
        return workoutSetGroups;
    }

    //********************************************************************************************//

    /**
     * DatabaseHelper to be used at connecting to Database extends SQLiteOpenHelper
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            Log.w(TAG, CREATE_ROUTINE_TABLE);

            //Execute Queries to Create tables
            database.execSQL(CREATE_SESSION_TABLE);
            database.execSQL(CREATE_ROUTINE_TABLE);
            database.execSQL(CREATE_EXERCISE_TABLE);
            database.execSQL(CREATE_WORKOUTSESSION_TABLE);
            database.execSQL(CREATE_SETGROUP_TABLE);
            database.execSQL(CREATE_WORKOUTSETGROUP_TABLE);
            database.execSQL(CREATE_UPDATE_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            database.execSQL("DROP TABLE IF EXISTS " + ROUTINE_TABLE);
            database.execSQL("DROP TABLE IF EXISTS " + SESSION_TABLE);
            database.execSQL("DROP TABLE IF EXISTS " + WORKOUTSESSION_TABLE);
            database.execSQL("DROP TABLE IF EXISTS " + SETGROUP_TABLE);
            database.execSQL("DROP TABLE IF EXISTS " + WORKOUTSETGROUP_TABLE);
            database.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);
            database.execSQL("DROP TABLE IF EXISTS " + UPDATE_TABLE);
            onCreate(database);
        }
    }
}
