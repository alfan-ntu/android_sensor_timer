package com.example.azadmin.mytimer;

import android.app.Activity;
import android.graphics.PointF;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class QRCodeReaderActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    private TextView myTextView;
    private QRCodeReaderView myQRDecoderView;
    private ToneGenerator toneG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_reader);

        myQRDecoderView = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        myQRDecoderView.setOnQRCodeReadListener(this);

        myTextView = (TextView) findViewById(R.id.exampleTextView);
        toneG = new ToneGenerator(AudioManager.STREAM_DTMF, 100);

        Log.d("Maoyi Debug", "qrCodeReaderActivity successfully initialized");
 /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        String qrCodeString;
        toneG.startTone(ToneGenerator.TONE_DTMF_0, 200);

        qrCodeString = text.substring(0, 10);
        myTextView.setText(qrCodeString);
//        myTextView.setText(text);

/*
    Examine QRCode contents here
 */

    }

    // Called when your device have no camera
    @Override
    public void cameraNotFound() {

    }

    // Called when there's no QR codes in the camera preview image
    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        myQRDecoderView.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myQRDecoderView.getCameraManager().stopPreview();
    }


}
