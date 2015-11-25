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
 * DatabaseAdapter class creates, drops, inserts and delept tables of Smartbells2
 */
public class DatabaseAdapter{
    /**
     * TAG is used to error messages log - (e.g. System.out.println(TAG + errmessage);)
     */
    public static final String TAG = "databaseAdapter";
    /**
     * ACCESS_TOKEN from API of The main system(SinguliryXL's SmartBells System)
     */
    public static final String ACCESS_TOKEN = "token";
    /**
     * Id of user who using the system now
     */
    public static final String SESSION_USER_ID = "user_id";
    /**
     * Routine Table Columns - PK_ROUTINE_ID - Primary Key(Routine id)
     */
    public static final String PK_ROUTINE_ID = "id";
    /**
     * User id of routine
     */
    public static final String ROUTINE_USER_ID = "user_id";
    /**
     * Name of Routine
     */
    public static final String ROUTINE_NAME= "name";
    /**
     * Boolean wheter a routines is public or not(true or false)
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
     * Workout Session Table Columns
     * WorkoutSession's id
     */
    public static final String PK_WORKOUTSESSION_ID = "id";
    /**
     * Foreign Key of WorkoutSession - User's id
     */
    public static final String FK_USER_ID = "user_id";
    /**
     * Name of WorkoutSession
     */
    public static final String SESSION_NAME = "name";
    /**
     * A date of creating a workoutsession record
     */
    public static final String SESSION_CREATED_AT = "created_at";
    /**
     * A date of updating a workoutsession record
     */
    public static final String SESSION_UPDATED_AT = "updated_at";
    //Workout Set Group Table Columns
    /**
     * An id of workoutsetgroup in the workoutsetgroup table
     */
    public static final String PK_WORKOUTSETGROUP_ID = "id";
    /**
     * An id of exercise of a worksessiongroup in the table
     */
    public static final String FK_WSG_EXERCISE_ID = "exercise_id";
    /**
     * Foreign Key - workoutsession id
     */
    public static final String FK_WORKOUTSESSION_ID = "workout_session_id";
    //Set Group Table Columns
    /**
     * Primary Key - Setgroup id
     */
    public static final String PK_SETGROUP_ID = "id";
    /**
     * Foreign Key - Exercise id
     */
    public static final String FK_EXERCISE_ID = "exercise_id";
    /**
     * The number of reps of Setgroup in the setgoup table
     */
    public static final String SETGROUP_REPS = "reps_per_set";
    /**
     * The number of sets of Setgroup in the setgoup table
     */
    public static final String SETGROUP_SETS = "number_of_sets";
    /**
     * A date of creating a setgroup record
     */
    public static final String SETGROUP_CREATED_AT = "created_at";
    /**
     * A date of updating a setgroup record
     */
    public static final String SETGROUP_UPDATED_AT  = "updated_at";
    /**
     * An id of routine in the setgroup table
     */
    public static final String SETGROUP_ROUTINE_ID = "routine_id";
    //Exercise Table Columns
    /**
     * Primary Key - Exercise id in the exercise table
     */
    public static final String PK_EXERCISE_ID = "id";
    /**
     * Name of exercise in the exercise table.
     */
    public static final String EXERCISE_NAME = "name";
    /**
     * A count number of encreasing per a session
     */
    public static final String INCREASE_PER_SESSION = "increase_per_session";
    /**
     * A date of creating an exercise record
     */
    public static final String EXERCISE_CREATED_AT = "created_at";
    /**
     * A date of updating an exercise record
     */
    public static final String EXERCISE_UPDATED_AT = "updated_at";
    /**
     * A boolean column of the exercise table whether the exercise is public or not
     */
    public static final String EXERCISE_IS_PUBLIC = "is_public";
    /**
     * User's id of Exercise
     */
    public static final String EXERCISE_USER_ID = "user_id";
    /**
     * A table name for updating table
     */
    private static final String UPDATE_ID = "update_id";
    /**
     * Table's name
     */
    private static final String UPDATE_TABLE_IDENTIFIER = "Table_name";
    /**
     * A type of updating
     */
    private static final String UPDATE_TYPE = "Update_Type";
    //Tables
    /**
     * A variable of Session table name
     */
    protected static final String SESSION_TABLE = "Session";
    /**
     * A variable of Routine table name
     */
    protected static final String ROUTINE_TABLE = "Routine";
    /**
     * A variable of WorkoutSession table name
     */
    protected static final String WORKOUTSESSION_TABLE = "WorkoutSession";
    /**
     * A variable of WorkoutSetGroup table name
     */
    protected static final String WORKOUTSETGROUP_TABLE = "WorkoutSetGroup";
    /**
     * A variable of Setgroup table name
     */
    protected static final String SETGROUP_TABLE = "Setgroup";
    /**
     * A variable of Exercise table name
     */
    protected static final String EXERCISE_TABLE = "Exercise";
    /**
     * A variable of UpdateTable table name
     */
    protected static final String UPDATE_TABLE = "UpdateTable";
    /**
     * A variable of smartbellsdata database name
     */
    private static final String DATABASE_NAME = "smartbellsdata";

    /**
     * A variable of Database version number
     */
    private static final int DATABASE_VERSION = 14;
    /*
     * Create A Table - SESSION_TABLE
     */
    private static final String CREATE_SESSION_TABLE =
            "CREATE TABLE if not exists " + SESSION_TABLE + " (" +
                    ACCESS_TOKEN        + "," +
                    SESSION_USER_ID     + " integer);";

