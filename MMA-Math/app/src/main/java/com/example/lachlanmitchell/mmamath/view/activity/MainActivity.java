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

package com.example.lachlanmitchell.mmamath.view.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lachlanmitchell.mmamath.BuildConfig;
import com.example.lachlanmitchell.mmamath.R;
import com.example.lachlanmitchell.mmamath.utils.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import timber.log.Timber;

/**
 * Allows the user select an organization and weight class to view the rankings of
 */

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.spinner_organization) Spinner mOrgSpinner;
    @BindView(R.id.spinner_division) Spinner mDivisionSpinner;
    @BindView(R.id.view_rankings_fab) FloatingActionButton mRankingsBtn;
    @BindView(R.id.progress_bar_main_activity) ProgressBar mProgressBarMain;
    private ArrayAdapter<CharSequence> mAdapterDivision;
    private String mOrgVal;
    private String mSelectedDivion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG)
            Timber.plant(new Timber.DebugTree());
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mOrgVal = intent.getStringExtra(Constants.ORGANIZATION);
        mSelectedDivion = intent.getStringExtra(Constants.DIVISION);

        mProgressBarMain.setVisibility(View.GONE);

        // Create an ArrayAdapter using the string array for the organisation spinner
        ArrayAdapter<CharSequence> adapterOrg = ArrayAdapter.createFromResource(this,
                R.array.mma_org_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterOrg.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mOrgSpinner.setAdapter(adapterOrg);

        // Create an ArrayAdapter using the string array for the division spinner
        mAdapterDivision = ArrayAdapter.createFromResource(this,
                R.array.mma_division_array, android.R.layout.simple_spinner_item);
        mAdapterDivision.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mDivisionSpinner.setAdapter(mAdapterDivision);
        //mDivisionSpinner.setSelection(adapterDivision.getPosition(Constants.FLY_DIVISION));

    }

    /**
     * Handles the spinner selection of unavailable organizations
     * @param position
     */

    @OnItemSelected(R.id.spinner_organization)
    void onItemSelected(int position) {
            Timber.d("Selected item is " + position);
            if (position != 0) {
                Toast.makeText(MainActivity.this, getString(R.string.org_not_available),Toast.LENGTH_SHORT).show();
            }
    }

    /**
     * Takes the user to the weight class they have selected
     */

    @OnClick(R.id.view_rankings_fab)
    public void viewRankings() {
        Intent divisionIntent = new Intent(MainActivity.this, RankingsActivity.class);
        String selectedOrganization = mOrgSpinner.getSelectedItem().toString();
        if (selectedOrganization.equals(Constants.UFC_ORG)) {
            mSelectedDivion = mDivisionSpinner.getSelectedItem().toString();
            divisionIntent.putExtra(Constants.DIVISION, mSelectedDivion);
            divisionIntent.putExtra(Constants.ORGANIZATION, selectedOrganization);
            MainActivity.this.startActivity(divisionIntent);
            mProgressBarMain.setVisibility(View.VISIBLE);
            mProgressBarMain.setIndeterminate(true);
            Timber.d("Start rankings act " + mSelectedDivion);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.org_not_available),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSelectedDivion != null) {
            mDivisionSpinner.setSelection(mAdapterDivision.getPosition(mSelectedDivion));
        }
    }

    /**
     * Handles navigating with the back button
     */

    public void onBackPressed() {
        Timber.d("Back pressed");
        Intent backToMain = new Intent(this, MainActivity.class);
        startActivity(backToMain);
        finish();
    }
}
