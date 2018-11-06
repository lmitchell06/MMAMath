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

package com.example.lachlanmitchell.mmamath.adapter;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Connects the the rankings web service through Retrofit
 */

public class RankingsRestAdapter {

    public static final String RANKINGS_URL = "https://www.parsehub.com/api/v2/projects/twahUusLML3p/";
    public static Retrofit retrofit;

    /**
     * Returns an instance of retrofit
     * @return
     */

    public static Retrofit getRetrofitInstance() {

        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(RANKINGS_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        Timber.d("Retrofit response is " + retrofit.toString());
        return retrofit;
    }
}
