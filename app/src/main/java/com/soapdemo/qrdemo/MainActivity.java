package com.soapdemo.qrdemo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findViewById(R.id.button).setOnClickListener( v -> {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setSingleTargetApplication( this.getPackageName() );
            integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES,-1);
        } );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanResult != null) {
            // handle scan result
            Toast.makeText(this, scanResult.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}