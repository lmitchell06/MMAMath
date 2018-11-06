/*
 * Copyright 2017 The Android Open Source Project
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

package com.example.lachlanmitchell.mmamath.model;

import android.net.Uri;
import android.os.AsyncTask;

import com.example.lachlanmitchell.mmamath.adapter.RankingsRestAdapter;
import com.example.lachlanmitchell.mmamath.interfaces.RankingsApi;
import com.example.lachlanmitchell.mmamath.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Model class with the ranking array and fighters order in the division
 */

public class UfcRanking {

    public ArrayList<String> mRankingsList = new ArrayList<String>();
    private String mDivisionVal;
    private UfcRank.OnRankingEventListener mOnRankingEventListener;

    /**
     * Constructs the rankings by parsing based on weight class
     * @param division
     */
    public UfcRanking(String division) {
        this.mDivisionVal = division;
        //new RankingsParser().execute();
        parseRankings();
    }

    /**
     * Sets ranking presenter listener to interact with activity
     * @param onRankingEventListener
     */

    public void setOnRankingEventListener(UfcRank.OnRankingEventListener onRankingEventListener){
        this.mOnRankingEventListener = onRankingEventListener;
    }

    /**
     * Parses rankings through retrofit
     */

    public void parseRankings() {

        RankingsApi service = RankingsRestAdapter.getRetrofitInstance().create(RankingsApi.class);
        Call<RankingsList> call = service.getRankingsApi();
        call.enqueue(new Callback<RankingsList>() {
            @Override
            public void onResponse(Call<RankingsList> call, Response<RankingsList> response) {



                RankingsList data = response.body();
                Timber.d("Data is " + data);
                Timber.d("Data length: " + data.mUfcFlyweights.size());

                for (UfcRank ufcRank : data.getRanks(mDivisionVal)){
                    String formattedFighter = ufcRank.name.substring(ufcRank.name.indexOf("\n")+1);
                    Timber.d("Fighter name: " + formattedFighter);
                    mRankingsList.add(formattedFighter);

                }



                mOnRankingEventListener.onEvent();

            }

            @Override
            public void onFailure(Call<RankingsList> call, Throwable t) {
                Timber.d("FAILURE_RETROFIT: " + t.toString());
            }
        });


    }

    /**
     * Parses rankings through a manual web service call
     */
    private class RankingsParser extends AsyncTask<Void, String, String> {
        HttpURLConnection conn;
        URL url;

        @Override
        protected String doInBackground(Void... voids) {
            // cannot access UI
            try {
                url = new URL("https://www.parsehub.com/api/v2/projects/twahUusLML3p/last_ready_run/data?api_key=tfmAP_rQ6w0d");
            } catch (MalformedURLException e) {
                // print error stack if thrown
                e.printStackTrace();
            } try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");

                conn.setDoInput(true);

                // add parameter to our above url
                Uri.Builder builder = new Uri.Builder().appendQueryParameter("format", "json");
                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            } try {
                int responseCode = conn.getResponseCode();

                // Check if successful connection made
                if (responseCode == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {
                    Timber.d("RESPONSE CODE IS: " + responseCode);
                    return("Connection error");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPreExecute() {
            // UI code goes here
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray(mDivisionVal);

                for (int i = 0; i< Constants.FIGHTER_RANKINGS; i++) {

                    if (i != 10) {
                        JSONObject jsonData = jsonArray.getJSONObject(i);
                        mRankingsList.add(jsonData.getString("name").substring(2));
                    } else {
                        JSONObject jsonData = jsonArray.getJSONObject(i);
                        mRankingsList.add(jsonData.getString("name").substring(3));
                    }
                }

                Timber.d("LIST: " + mRankingsList);


            } catch (JSONException e){
                e.printStackTrace();
            }

            mOnRankingEventListener.onEvent();

        }

    }

}
