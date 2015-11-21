package teameleven.smartbells2.BusinessLayer.tableclasses;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import teameleven.smartbells2.BusinessLayer.RESTCall;
import teameleven.smartbells2.BusinessLayer.localdatabase.DatabaseAdapter;

/**
 * Base Exercise Class. Describes a Single Exercise, including the number of reps and sets in a basic performance of the exercise.
 * portion of the SmartBells Project, created by Team Eleven.
 * Edits
 * Updated to match API fields AR Oct 21, 2015
 * <p/>
 * created : October 4th, 2015
 *
 * @author Andrew Rabb
 */
public class Exercise {
    /************************************** Attributes*********************************************/
    static final private String RestID = "exercises";
    /**
     * specific ID of the Exercise.
     */
    private int id;
    /**
     * specific Name of the Exercise
     */
    private String name;
    /**
     * The increase in resistance per session.
     */
    private int increase_Per_Session;
    /**
     * Date-stamp of the Creation Time of the Object
     */
    private String created_At;
    /**
     * Date-stamp of the last time that the object was edited.
     */
    private String updated_At;
    private boolean is_Public = true;
    /**
     * User ID of the creator of the exercise.
     */
    private int user_Id;

    /**************************************Constructors********************************************/
    /**
     * Constructor, sets name of object
     *
     * @param name : name of the exercise to be created.
     */
    public Exercise(String name) {
        this.setName(name);
    }

    /**
     * Default constructor
     */
    public Exercise() {
    }

    public Exercise(JSONObject exercise) {
        try {
            if (exercise.has("exercise")) exercise = exercise.getJSONObject("exercise");

            Log.d("Exercise.JSONConstructor - ", exercise.toString(4));
            if (exercise.has("id")) id = exercise.getInt("id");
            name = (String) exercise.getString("name");
            increase_Per_Session = exercise.getInt("increase_per_session");
            created_At = exercise.getString("created_at");
            updated_At = exercise.getString("updated_at");
            is_Public = exercise.getBoolean("is_public");
            if (!exercise.isNull("user_id")) {
                user_Id = exercise.getInt("user_id");
                user_Id = 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public Exercise(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /******************************** Base Methods     ********************************************/
    /**
     *
     * @param json
     * @return
     */
    private static ArrayList<Exercise> getAllExercise(JSONObject json) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        Exercise exercise;
        try {
            JSONArray jsonArray = json.getJSONArray("exercises");
            Log.d("Exercise.getAllExercise - ", jsonArray.toString(4));
            for (int index = 0; index < jsonArray.length(); index++) {
                exercise = new Exercise((JSONObject) jsonArray.get(index));
                exercises.add(exercise);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return exercises;
    }

    /**
     * @param id
     * @return
     */
    public static String restGetExercise(int id) {
        try {
            String temp = RestID + "/" + String.valueOf(id);
            System.out.println(temp);
            AsyncTask test = new RESTCall().execute(temp, "GET");
            return (String) test.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "Rest Call Failed";
    }

    /**
     *
     * @return
     */
    public static ArrayList<Exercise> restGetAll() {
        try {
            String temp = RestID;
            System.out.println(temp);
            AsyncTask result = new RESTCall().execute(temp, "GET");
            JSONObject json = (JSONObject) result.get();

            Log.d("Exercise.RestGetAll - ", String.valueOf(json.optInt("id")));
            ArrayList<Exercise> exercises = getAllExercise(json);
            return exercises;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * retrieves the name of the exercise
     *
     * @return Exercise Name
     */
    public String getName() {
        return name;
    }

    /**
     * changes or creates the Name of the Exercise
     * User Defined
     *
     * @param name : name to be changed to
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the ID of the current Exercise
     *
     * @return id of this exercise
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the current Exercise
     * System Defined
     *
     * @param id ID of the object to be set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the Increase per session
     *
     * @return integer, increase per session
     */
    public int getIncrease_Per_Session() {
        return increase_Per_Session;
    }

    /**
     * sets the Increase Per Session
     * User Defined
     *
     * @param increase_Per_Session sets the increase per session of this exercise
     */
    public void setIncrease_Per_Session(int increase_Per_Session) {
        this.increase_Per_Session = increase_Per_Session;
    }

    /**
     * User who initially created the object
     *
     * @return id of the user that created this object
     */
    public int getUser_Id() {
        return user_Id;
    }

    /**
     * Sets the user who created the Object
     * System Defined
     *
     * @param user_Id user id of this exercises creator.
     */
    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    /**
     * returns the most recent time that the exercise in question was updated
     *
     * @return date of last update
     */
    public String getUpdated_At() {
        return updated_At;
    }

    /**
     * sets the last update to the current system clock
     * todo see setCreated_At
     * System Defined
     */
    public void setUpdated_At(String updated_at) {
        updated_At = updated_at;
    }

    /**
     * returns the creation date of the Exercise
     *
     * @return exercise creation date
     */
    public String getCreated_At() {
        return created_At;
    }

    /**
     * sets the creation date to the current system clock
     * System Defined
     */
    public void setCreated_At(String created_at) {
        this.created_At = created_at;
    }

    /**
     * @return
     */
    public boolean getIsPublic() {
        return is_Public;
    }

    /**
     * @param is_Public
     */
    public void setIs_Public(boolean is_Public) {
        this.is_Public = is_Public;
    }

    /************************************** REST Methods*******************************************/

    /* (non-Javadoc)
     * returns the Name of the Exercise
     * @see java.lang.Object#toString()
     */
    public String toString() {
        try {
            return (this.createJSON().toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.name;
    }

    /**
     * Creates a JSON object from the parameters required to create a new object in API
     * missing some fields
     *
     * @return - JSON object
     */
    public JSONObject createJSON() {
        JSONObject json = new JSONObject();
        JSONObject exercise = new JSONObject();
        try {
            if (getId() != 0) json.put("id", getId());
            json.put("name", name);
            json.put("increase_per_session", increase_Per_Session);
            if (created_At != null) json.put("created_at", created_At);
            if (updated_At != null) json.put("updated_at", updated_At);
            json.put("is_public", is_Public);
            if (user_Id != 0) json.put("user_id", id);
            exercise.put("exercise", json);
            return exercise;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Accesses the Database and the RESTCall class to obtain an object from the Server, and save it into the database
     *
     * @param database
     */
    public void restPutExercise(DatabaseAdapter database) {
        Log.d("Exercise.restPutExercise", this.createJSON().toString());
        AsyncTask test = new RESTCall().execute(RestID, "POST", createJSON().toString(), database.getTokenAsString());
        //send to database here also
        Exercise exercise = null;

        try {
            exercise = new Exercise(new JSONObject(test.get().toString()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("database load - ", exercise.toString());
        database.insertExercise(exercise);
    }

    /**
     * @param database
     */
    public void restPOSTExercise(DatabaseAdapter database) {
        Log.d("Exercise.restPutExercise", this.createJSON().toString());
        AsyncTask test = new RESTCall().execute(RestID, "POST", createJSON().toString(), database.getTokenAsString());
        //send to database here also
        Exercise exercise = null;
        try {
            exercise = new Exercise(new JSONObject(test.get().toString()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("database load - ", this.toString());
        database.insertExercise(exercise);
    }


}
