package com.nhk.app.ktools;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Initial state
    Boolean general = false;
    Boolean root = false;
    //

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
        } else if (sharedPreferences.getString("theme", "Light (Default)").equals("Light (Default)")) {
            setTheme(R.style.AppTheme);
        } else {
            Toast.makeText(getApplicationContext(), "Something went wrong while setting theme",Toast.LENGTH_LONG).show();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (sharedPreferences.getString("startup_set", "Home (Default)").equals("General Tools")) {
            root = true;
            SwitchToTools(general, root);
        } else if (sharedPreferences.getString("startup_set", "Home (Default)").equals("Root Tools")) {
            general = true;
            SwitchToTools(general, root);
        }

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        final TextView infotxt = (TextView) findViewById(R.id.info_txt);
        final TextView MoreRootInfo = (TextView) findViewById(R.id.MoreRootInfo);
        final Button btn_general = (Button) findViewById(R.id.btn_general);
        final Button btn_root = (Button) findViewById(R.id.btn_root);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Snackbar.make(view, R.string.Share, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareApplication();
            }
        });



        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            infotxt.setText(getResources().getString(R.string.app_name) + " " + getResources().getString(R.string.app_by) + ". Version: " + version);
        } catch (PackageManager.NameNotFoundException e) {
            Toast.makeText(getApplicationContext(),"Error!\n" + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        MoreRootInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://en.wikipedia.org/wiki/Rooting_(Android)"));
                startActivity(i);
            }
        });

        btn_general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean general = true;
                SwitchToTools(general, root);

            }
        });
        btn_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean root = true;
                SwitchToTools(general, root);
            }
        });

        LinearLayout MainL = (LinearLayout) findViewById(R.id.MainL);
        MainL.setVisibility(View.VISIBLE);
        fab.setVisibility(View.VISIBLE);
        MainL.setAlpha(0.0f);
        MainL.animate().alpha(1.0f);

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }
*/

    Intent webi = new Intent(Intent.ACTION_VIEW);
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        final AlertDialog.Builder msg  = new AlertDialog.Builder(this);
        final TextView info_txt = (TextView) findViewById(R.id.info_txt);

        if (id == R.id.nav_share) {
            shareApplication();
        } else if (id == R.id.nav_send) {
            webi.setData(Uri.parse("mailto:noor.hotmail.h.1@gmail.com"));
            startActivity(webi);

        } else if (id == R.id.restartkt){
            RestartKT();
        } else if (id == R.id.nav_main) {
            SwitchToHome();
        } else if (id == R.id.nav_tools) {
            SwitchToTools(general,root);
        } else if (id == R.id.nav_xda) {
            msg.setMessage(getString(R.string.notyet_xda) + info_txt.getText().toString());
            msg.setTitle(R.string.xdaVisit);
            msg.setCancelable(true);
            msg.create().show();
        } else if (id == R.id.About_btn) {
            msg.setMessage(getString(R.string.About_txt) + "\n\n" + info_txt.getText());
            msg.setTitle(R.string.About);
            msg.setCancelable(true);
            msg.create().show();
        } else if (id == R.id.settings_){
            Intent SettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(SettingsActivity);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Credits to StackOverflow.com :P ... I wanted it fast
    public void shareApplication() {
        ApplicationInfo app = getApplicationContext().getApplicationInfo();
        String filePath = app.sourceDir;

        Intent intent = new Intent(Intent.ACTION_SEND);

        // MIME of .apk is "application/vnd.android.package-archive".
        // but Bluetooth does not accept this. Let's use "*/*" instead.
        intent.setType("*/*");

        // Append file and send Intent
        File originalApk = new File(filePath);
        Toast.makeText(getApplicationContext(), R.string.Sharing, Toast.LENGTH_LONG).show();

        try {
            //Make new directory in new location
            File tempFile = new File(getExternalCacheDir() + "/ExtractedApk");
            //If directory doesn't exists create new
            if (!tempFile.isDirectory())
                if (!tempFile.mkdirs())
                    return;
            //Get application's name and convert to lowercase
            tempFile = new File(tempFile.getPath() + "/" + getString(app.labelRes).replace(" ","").toLowerCase() + ".apk");
            //If file doesn't exists create new
            if (!tempFile.exists()) {
                if (!tempFile.createNewFile()) {
                    return;
                }
            }
            //Copy file to new location
            InputStream in = new FileInputStream(originalApk);
            OutputStream out = new FileOutputStream(tempFile);

            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {out.write(buf, 0, len);}
            in.close();
            out.close();
            //Open share dialog
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
            startActivity(Intent.createChooser(intent, "Share KTools via..."));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Credits/

    public void SwitchToHome(){
        RestartKT();
    }

    public void SwitchToTools(Boolean general, Boolean root){
        Intent SwitchTools = new Intent(this, ToolsActivity.class);
        if (general){
            SwitchTools.putExtra("to", "general");
            startActivity(SwitchTools);
        } else if(root){
            SwitchTools.putExtra("to", "root");
            startActivity(SwitchTools);
        } else if (!general && !root){
            general = true;
            SwitchToTools(general,root);
        } else {
            Toast.makeText(getApplicationContext(), "Error choosing destination. Please contact the developer to fix this issue.", Toast.LENGTH_LONG).show();
            return;
        }

        //Reset to false for later usage
        general = false;
        root = false;
    }

    public void RestartKT(){
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
