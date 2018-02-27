package com.nhk.app.ktools;
 /**
  * Units Converter Activity
  */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class unitconv_g_Activity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:

                    return true;
                case R.id.navigation_notifications:

                    return true;
            }
            return false;
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
        setContentView(R.layout.activity_unitconv_g);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    Boolean show = false;
    Boolean hide = false;

    public void SH_Data(View view){
        final LinearLayout data_unitconv_g = (LinearLayout) findViewById(R.id.data_unitconv_g);
        Button SH_Data_btn = (Button) findViewById(R.id.SH_Data_btn);

        if (SH_Data_btn.getText().equals(getString(R.string.Hide))) {
            SH_Data_btn.setText(R.string.Show);
            data_unitconv_g.setVisibility(View.VISIBLE);
            data_unitconv_g.setAlpha(0.0f);
            data_unitconv_g.animate().alpha(1.0f);

        } else {
            SH_Data_btn.setText(R.string.Hide);
            data_unitconv_g.setAlpha(1.0f);
            data_unitconv_g.animate().alpha(0.0f);
            data_unitconv_g.setVisibility(View.GONE);

        }
    }
    public void SHAll(View view){
        Button SHAll_btn = (Button) findViewById(R.id.SHAll_btn);

        if (SHAll_btn.getText().equals(getString(R.string.HideAll))) {
            hide = true;
            SHAll_btn.setText(R.string.ShowAll);
        } else {
            show = true;
            SHAll_btn.setText(R.string.HideAll);
        }
        HideShow(show,hide);
    }
    public void HideShow(Boolean show, Boolean hide) {
        Button SH_Data_btn = (Button) findViewById(R.id.SH_Data_btn);

        if (show){
            SH_Data_btn.setText(R.string.Show);
            SH_Data_btn.callOnClick();

            //Show more when exiting
        } else if (hide){
            SH_Data_btn.setText(R.string.Hide);
            SH_Data_btn.callOnClick();

            //Hide more when exiting
        }

        //Reset for later usage
        show = false;
        hide = false;
    }


}
