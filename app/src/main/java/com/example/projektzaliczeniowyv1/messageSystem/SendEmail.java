package com.example.projektzaliczeniowyv1.messageSystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projektzaliczeniowyv1.R;
import com.google.android.material.textfield.TextInputEditText;

public class SendEmail extends AppCompatActivity {
    TextInputEditText emailAddress;
    TextInputEditText emailTitle;
    TextInputEditText emailBody;
    Button sendEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        sendEmail = findViewById(R.id.buttonEMAIL);
        emailAddress = findViewById(R.id.emailAddress);
        emailTitle = findViewById(R.id.emailTitle);
        emailBody = findViewById(R.id.emailBody);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail();
            }
        });

    }
    public void sendMail(){
        String email = emailAddress.getText().toString();
        String subject = emailTitle.getText().toString();
        String body = emailBody.getText().toString();

        Intent mailIntent = new Intent(Intent.ACTION_SEND);

        mailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{email});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT,body);
        mailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(mailIntent, "Choose an Email client :"));

    }
}