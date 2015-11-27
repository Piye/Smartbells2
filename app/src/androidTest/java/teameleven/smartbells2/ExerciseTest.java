package teameleven.smartbells2;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;

import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.businesslayer.tableclasses.Exercise;

/**
 * Created by Andrew Rabb on 2015-11-26.
 */
public class ExerciseTest extends TestCase{

    ArrayList<Exercise> exercises;
    JSONObject jsonResult;
    DatabaseAdapter database;

    public void testExercise() throws Exception {
        for (Exercise x : exercises){
            assertNotNull(x);
            //testJSON(x);
            testDatabase(x);

        }
    }

    /**
     * tests Json for each exercise - pulls into program,
     * then tests it against the response created by the server.
     *
     * server bug gives us a null user_id at times, so method has been altered to account for that
     * server bug gives us a null is_public value at times. method has been altered to account
     * @param x
     * @throws JSONException
     */
    public void testJSON(Exercise x) throws JSONException {

        jsonResult = Exercise.restGetExercise(x.getId()).getJSONObject("exercise");
        JSONObject jsonTemp = x.createJSON().getJSONObject("exercise");

        assertTrue (jsonTemp.toString(), jsonTemp.remove("is_public") != null);
        assertTrue(jsonResult.toString(), jsonResult.remove("is_public") != null);

        if (jsonTemp.isNull("user_id")){
            assertTrue(jsonTemp.toString(), jsonResult.remove("user_id") != null);
        }
        if (jsonResult.isNull("user_id")){
            jsonResult.remove("user_id");
        }
        assertTrue("\ncreate Json\n" + jsonTemp + "\n jsonResult \n" +
                        jsonResult + "\n",
                jsonTemp.toString().equals(jsonResult.toString()));
    }

    public void testDatabase(Exercise x){
        assertNotNull(database);
        ArrayList<Exercise> exercises = database.getExercises();
        for (Exercise z : exercises){
            assertTrue (z.getId() == x.getId()); if (z == x);
            try {
                assertTrue(z.createJSON().toString().equals(database.getExercise(x.getId())));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setUp() throws Exception {
        super.setUp();
        exercises = Exercise.restGetAll();

        assertNotNull(exercises);
        database = new DatabaseAdapterTest().getDatabase();
        assertNotNull(database);

    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
}
