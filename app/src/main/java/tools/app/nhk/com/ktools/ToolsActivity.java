package tools.app.nhk.com.ktools;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.Map;

public class ToolsActivity extends AppCompatActivity {
    public DrawerLayout drawerLayout;
    public ListView drawerList;
    public String[] layers;
    private ActionBarDrawerToggle drawerToggle;
    private Map map;

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
    public void onBackPressed() {
        Intent ToolsBack = new Intent(ToolsActivity.this, MainActivity.class);
        ToolsActivity.this.startActivity(ToolsBack);
    }

    Intent webi = new Intent(Intent.ACTION_VIEW);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tools);
        final MainActivity MainActivity = new MainActivity();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        LinearLayout toolsg = (LinearLayout) findViewById(R.id.tools_general);
        LinearLayout toolsr = (LinearLayout) findViewById(R.id.tools_root);

        Bundle bundle = getIntent().getExtras();
        String to = bundle.getString("key");
        /*
        Intent intent = getIntent();
        String to = intent.getStringExtra("key");*/
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
        LinearLayout toolsg = (LinearLayout) findViewById(R.id.tools_general);
        LinearLayout toolsr = (LinearLayout) findViewById(R.id.tools_root);
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
        LinearLayout toolsg = (LinearLayout) findViewById(R.id.tools_general);
        LinearLayout toolsr = (LinearLayout) findViewById(R.id.tools_root);
        navigation.getMenu().getItem(1).setChecked(true);
        toolsr.setVisibility(View.VISIBLE);
        toolsr.setAlpha(0.0f);
        toolsr.animate().alpha(1.0f);
        toolsr.requestLayout();

        toolsg.setAlpha(1.0f);
        toolsg.animate().alpha(0.0f);
        toolsg.setVisibility(View.GONE);
    }
}
