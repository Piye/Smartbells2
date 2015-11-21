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
 * Base Routine Class. consists of a collection of Exercises, stored in a List, as well as a name.
 * Routines are collections of exercises that can be saved by the Android Application.
 * Each routine will have a name, as well as at least 1 exercise.
 * Routines at this point have no cap to the number of Exercises that can be added to the Routine.
 * Created : October  4th, 2015 @author : WonKyoung Chung
 * Update  : October 24th, 2015 @author : WonKyoung Chung
 **/
public class Routine {

    /******************************** Attributes***************************************************/
    static final private String RestID = "routines";
    /**
     * specific ID of the Exercise.
     */
    private int id;
    /**
     *
     */
    private int user_id;
    /**
     * name : Routine's name
     */
    private String name;
    /**
     * Date-stamp of the last time that the object was created.
     */
    private String created_At;
    /**
     * Date-stamp of the last time that the object was edited.
     */
    private String updated_At;
    /**
     *
     */
    private boolean is_Public;
    /**
     *
     */
    private ArrayList<SetGroup> setGroups = new ArrayList<>();


    /***********************************Constructors **********************************************/
    /**
     * Default Constructor
     */
    public Routine() {}


    /**
     * @param routine
     */
    public Routine(JSONObject routine) {
        try {
            if (routine.has("routine")) {
                routine = routine.getJSONObject("routine");
                //discarding outer JSON shell

                id = (int) routine.get("id");
                name = (String) routine.get("name");
                created_At = (String) routine.get("created_at");
                updated_At = (String) routine.get("updated_at");
                is_Public = (boolean) routine.get("is_public");

                JSONArray setGroupArray = routine.getJSONArray("set_groups");
                for (int x = 0; x < setGroupArray.length(); x++) {
                    SetGroup setGroup = new SetGroup(setGroupArray.getJSONObject(x));
                    setGroups.add(setGroup);
                }
                Log.d("Routine Constructor - ", routine.toString(4));
            }
            //setGroup = (SetGroup) routine.get("set_groups");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
/************************************ Static Methods   ********************************************/
    /**
     * @return
     */
    public static String restGetAll() {
        try {
            String temp = RestID;
            System.out.println(temp);
            AsyncTask result = new RESTCall().execute(temp, "GET");
            result.get();
            return (String) result.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "Rest Call Failed";
    }

    /**
     * @param id
     * @return
     */
    public static String restGetRoutine(int id) {
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
     * @param routineAll
     * @return
     */
    public static ArrayList<Routine> getAllRoutine(JSONObject routineAll) {
        JSONArray routineArray = null;
        try {
            routineArray = routineAll.getJSONArray("routines");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int index = 0; index < routineArray.length(); index++) {
            Log.d("debugging - testing index - ", String.valueOf(index));
        }
        ArrayList<Routine> routines = new ArrayList<>();
        for (int index = 0; index < routineArray.length(); index++) {
            JSONObject routine = null;
            Log.d("debugging, index = ", String.valueOf(index));
            try {
                routine = routineArray.getJSONObject(index);
                Routine tempRoutine = new Routine();
                tempRoutine.id = (int) routine.get("id");
                tempRoutine.name = (String) routine.get("name");
                tempRoutine.created_At = (String) routine.get("created_at");
                tempRoutine.updated_At = (String) routine.get("updated_at");
                tempRoutine.is_Public = (boolean) routine.get("is_public");

                JSONArray setGroupArray = routine.getJSONArray("set_groups");
                for (int x = 0; x < setGroupArray.length(); x++) {
                    SetGroup setGroup = new SetGroup(setGroupArray.getJSONObject(x));
                    tempRoutine.setGroups.add(setGroup);
                }
                Log.d("Routine Constructor - ", routine.toString(4));
                routines.add(tempRoutine);
                //setGroup = (SetGroup) routine.get("set_groups");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return routines;
    }
    /**************************			Base Methods  *********************************************/

    /**
     * retrieves the name of the Routine.
     *
     * @return : Routine Name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoutineId() {
        return id;
    }

    public void setRoutineID(int id) {
        this.id = id;
    }

    public void setIs_Public(boolean is_Public) {
        this.is_Public = is_Public;
    }

    public boolean getIsPublic() {
        return is_Public;
    }

    /**
     *
     * @param is_Public
     */
    public void setIsPublic(Boolean is_Public) {
        this.is_Public = is_Public;
    }

    /**
     *
     * @return
     */
    public String getCreated_At() {
        return created_At;
    }

    public String toString() {
        try {
            return (this.createJSON().toString(4));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return this.name;
    }

    public String getUpdated_At() {
        return updated_At;
    }

    public void setUpdated_At(String updated_At) {
        this.updated_At = updated_At;
    }

    public ArrayList<SetGroup> getSetGroups() {
        return setGroups;
    }

    public void setSetGroups(ArrayList<SetGroup> setgroups) {
        this.setGroups = setgroups;
    }
    /**************************************JSON****************************************************/

    /**
     * Makes a JSONObject from the current Routine Object.
     *
     * @return - JSON object
     */
    public JSONObject createJSON() {

        JSONObject json = new JSONObject();
        try {
            json.put("name", name);
            json.put("created At", created_At);
            json.put("updated At", updated_At);
            json.put("is public", is_Public);


            JSONArray setGroups = new JSONArray();
            for (int index = 0; index < this.setGroups.size(); index++) {
                setGroups.put(this.setGroups.get(index).jsonSetGroup());
            }
            json.put("set_groups", setGroups);

            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /******************************** ReST Methods    *********************************************/
    /**
     *
     *
     * @param database
     * @return
     */
    public String RestPutRoutine(DatabaseAdapter database) {
        Routine routine = null;
        try {
            Log.d("Routine.restPutRoutine - ", this.createJSON().toString(4));
            AsyncTask test = new RESTCall()
                    .execute(RestID, "POST", createJSON().toString(), database.getTokenAsString());
            //send to database here also


            routine = new Routine(new JSONObject(test.get().toString()));
            Log.d("Routine - RestPutRoutine 2 - ", routine.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        database.insertRoutine(routine);
        return null;
    }
}

/*********************************************Unused Methods***************************************/

/*    *//**
 * Accesses the Database and the RESTCall class to obtain an object from the Server,
 * and save it into the database
 *
 * @param database
 *//*
    public void createDBRoutine(DatabaseAdapter database,
                                String routineName, Boolean is_Public,
                                ArrayList<String> set_Groups) {
        Log.d("Routine DB creation", this.createJSON().toString());
        AsyncTask test = new RESTCall().execute(RestID, "POST", createJSON().toString(),
                database.getTokenAsString());

        //send to database here also
        Routine routine = null;

        try {
            routine = new Routine(new JSONObject(test.get().toString()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("database load - ", routineName + ", "
                + is_Public + ", " + set_Groups
                + ", " + routine.created_At + ", " + routine.updated_At);
        //database.insertRoutine (routineName,is_Public,set_Groups,date,date);
        //database.insertRoutine (routineName,is_Public,"WonRoutine",date,date);//test
    }*/




/*  *//**
 * Static creation of an Exercise. Allows creation of a simple JSONObject
 * through the fields in Routine.
 *
 * @param name
 * @param is_public
 * @param setGroups
 * @return
 *//*
    public static JSONObject createJSONRoutine(String name,
                                               boolean is_public,
                                               ArrayList<SetGroup> setGroups) {
        JSONObject json = new JSONObject();
        try {
            json = json
                    .put("name", name)
                    .put("is public", is_public);

            //JSONObject setGroups = new JSONObject();
            //put("set_groups_attributes", setGroups);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }*/


/*    *//**
 * Static creation of an Exercise. Allows creation of a simple JSONObject
 * through the fields in Exercise.
 *
 * @param name
 * @param is_public
 * @param set_groups
 * @return
 *//*
    public static JSONObject createStaticJSONRoutine(String name,
                                                     boolean is_public,
                                                     ArrayList<String> set_groups) {
        JSONObject json = new JSONObject();
        try {
            json = json
                    .put("name", name)
                    .put("is public", is_public)
                    .put("set_groups_attributes", set_groups);
            return json;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }*/