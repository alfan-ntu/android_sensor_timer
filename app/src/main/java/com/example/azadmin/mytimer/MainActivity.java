package com.example.azadmin.mytimer;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements SensorEventListener {
        private SensorManager mSensorManager;
        private Sensor mSensor;
        private ImageView iv;
        private TextView timeElapsedView;
        private boolean timerHasStarted = false;
        private Button buttonStartReset;

        private final long startTime = 50000;
        private final long interval = 1000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.content_main);
/*
    Demonstrate how to initiate a SENSOR_SERVICE, PROXIMITY sensor in this case
 */
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
            iv = (ImageView) findViewById(R.id.imageView1);
            buttonStartReset = (Button) findViewById(R.id.buttonReset);
            buttonStartReset.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(!timerHasStarted) {
                            timerHasStarted = true;
                            buttonStartReset.setText("START TIMER");
                        } else {
                            timerHasStarted = false;
                            buttonStartReset.setText("RESET TIMER");
                        }
                    }
                }
            );

            timeElapsedView = (TextView) findViewById(R.id.timeElapsed);

            Toast.makeText(this, "Sensor Manager and CountDown timer initiated",
                    Toast.LENGTH_LONG).show();

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
/*
    examining SensorEvent
 */
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.values[0] == 0){
                iv.setImageResource(R.drawable.near);
            } else {
                iv.setImageResource(R.drawable.far);
            }
        }

}

