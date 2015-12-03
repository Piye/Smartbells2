package teameleven.smartbells2.businesslayer.tableclasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Base SetGroup Class that represents a number of Sets with a given number of repsPerSet
 * Created by Brian McMahon on 22/10/2015.
 */
public class SetGroup {

    /*************************************
     * Attributes
     ***********************************************/
    private Exercise exercise = new Exercise();
    private int exerciseId;
    private int routineId;
    private int id;
    private int numOfSets;
    private int repsPerSet;
    private String creationDate;
    private String lastUpdated;
    private String TAG = "DEBUGGING!!!!!!!!!!!!!!!!!";

    /**************************************
     * Constructors
     ********************************************/
    /**
     * default constructor
     */
    public SetGroup() {
    }

    /**
     * Constructor for Creation of Objects for Insertion into JSON Object
     * @param exerciseId - exercise id of the set Group to be created
     * @param num_of_sets - number of sets
     * @param reps_per_set - number of reps
     */
    public SetGroup(int exerciseId, int num_of_sets, int reps_per_set) {
        this.exerciseId = exerciseId;
        this.numOfSets = num_of_sets;
        this.repsPerSet = reps_per_set;
    }

    /**
     * SetGroup JSON Constructor
     * @param setGroup - JSON setGroup
     */
    public SetGroup(JSONObject setGroup) {
        try {
            exerciseId = setGroup.getInt("exercise_id");
            if (setGroup.has("routine_id")) routineId = setGroup.getInt("routine_id");
            if (setGroup.has("id")) id = setGroup.getInt("id");
            numOfSets = setGroup.getInt("number_of_sets");
            repsPerSet = setGroup.getInt("reps_per_set");
            if (setGroup.has("created_at"))creationDate = setGroup.getString("created_at");
            if (setGroup.has("updated_at"))lastUpdated = setGroup.getString("updated_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Primitive Constructor
     * @param setGroupId - setGroup ID
     * @param exerciseId - Exercise ID
     * @param routineId - Routine ID
     * @param reps - number of reps
     * @param sets - number of sets
     */
    public SetGroup(int setGroupId, int exerciseId, int routineId, int reps, int sets) {
        this.id = setGroupId;
        this.exerciseId = exerciseId;
        this.routineId = routineId;
        this.repsPerSet = reps;
        this.numOfSets = sets;

    }

    /**
     * returns the Exercise ID
     * @return the exercise ID
     */
    public int getExerciseId() {
        return exerciseId;
    }

    /**
     * sets the Exercise ID for the set Group
     * @param exerciseId - exercise ID
     */
    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

    /**
     * returns the Routine ID
     * @return routine ID
     */
    public int getRoutineId() {
        return routineId;
    }

    /**
     * sets the Routine ID
     * @param routineId - routine id to be set
     */
    public void setRoutineId(int routineId) {
        this.routineId = routineId;
    }

    /**
     * sets the Exercise ID
     */
    public void setExerciseId() {
        this.exerciseId = exercise.getId();
    }

    /**
     * returns the set group id
     * @return the set group id
     */
    public int getId() {
        return id;
    }

    /**
     * sets the set group id
     * @param id - set group id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * returns the creation date (String)
     * @return creation Date, unformatted
     */
    public String getCreationDate() {
        return creationDate;
    }

    /**
     * sets the creation date
     * @param creationDate - creation date (should be retrieved from API)
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * returns the last updated date
     * @return last updated date
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * sets the last updated date
     * @param lastUpdated - last updated date (should be retrieved from API)
     */
    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    /**
     * returns the exercise
     * @return the exercise
     */
    public Exercise getExercise() {
        return exercise;
    }

    /**
     * Sets theExercise
     * @param exercise : the exercise being set
     */
    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    /**
     * returns the number of numOfSets for the exercise
     * @return the number of numOfSets
     */
    public int getNumberOfSets() {
        return numOfSets;
    }

    /**
     * numOfSets the number of numOfSets for an exercise
     * @param sets : the number of numOfSets
     */
    public void setNumberOfSets(int sets) {
        this.numOfSets = sets;
    }

    /**
     * returns the number of repsPerSet in a set
     * @return the number of repsPerSet
     */
    public int getRepsPerSet() {
        return repsPerSet;
    }

    /**
     * sets number of reps in a set
     * @param reps - reps per set
     */
    public void setRepsPerSet(int reps) {
        this.repsPerSet = reps;
    }

    //create JSON parameters to pass to the post method

    /**
     * returns a String representation of the Object
     * @return String setGroup (JSONObject.toString(4))
     */
    @Override
    public String toString(){
        try {
            return this.createJSON().toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.getExercise().getName() + " - Set Group " + this.getId();
    }
    /**
     * Create JSON parameters to pass to the post method
     * @return : setGroup JsonObject
     */
    public JSONObject createJSON() {
        try {
            JSONObject setGroup = new JSONObject();
            setGroup.put("number_of_sets", numOfSets);
            setGroup.put("reps_per_set", repsPerSet);
            setGroup.put("exercise_id", exerciseId);
            if (routineId != 0) setGroup.put("routine_id", routineId);
            if (id != 0) setGroup.put("id", id);
            return setGroup;
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return null;
    }
}
