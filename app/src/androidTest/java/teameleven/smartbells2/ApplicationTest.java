package teameleven.smartbells2;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    static TestSuite suite;
    public ApplicationTest() {
        super(Application.class);
    }

    public static Test testExercises(){

        suite.addTest(new ExerciseTest());
        return suite;
    }
}