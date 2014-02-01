package org.abrysov.crazywheel.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.abrysov.crazywheel.R;
import org.abrysov.crazywheel.activity.ActivityMain;
import org.abrysov.crazywheel.adapter.CustListAdapter;
import org.abrysov.crazywheel.helper.ServiceHandler;
import org.abrysov.crazywheel.model.ModelContent;
//import org.abrysov.crazywheel.tasks.GetDataWheelTask1;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by abrysov on 31.01.14.
 */
public class FragmentPlaceholder extends Fragment {

    public final static String URL = "http://crazy-dev.wheely.com/";

    private static final String TAG_ID = "id";
    private static final String TAG_TITLE = "title";
    private static final String TAG_TEXT = "text";

    private ListView listView;
    private LinearLayout waitingProgress;

    private ArrayList <ModelContent> modelContents = new ArrayList<ModelContent>();

    CustListAdapter adapter;

    public FragmentPlaceholder() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

//        modelContents.add(new ModelContent("0","0","0"));

        final FragmentManager fm = getFragmentManager();

        listView = (ListView)rootView.findViewById(R.id.main_lv_content);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fm.beginTransaction().replace(R.id.container,
                        new FragmentAllInfo(modelContents.get(position))).commit();

                ActivityMain.addCount(1);
            }
        });

        waitingProgress = (LinearLayout)rootView.findViewById(R.id.main_ll_roll);

        refreshData();


        return rootView;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;

            case R.id.action_refresh:

                new GetDataWheelTask1().execute();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private class GetDataWheelTask1 extends AsyncTask <Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            waitingProgress.setVisibility(View.VISIBLE);

        }

        @Override
        protected Void doInBackground(Void... params) {


            ServiceHandler sh = new ServiceHandler();

            String jsonStr = sh.makeServiceCall(FragmentPlaceholder.URL, ServiceHandler.GET);

            Log.d("Response: ", "> " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONArray jsonArray = new JSONArray(jsonStr);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject c = jsonArray.getJSONObject(i);

                        ModelContent mc = new ModelContent(c.getString(TAG_ID),
                                            c.getString(TAG_TITLE), c.getString(TAG_TEXT));

                        modelContents.add(i, mc);

                    }
                } catch (JSONException e) {
                    //e.printStackTrace();
                }
            } else {
                Log.e("ServiceHandler", "Couldn't get any data from the url");
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            waitingProgress.setVisibility(View.INVISIBLE);

            try {
                adapter = new CustListAdapter(getActivity(), modelContents);

            }catch (Exception e) {
                e.getStackTrace();
            }

            listView.setAdapter(adapter);
            listView.postInvalidate();
//            listView.

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

            listView.postInvalidate();

        }
    }

    @Override
    public void onPause() {
        super.onPause();

        modelContents.clear();
    }

    public void refreshData() {
        modelContents.clear();
        new GetDataWheelTask1().execute();
    }
}
