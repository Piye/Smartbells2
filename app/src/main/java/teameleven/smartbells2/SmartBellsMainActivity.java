package teameleven.smartbells2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import teameleven.smartbells2.create.CreateCustomSession;
import teameleven.smartbells2.create.CreateExercise;
import teameleven.smartbells2.create.CreateRoutine;
import teameleven.smartbells2.create.CreateWorkout;

public class SmartBellsMainActivity extends AppCompatActivity
                                    implements NavigationView.OnNavigationItemSelectedListener {

    public static Dashboard dashboardTab = new Dashboard();
    public static BeginWorkout2 bw2 = new BeginWorkout2();

    private Fragment fragment = null;
    private FragmentTransaction transaction;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_bells_main);

        //Go to the Dashboard
        fragment = new Dashboard();
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_main, fragment);
        transaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //*************************************FAB**************************************************

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* DASHBOARD TAB SELECTED */

                //Load Create a Workout on FAB click while on Workout Tab
                if(dashboardTab.getCheckTabPage() == 0) {
                    //Snackbar for debugging
                    //Snackbar.make(view, "Add a workout!", Snackbar.LENGTH_LONG)
                            //.setAction("Action", null).show();

                    //hide the fab
                    fab.animate().translationY(fab.getHeight() + 16).setInterpolator(
                                                            new AccelerateInterpolator(2)).start();
                    fragment = new CreateWorkout();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, fragment);
                    transaction.commit();

                }

                //Load Create a Routine on FAB click while on Routine Tab
                if(dashboardTab.getCheckTabPage() == 1) {
                    //hide the fab
                    fab.animate().translationY(fab.getHeight() + 16).setInterpolator(
                                                            new AccelerateInterpolator(2)).start();
                    fragment = new CreateRoutine();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, fragment);
                    transaction.commit();

                }

                //Probably going to remove this in favour for a new Exercise Class
                if(dashboardTab.getCheckTabPage() == 2) {
                    //hide the fab
                    fab.animate().translationY(fab.getHeight() + 16).setInterpolator(
                            new AccelerateInterpolator(2)).start();
                    fragment = new CreateExercise();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, fragment);
                    transaction.commit();


                }

                /* BEGIN WORKOUT FRAGMENT TAB SELECTED */

                //This might go all together? Will keep if we decide to add a new tab type.
                if( bw2.getCheckBWTabPage() == 100000) {
                    fab.animate().translationY(fab.getHeight() + 16).setInterpolator(
                            new AccelerateInterpolator(2)).start();
                    fragment = new CreateCustomSession();
                    transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.content_main, fragment);
                    transaction.commit();
                }
                //This might go all together? Will keep if we decide to add a new tab type.
                if (bw2.getCheckBWTabPage() == 1) {

                }

            }
        });

        //*******************************DRAWER****************************************************
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        //show the fab
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //Drawer Selected Items
        //Handle the Actions
        if (id == R.id.nav_dashboard) {
            fragment = new Dashboard();
        } else if (id == R.id.nav_beginworkout) {
            fragment = new BeginWorkout2();
        } else if (id == R.id.nav_achievements) {
            fragment = new AchievementDashboard();
        } else if (id == R.id.nav_about) {
            fragment = new About();
        } else if (id == R.id.nav_profile) {
            //View and edit profile
        } else if (id == R.id.nav_logout) {
            //logout
        }

        if (fragment != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.content_main, fragment);
            transaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
