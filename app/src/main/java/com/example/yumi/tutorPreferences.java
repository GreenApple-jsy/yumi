package com.example.yumi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.widget.Toast;

import androidx.preference.ListPreference;

public class tutorPreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.tutor_prf);

        Intent i = new Intent(tutorPreferences.this, MenuActivity.class);
        Preference logOut = findPreference("logout");
        logOut.setIntent(i);

        Intent intent = new Intent(tutorPreferences.this, tutorPrfInform.class);
        Preference myi = findPreference("myinform");
        myi.setIntent(intent);


    }

}
