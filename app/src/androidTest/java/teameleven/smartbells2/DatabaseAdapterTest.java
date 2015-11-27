package teameleven.smartbells2;

import android.test.AndroidTestCase;
import android.test.RenamingDelegatingContext;
import android.util.Log;

import java.sql.SQLException;

import teameleven.smartbells2.businesslayer.localdatabase.DatabaseAdapter;

/**
 * Created by Andrew Rabb on 2015-11-27.
 * based upon http://stackoverflow.com/questions/3096378/testing-database-on-android-providertestcase2-or-renamingdelegatingcontext
 */
public class DatabaseAdapterTest extends AndroidTestCase{

private static final String TEST_FILE_PREFIX = "test_";
    private DatabaseAdapter database;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        database = getDatabase();

        Log.d("database created - ", " you created a database");
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }

    public DatabaseAdapter getDatabase() throws SQLException {
        //RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), TEST_FILE_PREFIX);
        //database = new DatabaseAdapter(context);
        database = new DatabaseAdapter(getContext());

        assertNotNull(database);
        return database;
    }

}
