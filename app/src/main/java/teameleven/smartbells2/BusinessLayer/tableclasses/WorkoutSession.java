package teameleven.smartbells2.BusinessLayer.tableclasses;
//// TODO: 08/11/2015 Find way to retrieve Exercise ID. Add error handling for inputs to avoid stateExceptions

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import teameleven.smartbells2.BusinessLayer.RESTCall;

/**
 * Created by Andrew Rabb on 2015-10-23.
 */
public class WorkoutSession {

    /****************************************Attributes********************************************/
    static final private String RestID = "workout_sessions";
    /**
     *
     */
    private ArrayList<WorkoutSetGroup> setGroups = new ArrayList<>();
    /**
     *
     */
    private String name;
    /**
     *
     */
    private String created_At;
    /**
     *
     */
    private String updated_At;
    /**
     *
     */
    private int id;
    /**
     *
     */
    private int user_Id;

    /******************************Constructors****************************************************/

    /**
     * Default Constructor
     */
    public WorkoutSession() {}
    /**
     *
     * @param workoutSession
     */
    public WorkoutSession(JSONObject workoutSession) {
        try {
            workoutSession = workoutSession.getJSONObject("workout_session");
            name = (String) workoutSession.get("name");
            created_At = workoutSession.getString("created_at");
            updated_At = workoutSession.getString("updated_at");

            JSONObject workoutSetGroups =
                    workoutSession.getJSONArray("workout_set_groups").getJSONObject(0);

            //for (int index = 0; index < workoutsetgroups.length(); index++){}//todo will be necessary sprint 3

            id = (int) workoutSession.get("id");
            user_Id = (int) workoutSession.get("user_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @param name
     * @param sets
     * @param reps
     * @param exercise_id
     */
    public WorkoutSession(String name, int sets, int reps, int exercise_id) {
        this.name = name;
        WorkoutSetGroup workoutSetGroup = new WorkoutSetGroup();
        workoutSetGroup.setExerciseId(exercise_id);
        workoutSetGroup.getSet_group().setNumberOfSets(sets);
        workoutSetGroup.getSet_group().setRepsPerSet(reps);
        this.getSetGroups().add(workoutSetGroup);
    }

    /**************************					Base Methods               ************************/
    /**
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public String getCreated_At() {
        return created_At;
    }

    /**
     * @param created_At
     */
    public void setCreated_At(String created_At) {
        this.created_At = created_At;
    }

    /**
     * @return
     */
    public String getUpdated_At() {
        return updated_At;
    }

    /**
     * @param updated_At
     */
    public void setUpdated_At(String updated_At) {
        this.updated_At = updated_At;
    }

    /**
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return
     */
    public int getUser_Id() {
        return user_Id;
    }

    /**
     * @param user_Id
     */
    public void setUser_Id(int user_Id) {
        this.user_Id = user_Id;
    }

    /**
     *
     * @return
     */
    public ArrayList<WorkoutSetGroup> getSetGroups() {
        return setGroups;
    }

    /**
     *
     * @param setGroups
     */
    public void setSetGroups(ArrayList<WorkoutSetGroup> setGroups) {
        this.setGroups = setGroups;
    }

    /****************************************** JSON Methods **************************************/

    /**
     * create the workout_set_groups attribute and add to JSONArray object
     * currently saves one workout_set_group to the workoutsession
     * @param exerciseId
     * @param numberOfSets
     * @param repsPerSet
     * @return
     */
    private JSONArray jsonWorkoutSetGroupAttr(int exerciseId, int numberOfSets, int repsPerSet) {
        JSONObject setGroupsAttr = new JSONObject();
        JSONArray sessionArray = new JSONArray();
        //todo need to find way to make this an arraylist to add multiple JSON objects for multiple set groups
        //todo will need to remove the passed in parameters and use getters instead. populate with loop.
        //ArrayList<String> values = new ArrayList<>();
        try {
            setGroupsAttr.put("exercise_id", exerciseId);
            setGroupsAttr.put("number_of_sets", numberOfSets);
            setGroupsAttr.put("reps_per_set", repsPerSet);

            /*for (int index = 0; index < this.setGroups.size(); index++) {
                sessionArray.put(this.setGroups.get(index).jsonWorkoutSetGroup());
            }*/

            return sessionArray.put(setGroupsAttr);
            //values.add(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * create JSON parameters to pass to the post method
     * @param name
     * @param exerciseId
     * @param numOfSets
     * @param repsPerSet
     * @return
     */
    private String jsonWorkoutSession(String name, int exerciseId, int numOfSets, int repsPerSet) {
        String result = "";

        try {
            JSONObject workoutSession = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("name", name);
            params.put("workout_set_groups_attributes", jsonWorkoutSetGroupAttr(exerciseId, numOfSets, repsPerSet));
            workoutSession.put("workout_session", params);
            result = workoutSession.toString();
            Log.d("--- Debugging ---", result);
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param name
     * @param exerciseId
     * @param sets
     * @param reps
     * @param token
     */
    public void restCreateCustomSession(String name, int exerciseId, int sets, int reps, String token) {

        String result = "";
        String temp = RestID;
        String temp1 = jsonWorkoutSession(name, exerciseId, sets, reps);
        AsyncTask test = new RESTCall().execute(temp, "POST", temp1, token);
        //todo connect with SQLite
        WorkoutSession session = null;
        try {

            session = new WorkoutSession(new JSONObject(test.get().toString()));

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    /**
     *
     * @return
     */
    public String restGetAllWorkoutSession() {

        try {
            String temp = RestID;
            //System.out.println(temp);
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
     * @param id
     * @return
     */
    public String restGetSpecificWorkoutSession(int id) {
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

    public void restUpdateWorkoutSession() {

    }

    public void restDeleteWorkoutSession() {

    }
}
