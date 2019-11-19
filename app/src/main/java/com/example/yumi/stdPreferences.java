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

public class stdPreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.std_prf);

        Intent i = new Intent(stdPreferences.this, MenuActivity.class);
        Preference logOut = findPreference("logout");
        logOut.setIntent(i);

    }

}
