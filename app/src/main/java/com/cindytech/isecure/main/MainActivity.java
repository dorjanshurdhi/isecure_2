package com.cindytech.isecure.main;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.cindytech.isecure.database.DataBaseHelper;
import com.cindytech.isecure.model.Model;
import com.example.isecure.R;

public class MainActivity extends AppCompatActivity {

    //references to buttons and other controls on the layout
    Button btn_add, btn_save, btn_settings , btn_arm, btn_disarm, btn_status;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        Log.d("info", "MAIN CLASS");

        btn_arm = findViewById(R.id.btn_arm);
        //btn_disarm = findViewById(R.id.btn_disarm);
        //btn_status = findViewById(R.id.btn_status);
        //btn_settings = findViewById(R.id.btn_settings);

        btn_arm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) ==
                        PackageManager.PERMISSION_GRANTED){
                    //When permission is granted
                    //Create method
                    sensMessage();
                } else {
                    //When permission is not granted
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }

            }
        });
    }

    private void sensMessage() {

        //get number from DB
        Model model = new Model();
        Cursor res = dataBaseHelper.uploadSettings();
        while (res.moveToNext()) {

            model.setNumber(res.getString(1));
            model.setArm(res.getString(2));
        }

        //Get values from edit text
        String sPhone = model.getNumber();
        String sMessage = model.getArm();

        //String sPhone = "00355682828008";
        //String sMessage = "ARM";

        //check condition
        if(!sPhone.equals("") && !sMessage.equals("")){
            //When both edit text value not equal to blank
            //Initialize sms manager
            SmsManager smsManager = SmsManager.getDefault();
            //Send text message
            smsManager.sendTextMessage(sPhone, null, sMessage, null, null);
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
            //When perission is granted
            sensMessage();
        } else {
            //When permission is denied
            //Display toast
            Toast.makeText(getApplicationContext(), "Permission Denied!", Toast.LENGTH_SHORT).show();
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




    //Go SETTINGS
    public void goToSetting(View v){
        Intent i = new Intent(this, Settings.class);
        startActivity(i);
    }

}