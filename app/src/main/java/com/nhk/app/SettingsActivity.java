package com.nhk.app.ktools;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.widget.SpinnerAdapter;

public class SettingsActivity extends PreferenceActivity {
    //This listener is called when any value in the sharedPreference changes
    private SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if (key.equals("theme")) {
                recreate();
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences.getString("theme", "Light (Default)").equals("Material Dark")) {
            setTheme(R.style.AppThemeMaterialDark);
        }else if (sharedPreferences.getString("theme", "Light (Default)").equals("Holo Dark")) {
            setTheme(R.style.AppThemeHoloDark);
        } else if (sharedPreferences.getString("theme", "Light (Default)").equals("Black (For AMOLED)")){
            setTheme(R.style.AppThemeBlack);
        } else {
            setTheme(R.style.AppTheme);
        }

        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);

        final Preference reset_set =  findPreference("reset_set");
        reset_set.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getApplicationContext());
                dialog.setTitle("Confirm Reset");
                dialog.setMessage("Are you sure you want to reset all the settings to their default values?");
                dialog.setCancelable(true);
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SettingsActivity SettingsActivity = new SettingsActivity();
                        SettingsActivity.reset_settings();
                    }
                });

                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dlg, int which) {
                        dlg.cancel();
                    }
                });

                AlertDialog al = dialog.create();
                al.show();
                //CustomDialog();
                return true;
            }
        });
    }

    public void reset_settings(){
        final ListPreference theme = (ListPreference) findPreference("theme_set");
        final ListPreference startup = (ListPreference) findPreference("startup_set");

        theme.setValue("Light (Default)");
        startup.setValue("Home (Default)");

    }
}