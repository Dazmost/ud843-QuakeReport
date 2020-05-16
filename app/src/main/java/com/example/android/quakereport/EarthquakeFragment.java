/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeFragment extends Fragment {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    SwipeRefreshLayout mySwipeRefreshLayout;
    View rootView;

    /** URL for earthquake data from the USGS dataset */
    private static final String USGS_REQUEST_URL ="https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&limit=20";

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.earthquake_activity, container, false);

        mySwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swiperefresh);



        // Create an {@link AsyncTask} to perform the HTTP request to the given URL
        // on a background thread. When the result is received on the main UI thread,
        // then update the UI.
        NetworkConnect task = new NetworkConnect();
        task.execute(USGS_REQUEST_URL);

        /*
         * Sets up a SwipeRefreshLayout.OnRefreshListener that is invoked when the user
         * performs a swipe-to-refresh gesture.
         */
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");

                        // This method performs the actual data-refresh operation.
                        // The method calls setRefreshing(false) when it's finished.
                        myUpdateOperation();
                    }
                }
        );


        return rootView;
    }

    private void myUpdateOperation(){
        NetworkConnect task = new NetworkConnect();
        task.execute(USGS_REQUEST_URL);
    }

    //onCompletion is your server response with a success
    public void onCompletion() {

        if (mySwipeRefreshLayout.isRefreshing()) {
            mySwipeRefreshLayout.setRefreshing(false);
        }
    }


    /**
     * Update the UI with the given earthquake information.
     */
    private void updateUi(final ArrayList <Earthquake> earthquakes) {
        EarthquakeAdapter adapter = new EarthquakeAdapter(getActivity(), earthquakes);
        ListView earthquakeListView = (ListView) rootView.findViewById(R.id.list);
        // Find a reference to the {@link ListView} in the layout
        earthquakeListView.setAdapter(adapter);
        onCompletion();

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Earthquake earthquake = earthquakes.get(position);

                String url = earthquake.getmURL();
                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(url));
                startActivity(urlIntent);
            }
        });
    }

    /**
     * {@link AsyncTask} to perform the network request on a background thread, and then
     * update the UI with the first earthquake in the response.
     */
    //Generic Data Types
    //String data type for input params because the string UGSURL is input to doInBackground method
    //Void for progress parameter because we do not need to update the user on the task
    //Result parameter is Event bc want the result of the doInBackground method to be an Event Object
    private class NetworkConnect extends AsyncTask<String, Void, ArrayList <Earthquake>> {

        //ALT+Enter to import the Async task at top of java file

        /**
         * This method is invoked (or called) on a background thread, so we can perform
         * long-running operations like making a network request.
         *
         * It is NOT okay to update the UI from a background thread, so we just return an
         * {@link ArrayList <Earthquake>} object as the result.
         */
        @Override
        protected ArrayList <Earthquake> doInBackground(String... urls) {

            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            // Perform the HTTP request for earthquake data and process the response.
            //Util methods are declared static which means you can reference them using the class name wihtout creating an instance of the Utils Object

            ArrayList <Earthquake> earthquake = QueryUtils.fetchEarthquakeData(urls[0]);
            return earthquake;
        }

        /**
         * This method is invoked on the main UI thread after the background work has been
         * completed.
         *
         * It IS okay to modify the UI within this method. We take the {@link ArrayList <Earthquake>} object
         * (which was returned from the doInBackground() method) and update the views on the screen.
         */
        @Override
        protected void onPostExecute(ArrayList <Earthquake> earthquake) {
            // If there is no result, do nothing.
            if (earthquake==null){
                return;
            }
            updateUi(earthquake);
        }
    }
}

