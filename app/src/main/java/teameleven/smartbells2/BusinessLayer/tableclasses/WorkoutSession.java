package teameleven.smartbells2.businesslayer.tableclasses;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import teameleven.smartbells2.businesslayer.RESTCall;

/**
 * Workout Session. Tracks attributes related to the Workout Session Table.
 * Created by Andrew Rabb on 2015-10-23.
 */
@SuppressWarnings("unused")
public class WorkoutSession {

    /****************************************Attributes********************************************/
    @SuppressWarnings("SpellCheckingInspection")
    static final private String RESTID = "workout_sessions";
    /**
     * ArrayList of  WorkoutSetGroups
     */
    private ArrayList<WorkoutSetGroup> setGroups = new ArrayList<>();
    /**
     * Name of the Workout Session
     */
    private String name;
    /**
     * Creation Date of the Workout Session (set by API)
     */
    private String created_At;
    /**
     * Update Date of the Workout Session (set by API)
     */
    private String updated_At;
    /**
     * id of the Workout Session
     */
    private int id;
    /**
     * User ID of the Workout Session Creator (the user)
     */
    private int user_Id;

    /******************************Constructors****************************************************/

    /**
     * Default Constructor
     */
    public WorkoutSession() {}
    /**
     * Workout Session Constructor for JsonObjects
     * @param workoutSession - WorkoutSession Json Object
     */
    public WorkoutSession(JSONObject workoutSession) {
        try {
            if (workoutSession.has("workout_session")) {
                workoutSession = workoutSession.getJSONObject("workout_session");
            }
            id = workoutSession.getInt("id");
            user_Id = workoutSession.getInt("user_id");
            name = workoutSession.getString("name");
            created_At = workoutSession.getString("created_at");
            updated_At = workoutSession.getString("updated_at");

            if (workoutSession.has("workout_set_groups")) {
                JSONArray workoutSetGroups =
                        workoutSession.getJSONArray("workout_set_groups");
                WorkoutSetGroup sessionSetGroup;
                for (int index = 0; index < workoutSetGroups.length(); index++) {
                    sessionSetGroup = new WorkoutSetGroup(workoutSetGroups.getJSONObject(index));
                    setGroups.add(sessionSetGroup);
                }
            }
            }catch(JSONException e){
                e.printStackTrace();
            }
    }

    /**
     * Constructor for Name and ID of Workout Session
     * @param name - Name of Workout Session
     * @param id - Id of Workout Session
     */
    public WorkoutSession(String name, int id) {
        this.name = name;
        this.id = id;
    }

    /**************************					Base Methods               ************************/
    /**
     * returns the Name of the Workout Session
     * @return - workout Session Name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name of the Workout Session
     * @param name - Name of the Workout Session
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * returns the Creation date of the Object (from API)
     * @return - Creation Date (String)
     */
    public String getCreated_At() {
        return created_At;
    }

    /**
     * sets the creation date of the object (from API)
     * @param created_At - creation Date (String)
     */
    public void setCreated_At(String created_At) {
        this.created_At = created_At;
    }

    /**
     * returns the updated date of the Object (from API)
     * @return - update date (String)
     */
    public String getUpdated_At() {
        return updated_At;
    }

    /**
     * returns the updated at date (from API)
     * @param updated_At - Updated at (String)
     */
    public void setUpdated_At(String updated_At) {
        this.updated_At = updated_At;
    }

    /**
     * gets the Id of the WorkoutSession
     * @return - Workout Session Id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the Id of the Workout Session
     * @param id - Workout Session Id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the User Id tied to this Workout Session
     * @return User_ID
     */
    public int getUser_Id() {
        return user_Id;
    }

    /**
     * Sets the User Id
     * @param user_Id - User Id
     */
    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    /**
     * gets the Set Groups Array
     * @return the group of Set Groups
     */
    public ArrayList<WorkoutSetGroup> getSetGroups() {
        return setGroups;
    }

    /**
     * sets the Set Group Array
     * @param setGroups - Set Group Array
     */
    public void setSetGroups(ArrayList<WorkoutSetGroup> setGroups) {
        this.setGroups = setGroups;
    }

    /**
     * returns a String Representation of the Workout Session
     * @return Workout Session (JsonObject.toString(4))
     */
    @Override
    public String toString(){
        try {
            return this.createJSON().toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.name;
    }
    /****************************************** JSON Methods **************************************/

    /**
     * Creates a JSON Object from the current Workout session Object
     * capable of changing all attributes to JSON (skips unnecessary null values if present)
     */
    public JSONObject createJSON() {
        JSONArray workoutSetGroup = new JSONArray();
        JSONObject workoutSession = new JSONObject();
        JSONObject json = new JSONObject();
        try {
            if (getId() != 0) workoutSession.put("id", id);
            if (user_Id != 0) workoutSession.put("user_id", user_Id);
            workoutSession.put("name", this.name);
            if (created_At != null) workoutSession.put("created_at", created_At);
            if (updated_At != null) workoutSession.put("updated_at", updated_At);

            for (WorkoutSetGroup setGroup : setGroups){
                workoutSetGroup.put(setGroup.createJSON());
            }

            workoutSession.put("workout_set_groups_attributes", workoutSetGroup);
            json.put("workout_session", workoutSession);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
   }

    /**
     * Get all records of the WorkoutSession by User id
     * @param userIDForSession : User id
     * @return : List of WorkoutSession of a user
     */
    public static ArrayList<WorkoutSession> restGetAll(int userIDForSession) {
        try {
            //Log.d("Exercise.restGetAll - ", RESTID);
            AsyncTask result = new RESTCall().execute(RESTID + "?user_id=" + userIDForSession, "GET");
            //AsyncTask result = new RESTCall().execute(RESTID, "GET");
            JSONObject json = (JSONObject) result.get();

            return restGetWorkoutSessions(json, userIDForSession);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
   }

    /**
     * Get WorkoutSession by user id
     * @param json : JSONObject
     * @param userIDForSession : User id
     * @return List of WorkoutSession
     */
    private static ArrayList<WorkoutSession> restGetWorkoutSessions(JSONObject json, int userIDForSession) {
        ArrayList<WorkoutSession> workoutSessions = new ArrayList<>();

        try{
            JSONArray jsonArray = json.getJSONArray("workout_sessions");
            for (int index = 0 ; index < jsonArray.length(); index ++){
                JSONObject json2 = (jsonArray.getJSONObject(index));

                if (json2.getInt("user_id") == userIDForSession){
                    workoutSessions.add(new WorkoutSession(json2));
                }
            }
            return workoutSessions;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}