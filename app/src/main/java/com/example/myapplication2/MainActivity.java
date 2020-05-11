package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import GPSTracker.GPSTracker;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensorL, sensorG;
    private Button getGPSBtn;

    TextView id_example1, id_example2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorL = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorG = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


         id_example1 = findViewById(R.id.textView1);
         id_example2 = findViewById(R.id.textView2);

         if(sensorL != null){
             sensorManager.registerListener(MainActivity.this,sensorL,SensorManager.SENSOR_DELAY_NORMAL);
         }
         else{
             id_example1.setText("Sensor not Supported");
         }

         if(sensorG != null){
             sensorManager.registerListener(MainActivity.this, sensorG,SensorManager.SENSOR_DELAY_NORMAL);
         }
         else{
             id_example2.setText("Sensor not Supported");
         }
        getGPSBtn = (Button) findViewById(R.id.getGPSBtn);
        ActivityCompat.requestPermissions(MainActivity.this, new
                String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 123);
        getGPSBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GPSTracker g = new GPSTracker(getApplicationContext());
                Location l = g.getLocation();
                if (l != null) {
                    double lat = l.getLatitude();
                    double longi = l.getLongitude();
                    Toast.makeText(getApplicationContext(), "LAT: " + lat + "LONG: " +
                            longi, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(sensorL.getType() == Sensor.TYPE_LIGHT && event.sensor.getType() == Sensor.TYPE_LIGHT){
            id_example1.setText("light intensity: "+ event.values[0]);
        }
        if(sensorG.getType() == Sensor.TYPE_GYROSCOPE && event.sensor.getType() == Sensor.TYPE_GYROSCOPE){
            id_example2.setText("Gyroscope: "+ event.values[0]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


}
