package com.coolweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;

import com.coolweather.android.service.AutoUpdateService;

/**
 * Created by Administrator on 2018/5/4/004.
 */

public class Settings extends PreferenceActivity implements Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener {
    String updateSwitchKey;
    String updateFrequencyKey;
    CheckBoxPreference updateSwitchCheckPref;
    ListPreference updateFrequencyListPref;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //定义相关变量
        addPreferencesFromResource(R.xml.setting);
        //获取各个Preference
        updateSwitchKey = getResources().getString(R.string.auto_update_switch_key);
        updateFrequencyKey = getResources().getString(R.string.auto_update_frequency_key);
        updateSwitchCheckPref = (CheckBoxPreference)findPreference(updateSwitchKey);
        updateFrequencyListPref = (ListPreference)findPreference(updateFrequencyKey);
        //为各个Preference注册监听接口
        updateSwitchCheckPref.setOnPreferenceChangeListener(this);
        updateSwitchCheckPref.setOnPreferenceClickListener(this);
        updateFrequencyListPref.setOnPreferenceChangeListener(this);
        updateFrequencyListPref.setOnPreferenceClickListener(this);
    }
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // TODO Auto-generated method stub
        // Log.v("SystemSetting", "preference is changed");
        //Log.v("Key_SystemSetting", preference.getKey());
        //判断是哪个Preference改变了
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        if(preference.getKey().equals(updateSwitchKey))
        {
            Intent intent = new Intent(this,AutoUpdateService.class);
            if(settings.getBoolean(updateSwitchKey,true)){
                stopService(intent);
            }else {
                startService(intent);
            }
        }
        else if(preference.getKey().equals(updateFrequencyKey))
        {
            Log.v("SystemSetting", "list preference is changed");
        }
        else
        {
            //如果返回false表示不允许被改变
            return false;
        }
        //返回true表示允许改变
        return true;
    }
    @Override
    public boolean onPreferenceClick(Preference preference) {
        // TODO Auto-generated method stub
        //Log.v("SystemSetting", "preference is clicked");
        //Log.v("Key_SystemSetting", preference.getKey());
        //判断是哪个Preference被点击了
        if(preference.getKey().equals(updateSwitchKey))
        {
            Log.v("SystemSetting", "checkbox preference is clicked");
        }
        else if(preference.getKey().equals(updateFrequencyKey))
        {
            Log.v("SystemSetting", "list preference is clicked");
        }
        else
        {
            return false;
        }
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
