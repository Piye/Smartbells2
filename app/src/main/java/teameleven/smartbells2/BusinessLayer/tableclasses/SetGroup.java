package teameleven.smartbells2.BusinessLayer.tableclasses;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import teameleven.smartbells2.BusinessLayer.RESTCall;
import teameleven.smartbells2.BusinessLayer.localdatabase.DatabaseAdapter;
import teameleven.smartbells2.ViewLayer.login.Authentication;

/**
 * Base SetGroup Class that represents a number of numOfSets with a given number of repsPerSet
 * Created by Brian McMahon on 22/10/2015.
 */
public class SetGroup {

    private Exercise exercise = new Exercise();
    private int exerciseId;
    private int routineId;
    private int id;
    private int numOfSets;
    private int repsPerSet;
    private String creationDate;
    private String lastUpdated;
    private String TAG = "DEBUGGING!!!!!!!!!!!!!!!!!";

    /**************************************Constructors********************************************/
    public SetGroup(){}
    /**
     * Constructor for Creation of Objects for Insertion into JSON Object
     * @param exerciseId
     * @param num_of_sets
     * @param reps_per_set
     */
    public SetGroup(int exerciseId, int num_of_sets, int reps_per_set){
        this.exerciseId = exerciseId;
        this.numOfSets = num_of_sets;
        this.repsPerSet = reps_per_set;
    }
    /**
     * @param setGroup
     */
    public SetGroup (JSONObject setGroup){
        try {
            //exercise = (Exercise) setGroup.get("exercise");
            //exercises = (ArrayList) setGroup.get("exercises");
            exerciseId = (int) setGroup.get("exercise_id");
            routineId = (int) setGroup.get("routine_id");
            id = (int) setGroup.get("id");
            numOfSets = (int) setGroup.get("number_of_sets");
            repsPerSet = (int) setGroup.get("reps_per_set");
            creationDate = (String) setGroup.get("created_at");
            lastUpdated = (String) setGroup.get("updated_at");
            //Log.d("SetGroup, Constructor - ", setGroup.toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /*************************************Attributes***********************************************/

    public int getRoutineId() {
        return routineId;
    }

    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    public void setExerciseId(int exerciseId){
        this.exerciseId = exerciseId;
    }

    public void setExerciseId(){
        this.exerciseId = exercise.getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**************************************Base Methods********************************************/

    /**
     * returns the exercise
     * @return the exercise
     */
    public Exercise getExercise(){
        return exercise;
    }
    /**
     * numOfSets the Exercise
     * @param exercise : the exercise being set
     */
    public void setExercise(Exercise exercise){
        this.exercise = exercise;
    }

    /**
     * returns the number of numOfSets for the exercise
     * @return the number of numOfSets
     */
    public int getNumberOfSets(){
        return numOfSets;
    }
    /**
     * numOfSets the number of numOfSets for an exercise
     * @param sets : the number of numOfSets
     */
    public void setNumberOfSets(int sets){
        this.numOfSets = sets;
    }
    /**
     * returns the number of repsPerSet in a set
     * @return the number of repsPerSet
     */
    public int getRepsPerSet(){
        return repsPerSet;
    }
    public void setRepsPerSet(int reps){
        this.repsPerSet = reps;
    }

    //create JSON parameters to pass to the post method
    public JSONObject  jsonSetGroup(){
        String result = "";
        //todo temporary for testing
        exerciseId = 1;
        routineId = 1;
        try{
            JSONObject setGroup = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("number_of_sets", numOfSets);
            params.put("reps_per_set", repsPerSet);
            params.put("exercise_id", exerciseId);
            params.put("routine_id", routineId);
            setGroup.put("set_group", params);
            return setGroup;
        }catch(JSONException je){
            je.printStackTrace();
        }
            return null;
    }

    /**************************************REST Methods********************************************/

    public String restCreateSetGroup(DatabaseAdapter adapter){

        String accessToken = Authentication.getAccessToken();
        String result = "";
        try{
            String temp = "set_groups";
            JSONObject temp1 = jsonSetGroup();
            AsyncTask test = new RESTCall().execute(temp, "POST", temp1.toString(), accessToken);
            result = test.get().toString();
            return result;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }
    public void restUpdateExercise(){}
    public void restDeleteExercise(){}

    /*
    * methods to make calls on the API
    * */
    public void restGetAllSetGroups(){

    }
    public void restGetExercise(){}


}