    /*
     * Create A Table - ROUTINE_TABLE
     */
    private static final String CREATE_ROUTINE_TABLE =
            "CREATE TABLE if not exists " + ROUTINE_TABLE + " (" +
                    PK_ROUTINE_ID       + " integer PRIMARY KEY autoincrement," +
                    ROUTINE_USER_ID     + "," +
                    ROUTINE_NAME        + "," +
                    ROUTINE_IS_PUBLIC   + "," +
                    ROUTINE_CREATED_AT  + "," +
                    ROUTINE_UPDATED_AT  + "," +
                    " UNIQUE (" + PK_ROUTINE_ID + "));";

    /*
     * Create A Table - WORKOUTSESSION_TABLE
     */
    private static final String CREATE_WORKOUTSESSION_TABLE =
            "CREATE TABLE if not exists " + WORKOUTSESSION_TABLE + " (" +
                    PK_WORKOUTSESSION_ID    + " integer PRIMARY KEY," +
                    FK_USER_ID              + "," +
                    SESSION_NAME            + "," +
                    SESSION_CREATED_AT      + "," +
                    SESSION_UPDATED_AT      + "," +
                    " UNIQUE (" + PK_WORKOUTSESSION_ID + "));";
    /*
     * Create A Table - SETGROUP_TABLE
     */
    private static final String CREATE_SETGROUP_TABLE =
            "CREATE TABLE if not exists " + SETGROUP_TABLE + " (" +
                    PK_SETGROUP_ID          + " integer PRIMARY KEY," +
                    SETGROUP_ROUTINE_ID     + "," +
                    FK_EXERCISE_ID          + "," +
                    SETGROUP_SETS           + "," +
                    SETGROUP_REPS           + "," +
                    SETGROUP_CREATED_AT     + "," +
                    SETGROUP_UPDATED_AT     + "," +
                    " UNIQUE (" + PK_SETGROUP_ID + "));";
    /*
     * Create A Table - WORKOUTSETGROUP_TABLE
     */
    private static final String CREATE_WORKOUTSETGROUP_TABLE =
            "CREATE TABLE if not exists " + WORKOUTSETGROUP_TABLE + " (" +
                    PK_WORKOUTSETGROUP_ID   + " integer PRIMARY KEY," +
                    FK_WSG_EXERCISE_ID      + "," +
                    FK_WORKOUTSESSION_ID    + "," +
                    " FOREIGN KEY (" + FK_WSG_EXERCISE_ID  + ") REFERENCES "
                    + EXERCISE_TABLE + "(" + PK_EXERCISE_ID  + ")" +
                    " FOREIGN KEY (" + FK_WORKOUTSESSION_ID + ") REFERENCES "
                    + WORKOUTSESSION_TABLE + "(" + PK_WORKOUTSESSION_ID + ")" +
                    "  UNIQUE (" + PK_WORKOUTSETGROUP_ID  +"));";
    /*
     * Create A Table - EXERCISE_TABLE
     */
    private static final String CREATE_EXERCISE_TABLE =
            "CREATE TABLE if not exists " + EXERCISE_TABLE + " (" +
                    PK_EXERCISE_ID          + " integer PRIMARY KEY," +
                    EXERCISE_NAME           + "," +
                    INCREASE_PER_SESSION    + "," +
                    EXERCISE_CREATED_AT     + "," +
                    EXERCISE_UPDATED_AT     + "," +
                    EXERCISE_IS_PUBLIC      + "," +
                    EXERCISE_USER_ID        + "," +
                    " UNIQUE (" + PK_EXERCISE_ID + "));";
    /*
     * Create A Table - UPDATE_TABLE
     */
    private static final String CREATE_UPDATE_TABLE =
            "Create table if not exists " + UPDATE_TABLE + " (" +
                    UPDATE_ID               + " integer," +
                    UPDATE_TABLE_IDENTIFIER + " integer," +
                    UPDATE_TYPE + " integer );";
    /**
     * A final variable for initiated DatabaseHelper - Context
     */
    private final Context context;
    /**
     * DatabaseHelper method's variable for intiation Database
     */
    private DatabaseHelper databaseHelper;
    /**
     * A variable for using SQLite database - database
     */
    private SQLiteDatabase database;

    /**
     * DatabaseAdapter constructor
     * @param context :
     */
    public DatabaseAdapter(Context context) { this.context = context; }
    //********************************************************************************************//

    //Open database

