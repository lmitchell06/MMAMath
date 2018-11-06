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

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lachlanmitchell.mmamath.R;
import com.example.lachlanmitchell.mmamath.model.UfcRank;
import com.example.lachlanmitchell.mmamath.utils.Constants;

import java.util.List;

/**
 * Adapts the ranking activity to the recycler vieww
 */

public class RankingsAdapter extends RecyclerView.Adapter<RankingsAdapter.ViewHolder>  {

    private Context mContext;
    private List<UfcRank> mUfcRankList;

    public RankingsAdapter(Context context, List<UfcRank> ufcRankList) {
        mContext = context;
        mUfcRankList = ufcRankList;
    }

    /**
     * Creates view for single row
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Layout inflater
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ranking_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // TrainDatabaseHelper trainHelper = new TrainDatabaseHelper(mContext);
        UfcRank ufcRank = mUfcRankList.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        //viewHolder.mFighterNamesTv.setText(String.valueOf(mUfcRankList.get(position)));

        for (int i = 0; i< mUfcRankList.size(); i++) {
            viewHolder.mFighterNamesTv[i].setText(String.valueOf(mUfcRankList.get(position)));
        }



    }

    @Override
    public int getItemCount() {
        return mUfcRankList.size();
    }

    /**
     * ViewHolder nested class that initialises all text view elements into the view
     *
     */

    public class ViewHolder extends RecyclerView.ViewHolder {
        // make fields public final

        public final TextView[] mFighterNamesTv = new TextView[Constants.FIGHTER_RANKINGS];


        /**
         * Constructor for ViewHolder that takes in each item
         * @param itemView
         */
        public ViewHolder(View itemView) {
            super(itemView);

            mFighterNamesTv[0] = (TextView)itemView.findViewById(R.id.champ_rank);
            mFighterNamesTv[1] = (TextView)itemView.findViewById(R.id.first_rank);
            mFighterNamesTv[2] = (TextView)itemView.findViewById(R.id.second_rank);
            mFighterNamesTv[3] = (TextView)itemView.findViewById(R.id.third_rank);
            mFighterNamesTv[4] = (TextView)itemView.findViewById(R.id.fourth_rank);
            mFighterNamesTv[5] = (TextView)itemView.findViewById(R.id.fifth_rank);
            mFighterNamesTv[6] = (TextView)itemView.findViewById(R.id.sixth_rank);
            mFighterNamesTv[7] = (TextView)itemView.findViewById(R.id.seventh_rank);
            mFighterNamesTv[8] = (TextView)itemView.findViewById(R.id.eighth_rank);
            mFighterNamesTv[9] = (TextView)itemView.findViewById(R.id.ninth_rank);
            mFighterNamesTv[10] = (TextView)itemView.findViewById(R.id.tenth_rank);


        }


    }
}
