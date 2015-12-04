package teameleven.smartbells2;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Exercise;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;
import teameleven.smartbells2.businesslayer.tableclasses.SetGroup;
import teameleven.smartbells2.businesslayer.tableclasses.WorkoutSession;
import teameleven.smartbells2.businesslayer.tableclasses.WorkoutSetGroup;

/**
 * Created by Andrew Rabb on 2015-11-27.
 * based upon http://stackoverflow.com/questions/3096378/testing-database-on-android-providerTestCase2-or-renamingDelegatingContext
 */
public class DatabaseAdapterTest extends AndroidTestCase {

    private static final String TEST_FILE_PREFIX = "test_";
    private DatabaseAdapter database;
    Random r = new Random();

    /**
     * setup for the test
     * @throws Exception exception
     */
    @Override
    public void setUp() throws Exception {
        super.setUp();
        database = getDatabase();
        Log.d("database creation - ", " new database created");
    }
    /**
     * teardown for the test
     */
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        database.closeLocalDatabase();
    }

    /**
     * Tests for Exercises
     * @throws Exception exception
     */
    public void testExercise() throws Exception {
        this.initialDatabaseSync(0);
        ArrayList<Exercise> exercises = database.selectAllExercises();
        //Log.d("exercise.get", "testing");
        assertNotNull(database);
        assertTrue(exercises.size() > 0);

        for (Exercise retrievedExercises : exercises) {//test Get all from Database and REST
            //testing select
            Exercise singleRetrievedExercise = new Exercise(database.getExercise(retrievedExercises.getId()));
            //only necessary variables are stored in getExercises, so it isn't a full Exercise.
            //only check is performed upon getName()
            assertTrue(retrievedExercises.getName().equals(singleRetrievedExercise.getName()));

            //testing insert
            singleRetrievedExercise.setId(-1);
            long result = database.insertExercise(singleRetrievedExercise, true);

            assertTrue(result > -1);
            Exercise createdExercise = new Exercise(database.getExercise(result));
            assertTrue(singleRetrievedExercise.getName().equals(createdExercise.getName()));
            assertTrue(database.readUpdateRecord().size() == 1);

            //testing update
            createdExercise.setName("testExercise!");
            createdExercise.setId(-1);
            result = database.updateExercise(result, createdExercise, true);
            createdExercise = new Exercise(database.getExercise(result));
            assertTrue(database.readUpdateRecord().size() == 1);


            //testing delete
            database.deleteExercise(createdExercise.getId(), true);
            try {
                assertTrue(database.getExercise(createdExercise.getId()) == null);
                assertTrue(false);//above statement should throw exception
            } catch (SQLException ex) {
                assertTrue(true);
            }
            assertTrue(database.readUpdateRecord().size() == 0);
            //this should be zero, as all changed records are removed
        }
        deleteObject(r.nextInt(exercises.size()), 0);
    }

    /**
     * runs SetGroup test
     * @throws Exception exception
     */
    public void testSetGroups() throws Exception {
        this.initialDatabaseSync(1);//retrieves routine list - should populate SetGroups
        ArrayList<SetGroup> setGroups = database.selectAllSetGroups();
        assertNotNull(database);
        assertTrue(setGroups.size() > 0);

        for (SetGroup retrievedSetGroup : setGroups) {//test Get and GetAll from Database and REST
            //testing select
            SetGroup singleRetrievedSetGroup = new SetGroup(database.getSetGroup(retrievedSetGroup.getId()));
            assertTrue(retrievedSetGroup.getRoutineId() == singleRetrievedSetGroup.getRoutineId());

            //testing insert
            singleRetrievedSetGroup.setId(-1);
            long result = database.insertSetGroup(singleRetrievedSetGroup, true);
            assertTrue(result > -1);

            SetGroup createdSetGroup = new SetGroup(database.getSetGroup(result));

            assertTrue(singleRetrievedSetGroup.getRoutineId() == createdSetGroup.getRoutineId());
            assertTrue(database.readUpdateRecord().size() == 1);
            //Log.d("update record for workouts contains values - ", viewIntArray(database.readUpdateRecord().get(0)));

            //testing update
            createdSetGroup.setRoutineId(-1);
            createdSetGroup.setId(-1);
            result = database.updateSetGroup(result, createdSetGroup, true);
            createdSetGroup = new SetGroup(database.getSetGroup(result));
            assertTrue("update record contains " + database.readUpdateRecord().size() + " record", database.readUpdateRecord().size() == 1);

            //testing delete
            database.deleteSetGroup(createdSetGroup.getId(), true);
            try {
                assertTrue(database.getSetGroup(createdSetGroup.getId()) == null);
                assertTrue(false);//above statement should throw exception
            } catch (SQLException ex) {
                assertTrue(true);
            }
            assertTrue(database.readUpdateRecord().size() == 0);
            //this should be zero, as all changed records are removed
        }
        deleteObject(r.nextInt(setGroups.size()), 1);
    }
    /**
     * runs Routine Test
     * @throws Exception exception
     */
    public void testRoutine() throws Exception {
        this.initialDatabaseSync(1);
        ArrayList<Routine> routines = database.selectAllRoutines();
        assertNotNull(database);
        assertTrue(routines.size() > 0);
        for (Routine retrievedRoutines : routines) {//test Get all from Database and REST
            //testing select
            Routine singleRetrievedRoutine = new Routine(database.getRoutine(retrievedRoutines.getRoutineId()));
            assertTrue(retrievedRoutines.getName().equals(singleRetrievedRoutine.getName()));

            //testing insert
            singleRetrievedRoutine.setRoutineId(-1);
            long result = database.insertRoutine(singleRetrievedRoutine, true);

            assertTrue(result > -1);
            Routine createdRoutine = new Routine(database.getRoutine(result));
            assertTrue(singleRetrievedRoutine.getName().equals(createdRoutine.getName()));
            assertTrue(database.readUpdateRecord().size() == 1);

            //testing update
            createdRoutine.setName("testRoutine");
            createdRoutine.setRoutineId(-1);
            result = database.updateRoutine(result, createdRoutine, true);
            createdRoutine = new Routine(database.getRoutine(result));
            assertTrue("update record contains > 1 record", database.readUpdateRecord().size() == 1);


            //testing delete
            database.deleteRoutine(createdRoutine.getRoutineId(), true);
            try {
                assertTrue(database.getRoutine(createdRoutine.getRoutineId()) == null);
                assertTrue(false);//above statement should throw exception
            } catch (SQLException ex) {
                assertTrue(true);
            }
            assertTrue(database.readUpdateRecord().size() == 0);
            //this should be zero, as all changed records are removed
        }
        deleteObject(r.nextInt(routines.size()), 2);
    }

    /**
     * runs WorkoutSession test
     * @throws Exception exception
     */
    public void testWorkoutSession() throws Exception {
        this.initialDatabaseSync(2);
        ArrayList<WorkoutSession> sessions = database.selectAllWorkoutSessions();
        assertNotNull(database);
        assertTrue(sessions.size() > 0);
        for (WorkoutSession retrievedSession : sessions) {//test Get all from Database and REST
            //testing select
            WorkoutSession singleRetrievedSession =
                    new WorkoutSession(database.getWorkoutSession(retrievedSession.getId()));
            assertTrue(retrievedSession.getName().equals(singleRetrievedSession.getName()));

            //testing insert
            singleRetrievedSession.setId(-1);
            long result = database.insertWorkoutSession(singleRetrievedSession, true);
            assertTrue(result > -1);

            WorkoutSession createdSession = new WorkoutSession(database.getWorkoutSession(result));
            assertTrue(singleRetrievedSession.getName().equals(createdSession.getName()));
            assertTrue(database.readUpdateRecord().size() == 1);
            //Log.d("update record for workouts contains values - ",
            // viewIntArray(database.readUpdateRecord().get(0)));

            //testing update
            createdSession.setName("testSession");
            createdSession.setId(-1);
            result = database.updateWorkoutSession(result, createdSession, true);
            createdSession = new WorkoutSession(database.getWorkoutSession(result));
            assertTrue("update record contains " + database.readUpdateRecord().size()
                        + " records", database.readUpdateRecord().size() == 1);

            //testing delete
            database.deleteWorkoutSession(createdSession.getId(), true);
            try {
                assertTrue(database.getWorkoutSession(createdSession.getId()) == null);
                assertTrue(false);//above statement should throw exception
            } catch (SQLException ex) {
                assertTrue(true);
            }
            assertTrue(database.readUpdateRecord().size() == 0);
            //this should be zero, as all changed records are removed
        }
        deleteObject(r.nextInt(sessions.size()), 3);
    }

    /**
     * Workout Set Group test method. thoroughly tests both the DB and the workout set group class
     * @throws Exception exception
     */
    public void testWorkoutSetGroups() throws Exception {
        this.initialDatabaseSync(2);//retrieves Workout Session - should populate WorkoutSetGroups
        ArrayList<WorkoutSetGroup> workoutSetGroups = database.selectAllWorkoutSetGroups();
        assertNotNull(database);
        assertTrue(workoutSetGroups.size() > 0);

        for (WorkoutSetGroup retrievedWSG : workoutSetGroups) {//test Get and GetAll from Database and REST
            //testing select
            //Log.d("retrieved Workout Set Group  = ", retrievedWSG.toString());
            WorkoutSetGroup singleRetrievedWSG =
                    new WorkoutSetGroup(database.getWorkoutSetGroup
                            (retrievedWSG.getSetGroupID()));
            assertTrue(retrievedWSG.getSetGroupID() == singleRetrievedWSG.getSetGroupID());

            //testing insert
            singleRetrievedWSG.setSetGroupID(-1);

            //Log.d("single retrieved set group before insert= ", singleRetrievedWSG.toString());
            long result = database.insertWorkoutSetGroup(singleRetrievedWSG, true);
            assertTrue(result > -1);

            //Log.d("result = ", String.valueOf(result));
            WorkoutSetGroup createdWSG = new WorkoutSetGroup(database.getWorkoutSetGroup(result));
            //Log.d("created set group = ", createdWSG.toString());

            assertTrue(singleRetrievedWSG.getExerciseId() ==
                    createdWSG.getExerciseId());
            assertTrue(database.readUpdateRecord().size() == 1);
            //Log.d("update record for workouts contains values - ",
                    //viewIntArray(database.readUpdateRecord().get(0)));

            //testing update
            createdWSG.setExerciseId(5);
            createdWSG.setSetGroupID(-1);
            result = database.updateWorkoutSetGroup(result, createdWSG, true);
            createdWSG = new WorkoutSetGroup(database.getWorkoutSetGroup(result));
//            Log.d("update record for workouts contains values - ",
//                    viewIntArray(database.readUpdateRecord().get(0)));
            assertTrue("update record contains " + database.readUpdateRecord().size()
                    + " record", database.readUpdateRecord().size() == 1);

            //testing delete
            database.deleteWorkoutSetGroup(createdWSG.getSetGroupID(), true);
            try {
                assertTrue(database.getWorkoutSetGroup(createdWSG.getSetGroupID()) == null);
                assertTrue(false);//above statement should throw exception
            } catch (SQLException ex) {
                assertTrue(true);
            }
            assertTrue(database.readUpdateRecord().size() == 0);
            //this should be zero, as all changed records are removed
        }
        deleteObject(r.nextInt(workoutSetGroups.size()), 4);
    }
