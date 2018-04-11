package com.coolweather.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.coolweather.android.service.AutoUpdateService;

public class Setting extends AppCompatActivity {
    private EditText editText;
    private Button back;
    private Button onoff;
    private TextView text;
    private Button enter;
    private TextView title;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        back=(Button)findViewById(R.id.back_button);
        onoff=(Button)findViewById(R.id.style);
        editText=(EditText)findViewById(R.id.time);
        enter = (Button)findViewById(R.id.enter);
        final SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(this).edit();
        back.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                finish();
            }
        });
        text = (TextView)findViewById(R.id.text2);

        onoff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onoff.getText().equals("ON")){
                    enter.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    onoff.setText("OFF");
                    Intent intent = new Intent(Setting.this,AutoUpdateService.class);
                    stopService(intent);
                }else{
                    onoff.setText("ON");
                    enter.setVisibility(View.VISIBLE);
                    editText.setVisibility(View.VISIBLE);
                    text.setVisibility(View.VISIBLE);
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
}
