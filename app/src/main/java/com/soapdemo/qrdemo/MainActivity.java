package com.soapdemo.qrdemo;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> mGetQRCode = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                Intent data = result.getData();
                int resultCode = result.getResultCode();
                IntentResult scanResult = IntentIntegrator.parseActivityResult(IntentIntegrator.REQUEST_CODE, resultCode, data);
                if (scanResult != null) {
                    // handle scan result
                    Toast.makeText( getApplicationContext() , scanResult.toString(), Toast.LENGTH_SHORT).show();
                }
            });

    ActivityResultLauncher<String> mRequestPermission = registerForActivityResult( new ActivityResultContracts.RequestPermission() ,
            result -> {
                Toast.makeText( getApplicationContext() , result ? "同意,请重试":"拒绝", Toast.LENGTH_SHORT).show();
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findViewById(R.id.button).setOnClickListener( v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                IntentIntegrator integrator = new IntentIntegrator(this);
                integrator.setSingleTargetApplication(this.getPackageName());
                mGetQRCode.launch(integrator.initiateScanIntent(IntentIntegrator.QR_CODE_TYPES, -1));
            }
            else {
                mRequestPermission.launch( Manifest.permission.CAMERA );
            }
        } );
    }
}