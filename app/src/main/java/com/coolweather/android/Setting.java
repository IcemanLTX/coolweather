package com.coolweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coolweather.android.service.AutoUpdateService;

public class Setting extends AppCompatActivity {
    private EditText editText;
    private Button onoff;
    private TextView text;
    private Button enter;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        onoff=(Button)findViewById(R.id.style);
        editText=(EditText)findViewById(R.id.time);
        enter = (Button)findViewById(R.id.enter);
        SharedPreferences shar = PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = shar.edit();
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(true);
        text = (TextView)findViewById(R.id.text2);
        if(shar.getInt("type",0)==1){
            onoff.setText("ON");
            enter.setVisibility(View.VISIBLE);
            editText.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
        }else{
            onoff.setText("OFF");
            enter.setVisibility(View.GONE);
            editText.setVisibility(View.GONE);
            text.setVisibility(View.GONE);
        }
        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onoff.getText().equals("ON")){
                    enter.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    editor.putInt("type",0);
                    editor.apply();
                    onoff.setText("OFF");
                    Intent intent = new Intent(Setting.this,AutoUpdateService.class);
                    stopService(intent);

                }else{
                    onoff.setText("ON");
                    enter.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    text.setVisibility(View.VISIBLE);
                    editor.putInt("type",1);
                    editor.apply();
                    Intent intent = new Intent(Setting.this,AutoUpdateService.class);
                    startService(intent);

                }
            }
        });
        enter.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                try{
                    if(onoff.getText().equals("ON")){
                        Intent intent = new Intent(Setting.this,AutoUpdateService.class);
                        stopService(intent);
                        int i= Integer.parseInt(editText.getText().toString());
                        editor.putInt("time", i);
                        editor.apply();
                        startService(intent);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
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
