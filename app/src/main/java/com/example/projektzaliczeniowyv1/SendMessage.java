package com.example.projektzaliczeniowyv1;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class SendMessage extends AppCompatActivity {
    private static final String TAGsms = "SMS1111";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 1;
    private static final int MY_PERMISSIONS_REQUEST_RECEIVE_SMS = 2;
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.putExtra("sms_body", "default content");
                    sendIntent.setType("vnd.android-dir/mms-sms");
                    startActivity(sendIntent);
                }});
    SmsManager smsManager;
    String destinationAddress = "";
    String scAddress = null;
    String text="";
    PendingIntent sentIntent = null;
    PendingIntent deliveryIntent;
    long messageId = 0;
    TextInputEditText phoneNumber;
    TextInputEditText smsMessage;
    Button sendSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_message);
        sendSms = findViewById(R.id.buttonSMS);
        phoneNumber = findViewById(R.id.phoneNumber);
        smsMessage = findViewById(R.id.destinationAddress);
        sendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        if (checkPermission(Manifest.permission.SEND_SMS)){
            destinationAddress = phoneNumber.getText().toString();
            text = smsMessage.getText().toString();
            if(!destinationAddress.equals("")&& !text.equals("")){
                smsManager = SmsManager.getDefault();

                smsManager.sendTextMessage(
                        destinationAddress,
                        null,
                        text,
                        null,
                        null
                );
                Toast.makeText(SendMessage.this,"SMS send", Toast.LENGTH_SHORT).show();
                Log.v(TAGsms,"Sms send");

            }
            else{
                Toast.makeText(SendMessage.this,"Permission denied", Toast.LENGTH_SHORT).show();
                Log.v(TAGsms,"Permission denied");
            }
        }
    }

    private boolean checkPermission(String sendSms) {

        if (ContextCompat.checkSelfPermission(this, sendSms) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissionLauncher.launch(
                    Manifest.permission.SEND_SMS);

        }
        return true;
    }

}