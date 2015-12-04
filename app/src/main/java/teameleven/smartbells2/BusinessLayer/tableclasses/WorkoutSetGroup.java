
package teameleven.smartbells2.businesslayer.tableclasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class treats the WorkoutSetGroup Table.
 * created : October 4th, 2015
 * @author Andrew Rabb
 */
@SuppressWarnings("unused")
public class WorkoutSetGroup {
    /**************************
     * Attributes
     ********************************************/
    /**
     * Declaration of set_group as the SetGroup object
     */
    private SetGroup set_group = new SetGroup();
    /**
     * WorkoutSession Id
     */
    private int workoutSessionId;
    /**
     * Resistance of the WorkoutSession
     */
    private int resistance;

    /**
     * Constructor with JSONObject parameter : Set the values of set_group
     * @param json JSONObject
     */
    public WorkoutSetGroup(JSONObject json) {
        try {
            //Log.d("json result of constructor - ", json.toString(4));
            this.setSetGroupID(json.getInt("id"));
            this.setWorkoutSessionId(json.getInt("workout_session_id"));
            this.setExerciseId(json.getInt("exercise_id"));
            if (json.has("number_of_sets")) set_group.setNumberOfSets(json.getInt("number_of_sets"));
            if (json.has("reps_per_set")) set_group.setRepsPerSet(json.getInt("reps_per_set"));
            if (json.has("created_at")) set_group.setCreationDate(json.getString("created_at"));
            if (json.has("updated_at")) set_group.setLastUpdated(json.getString("updated_at"));
            if (json.has("resistance"))  this.setResistance(json.getInt("resistance"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Log.d("workout set group constructor - ", this.toString());
    }
    public WorkoutSetGroup(SetGroup setGroup){
        this.set_group = setGroup;
    }
    /**
     * Default Constructor
     */
    public WorkoutSetGroup() {}

    public WorkoutSetGroup(int id, int exerciseId, int sessionId) {
        this.workoutSessionId = sessionId;
        this.setSetGroupID(id);
        this.setExerciseId(exerciseId);
    }

    /**
     * Get the Resistance of WorkoutSetGroup
     * @return resistance
     */
    public int getResistance() {
        return resistance;
    }

    /**
     * Set the Resistance with parameter resistance
     * @param resistance : Resistance of exercise set groups
     */
    public void setResistance(int resistance) {
        this.resistance = resistance;
    }

    /**
     * Get the Set_group
     * @return : the SetGroup
     */
    public SetGroup getSet_group() {
        return set_group;
    }

    /**
     * Set the Set_group
     * @param set_group : SetGroup
     */
    public void setSet_group(SetGroup set_group) {        this.set_group = set_group;    }

    public int getSetGroupID(){
        return this.getSet_group().getId();
    }

    public int getExerciseId(){
        return this.getSet_group().getExerciseId();
    }
    public void setSetGroupID(int id){
        this.getSet_group().setId(id);
    }
    public void setExerciseId(int id){
        this.getSet_group().setExerciseId(id);
    }
    /**
     * Get the WorkoutSessionID
     * @return WorkoutSessionID
     */
    public int getWorkoutSessionId() {
        return workoutSessionId;
    }

    /**
     * Set the WorkoutSession id
     * @param workoutSessionId - Workout Session ID
     */
    public void setWorkoutSessionId(int workoutSessionId){this.workoutSessionId = workoutSessionId;}

    /**
     * Creates a new workoutSetGroup JSON Object
     * @return the JSONObject for the WorkoutSetGroup
     */
    public JSONObject createJSON() {
        try {
            JSONObject workoutSetGroup = new JSONObject();
            workoutSetGroup.put("workout_session_id", getWorkoutSessionId());
            workoutSetGroup.put("id", getSetGroupID());
            workoutSetGroup.put("resistance", resistance);
            workoutSetGroup.put("number_of_sets", this.set_group.getNumberOfSets());
            workoutSetGroup.put("reps_per_set", this.set_group.getRepsPerSet());
            workoutSetGroup.put("created_at", this.set_group.getCreationDate());
            workoutSetGroup.put("updated_at", this.set_group.getLastUpdated());
            workoutSetGroup.put("exercise_id", this.getExerciseId());



            return workoutSetGroup;
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return null;
    }


    @Override
    public String toString() {
        try {
            return this.createJSON().toString(4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "setGroupId = " + this.getSetGroupID();
    }

}