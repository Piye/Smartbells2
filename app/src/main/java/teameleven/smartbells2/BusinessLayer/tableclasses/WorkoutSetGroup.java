
package teameleven.smartbells2.businesslayer.tableclasses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * created : October 4th, 2015
 *
 * @author Andrew Rabb
 */
public class WorkoutSetGroup {
    /**************************
     * Attributes
     ********************************************/

    private SetGroup set_group = new SetGroup();

    private int workoutSessionId;
    private int resistance;

    /**
     *
     * @param json
     */
    public WorkoutSetGroup(JSONObject json) {
        try {
            set_group.setId(json.getInt("id"));
            set_group.setNumberOfSets(json.getInt("number_of_sets"));
            set_group.setRepsPerSet(json.getInt("reps_per_set"));
            set_group.setCreationDate(json.getString("created_at"));
            set_group.setLastUpdated(json.getString("updated_at"));
            set_group.setExerciseId(json.getInt("exercise_id"));
            this.setWorkoutSessionId(json.getInt("workout_session_id"));
            this.setResistance(json.getInt("resistance"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public WorkoutSetGroup() {

    }

    public int getResistance() {
        return resistance;
    }

    public void setResistance(int resistance) {
        this.resistance = resistance;
    }


    /**************************					Constructors               ********************************************/

    /**************************
     * Base Methods
     ********************************************/

    public SetGroup getSet_group() {
        return set_group;
    }

    public void setSet_group(SetGroup set_group) {        this.set_group = set_group;    }

    public int getWorkoutSessionId() {
        return workoutSessionId;
    }

    public void setWorkoutSessionId(int workoutSessionId){this.workoutSessionId = workoutSessionId;}

   /* public WorkoutSetGroup (JSONObject workoutSetGroup){
        try {
            set_group = (SetGroup) workoutSetGroup.get("user_id");
            exerciseId = (int) workoutSetGroup.get("exerciseId");
            workoutSessionId = (int) workoutSetGroup.get("workout_session_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/

    /**
     * Creates a new workoutSetGroup JSON Object
     //todo SPRINT 3:  pass this value to the ArrayList of WorkOutSetGroups in WorkoutSession
     //todo then loop through the ArrayList and pass each value into the JSONArray jsonWorkoutSetGroupAttr() of WorkoutSession
     //todo then adjust jsonWorkoutSession to take the array
     //todo pass in the exerciseId, sets and reps in the Constructor of the WorkoutSetGroup, Instantiate when new SetGroup is created on button click
     * @return the JSONObject for the WorkoutSetGroup
     */
    public JSONObject createJSON() {
        try {
            JSONObject workoutSetGroup = new JSONObject();
            workoutSetGroup.put("workout_session_id", getWorkoutSessionId());
            workoutSetGroup.put("id", this.set_group.getId());
            workoutSetGroup.put("resistance", resistance);
            workoutSetGroup.put("number_of_sets", this.set_group.getNumberOfSets());
            workoutSetGroup.put("reps_per_set", this.set_group.getRepsPerSet());
            workoutSetGroup.put("created_at", this.set_group.getCreationDate());
            workoutSetGroup.put("updated_at", this.set_group.getLastUpdated());
            workoutSetGroup.put("exercise_id", this.set_group.getExerciseId());



            return workoutSetGroup;
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return null;
    }


    @Override
    public String toString() {

        return "unimplemented";//todo implement
    }

}