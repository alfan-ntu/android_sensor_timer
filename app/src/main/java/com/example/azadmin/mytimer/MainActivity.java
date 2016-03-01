package com.example.azadmin.mytimer;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
        private SensorManager mSensorManager;
        private Sensor mSensor;
        ImageView iv;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_main);
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            iv = (ImageView) findViewById(R.id.imageView1);
            Toast.makeText(this, "Sensor Manager activity started", Toast.LENGTH_LONG).show();

        }

        @Override
        protected void onResume() {
            super.onResume();
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        @Override
        protected void onPause() {
            super.onPause();
            mSensorManager.unregisterListener(this);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.values[0] == 0){
                iv.setImageResource(R.drawable.near);
            } else {
                iv.setImageResource(R.drawable.far);
            }
        }

}

