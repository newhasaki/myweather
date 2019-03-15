package com.hask.pc.weather;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.hask.pc.weather.service.AutoUpdateService;
import java.util.List;

public class SetActivity extends AppCompatActivity {
    private static final String TAG = "SetActivity";
    private CheckBox set_check;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        Button set_back = (Button)findViewById(R.id.set_back);
         set_check = (CheckBox)findViewById(R.id.check_box);

        Intent intent = new Intent(this,AutoUpdateService.class);

        SharedPreferences prefs = getSharedPreferences("seting",MODE_PRIVATE);
        String ischecked = prefs.getString("startService",null);

        if("ok".equals(ischecked)){
            set_check.setChecked(true);
        }else{
            set_check.setChecked(false);
        }

        Log.d(TAG, "onCreate: " + ischecked);

         set_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Intent intent = new Intent(SetActivity.this,AutoUpdateService.class);
                SharedPreferences.Editor editor = getSharedPreferences("seting",MODE_PRIVATE).edit();
                if(isChecked){
                    if( isServiceRunning("com.hask.pc.weather.service.AutoUpdateService")) {
                        return;
                    }
                    editor.putString("startService", "ok");
                    startService(intent);
                }else{
                    editor.putString("startService","no");
                    stopService(intent);
                }
                editor.commit();
            }
        });

        set_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean isServiceRunning(final String className) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> info = activityManager.getRunningServices(Integer.MAX_VALUE);
        if (info == null || info.size() == 0) return false;
        for (ActivityManager.RunningServiceInfo aInfo : info) {
            if (className.equals(aInfo.service.getClassName())) return true;
        }
        return false;
    }
}
