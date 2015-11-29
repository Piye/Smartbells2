package teameleven.smartbells2;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.security.Key;
import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Exercise;
import teameleven.smartbells2.businesslayer.tableclasses.Routine;
import teameleven.smartbells2.businesslayer.tableclasses.SetGroup;
import teameleven.smartbells2.businesslayer.tableclasses.WorkoutSession;
import teameleven.smartbells2.businesslayer.tableclasses.WorkoutSetGroup;

/**
 * Created by Andrew Rabb on 2015-11-27.
 * based upon http://stackoverflow.com/questions/3096378/testing-database-on-android-providertestcase2-or-renamingdelegatingcontext
 */
public class DatabaseAdapterTest extends AndroidTestCase{

    private static final String TEST_FILE_PREFIX = "test_";
    private DatabaseAdapter database;


    @Override
    public void setUp() throws Exception {
        super.setUp();
        database = getDatabase();
        Log.d("database created - ", " you created a database");
        database.clearUpdateTable();
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        database.closeLocalDatabase();
    }

    /**
     *
     * @throws Exception
     */
    public void testExercise() throws Exception {

            ArrayList<Exercise> exercises = database.getExercises();
            Log.d("exercise.get", "testing");
            assertNotNull(database);
            assertTrue(exercises.size() > 0);

            for (Exercise retrievedExercises : exercises) {//test Get and Getall from Database and REST
                //testing select
                Exercise singleRetrievedExercise = new Exercise(database.getExercise(retrievedExercises.getId()));
                //only necessary variables are stored in getExercises, so it isn't a full Exercise.
                //only check is performed upon getName()
                assertTrue(retrievedExercises.getName().equals(singleRetrievedExercise.getName()));

                //testing insert
                singleRetrievedExercise.setId(-1);
                long result = insertIntoDatabase(singleRetrievedExercise);

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
    }
    public void testRoutine() throws Exception{

            ArrayList<Routine> routines = database.selectAllRoutines();
            assertNotNull(database);
            assertTrue(routines.size() > 0);

            for (Routine retrievedRoutines : routines) {//test Get and Getall from Database and REST
                //testing select
                Routine singleRetrievedRoutine = new Routine(database.getRoutine(retrievedRoutines.getRoutineId()));
                assertTrue(retrievedRoutines.getName().equals(singleRetrievedRoutine.getName()));

                //testing insert
                singleRetrievedRoutine.setRoutineId(-1);
                long result = insertIntoDatabase(singleRetrievedRoutine);

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
    }

    public void testWorkoutSession() throws Exception{
        ArrayList<WorkoutSession> sessions = database.selectAllWorkoutSessions();
        assertNotNull(database);
        assertTrue(sessions.size() > 0);

        for (WorkoutSession retrievedSession : sessions) {//test Get and Getall from Database and REST
            //testing select
            WorkoutSession singleRetrievedSession = new WorkoutSession(database.getWorkoutSession(retrievedSession.getId()));
            assertTrue(retrievedSession.getName().equals(singleRetrievedSession.getName()));

            //testing insert
            singleRetrievedSession.setId(-1);
            long result = insertIntoDatabase(singleRetrievedSession);
            Log.d("result is ", String.valueOf(result));
            assertTrue(result > -1);

            WorkoutSession createdSession = new WorkoutSession(database.getWorkoutSession(result));
            Log.d("created session - ", createdSession.toString());
            assertTrue(singleRetrievedSession.getName().equals(createdSession.getName()));
            assertTrue(database.readUpdateRecord().size() == 1);
            //Log.d("update record for workouts contains values - ", x(database.readUpdateRecord().get(0)));

            //testing update
            createdSession.setName("testSession");
            createdSession.setId(-1);
            Log.d("result is ", String.valueOf(result));
            result = database.updateWorkoutSession(result, createdSession, true);
            createdSession = new WorkoutSession(database.getWorkoutSession(result));

            Log.d("update record for workouts contains values 1 - ", x(database.readUpdateRecord().get(0)));
            Log.d("update record for workouts contains values 2 - ", x(database.readUpdateRecord().get(1)));



            assertTrue("update record contains " + database.readUpdateRecord().size() + " record", database.readUpdateRecord().size() == 1);



            //testing delete
            database.deleteWorkoutSession(createdSession.getId(), true);
            try{
                assertTrue(database.getWorkoutSession(createdSession.getId()) == null);
                assertTrue(false);//above statement should throw exception
            }catch(SQLException ex){
                assertTrue(true);
            }
            assertTrue(database.readUpdateRecord().size() == 0);
            //this should be zero, as all changed records are removed
        }
    }

    /**
     * for debugging, turns int[] into readable string
     * @param y
     * @return
     */
    private String x(int[] y){
        String result = "";

        for(int num : y){
            result += " " + num;
        }


        return result;
    }


    private long insertIntoDatabase(Object object){
        long result = -1;
        switch (object.getClass().getSimpleName()){
            case("Exercise"):
                result = database.insertExercise((Exercise) object, true);
                break;
            case("SetGroup"):
                result = database.insertSetGroup((SetGroup) object, true);
                break;
            case("Routine"):
                result = database.insertRoutine((Routine) object, true);
                break;
            case("WorkoutSetGroup"):
                result = database.insertWorkoutSetGroup((WorkoutSetGroup) object, true);
                break;
            case("WorkoutSession"):
                result = database.insertWorkoutSession((WorkoutSession) object, true);
        }
        return result;
    }





    public DatabaseAdapter getDatabase() throws SQLException {
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext().getApplicationContext(), TEST_FILE_PREFIX);

        if (database == null){
            database = new DatabaseAdapter(context);
            database.openLocalDatabase();
            this.initialDatabaseSync();
        }



        //database.updateDB();
        assertNotNull(database);
        return database;
    }
    /**
     *
     */
    private void initialDatabaseSync() {
        database.updateDB();
        long x = System.currentTimeMillis();
        long y;
        int user_id = 1;

        ArrayList<Exercise> exercise = Exercise.restGetAll();
        Log.d("LoginActivity.initialDatabaseSync - Exercise row count = ", String.valueOf(exercise.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        database.loadAllExercises(exercise);


        ArrayList<Routine> routines = Routine.restGetAll(user_id);
        Log.d("LoginActivity.initialDatabaseSync - Routine row count = ", String.valueOf(routines.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        database.loadAllRoutines(routines);

        ArrayList<WorkoutSession> workoutSessions = WorkoutSession.
                restGetAll(user_id);
        Log.d("LoginActivity.initialDatabaseSync - WorkoutSession row count = ", String.valueOf(workoutSessions.size()));
        y = (System.currentTimeMillis() - x);
        Log.d("time taken = ", String.format("%s milliseconds", y));
        database.loadAllWorkoutSessions(workoutSessions);

    }
}
