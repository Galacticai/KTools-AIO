package tools.app.nhk.com.ktools;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Timer;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import static tools.app.nhk.com.ktools.R.id.MainL;
import static tools.app.nhk.com.ktools.R.id.navigation_general;
import static tools.app.nhk.com.ktools.R.id.navigation_root;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView.setNavigationItemSelectedListener(this);


        final TextView infotxt = (TextView) findViewById(R.id.info_txt);
        final TextView MoreRootInfo = (TextView) findViewById(R.id.MoreRootInfo);
        final Button btn_general = (Button) findViewById(R.id.btn_general);
        final Button btn_root = (Button) findViewById(R.id.btn_root);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);


        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Snackbar.make(view, R.string.contact_dev, Snackbar.LENGTH_LONG).setAction("Action", null).show();
                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("mailto:noor.hotmail.h.1@gmail.com"));
                startActivity(i);
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


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
                String root = null;
                String general = "general";
                SwitchToTools(general, root);

            }
        });
        btn_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String general = null;
                String root = "root";
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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

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
        if (id == R.id.nav_share) {
            shareApplication();
        } else if (id == R.id.nav_send) {
            webi.setData(Uri.parse("mailto:noor.hotmail.h.1@gmail.com"));
            startActivity(webi);

        } else if (id == R.id.restartkt){
            RestartKT();
        } else if (id == R.id.nav_main) {
            SwitchToMain();
        } else if (id == R.id.nav_tools) {
            String general = "general";
            String root = null;
            SwitchToTools(general,root);
        } else if (id == R.id.nav_xda) {
            final AlertDialog.Builder notYet  = new AlertDialog.Builder(this);
            notYet.setMessage("Not yet \uD83D\uDE1C");
            notYet.setTitle(R.string.xdaVisit);
            notYet.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            notYet.setCancelable(true);
            notYet.create().show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

    public void SwitchToMain(){
        RestartKT();
    }

    public void SwitchToTools(String general, String root){
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.getMenu().getItem(1).setChecked(true);
        LinearLayout MainL = (LinearLayout) findViewById(R.id.MainL);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        MainL.setAlpha(1.0f);
        MainL.animate().translationY(-MainL.getHeight()).alpha(0.0f);
        MainL.setVisibility(View.GONE);
        fab.setVisibility(View.GONE);

        Intent SwitchTools = new Intent(MainActivity.this, ToolsActivity.class);

        if (general != null && general.equals("general")){
            SwitchTools.putExtra("key", "general");
        } else if(root != null && root.equals("root")){
            SwitchTools.putExtra("key","root");
        }

        MainActivity.this.startActivity(SwitchTools);

    }
    public void RestartKT(){
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
