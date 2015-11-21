
package teameleven.smartbells2.BusinessLayer.tableclasses;

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

    private int exerciseId;

    private int workoutSessionId;

    public WorkoutSetGroup(JSONObject jsonObject) {
        //todo
    }

    public WorkoutSetGroup() {

    }


    /**************************					Constructors               ********************************************/

    /**************************
     * Base Methods
     ********************************************/

    public SetGroup getSet_group() {
        return set_group;
    }

    public void setSet_group(SetGroup set_group) {
        this.set_group = set_group;
    }

    public int getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(int exerciseId) {
        this.exerciseId = exerciseId;
    }

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

    public JSONObject jsonWorkoutSetGroup() {
        //String result = "";
        try {
            //JSONObject setGroup = new JSONObject();
            JSONObject params = new JSONObject();
            params.put("exerciseId", exerciseId);
            //params.put("workout_session_id", workoutSessionId);
            //setGroup.put("set_group", params);
            return params;
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