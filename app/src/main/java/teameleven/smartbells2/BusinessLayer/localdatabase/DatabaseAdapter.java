package teameleven.smartbells2.BusinessLayer.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.BusinessLayer.tableclasses.Exercise;
import teameleven.smartbells2.BusinessLayer.tableclasses.Routine;
import teameleven.smartbells2.BusinessLayer.tableclasses.SetGroup;
import teameleven.smartbells2.BusinessLayer.tableclasses.WorkoutSession;
import teameleven.smartbells2.BusinessLayer.tableclasses.WorkoutSetGroup;

/**
 * Created by Jarret on 2015-10-24.
 */
public class DatabaseAdapter {

    public static final String TAG = "databaseAdapter";
    //Session Table
    public static final String ACCESS_TOKEN = "token";
    //Routine Table Columns
    public static final String PK_ROUTINE_ID = "routine_id";
    public static final String ROUTINE_NAME= "routineName";
    public static final String ROUTINE_IS_PUBLIC = "routineIsPublic";
    public static final String ROUTINE_SET_GROUPS = "routineSetGroups";
    public static final String ROUTINE_CREATED_AT = "createdAt";
    public static final String ROUTINE_UPDATED_AT = "updatedAt";
    //Workout Session Table Columns
    public static final String PK_WORKOUTSESSION_ID = "workoutsession_id";
    public static final String FK_USER_ID = "user_id";
    public static final String SESSION_NAME = "sessionName";
    public static final String FK_WORKOUTSETGROUP_ID = "setgroup_id";
    public static final String SESSION_CREATED_AT = "createdAt";
    public static final String SESSION_UPDATED_AT = "updatedAt";
    //Workout Set Group Table Columns
    public static final String PK_WORKOUTSETGROUP_ID = "workoutsetgroup_id";
    public static final String FK_SETGROUP_ID = "setgroup_id";
    public static final String FK_WSG_EXERCISE_ID = "exercise_id";
    public static final String FK_WORKOUTSESSION_ID = "workoutSession_id";
    //Set Group Table Columns
    public static final String PK_SETGROUP_ID = "setgroup_id";
    public static final String FK_EXERCISE_ID = "exercise_id";
    public static final String SETGROUP_SETS = "sets";
    public static final String SETGROUP_REPS = "reps";
    public static final String SETGROUP_CREATED_AT = "createdAt";
    public static final String SETGROUP_UPDATED_AT  = "updatedAt";
    //Exercise Table Columns
    public static final String PK_EXERCISE_ID = "exercise_id";
    public static final String EXERCISE_NAME = "exerciseName";
    public static final String INCREASE_PER_SESSION = "increasePerSession";
    public static final String EXERCISE_CREATED_AT = "createdAt";
    public static final String EXERCISE_UPDATED_AT = "updatedAt";
    public static final String EXERCISE_IS_PUBLIC = "isPublic";
    public static final String EXERCISE_USER_ID = "userID";
    //Tables
    protected static final String SESSION_TABLE = "Session";
    protected static final String ROUTINE_TABLE = "Routine";
    protected static final String WORKOUTSESSION_TABLE = "WorkoutSession";
    protected static final String WORKOUTSETGROUP_TABLE = "WorkoutSetGroup";
    protected static final String SETGROUP_TABLE = "Setgroup";
    protected static final String EXERCISE_TABLE = "Exercise";
    private static final String DATABASE_NAME = "smartbellsdata";
    private static final int DATABASE_VERSION = 8;
    /*
     * Create Tables
     */
    private static final String CREATE_SESSION_TABLE =
            "CREATE TABLE if not exists " + SESSION_TABLE + " (" +
                    ACCESS_TOKEN        + ");";
    private static final String CREATE_ROUTINE_TABLE =
            "CREATE TABLE if not exists " + ROUTINE_TABLE + " (" +
                    PK_ROUTINE_ID       + " integer PRIMARY KEY autoincrement," +
                    ROUTINE_NAME        + "," +
                    ROUTINE_IS_PUBLIC   + "," +
                    ROUTINE_SET_GROUPS  + "," +
                    ROUTINE_CREATED_AT  + "," +
                    ROUTINE_UPDATED_AT  + "," +
                    " UNIQUE (" + PK_ROUTINE_ID + "));";
    private static final String CREATE_WORKOUTSESSION_TABLE =
            "CREATE TABLE if not exists " + WORKOUTSESSION_TABLE + " (" +
                    PK_WORKOUTSESSION_ID    + " integer PRIMARY KEY," +
                    FK_USER_ID              + "," +
                    SESSION_NAME            + "," +
                    ROUTINE_SET_GROUPS      + "," +
                    FK_WORKOUTSETGROUP_ID   + "," +
                    SESSION_CREATED_AT      + "," +
                    SESSION_UPDATED_AT      + "," +
                    " UNIQUE (" + PK_WORKOUTSESSION_ID + "));";
    private static final String CREATE_SETGROUP_TABLE =
            "CREATE TABLE if not exists " + SETGROUP_TABLE + " (" +
                    PK_SETGROUP_ID          + " integer PRIMARY KEY," +
                    FK_EXERCISE_ID          + "," +
                    SETGROUP_SETS           + "," +
                    SETGROUP_REPS           + "," +
                    SETGROUP_CREATED_AT     + "," +
                    SETGROUP_UPDATED_AT     + "," +
                    " UNIQUE (" + PK_SETGROUP_ID + "));";
    private static final String CREATE_WORKOUTSETGROUP_TABLE =
            "CREATE TABLE if not exists " + WORKOUTSETGROUP_TABLE + " (" +
                    PK_WORKOUTSETGROUP_ID   + " integer PRIMARY KEY," +
                    FK_SETGROUP_ID          + "," +
                    FK_WSG_EXERCISE_ID      + "," +
                    FK_WORKOUTSESSION_ID    + "," +
                    " FOREIGN KEY (" + FK_SETGROUP_ID + ") REFERENCES "
                    + SETGROUP_TABLE + "(" + PK_SETGROUP_ID + ")" +
                    " FOREIGN KEY (" + FK_WSG_EXERCISE_ID  + ") REFERENCES "
                    + EXERCISE_TABLE + "(" + PK_EXERCISE_ID  + ")" +
                    " FOREIGN KEY (" + FK_WORKOUTSESSION_ID + ") REFERENCES "
                    + WORKOUTSESSION_TABLE + "(" + PK_WORKOUTSESSION_ID + ")" +
                    "  UNIQUE (" + PK_WORKOUTSETGROUP_ID  +"));";
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
    private final Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public DatabaseAdapter(Context context) { this.context = context; }
    //********************************************************************************************//

