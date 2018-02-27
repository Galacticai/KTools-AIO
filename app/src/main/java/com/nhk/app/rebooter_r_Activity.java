package com.nhk.app.ktools;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;

public class rebooter_r_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sharedPreferences.getString("theme", "Light").equals("Material Dark")) {
            setTheme(R.style.AppThemeMaterialDark_r);
        }else if (sharedPreferences.getString("theme", "Light").equals("Holo Dark")) {
            setTheme(R.style.AppThemeHoloDark_r);
        } else if (sharedPreferences.getString("theme", "Light").equals("Black (For AMOLED)")){
            setTheme(R.style.AppThemeBlack_r);
        } else {
            setTheme(R.style.AppTheme_r);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rebooter_r);

    }

    public void Regular_Restart(View view){
        String doing = "reboot";
        String command = "reboot";
        executeShellCommand(command,doing);
    }

    public void Hot_Reboot(View view){
        String doing = "hot-reboot";
        String command = "su -c setprop ctl.restart zygote";
        executeShellCommand(command,doing);
    }

    public void Safe_Mode(View view){
        String doing = "reboot into safe mode";
        String command = "su -c setprop persist.sys.safemode 1 && su -c setprop ctl.restart zygote";
        executeShellCommand(command,doing);
    }
    public void Recovery(View view){
        String doing = "reboot into recovery mode";
        String command = "reboot recovery";
        executeShellCommand(command,doing);
    }
    public void Download(View view){
        String doing = "reboot into download mode";
        String command = "reboot download";
        executeShellCommand(command,doing);
    }

    public void Shutdown(View view){
        String doing = "shutdown";
        String command = "reboot -p";
        executeShellCommand(command,doing);
    }

    public void executeShellCommand(String command, String doing){
        final String commandInner = command;
        final AlertDialog.Builder sure  = new AlertDialog.Builder(this);
        sure.setMessage("Are you sure you want to " + doing + "?");
        sure.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                try{
                    Runtime.getRuntime().exec(new String[]{"su","-c",commandInner});
                }catch (IOException e){
                    toast_Error(e);
                }
            }
        });
        sure.setNegativeButton("Cancel", null);
        sure.show();
    }

    public void toast_Error(IOException e){
        Toast.makeText(getApplicationContext(), getString(R.string.error) + "\n" + e.getMessage(), Toast.LENGTH_LONG).show();
    }
}
