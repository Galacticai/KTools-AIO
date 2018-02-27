package com.nhk.app.ktools;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ScrollingView;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

public class ToolsActivity extends MainActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_general:
                    ShowG();
                    return true;
                case R.id.navigation_root:
                    ShowR();
                    return true;
            }
            return false;
        }

    };

    @Override
    public void onBackPressed() {SwitchToHome();}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Intent intent = getIntent();
        String to = intent.getStringExtra("to");
        if (to.equals("general")){
            gTheme();
        } else if (to.equals("root")){
            rTheme();
        } else {
            gTheme();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        this.setTitle("Tools Drawer");

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ScrollView toolsg = (ScrollView) findViewById(R.id.tools_general);
        ScrollView toolsr = (ScrollView) findViewById(R.id.tools_root);

        if (to.equals("general")) {
            navigation.getMenu().getItem(0).setChecked(true);
            toolsg.setVisibility(View.VISIBLE);
            toolsr.setVisibility(View.GONE);
        } else if (to.equals("root")){
            navigation.getMenu().getItem(1).setChecked(true);
            toolsg.setVisibility(View.GONE);
            toolsr.setVisibility(View.VISIBLE);
        }

    }

    public void ShowG(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        ScrollView toolsg = (ScrollView) findViewById(R.id.tools_general);
        ScrollView toolsr = (ScrollView) findViewById(R.id.tools_root);
        navigation.getMenu().getItem(0).setChecked(true);
        toolsg.setVisibility(View.VISIBLE);
        toolsg.setAlpha(0.0f);
        toolsg.animate().alpha(1.0f);
        toolsg.requestLayout();

        toolsr.setAlpha(1.0f);
        toolsr.animate().alpha(0.0f);
        toolsr.setVisibility(View.GONE);
    }
    public void ShowR(){
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        ScrollView toolsg = (ScrollView) findViewById(R.id.tools_general);
        ScrollView toolsr = (ScrollView) findViewById(R.id.tools_root);
        navigation.getMenu().getItem(1).setChecked(true);
        toolsr.setVisibility(View.VISIBLE);
        toolsr.setAlpha(0.0f);
        toolsr.animate().alpha(1.0f);
        toolsr.requestLayout();

        toolsg.setAlpha(1.0f);
        toolsg.animate().alpha(0.0f);
        toolsg.setVisibility(View.GONE);
    }
    public void gTheme(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences.getString("theme", "Light (Default)").equals("Material Dark")) {
            setTheme(R.style.AppThemeMaterialDark_ActionBar);
        } else if (sharedPreferences.getString("theme", "Light (Default)").equals("Holo Dark")) {
            setTheme(R.style.AppThemeHoloDark_ActionBar);
        } else if (sharedPreferences.getString("theme", "Light (Default)").equals("Black (For AMOLED)")){
            setTheme(R.style.AppThemeBlack_ActionBar);
        } else {
            setTheme(R.style.AppTheme_ActionBar);
        }
    }

    public void rTheme(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences.getString("theme", "Light (Default)").equals("Material Dark")) {
            setTheme(R.style.AppThemeMaterialDark_ActionBar_r);
        } else if (sharedPreferences.getString("theme", "Light (Default)").equals("Holo Dark")) {
            setTheme(R.style.AppThemeHoloDark_ActionBar_r);
        } else if (sharedPreferences.getString("theme", "Light (Default)").equals("Black (For AMOLED)")){
            setTheme(R.style.AppThemeBlack_ActionBar_r);
        } else {
            setTheme(R.style.AppTheme_ActionBar_r);
        }
    }



    public void rebooter_r(View view){
        Intent Rebooter_R = new Intent(ToolsActivity.this, rebooter_r_Activity.class);
        ToolsActivity.this.startActivity(Rebooter_R);
    }

    public void unitconv_g(View view){
        Intent UnitConv_G = new Intent(ToolsActivity.this, unitconv_g_Activity.class);
        ToolsActivity.this.startActivity(UnitConv_G);
    }
}
