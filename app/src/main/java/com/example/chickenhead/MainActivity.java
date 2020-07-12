package com.example.chickenhead;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SensorManager sensorManager;
    private Sensor sensor;
    private TriggerEventListener triggerEventListener;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final TextView head = findViewById(R.id.head);
        head.setText("hello tjun");

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        triggerEventListener = new TriggerEventListener() {
            @Override
            public void onTrigger(TriggerEvent event) {
                System.out.println("tjun: triggered");
                // Do work
            }
        };
        final float [] velocity = {0, 0, 0};
        final float [] location = {0, 0, 0};
        sensorManager.requestTriggerSensor(triggerEventListener, sensor);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                final float[] values = event.values;
                location[0] += velocity[0] * 1 + (velocity[0] += values[0]) * 0.5;
                location[1] += velocity[1] * 1 + (velocity[1] += values[1]) * 0.5;
                location[2] += velocity[2] * 1 + (velocity[2] += values[2]) * 0.5;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("new values: " + values);
                        head.setText(
                                "velocity values: %n" + String.format("%.3f%n, %.3f%n, %.3f%n", velocity[0], velocity[1], velocity[2]) +
                                "%n  location values: %n" + String.format("%.3f%n, %.3f%n, %.3f%n", location[0], location[1], location[2]));
                    }
                });
//                head.postInvalidate();
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, sensor, 1);
    }
}