    //Open database
    public DatabaseAdapter openLocalDatabase() throws SQLException {
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getWritableDatabase();

        //insertTESTRoutines();

        return this;
    }

    //Close database
    public void closeLocalDatabase() {
        if (databaseHelper != null ) {
            databaseHelper.close();
        }
    }

    /*********************************SESSION TABLE***********************************************/
    //Insert Token
    public long insertToken(String accessToken) {

        ContentValues initialValues = new ContentValues();
        initialValues.put(ACCESS_TOKEN, accessToken);
        //insert
        return database.insert(SESSION_TABLE, null, initialValues);
    }//todo add additional columns. ensure that adding a new token will clobber old token.

    //get token
    public String getTokenAsString() {
        String[] columns = new String[]{ACCESS_TOKEN};
        Cursor cursor = database.query(SESSION_TABLE, columns, null, null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<String>();
        int token = cursor.getColumnIndex(ACCESS_TOKEN);

        cursor.moveToLast();
        return cursor.getString(token); //todo - currently loading the most recently added record.
        //todo  This works, for the time being, but is certainly not best practice
        //todo especially considering all the records that are being created and not used
        /*
        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            return cursor.getString(token);
        }
        //return result;
        return null;
        */
    }

    /*********************************ROUTINE TABLE***********************************************/

    public void insertRoutine(Routine routine) {

        Log.d("DatabaseAdaptor.insertRoutine", routine.toString());
        String setgroupid = "";
        for (SetGroup x: routine.getSetGroups()){
            setgroupid += String.valueOf( x.getId());
        }
        Log.d("DatabaseAdaptor.insertRoutine", setgroupid);
        Log.d("DatabaseAdaptor.insertRoutine", String.valueOf
                (insertRoutine(routine.getName(),
                        routine.getIsPublic(),
                        setgroupid, routine.getCreated_At(), routine.getUpdated_At())));
    }

    //Insert Routine
    public long insertRoutine(String routineName,
                              Boolean routineIsPublic,
                              String routineSetGroups,
                              String createDate,
                              String updateDate ) {

        //This class is used to store a set of values that the ContentResolver can process.
        ContentValues initialValues = new ContentValues();

        initialValues.put(ROUTINE_NAME, routineName);
        initialValues.put(ROUTINE_IS_PUBLIC, routineIsPublic);
        initialValues.put(ROUTINE_SET_GROUPS, routineSetGroups);
        initialValues.put(ROUTINE_CREATED_AT, createDate);
        initialValues.put(ROUTINE_UPDATED_AT, updateDate);

        //insert
        return database.insert(ROUTINE_TABLE, null, initialValues);
    }

    //Delete Routine
    public long deleteRoutine(String routineId) {

        //Delete row from routine table
        long v = database.delete(ROUTINE_TABLE, PK_ROUTINE_ID + "=" + routineId, null);

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
    public Cursor selectRoutineById(long id) throws android.database.SQLException {
        Cursor mCursor = null;
        Log.d(TAG, "id is " + id);
        mCursor = database.query(ROUTINE_TABLE, new String[] {PK_ROUTINE_ID,
                        ROUTINE_NAME, ROUTINE_IS_PUBLIC, ROUTINE_SET_GROUPS,
                        ROUTINE_CREATED_AT, ROUTINE_UPDATED_AT},

                PK_ROUTINE_ID + " = ?", new String[] {String.valueOf(id)}, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //Select All Routines
    public Cursor selectAllRoutines() {
        Cursor myCursor = database.query(ROUTINE_TABLE, new String[]{PK_ROUTINE_ID,
                        ROUTINE_NAME, ROUTINE_IS_PUBLIC, ROUTINE_SET_GROUPS,
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
    public ArrayList<String> getRoutinesAsStrings() {
        String[]columns = new String[]{ROUTINE_NAME};
        Cursor cursor = database.query(ROUTINE_TABLE, columns, null, null, null, null, null);
        //Results String Array
        ArrayList<String> result = new ArrayList<String>();
        //int id = cursor.getColumnIndex(PK_ROUTINE_ID);
        int name = cursor.getColumnIndex(ROUTINE_NAME);
        //int group = cursor.getColumnIndex(ROUTINE_SET_GROUPS);

        for(cursor.moveToFirst(); !cursor.isAfterLast();cursor.moveToNext()){
            result.add(cursor.getString(name));
        }

        return result;
    }

    /******************************WORKOUTSESSION TABLE********************************************/

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


    /**********************************************************************************************/

    public void insertWorkoutSession(WorkoutSession session) {
        for (WorkoutSetGroup setgroup: session.getSetGroups())
            insertSetGroup(setgroup.getSet_group());//todo check whether this works
        insertWorkoutSession(session.getUser_Id(), session.getName(), null, null, null);
    }

    //Insert Workout Session
    public long insertWorkoutSession(int userId,
                                     String sessionName,
                                     String setGroupId,
                                     String createdAt,
                                     String updatedAt) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(FK_USER_ID, userId);
        initialValues.put(SESSION_NAME, sessionName);
        initialValues.put(FK_WORKOUTSETGROUP_ID, setGroupId);
        initialValues.put(SESSION_CREATED_AT, createdAt);
        initialValues.put(SESSION_UPDATED_AT, updatedAt);

        //insert
        return database.insert(WORKOUTSESSION_TABLE, null, initialValues);
    }

    //Delete Workout Session
    public long deleteWorkoutSession(String workoutsessionid) {

        //Delete row
        long v = database.delete(WORKOUTSESSION_TABLE, PK_WORKOUTSESSION_ID
                + "=" + workoutsessionid, null);

        return v;
    }

    //Select All workout Sessions
    public Cursor selectAllWorkoutSessions() {
        Cursor myCursor = database.query(WORKOUTSESSION_TABLE, new String[]{PK_WORKOUTSESSION_ID,
                        FK_USER_ID, SESSION_NAME, FK_WORKOUTSETGROUP_ID, SESSION_CREATED_AT,
                        SESSION_UPDATED_AT},
                null, null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    /**********************************WORKOUT SETGROUP TABLE**************************************/

    //insert workoutsetgroup
    public long insertWorkoutSetGroup(  String setgroupid,
                                        String exerciseid,
                                        String workoutsessionid ) {

        ContentValues initialValues = new ContentValues();

        initialValues.put(FK_SETGROUP_ID, setgroupid);
        initialValues.put(FK_EXERCISE_ID, exerciseid);
        initialValues.put(FK_WORKOUTSESSION_ID, workoutsessionid);


        //insert
        return database.insert(WORKOUTSETGROUP_TABLE, null, initialValues);
    }
    /**********************************************************************************************/

    //Delete Workout Set Group
    public long deleteWorkoutSetGroup(String workoutsetgroupid) {

        //Delete row
        long v = database.delete(WORKOUTSETGROUP_TABLE, PK_WORKOUTSETGROUP_ID
                + "=" + workoutsetgroupid, null);

        return v;
    }

    //Select All Set Groups
    public Cursor selectAllWorkoutSetGroups() {
        Cursor myCursor = database.query(WORKOUTSETGROUP_TABLE, new String[]{PK_WORKOUTSETGROUP_ID,
                        FK_SETGROUP_ID, FK_WSG_EXERCISE_ID, FK_WORKOUTSETGROUP_ID},
                null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

    /**********************************SETGROUP TABLE**********************************************/

    public void insertSetGroup(SetGroup set_group) {
        insertSetGroup(
                set_group.getExercise().getId(),
                set_group.getNumberOfSets(),
                set_group.getRepsPerSet(),
                null, null);
    }

    /**********************************************************************************************/

    //Insert Set Group
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
        return database.insert(SETGROUP_TABLE, null, initialValues);
    }

    //Delete Set Groups
    public long deleteSetGroup(String setgroupid) {

        //Delete row
        long v = database.delete(SETGROUP_TABLE, PK_SETGROUP_ID
                + "=" + setgroupid, null);

        return v;
    }

    //Select All Set Groups
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

    /**
     * Efficiently Loads all Exercises from the remote Database
     * @param exercise
     */
    public void loadAllExercises(ArrayList<Exercise> exercise) {
        database.beginTransaction();
        for (Exercise i : exercise){
            ContentValues initialValues = new ContentValues();
            initialValues.put(EXERCISE_NAME, i.getName());
            initialValues.put(INCREASE_PER_SESSION, i.getIncrease_Per_Session());
            initialValues.put(EXERCISE_CREATED_AT, i.getCreated_At());
            initialValues.put(EXERCISE_UPDATED_AT, i.getUpdated_At());
            initialValues.put(EXERCISE_IS_PUBLIC, i.getIsPublic());
            initialValues.put(EXERCISE_USER_ID, i.getUser_Id());
            database.insertWithOnConflict
                    (EXERCISE_TABLE, null, initialValues, SQLiteDatabase.CONFLICT_IGNORE);
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    /**********************************************************************************************/

    /**************************************EXERCISE TABLE******************************************/

    public void insertExercise(ArrayList<Exercise> exercise) {
        for (Exercise x : exercise){
            insertExercise(exercise);
        }
    }

    public void insertExercise(Exercise exercise){
        insertExercise(
                exercise.getName(),
                String.valueOf(exercise.getIncrease_Per_Session()),
                exercise.getCreated_At(),
                exercise.getUpdated_At(),
                exercise.getIsPublic(),
                exercise.getUser_Id());
    }

    //Insert Exercise
    public long insertExercise(String exerciseName,
                               String increasePerSession,
                               String createdAt,
                               String updatedAt,
                               boolean is_Public,
                               int user_ID) {
        Log.d("DatabaseAdaptor.insertExercise - ", exerciseName);
        Log.d("DatabaseAdaptor.insertExercise - ", increasePerSession);
        ContentValues initialValues = new ContentValues();

        initialValues.put(EXERCISE_NAME, exerciseName);
        initialValues.put(INCREASE_PER_SESSION, increasePerSession);
        initialValues.put(EXERCISE_CREATED_AT, createdAt);
        initialValues.put(EXERCISE_UPDATED_AT, updatedAt);
        initialValues.put(EXERCISE_IS_PUBLIC, is_Public);
        initialValues.put(EXERCISE_USER_ID, user_ID);

        //insert
        return database.insert(EXERCISE_TABLE, null, initialValues);
    }

    //Delete Exercises
    public long deleteExercise(String exerciseid) {

        //Delete row
        long v = database.delete(EXERCISE_TABLE, PK_EXERCISE_ID
                + "=" + exerciseid, null);

        return v;
    }

    //Select All Exercises
    public Cursor selectAllExercises() {
        Cursor myCursor = database.query(SETGROUP_TABLE, new String[]{PK_EXERCISE_ID,
                        EXERCISE_NAME, INCREASE_PER_SESSION, EXERCISE_CREATED_AT,
                        EXERCISE_UPDATED_AT},
                null, null, null, null, null);

        if (myCursor != null) {
            myCursor.moveToFirst();
        }
        return myCursor;
    }

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
     *
     * @param name
     * @return
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

    public void updateDB() {
        Log.d("DatabaseAdaptor.UpdateDB - ", "Clearing data from Database");
        database.execSQL("DROP TABLE IF EXISTS " + ROUTINE_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + SESSION_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + WORKOUTSESSION_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + SETGROUP_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + WORKOUTSETGROUP_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + EXERCISE_TABLE);

        DatabaseHelper helper = new DatabaseHelper(context);
        helper.onCreate(database);
    }

    //********************************************************************************************//
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
            onCreate(database);
        }
    }
}
