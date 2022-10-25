package com.cindytech.isecure.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cindytech.isecure.database.DataBaseHelper;
import com.cindytech.isecure.main.MainActivity;
import com.cindytech.isecure.model.Model;
import com.example.isecure.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Settings extends AppCompatActivity {

    final private static String ID = "1";
    //references to buttons and other controls on the layout
    Button btn_save;
    EditText et_number, et_arm, et_disarm, et_night, et_day, et_status, et_enable, et_disable, et_time, et_password;
    DataBaseHelper dataBaseHelper = new DataBaseHelper(Settings.this);
    BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        //List all components
        btn_save = findViewById(R.id.btn_save);
        et_number = findViewById(R.id.et_number);
        et_arm = findViewById(R.id.et_arm);
        et_disarm = findViewById(R.id.et_disarm);
        et_night = findViewById(R.id.et_night);
        et_day = findViewById(R.id.et_day);
        et_status = findViewById(R.id.et_status);
        et_enable = findViewById(R.id.et_enable);
        et_disable = findViewById(R.id.et_disable);
        et_time = findViewById(R.id.et_time);
        et_password = findViewById(R.id.et_password);

        Cursor res = dataBaseHelper.uploadSettings();

        while (res.moveToNext()){
            Log.d("ID ", res.getString(0));
            Log.d("NUMBER ", res.getString(1));
            Log.d("ARM ", res.getString(2));
            Log.d("DISARM ", res.getString(3));
            Log.d("NIGHT ", res.getString(4));
            Log.d("DAY ", res.getString(5));
            Log.d("STATUS ", res.getString(6));
            Log.d("ENABLE ", res.getString(7));
            Log.d("DISABLE ", res.getString(8));
            Log.d("TIME ", res.getString(9));
            Log.d("PASSOWRD ", res.getString(10));

            Model model = new Model();

            model.setId(1);
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

            Log.d("Model to String", model.toString());

            et_number.setText(model.getNumber());
            et_arm.setText(model.getArm());
            et_disarm.setText(model.getDisarm());
            et_status.setText(model.getStatus());
            et_enable.setText(model.getEnable());
            et_disable.setText(model.getDisable());
            et_night.setText(model.getNight());
            et_day.setText(model.getDay());
            et_time.setText(model.getTime());
            et_password.setText(model.getPassword());
        }


        //button listeners
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String number = et_number.getText().toString();
                String arm = et_arm.getText().toString();
                String disarm = et_disarm.getText().toString();
                String night = et_night.getText().toString();
                String day = et_day.getText().toString();
                String status = et_status.getText().toString();
                String enable = et_enable.getText().toString();
                String disable = et_disable.getText().toString();
                String time = et_time.getText().toString();
                String password = et_password.getText().toString();

                Model model;
                try {
                    model = new Model(1, number, arm, disarm, night, day, status, enable, disable, time, password);
                    Log.d("info NUMBER", model.getNumber());
                    if (dataBaseHelper.firstLogin()==true) {
                        dataBaseHelper.addOne(model);
                        Toast.makeText(Settings.this, model.toString(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(Settings.this, "Saved", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Settings.this, "Updated", Toast.LENGTH_LONG).show();
                        dataBaseHelper.updateModel(model);
                    }
                } catch (Exception e){
                    Toast.makeText(Settings.this, "ERROR CREATING MODEL", Toast.LENGTH_SHORT).show();
                    model = new Model(-1, "error", "error"," error", "error", "error", "error", "error", "error", "error", "error");
                }

                //boolean success = dataBaseHelper.addOne(model);
                //dataBaseHelper.updateModel(model);
                //Toast.makeText(Settings.this, "SUCCESS", Toast.LENGTH_SHORT).show();
                goToHome();

                bnv = findViewById(R.id.bottomNavigationView);
                bnv.setOnItemSelectedListener(item -> {

                    switch (item.getItemId()){
                        case R.id.home:
                            goToHome();
                            break;
                        case R.id.profile:
                            break;
                        case R.id.settings:
                            //goSetting();
                            break;
                    }
                    return true;
                });
            }
        });
    }


    public void goToHome(){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}