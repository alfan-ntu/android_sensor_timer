package com.example.azadmin.mytimer;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
        private SensorManager mSensorManager;
        private Sensor mSensor;
        private ImageView iv;
        private TextView timeElapsedView;
        private TextView timeRemainView;
        private boolean timerHasStarted = false;
        private Button buttonStartReset;
        private MyCountDownTimer myCountDownTimer;

        private EditText timerValueEditText;
        private long startTimeInSec;

        private long timeElapsed;
        private final long startTime = 50000;
        private final long interval = 1000;
        private static final int REQUEST_TAKE_PHOTO = 1;

/*
    Per http://developer.android.com/reference/android/app/Activity.html
    onCreate(Bundle) is where you initialize your activity. Most importantly, here you will usually
    call setContentView(int) with a layout resource defining your UI, and using findViewById(int)
    to retrieve the widgets in that UI that you need to interact with programmatically.
*/

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

            timerValueEditText = (EditText) findViewById(R.id.timerValueField);
            buttonStartReset = (Button) findViewById(R.id.buttonReset);
            buttonStartReset.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        if(!timerHasStarted) {
                            startTimeInSec = Integer.parseInt(timerValueEditText.getText().toString());
                            Log.d("Start Time in Sec.", timerValueEditText.getText().toString());

                            myCountDownTimer = new MyCountDownTimer(startTimeInSec * 1000, interval);

                            myCountDownTimer.start();
                            timerHasStarted = true;
                            buttonStartReset.setText("PAUSE TIMER");

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

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_settings){
            Toast.makeText(this, "Setting menu...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_help){
            Toast.makeText(this, "Help menu...", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_camera) {
            Toast.makeText(this, "Camera...", Toast.LENGTH_SHORT).show();
/*
    Demonstrate how to capture image
 */
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            Log.d("maoyi debug", MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return super.onOptionsItemSelected(item);

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
                timeElapsedView.setText("Time Elapsed: " + String.valueOf(startTimeInSec / 1000) + " sec");
            }

            @Override
            public void onTick(long millisUntilFinished) {
                timeRemainView.setText("Time remain: " + String.valueOf(millisUntilFinished / 1000) + " sec" );
                timeElapsed = startTimeInSec * 1000 - millisUntilFinished;
                timeElapsedView.setText("time elapsed: " + String.valueOf(timeElapsed / 1000) + " sec");
            }
        }

}

