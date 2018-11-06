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

package com.example.lachlanmitchell.mmamath.database;

import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.example.lachlanmitchell.mmamath.model.Fighter;
import com.example.lachlanmitchell.mmamath.presenter.FighterPresenter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

import timber.log.Timber;

/**
 * Fetches stats fighter's statistics from the database
 */

public class FirebaseResult {
    // database setups and mappings
    private DatabaseReference mDatabaseReference;
    public Map<String, String> mFighterDataMap = new HashMap<String, String>();
    private Fighter.OnFighterEventListener mOnCustomEventListener;


    /**
     * Takes in a fighter name and retrieves their statistics from the database
     * @param fighterName
     */
    public FirebaseResult(String fighterName) {

        //parse fighter name for irregularities due to firebase key conventions
        if(fighterName.contains("St.")){
            //replace "St." with "Saint"
            fighterName = "Ovince Saint Preux";
        }
        else{
            fighterName = fighterName.substring(fighterName.indexOf(" ")+1);

        }


        mDatabaseReference = FirebaseDatabase.getInstance().getReference("fighters");


        DatabaseReference ref = mDatabaseReference.child(fighterName);

        final Query fighterQuery = ref.orderByValue();
        Timber.d("The result is " + fighterQuery.toString());
        fighterQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Timber.d("The result is " + dataSnapshot.toString());
                if (dataSnapshot != null) {
                    mFighterDataMap.put("Name", dataSnapshot.child("First Name").getValue().toString() + " " + dataSnapshot.child("Last Name").getValue().toString());
                    mFighterDataMap.put("Nickname", dataSnapshot.child("Nickname").getValue().toString());
                    mFighterDataMap.put("Record", dataSnapshot.child("Record").getValue().toString());
                    mFighterDataMap.put("Height", dataSnapshot.child("Height").getValue().toString());
                    mFighterDataMap.put("Weight", dataSnapshot.child("Weight").getValue().toString());
                    mFighterDataMap.put("Reach", dataSnapshot.child("Reach").getValue().toString());
                    mFighterDataMap.put("Stance", dataSnapshot.child("Stance").getValue().toString());
                    mFighterDataMap.put("Image URL", dataSnapshot.child("Image URL").getValue().toString());
                    mFighterDataMap.put("SLpM", dataSnapshot.child("SLpM").getValue().toString());
                    mFighterDataMap.put("StrAcc", dataSnapshot.child("StrAcc").getValue().toString());
                    mFighterDataMap.put("SApM", dataSnapshot.child("SApM").getValue().toString());
                    mFighterDataMap.put("StrDef", dataSnapshot.child("StrDef").getValue().toString());
                    mFighterDataMap.put("TD Avg", dataSnapshot.child("TD Avg").getValue().toString());
                    mFighterDataMap.put("TD Acc", dataSnapshot.child("TD Acc").getValue().toString());
                    mFighterDataMap.put("TD Def", dataSnapshot.child("TD Def").getValue().toString());
                    mFighterDataMap.put("SubAvg", dataSnapshot.child("SubAvg").getValue().toString());
                }
                Timber.d("Stats are " + mFighterDataMap);
                mOnCustomEventListener.onEvent();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Timber.e("Failed to reach database: " + databaseError.getCode());
            }
        });

    }

    /**
     * Sets the on click listener for the fighter presenter
     * @param onFighterPresenterListener
     */
    public void setOnFighterPresenterListener(Fighter.OnFighterEventListener onFighterPresenterListener){
        this.mOnCustomEventListener = onFighterPresenterListener;
    }
}


