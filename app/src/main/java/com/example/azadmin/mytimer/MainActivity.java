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
        private TextView timeRemainView;
        private boolean timerHasStarted = false;
        private Button buttonStartReset;
        private MyCountDownTimer myCountDownTimer;

        private long timeElapsed;
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
/*
    Demonstrate how to setup a CountDownTimer
 */

            myCountDownTimer = new MyCountDownTimer(startTime, interval);

            buttonStartReset = (Button) findViewById(R.id.buttonReset);
            buttonStartReset.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(!timerHasStarted) {
                            myCountDownTimer.start();
                            timerHasStarted = true;
                            buttonStartReset.setText("START TIMER");
                        } else {
                            myCountDownTimer.cancel();
                            timerHasStarted = false;
                            buttonStartReset.setText("RESET TIMER");
                        }
                    }
                }
            );

            timeElapsedView = (TextView) findViewById(R.id.timeElapsed);
            timeRemainView = (TextView) findViewById(R.id.timeRemain);
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

/*
    CountDownTimer class
 */
        public class MyCountDownTimer extends CountDownTimer
        {
             public MyCountDownTimer(long startTime, long interval){
                 super(startTime, interval);
             }

            @Override
            public void onFinish() {
                timeRemainView.setText("Time's up!");
                timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTime / 1000) + " sec");
            }

            @Override
            public void onTick(long millisUntilFinished) {
                timeRemainView.setText("Time remain: " + String.valueOf(millisUntilFinished / 1000) + " sec" );
                timeElapsed = startTime - millisUntilFinished;
                timeElapsedView.setText("time elapsed: " + String.valueOf(timeElapsed / 1000) + " sec");
            }
        }

}

