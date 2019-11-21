package com.example.yumi;


import android.content.Intent;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;


public class stdPreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.std_prf);

        Intent i = new Intent(stdPreferences.this, MenuActivity.class);
        Preference logOut = findPreference("logout");
        logOut.setIntent(i);

        Intent intent = new Intent(stdPreferences.this, stdPrfInform.class);
        Preference myi = findPreference("myinform");
        myi.setIntent(intent);

    }

}