//
//    /**
//     * for debugging, turns int[] into readable string
//     *
//     * @param y int[]
//     * @return string of int[] input
//     */
//    private String viewIntArray(int[] y) {
//        String result = "";
//
//        for (int num : y) {
//            result += " " + num;
//        }
//        return result;
//    }

    /**
     * opens a Renaming delegating context to test the Database
     * @return database connection
     * @throws SQLException - if database cannot be opened
     */
    public DatabaseAdapter getDatabase() throws SQLException {
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext().getApplicationContext(), TEST_FILE_PREFIX);
        if (database == null) {
            database = new DatabaseAdapter(context);
            database.openLocalDatabase();
        }
        assertNotNull(database);
        return database;
    }

    /**
     * Syncs the Database to the current Remote Database
     * @param i the table to be synchronized
     */
    private void initialDatabaseSync(int i) {
        database.updateDB();
        long x = System.currentTimeMillis();
        long y;
        int user_id = 1;
        assertTrue("only supports values 0 <= x <= 2", 0 <= i && i <= 2);
        if (i == 0) {
            ArrayList<Exercise> exercise = Exercise.restGetAll();
            assert exercise != null;
            Log.d("LoginActivity.initialDatabaseSync - Exercise row count = ", String.valueOf(exercise.size()));
            y = (System.currentTimeMillis() - x);
            Log.d("time taken = ", String.format("%s milliseconds", y));
            database.loadAllExercises(exercise);

        } else if (i == 1) {
            ArrayList<Routine> routines = Routine.restGetAll(user_id);
            assert routines != null;
            Log.d("LoginActivity.initialDatabaseSync - Routine row count = ", String.valueOf(routines.size()));
            y = (System.currentTimeMillis() - x);
            Log.d("time taken = ", String.format("%s milliseconds", y));
            database.loadAllRoutines(routines);
        } else if (i == 2) {
            ArrayList<WorkoutSession> workoutSessions = WorkoutSession.
                    restGetAll(user_id);
            assert workoutSessions != null;
            Log.d("LoginActivity.initialDatabaseSync - WorkoutSession row count = ", String.valueOf(workoutSessions.size()));
            y = (System.currentTimeMillis() - x);
            Log.d("time taken = ", String.format("%s milliseconds", y));
            database.loadAllWorkoutSessions(workoutSessions);
        }
    }

    /**
     * delete Object
     * @param id_num - id of the row to be changed (randomly selected)
     * @param table_id - id of the table in question
     */
    private void deleteObject(int id_num, int table_id){
        try {
            Log.d("id num - table id ", String.valueOf(id_num) + " - " + table_id);
            database.deleteObject(id_num, table_id, true);
            assertTrue(database.readUpdateRecord().size() == 1);
            database.clearUpdateTable();
            assertTrue(database.readUpdateRecord().size() == 0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
