package org.abrysov.crazywheel.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.abrysov.crazywheel.R;
import org.abrysov.crazywheel.fragment.FragmentPlaceholder;

public class ActivityMain extends Activity {

    private final long UPDATE_TIME_INTERVAL = 15 * 1000;

    private final String TAG_FRAGMENT_LIST = "fragment_list";

    private FragmentPlaceholder fragmentPlaceholder;

    private FragmentManager fm;

    private static Integer count = 0;

//    private boolean serviseOn = true;

    private Thread updater = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            fragmentPlaceholder = new FragmentPlaceholder();
            fm = getFragmentManager();
            fm.beginTransaction()
                    .add(R.id.container, fragmentPlaceholder, TAG_FRAGMENT_LIST).commit();
        }

        //useUpdater();

//        ActionBar actionBar = getActionBar();
//        actionBar.show();
    }

    private void useUpdater (){

        if (updater == null){
            updater = new Thread(){

                @Override
                public void run() {

                    while (true) {
                        try {
                            sleep(UPDATE_TIME_INTERVAL);
                            fragmentPlaceholder.refreshData();
                        }catch (Exception e) {
                            e.getStackTrace();
                        }
                    }

                }
            };
            updater.start();
        }else{
            updater.interrupt();
            updater = null;
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        useUpdater();
    }

    @Override
    protected void onPause() {
        super.onPause();
        useUpdater();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.activity_main, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.room, menu);
        return true;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.room, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            case R.id.action_refresh:
//                Toast.makeText(this, "Settings clicked from Activity!!!", Toast.LENGTH_SHORT)
//                        .show();

                  fragmentPlaceholder.refreshData();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        if (count == 0) {
            super.onBackPressed();
        }else {
            fm.beginTransaction().replace(R.id.container, fragmentPlaceholder).commit();
            count--;
        }

    }

    public static Integer getCount() {
        return count;
    }

    public static void addCount(Integer number) {
        count = count + number;
    }
}
