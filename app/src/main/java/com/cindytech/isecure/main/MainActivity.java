package com.cindytech.isecure.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.cindytech.isecure.database.DataBaseHelper;
import com.cindytech.isecure.model.Model;
import com.cindytech.isecure.settings.Settings;
import com.example.isecure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    //references to buttons and other controls on the layout
    Button btn_add, btn_save, btn_settings , btn_arm, btn_disarm, btn_status, btn_night, btn_day, btn_time, btn_enable, btn_disable, settings;
    TextView tv_lastarm , tv_lastdisarm, tv_last_events;
    FrameLayout fl;
    BottomNavigationView bnv;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Log.d("info", "MAIN CLASS");

        fl = findViewById(R.id.frame_layout);
        bnv = findViewById(R.id.bottomNavigationView);

        bnv.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    goHome();
                    break;
                case R.id.profile:
                    break;
                case R.id.settings:
                    goSetting();
                    break;
            }
            return true;
        });

        //Give permission to Read SMS
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        tv_lastarm = findViewById(R.id.tv_lastarm);
        tv_lastdisarm = findViewById(R.id.tv_lastdisarm);
        tv_last_events = findViewById(R.id.tv_last_events);
        displaySmsLog();
        Read_SMS();

        btn_arm = findViewById(R.id.btn_arm);
        btn_disarm = findViewById(R.id.btn_disarm);
        btn_status = findViewById(R.id.btn_status);
        btn_night = findViewById(R.id.btn_night);
        btn_day = findViewById(R.id.btn_day);
        btn_enable = findViewById(R.id.btn_enable);
        btn_disable = findViewById(R.id.btn_disable);
        btn_time =  findViewById(R.id.btn_time);
        //settings = findViewById(R.id.settings);

        //ARM Button Action
        btn_arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getArm();
                    //When permission is granted
                    //Create method
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }

            }
        });

        //Disarm Button Action
        btn_disarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getDisarm();
                    //When permission is granted
                    //Create method
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        //Status Button Action
        btn_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create method
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getStatus();
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        //Night Button Action
        btn_night.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create method
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getNight();
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }

            }
        });

        //Day Button Action
        btn_day.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create method
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getDay();
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        //ENABLE Button Action
        btn_enable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create method
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getEnable();
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        //DISABLE Button Action
        btn_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create method
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getDisable();
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });

        //TIME Button Action
        btn_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create method
                    //Get data from DB
                    Model model = getModel();
                    String sPhone = model.getNumber();
                    String command = model.getTime();
                    sendMessage(sPhone, command);
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });
    }

    private void sendMessage(String sPhone, String command) {

        //Get values from edit text
        //String sPhone = model.getNumber();
        //String sMessage = model.getArm();

        //String sPhone = "00355682828008";
        //String sMessage = "ARM";

        //check condition
        if(!sPhone.equals("") && !command.equals("")){
            //When both edit text value not equal to blank
            //Initialize sms manager
            SmsManager smsManager = SmsManager.getDefault();
            //Send text message
            smsManager.sendTextMessage(sPhone, null, command, null, null);
            //Display toast
            Toast.makeText(getApplicationContext(), "SMS sent succesfully!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), "Enter value first!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Check condition
        if(requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //Get data from DB
            Model model = getModel();
            String sPhone = model.getNumber();
            String command = model.getDisarm();
            //When perission is granted
            sendMessage(sPhone, command);
        } else {
            //When permission is denied
            //Display toast
            //Toast.makeText(getApplicationContext(), , Toast.LENGTH_SHORT).show();
            //Toast.makeText(getApplicationContext(), "Permission Denied ....!", Toast.LENGTH_SHORT).show();
        }
    }

    //button listeners
    public void sendArm(View v){
        //Get the SmsManager instance and call the sendTextMessage method to send message

        Log.d("Sms","PRIMA");
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

        Log.d("Sms","MENTRE");
        SmsManager sms= SmsManager.getDefault();
        sms.sendTextMessage("null", "null", "ARM", pi,null);

        Toast.makeText(MainActivity.this, "Message sended", Toast.LENGTH_SHORT).show();
        Log.d("SMS","message sended");
    }


    //read Sms method
    public void Read_SMS(){
        Log.d("SMS","prima");
        Cursor cursor = getContentResolver().query(Uri.parse("content://sms/sent"), null, null,null,null);
        cursor.moveToFirst();
        Log.d("SMS","mezzo");

        //tv_last_events.setText(cursor.getString(12));
        //tv_lastdisarm.setText(cursor.getString(9));

        Log.d("SMS","dopo");

    }

    private void displaySmsLog() {
        Uri allMessages = Uri.parse("content://sms/");
        //Cursor cursor = managedQuery(allMessages, null, null, null, null); Both are same
        Cursor cursor = this.getContentResolver().query(Uri.parse("content://sms/sent"), null,
                null, null, null);
        cursor.moveToFirst();

        StringBuilder strBuilder = new StringBuilder();
        /*while (cursor.moveToFirst()) {
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                Log.d(cursor.getColumnName(i) + " READ SMS", cursor.getString(i) + "");
                Log.d("PLUTO", "PRIMA");

                if(i==2){
                    Log.d("PLUTO", cursor.getString(i) + " if==2");
                    strBuilder.append("LAST SMS NUMBER " +cursor.getString(2) +"\n");
                }
                if(i==12){
                    Log.d("PLUTO", cursor.getString(i) + " if==12");
                    strBuilder.append("LAST SMS TEXT " +cursor.getString(12) +"\n");
                }
                Log.d("PLUTO", strBuilder.toString() + " toString");

            }
            Log.d("One row finished",
                    "**************************************************");
        }*/
        strBuilder.append("LAST SMS NUMBER  --> | " +cursor.getString(2) +"\n");
        strBuilder.append("LAST SMS COMAND --> | " +cursor.getString(12) +"\n");
        tv_last_events.setText(strBuilder.toString());

    }


    //Get data from DB
    public Model getModel(){
        //get data from DB
        Model model = new Model();
        Cursor res = dataBaseHelper.uploadSettings();
        while (res.moveToNext()) {

            model.setNumber(res.getString(1));
            model.setArm(res.getString(2));
            model.setDisarm(res.getString(3));
            model.setNight(res.getString(4));
            model.setDay(res.getString(5));
            model.setStatus(res.getString(6));
            model.setEnable(res.getString(7));
            model.setDisable(res.getString(8));
            model.setTime(res.getString(9));
            model.setPassword(res.getString(10));
        }
        return model;
    }

    //Go SETTINGS
    public void goToSetting(View v){
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    //Go SETTINGS
    public void goSetting(){
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

    //Go to Home
    public void goHome(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

}