    /**
     * Open a Database
     * @return this
     * @throws SQLException
     */
    public DatabaseAdapter openLocalDatabase() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }
    /**
     * Close  databse
     */
    public void closeLocalDatabase() {
        if (databaseHelper != null ) {
            databaseHelper.close();
        }
    }

    /*********************************Update Table*************************************************/
    /**
     * Creates a record in the update table. The update table is used to track all transactions that
     * occur within the database. the parameters are used to determine the different column values.
     * @param id_num - id of the column that was affected by the change in question.
     *               As an example, if exercise_id = 1, then the id would be 1, and table defined by
     *               tableIdentifier.
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
     * @return row number of the insert
     */
    /**
     * Insert and update a record of tables
     * @param id_num            : Table's id number
     * @param tableIdentifier   : tableIdentifier
     * @param transactionType   : A type of transaction
     * @return database.insert(database.insert(UPDATE_TABLE, null, initialValues)
     * @throws SQLException : SQL error exception
     */
    private long insertUpdateRecord(Long id_num, String tableIdentifier, int transactionType) throws SQLException {
        /*
        There are not currently any updates(U) on this database.
        If in the future we add them, ensure that they hit this method.
        insertUpdateRecord(objectID, tableIdentifier, 1);
         */
        /**
         * A number of table
         */
        int tableNum = -1;
        switch(tableIdentifier){
            case(EXERCISE_TABLE):
                tableNum = 0;
                break;
            case(SETGROUP_TABLE):
                tableNum = 1;
                break;
            case(ROUTINE_TABLE):
                tableNum = 2;
                break;
            case(WORKOUTSESSION_TABLE):
                tableNum = 3;
                break;
            case(WORKOUTSETGROUP_TABLE):
                tableNum = 4;
                break;
        }
        if (id_num == -1) throw new SQLException("previous statement failed"); // To check the table number from 0 to 4
        if (tableNum == -1) throw new SQLException("Improper table selection");
        if (transactionType <0 || transactionType > 2 )//To check the transaction type(0-2)
            throw new SQLException("Improper transaction type, should be between 0 and 2");
        /**
         * Content values
         */
        ContentValues initialValues = new ContentValues();
        /**
         * To put the id, number of table, and transactionType
         */
        initialValues.put(UPDATE_ID, id_num);
        initialValues.put(UPDATE_TABLE_IDENTIFIER, tableNum);
        initialValues.put(UPDATE_TYPE, transactionType);

        return database.insert(UPDATE_TABLE, null, initialValues);
        }

    /**
     * reads the update table, and returns an integer representation of the results.
     * This seemed the most lightweight way to accomplish this.
     * @return - integer array
     *          [0] = id# of the row changed
     *          [1] = table that was changed (see insertUpdateRecord)
     *          [2] = type of change (see insertUpdateRecord)
     */
    public ArrayList<int[]> readUpdateRecord(){
        Cursor myCursor = database.query(UPDATE_TABLE, null,
                null, null, null, null, null);
        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        ArrayList<int[]> result = new ArrayList<>();
        for (int index = 0; index < myCursor.getCount(); index++) {
            result.add(new int[3]);
            for (int i = 0; i < myCursor.getColumnCount(); i++) {
                result.get(index)[i] = myCursor.getInt(i);
            }
        }
        return result;
    }

    /**
     * Query for updating
     * @return true or false
     */
    public boolean hasUpdates(){
        Cursor myCursor = database.query(UPDATE_TABLE, null, null, null, null, null, null);
        if (myCursor.getCount() > 0){
            return true;
        }
        return false;
    }


    /*********************************SESSION TABLE***********************************************/
    //Insert Token

    /**
     * Insert Token into the SESSION_TABLE
     * @param accessToken  : Access Token from API of SingularityXL
     * @param user_Id      : User's id
     * @return             : result = database.insert(SESSION_TABLE, null, initialValues);
     */
    public long insertToken(String accessToken, int user_Id) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(ACCESS_TOKEN, accessToken);
        initialValues.put(SESSION_USER_ID, user_Id);
        //insert
        database.delete(SESSION_TABLE, null, null);// delete a previous token

        long result;
        result = database.insert(SESSION_TABLE, null, initialValues);// Insert a new token
        return result;
    }

    //get token

    /**
     * To get an index of Access_Token of Session_Table
     * @return cursor.getString(token);
     */
    public String getTokenAsString() {
        String[] columns = new String[]{ACCESS_TOKEN};
        Cursor cursor = database.query(SESSION_TABLE, columns, null, null, null, null, null);
        int token = cursor.getColumnIndex(ACCESS_TOKEN);
        cursor.moveToLast();
        return cursor.getString(token);
    }

    /**
     * To get a Session's user id of Session_Table
     * @return cursor.getInt(token);
     */
    public int getUserIDForSession(){
        String[] columns = new String[]{SESSION_USER_ID};
        Cursor cursor = database.query(SESSION_TABLE, columns, null, null, null, null, null);
        int token = cursor.getColumnIndex(SESSION_USER_ID);
        cursor.moveToLast();
        return cursor.getInt(token);
    }

    /*********************************ROUTINE TABLE***********************************************/
    /**
     * To Load (insert) All Routines as the routines of parameter
     * @param routines
     */
    public void loadAllRoutines(ArrayList<Routine> routines) {
        database.beginTransaction();
        for (Routine i : routines){
            //loads all values
            ContentValues initialValues = routineAttributes(i);

            //database insert
            database.insertWithOnConflict
                    (ROUTINE_TABLE, null, initialValues,
                            SQLiteDatabase.CONFLICT_IGNORE);
            for (SetGroup y : i.getSetGroups()){
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
     * To insert a record of the routine table
     * @param routine : a Routine Record
     */
    public void insertRoutine(Routine routine) {

        Log.d("DatabaseAdaptor.insertRoutine", routine.toString());
        String setgroupid = "";
        for (SetGroup x: routine.getSetGroups()){
            setgroupid += String.valueOf( x.getId());
        }
        Log.d("DatabaseAdaptor.insertRoutine", setgroupid);
        Log.d("DatabaseAdaptor.insertRoutine", String.valueOf
                (insertRoutine(routine.getName(),
                        routine.getIsPublic(), routine.getCreated_At(), routine.getUpdated_At())));
    }

    /**
     * To put the values of routine record into the contentvalues field names.
     * @param routine : A routine Record
     * @return
     */
    private ContentValues routineAttributes(Routine routine){
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
     *  To insert a routine record as the parameters(name, ispulic,created date,updated date)
     * @param routineName : Name of a Routine
     * @param routineIsPublic : A boolean where it is public or not
     * @param createDate : A date of creating a record
     * @param updateDate : A date of updating a record
     * @return
     */
    public long insertRoutine(String routineName,
                              Boolean routineIsPublic,
                              String createDate,
                              String updateDate ) {

        //This class is used to store a set of values that the ContentResolver can process.
        ContentValues initialValues = new ContentValues();

        initialValues.put(ROUTINE_NAME, routineName);
        initialValues.put(ROUTINE_IS_PUBLIC, routineIsPublic);
        initialValues.put(ROUTINE_CREATED_AT, createDate);
        initialValues.put(ROUTINE_UPDATED_AT, updateDate);

        //insert
        long result = database.insert(ROUTINE_TABLE, null, initialValues);
        try {
            insertUpdateRecord(result, ROUTINE_TABLE, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Delete Routine

    /**
     * To delete a routine record with routineId
     * @param routineId
     * @return
     */
    public long deleteRoutine(String routineId) {

        //Delete row from routine table
        long v = database.delete(ROUTINE_TABLE, PK_ROUTINE_ID + "=" + routineId, null);
        try {
            insertUpdateRecord(Long.valueOf(routineId), ROUTINE_TABLE, 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    // insert Values
    /*public void insertTESTRoutines() {
        insertRoutine("Routine1", false, (["bells", "today", "today"]));
        insertRoutine("Routine2", true, ["bells", "1 week ago", "today"]);
        insertRoutine("Routine3", false, ["punch", "2 days ago", "today"]);
        insertRoutine("Routine4", true, ["kick", "today", "today"]);
        insertRoutine("Routine5", false,["drops","yesterday","today"]);

    }*/

    //Select routine by it's ID

    /**
     * Select Routines with routine id
     * @param id : Routind id
     * @return   : A cursor of a routine record
     * @throws android.database.SQLException
     */
    public Cursor selectRoutineById(long id) throws android.database.SQLException {
        Cursor mCursor = null;
        Log.d(TAG, "id is " + id);
        mCursor = database.query(ROUTINE_TABLE, new String[] {PK_ROUTINE_ID,
                        ROUTINE_NAME, ROUTINE_IS_PUBLIC,
                        ROUTINE_CREATED_AT, ROUTINE_UPDATED_AT},

                PK_ROUTINE_ID + " = ?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    //Select private routine by User's ID

    /**
     * Select routine records by user id
     * @param userId : User id
     * @return : Routine Records of one specific user
     * @throws android.database.SQLException
     */
    public Cursor selectMyRoutineById(int userId) throws android.database.SQLException {
        Cursor mCursor = null;
        Log.d(TAG, "userId is " + userId);
        mCursor = database.query(ROUTINE_TABLE, new String[] {PK_ROUTINE_ID,
                        ROUTINE_USER_ID, ROUTINE_IS_PUBLIC,
                        ROUTINE_CREATED_AT, ROUTINE_UPDATED_AT},
                ROUTINE_USER_ID + " = ?", new String[] {String.valueOf(userId)},
                ROUTINE_IS_PUBLIC + " = false", null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    /**
     *Select All Public Routines
     */
    //Select All Routines
    public Cursor selectAllRoutines() {
        Cursor myCursor = database.query(ROUTINE_TABLE, new String[]{PK_ROUTINE_ID,
                        ROUTINE_NAME, ROUTINE_IS_PUBLIC,
                        ROUTINE_CREATED_AT, ROUTINE_UPDATED_AT},
                null, null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    //Reference: http://stackoverflow.com/questions/9466380/
    //           how-to-get-data-from-my-database-and-put-it-on-a-listview-that-is-clickable
    // Get all Routines and return a string

    /**
     * Select All routines
     * @return
     */
    public ArrayList<String> getRoutinesAsStrings() {
        String[]columns = new String[]{ROUTINE_NAME};
        Cursor cursor = database.query(ROUTINE_TABLE, columns, null, null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<String>();
        //int id = cursor.getColumnIndex(PK_ROUTINE_ID);
        int name = cursor.getColumnIndex(ROUTINE_NAME);

        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            result.add(cursor.getString(name));
        }

        return result;
    }
    //Reference: http://stackoverflow.com/questions/9466380/
    //           how-to-get-data-from-my-database-and-put-it-on-a-listview-that-is-clickable
    // Get private Routines of specific user and return a string

    /**
     * Select routines by user id
     * @param userId : User id
     * @return : Routines of an user
     */
    public ArrayList<String> getMyRoutinesAsStrings(int userId) {
        String[]columns = new String[]{ROUTINE_NAME};
        Cursor cursor = database.query(ROUTINE_TABLE, null, ROUTINE_USER_ID + "=" + userId,
                null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<String>();
        //int id = cursor.getColumnIndex(PK_ROUTINE_ID);
        int name = cursor.getColumnIndex(ROUTINE_NAME);

        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            result.add(cursor.getString(name));
        }

        return result;
    }
    /******************************WORKOUTSESSION TABLE********************************************/

    /*
     * Insert all workoutsession as the parameter Arraylist
     * @param workoutSessions : ArrayList of woroutsessions
     */
    public void loadAllWorkoutSessions(ArrayList<WorkoutSession> workoutSessions) {
        database.beginTransaction();
        for (WorkoutSession i : workoutSessions){
            ContentValues initialValues = new ContentValues();
            initialValues.put(PK_WORKOUTSESSION_ID, i.getId());
            initialValues.put(FK_USER_ID, i.getUser_Id());
            initialValues.put(SESSION_NAME, i.getName());
            initialValues.put(SESSION_CREATED_AT, i.getCreated_At());
            initialValues.put(SESSION_UPDATED_AT, i.getUpdated_At());

            //database insert
            database.insertWithOnConflict
                    (WORKOUTSESSION_TABLE, null, initialValues,
                            SQLiteDatabase.CONFLICT_IGNORE);
            for (WorkoutSetGroup y : i.getSetGroups()){
                ContentValues initialValuesSetGroup = new ContentValues();
                SetGroup x = y.getSet_group();

                initialValuesSetGroup.put(PK_WORKOUTSETGROUP_ID, y.getWorkoutSessionId());
                initialValuesSetGroup.put(FK_WORKOUTSESSION_ID, i.getId());
                initialValuesSetGroup.put(FK_WSG_EXERCISE_ID, x.getExerciseId());

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
     * Select a record by session_name
     * @return
     */
    public ArrayList<String> getWorkoutSessionsAsStrings() {
        String[] columns = new String[]{SESSION_NAME};
        Cursor cursor = database.query(WORKOUTSESSION_TABLE, columns, null, null, null, null, null);
        //todo should be "where user_id = user_id" - only pull the users object
        //Results String Array
        ArrayList<String> result = new ArrayList<String>();
        int exname = cursor.getColumnIndex(SESSION_NAME);

        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            result.add(cursor.getString(exname));
        }

        return result;
    }

    // Get private WorkoutSessions of specific user and return a string

    /**
     * Select workouts by user id
     * @param userId : User id
     * @return : Records of Workouts of user id
     */
    public ArrayList<String> getMyWorkoutsAsStrings(int userId) {
        String[]columns = new String[]{SESSION_NAME};
        Cursor cursor = database.query(WORKOUTSESSION_TABLE, null, FK_USER_ID + "=" + userId,
                null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<String>();
        /**
         * Index of the Session name
         */
        int nameIndex = cursor.getColumnIndex(SESSION_NAME);

        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            result.add(cursor.getString(nameIndex));
        }
        return result;
    }

    // Get private WorkoutSessions of specific user and return a string

    /**
     * Select workout ids by user
     * @param userId : User id
     * @return : Indexes of workouts
     */
    public ArrayList<String> getMyWorkoutIds(int userId) {
        String[]columns = new String[]{PK_WORKOUTSESSION_ID};
        Cursor cursor = database.query(WORKOUTSESSION_TABLE, null, FK_USER_ID + "=" + userId,
                null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<String>();
        /**
         * Index of the WorkoutSessionId
         */
        int workoutIdIndex = cursor.getColumnIndex(PK_WORKOUTSESSION_ID);

        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            result.add(cursor.getString(workoutIdIndex));
        }
        return result;
    }
    /**********************************************************************************************/
    /**
     * Insert a WourkoutSession record
     * @param session : a WorkoutSession record
     */
    public void insertWorkoutSession(WorkoutSession session) {
        for (WorkoutSetGroup setgroup: session.getSetGroups())
            insertSetGroup(setgroup.getSet_group());//todo check whether this works
        insertWorkoutSession(session.getUser_Id(), session.getName(), null, null, null);
    }

    //Insert Workout Session

    /**
     * Insert A WorkoutSession Reocrd from parameter
     *                     (User id, Session Nmae, setGroup id, Created date, Updated date)
     * @param userId : User id
     * @param sessionName : Session Name
     * @param setGroupId  : SetGroup ID
     * @param createdAt   : Created date
     * @param updatedAt   : Updated date
     * @return : result = insertUpdateRecord(result, WORKOUTSESSION_TABLE, 0);
     */
    public long insertWorkoutSession(int userId,
                                     String sessionName,
                                     String setGroupId,
                                     String createdAt,
                                     String updatedAt) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(FK_USER_ID, userId);
        initialValues.put(SESSION_NAME, sessionName);
        initialValues.put(SESSION_CREATED_AT, createdAt);
        initialValues.put(SESSION_UPDATED_AT, updatedAt);

        //insert
        long result;
        result = database.insert(WORKOUTSESSION_TABLE, null, initialValues);
        try {
            result = insertUpdateRecord(result, WORKOUTSESSION_TABLE, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Delete Workout Session

    /**
     * Delete a workoutsession record
     * @param workoutsessionid : Workoutsession id
     * @return Result of deleted record
     */
    public long deleteWorkoutSession(String workoutsessionid) {

        //Delete row
        long v = database.delete(WORKOUTSESSION_TABLE, PK_WORKOUTSESSION_ID
                + "=" + workoutsessionid, null);
        try {
            insertUpdateRecord(Long.valueOf(workoutsessionid), WORKOUTSESSION_TABLE, 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    //Select All workout Sessions

    /**
     * Select all workoutsessions of WORKOUTSESSION_TABLE
     * @return Cursor of selection of all workoutsession records
     */
    public Cursor selectAllWorkoutSessions() {
        Cursor myCursor = database.query(WORKOUTSESSION_TABLE, new String[]{PK_WORKOUTSESSION_ID,
                        FK_USER_ID, SESSION_NAME, SESSION_CREATED_AT,
                        SESSION_UPDATED_AT},
                null, null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    /**********************************WORKOUT SETGROUP TABLE
     * @param exerciseid
     * @param workoutsessionid**************************************/

    //insert workoutsetgroup

    /**
     * Insert WorkoutSetGroup record by parameter(WorkoutSetGrouid, exerciseid, workoutsessionid)
     * @param workoutSetGroupid : WouroutSetGroup id
     * @param exerciseid        : Exercise id
     * @param workoutsessionid  : Workoutsession id
     * @return : result of the insert query
     */
    public long insertWorkoutSetGroup(  int workoutSetGroupid,
                                        int exerciseid,
                                        int workoutsessionid) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(PK_WORKOUTSETGROUP_ID, workoutSetGroupid);
        initialValues.put(FK_EXERCISE_ID, exerciseid);
        initialValues.put(FK_WORKOUTSESSION_ID, workoutsessionid);


        //insert
        long result = database.insert(WORKOUTSETGROUP_TABLE, null, initialValues);
        try {
            insertUpdateRecord(result, WORKOUTSETGROUP_TABLE, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
    /**********************************************************************************************/

    //Delete Workout Set Group

    /**
     * Delete a WorkoutSetGroup record by workoutsetgroupid
     * @param workoutsetgroupid
     * @return result of deleting a record
     */
    public long deleteWorkoutSetGroup(String workoutsetgroupid) {

        //Delete row
        long v = database.delete(WORKOUTSETGROUP_TABLE, PK_WORKOUTSETGROUP_ID
                + "=" + workoutsetgroupid, null);
        try {
            insertUpdateRecord(Long.valueOf(workoutsetgroupid), WORKOUTSETGROUP_TABLE, 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    //Select All Set Groups

    /**
     * Select all workoutsetgroups from WORKOUTSETGROUP_TABLE
     * @return Cursor of the query
     */
    public Cursor selectAllWorkoutSetGroups() {
        Cursor myCursor = database.query(WORKOUTSETGROUP_TABLE, new String[]{PK_WORKOUTSETGROUP_ID, FK_WSG_EXERCISE_ID},
                null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }


    //Select All Set Groups By WorkoutSessioID - Cursor

    /**
     * Select all workoutsetgroups by workoutsession id
     * @param workoutSessionId : Workoutsession id
     * @return : Curso of the Query
     */
    public Cursor selectMyAllWorkoutSetGroups(int workoutSessionId) {
        Cursor myCursor = database.query(WORKOUTSETGROUP_TABLE, null,
                FK_WORKOUTSESSION_ID + "=" + workoutSessionId, null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    // Get private WorkoutSessions of specific user and return a string - ArrayList

    /**
     * Select WorkoutSetGroupIds by workoutSessionId
     * @param workoutSessionId : WorkoutSession id
     * @return Arraylist of WorkoutSetGroupIds
     */
    public ArrayList<String> getMyWorkoutSetGroupIds(String workoutSessionId) {
        String[]columns = new String[]{PK_WORKOUTSETGROUP_ID};
        Cursor cursor = database.query(WORKOUTSETGROUP_TABLE,null,
                FK_WORKOUTSESSION_ID + "=" + workoutSessionId,null, null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<String>();
        /**
         * Index of the WorkoutSessionId
         */
        int setGroupId = cursor.getColumnIndex(PK_WORKOUTSETGROUP_ID);

        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            result.add(cursor.getString(setGroupId));
        }
        return result;
    }
    /**********************************SETGROUP TABLE**********************************************/
    /**
     * Insert a setgroup record into the Setgroup table
     * @param set_group : a setgroup record
     */
    public void insertSetGroup(SetGroup set_group) {
        insertSetGroup(
                set_group.getExercise().getId(),
                set_group.getNumberOfSets(),
                set_group.getRepsPerSet(),
                null, null);
    }

    /**
     * Set the values of setgroup into the setgoup table
     * @param setgroup : SetGroup
     * @return : Valuues of a Setgroup reord
     */
    private ContentValues setGroupAttributes(SetGroup setgroup){
        ContentValues initialValuesSetGroup = new ContentValues();
        initialValuesSetGroup.put(PK_SETGROUP_ID, setgroup.getId());
        initialValuesSetGroup.put(SETGROUP_ROUTINE_ID, setgroup.getRoutineId());
        initialValuesSetGroup.put(FK_EXERCISE_ID, setgroup.getExerciseId());
        initialValuesSetGroup.put(SETGROUP_SETS, setgroup.getNumberOfSets());
        initialValuesSetGroup.put(SETGROUP_REPS, setgroup.getRepsPerSet());
        initialValuesSetGroup.put(SETGROUP_CREATED_AT, setgroup.getCreationDate());
        initialValuesSetGroup.put(SETGROUP_UPDATED_AT, setgroup.getLastUpdated());
        return initialValuesSetGroup;
    }


    /**********************************************************************************************/

    //Insert Set Group

    /**
     * Insert a set group record by the values of the parameter
     * @param exerciseId : Exercise id
     * @param sets       : Sets of the exercise
     * @param reps       : Reps of the Sets
     * @param createdAt  : Created date
     * @param updatedAt  : Updated date
     * @return : The result of insert query
     */
    public long insertSetGroup(int exerciseId,
                               int sets,
                               int reps,
                               String createdAt,
                               String updatedAt) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(FK_EXERCISE_ID, exerciseId);
        initialValues.put(SETGROUP_SETS, sets);
        initialValues.put(SETGROUP_REPS, reps);
        initialValues.put(SETGROUP_CREATED_AT, createdAt);
        initialValues.put(SETGROUP_UPDATED_AT, updatedAt);

        //insert
        long result;
        result = database.insert(SETGROUP_TABLE, null, initialValues);
        try {
            insertUpdateRecord(result, SETGROUP_TABLE, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    //Delete Set Groups

    /**
     * Delect a set group record by set group id
     * @param setgroupid : Set group id
     * @return : A result of the delete Query
     */
    public long deleteSetGroup(String setgroupid) {

        //Delete row
        long v = database.delete(SETGROUP_TABLE, PK_SETGROUP_ID
                + "=" + setgroupid, null);
        try {
            insertUpdateRecord(Long.valueOf(setgroupid), SETGROUP_TABLE, 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    //Select All Set Groups

    /**
     * Select All Set groups
     * @return : Curso of the all setgroups
     */
    public Cursor selectAllSetGroups() {
        Cursor myCursor = database.query(SETGROUP_TABLE, new String[]{PK_SETGROUP_ID,
                        FK_EXERCISE_ID, SETGROUP_SETS, SETGROUP_REPS, SETGROUP_CREATED_AT,
                        SETGROUP_UPDATED_AT},
                null, null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }
    //Select UserId's Set Groups.
    // Get private Routines of specific user and return a ArrayList<string>

    /**
     * Select Setgoupids by workoutsetgoupid
     * @param workoutSetGroupId : WorkoutSetGroup id
     * @return : ArrayList of the select query
     */
    public ArrayList<String> getMySetGroupIdsByWSG(String workoutSetGroupId) {
        SetGroup[]setGroups = new SetGroup[]{};
        Cursor cursor = database.query(SETGROUP_TABLE, new String[]{PK_SETGROUP_ID + "=" +workoutSetGroupId,
                FK_EXERCISE_ID, SETGROUP_SETS, SETGROUP_REPS, SETGROUP_CREATED_AT,
                SETGROUP_UPDATED_AT},null, null, null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<String>();
        int id = cursor.getColumnIndex(FK_EXERCISE_ID);

        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            result.add(cursor.getString(id));
        }
        return result;
    }
    /**
     * Efficiently Loads all Exercises from the remote Database
     * @param exercise : ArrayList of exercises
     */
    public void loadAllExercises(ArrayList<Exercise> exercise) {
        database.beginTransaction();
        for (Exercise i : exercise){
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
     * Insert an arraylist of exercises
     * @param exercise : Exercises
     */
    public void insertExercise(ArrayList<Exercise> exercise) {
        for (Exercise x : exercise){
            insertExercise(x);
        }
    }

    /**
     * Insert a exercise record
     * @param exercise : Exercise
     */
    public void insertExercise(Exercise exercise){
        insertExercise(
                exercise.getId(),
                exercise.getName(),
                String.valueOf(exercise.getIncrease_Per_Session()),
                exercise.getCreated_At(),
                exercise.getUpdated_At(),
                exercise.getIsPublic(),
                exercise.getUser_Id());
    }

    /**
     * Insert a exercise by parameter values
     * @param exerciseName : Exercise Name
     * @param increasePerSession : Increase per Session
     * @param createdAt : Created date
     * @param updatedAt : Updated date
     * @param is_Public : Boolear where it is public or not
     * @param user_ID   : user id
     * @return A result of the insert Query
     */
    public long insertExercise(int exerciseID,
                               String exerciseName,
                               String increasePerSession,
                               String createdAt,
                               String updatedAt,
                               boolean is_Public,
                               int user_ID) {
        //Log.d("DatabaseAdaptor.insertExercise(name) - ", exerciseName);
        //Log.d("DatabaseAdaptor.insertExercise(increase) - ", increasePerSession);
        ContentValues initialValues = new ContentValues();
        initialValues.put(PK_EXERCISE_ID, exerciseID);
        initialValues.put(EXERCISE_NAME, exerciseName);
        initialValues.put(INCREASE_PER_SESSION, increasePerSession);
        initialValues.put(EXERCISE_CREATED_AT, createdAt);
        initialValues.put(EXERCISE_UPDATED_AT, updatedAt);
        initialValues.put(EXERCISE_IS_PUBLIC, is_Public);
        initialValues.put(EXERCISE_USER_ID, user_ID);

        //insert
        long result;
        result =  database.insert(EXERCISE_TABLE, null, initialValues);
        try {
            insertUpdateRecord(result, EXERCISE_TABLE, 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Put the contentvaluses of exercise attributes by parameter(Exercise)
     * @param exercise : Exercise
     * @return : Values of the exercise
     */
    private ContentValues exerciseAttributes(Exercise exercise){
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
     * Delcte a exercise record by exercise id
     * @param exerciseid : Exercise id
     * @return : A result of the delect query
     */
    public long deleteExercise(String exerciseid) {

        //Delete row
        long v = database.delete(EXERCISE_TABLE, PK_EXERCISE_ID
                + "=" + exerciseid, null);
        try {
            insertUpdateRecord(Long.valueOf(exerciseid), EXERCISE_TABLE, 2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    //Select All Exercises

    /**
     * Select All exercises
     * @return : Cursosr of all execises
     */
    public Cursor selectAllExercises() {
        Cursor myCursor = database.query(SETGROUP_TABLE, null,
                null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    /**
     * Select list of excercises from EXERCISE_TABLE
     * @return Arraylist of the exercises
     */
    public ArrayList<Exercise> getExercises(){
        String[] columns = new String[]{EXERCISE_NAME, PK_EXERCISE_ID};
        Cursor cursor = database.query(EXERCISE_TABLE, columns, null, null, null, null, null);
        //todo should be "where user_id = user_id" - only pull the users object
        ArrayList<Exercise> exercises = new ArrayList<>();
        Exercise exercise;

        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            exercise= new Exercise(cursor.getString(0), cursor.getInt(1));
            exercises.add(exercise);
        }
        Log.d("DatabaseAdaptor.getExercises - ", exercises.toString());
        return exercises;
    }

    /**
     * Select Exercises's list by exercise_name from EXERCISE_TABLE
     * @return Arrylist of exercises
     */
    public ArrayList<String> getExercisesAsStrings() {
        String[] columns = new String[]{EXERCISE_NAME};
        Cursor cursor = database.query(EXERCISE_TABLE, columns, null, null, null, null, null);
        //todo should be "where user_id = user_id" - only pull the users object
        //Results String Array
        ArrayList<String> result = new ArrayList<String>();
        int exname = cursor.getColumnIndex(EXERCISE_NAME);

        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            result.add(cursor.getString(exname));
        }

        return result;
    }

    /**
     * Select Exercise id by the name
     * @param name : Exercise name
     * @return : exerciseId
     */
    public int getExerciseIdByName(String name){
        String[] columns = new String[]{EXERCISE_NAME, PK_EXERCISE_ID};
        Cursor cursor = database.query
                (EXERCISE_TABLE, null,
                EXERCISE_NAME + " like \"%" + name + "%\"",
                null, null, null, null);

        cursor.moveToFirst();
        int exerciseId = cursor.getInt(0);

        return exerciseId;
    }

    /**
     * update all tables.
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
     * This method get record by the parameter valuse
     * @param table : Table name
     * @param primary_key : Primanry key of the table name
     * @param id : id of the table
     * @return : Json.tostring()
     * @throws SQLException : SQL exceptions
     */
    private String getObject(String table, String primary_key, int id) throws SQLException {
        Cursor query = database.query(table, null, primary_key + " =? ", new String[]{String.valueOf(id)}, null, null, null);
        Log.d("number of records returned =================", String.valueOf(query.getCount()));
        if (query.getCount() != 1) throw new SQLException("Improper Key");
        query.moveToFirst();
        JSONObject json = new JSONObject();
        for (int x = 0; x < query.getColumnCount(); x++){
            try {
                Log.d(query.getColumnName(x), query.getString(x));
                if (query.getColumnName(x).equals(EXERCISE_IS_PUBLIC)) {
                    if (query.getInt(x) == 0){
                        json.put(query.getColumnName(x), false);
                    }else {
                        json.put(query.getColumnName(x), true);
                    }
                }
                else json.put(query.getColumnName(x), query.getString(x));
            } catch (JSONException e) {
                Log.d("Throwing error on json creation", " might not work actually");
                e.printStackTrace();
            }
        }
        Log.d("returning following Json Object", json.toString());
        return json.toString();
    }

    /**
     * To get a exercise record by exercise id
     * @param id : exercise id
     * @return : a exercise reocrd
     * @throws SQLException : SQL exception
     */
    public String getExercise(int id) throws SQLException {
        //should return a json representation of the object in question
        return getObject(EXERCISE_TABLE, PK_EXERCISE_ID, id);
    }
    /**
     * To get a Set_Group record by Set_Group id
     * @param id : Set_Group id
     * @return : a Set_Group reocrd
     * @throws SQLException : SQL exception
     */
    public String getSet_Group(int id) throws SQLException {
        return getObject(SETGROUP_TABLE, PK_SETGROUP_ID, id);
    }
    /**
     * To get a Routine record by Routine id
     * @param id : Routine id
     * @return : a Routine reocrd
     * @throws SQLException : SQL exception
     */
    public String getRoutine(int id) throws SQLException {
        return getObject(ROUTINE_TABLE, PK_ROUTINE_ID, id);
    }
    /**
     * To get a WorkoutSession record by WorkoutSession id
     * @param id : WorkoutSession id
     * @return : a WorkoutSession reocrd
     * @throws SQLException : SQL exception
     */
    public String getWorkoutSession(int id) throws SQLException {
        return getObject(WORKOUTSESSION_TABLE, PK_WORKOUTSESSION_ID, id);
    }
    /**
     * To get a WorkoutSetGroup record by WorkoutSetGroup id
     * @param id : WorkoutSetGroup id
     * @return : a WorkoutSetGroup reocrd
     * @throws SQLException : SQL exception
     */
    public String getWorkoutSetGroup(int id) throws SQLException {
        return getObject(WORKOUTSETGROUP_TABLE, PK_WORKOUTSETGROUP_ID, id);
    }
    /**
     * Insert a WorkoutSetGroup record by workoutSetGroup value
     * @param workoutSetGroup : workoutSetGroup
     */
    public void insertWorkoutSetGroup(WorkoutSetGroup workoutSetGroup) {
        insertWorkoutSetGroup(
                workoutSetGroup.getSet_group().getId(),
                workoutSetGroup.getSet_group().getExerciseId(),
                workoutSetGroup.getWorkoutSessionId());
    }

    //********************************************************************************************//
    /**
     * This class is used as SQLitOpenHelper
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        /**
         * Create all tables of Smartbells
         * @param database : Database of Smartbells
         */
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

        /**
         * Drop all tables if exists in the smarbells' database
         * @param database : database
         * @param oldVersion : previous version
         * @param newVersion : New version
         */
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
