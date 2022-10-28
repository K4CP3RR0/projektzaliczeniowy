package com.example.projektzaliczeniowyv1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    private static final String TAG = "sms1111";
    public static final String pdu_type = "pdus";
    private static String SMS = "android.provider.Telephony.SMS_RECEIVED";
    String message="";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(SMS)){
            Bundle bundle = intent.getExtras();
            SmsMessage[] smsMessages = null;
            String format = bundle.getString("format");
            Log.v(TAG,"format=" + format);
            Object[] pdus = (Object[]) bundle.get(pdu_type);

            if(pdus!=null){
                smsMessages = new SmsMessage[pdus.length];
                for (int i=0;i< smsMessages.length;i++){
                    smsMessages[i] = SmsMessage.createFromPdu(
                            (byte[]) pdus[i],
                            format
                    );
                    message += "Sms from: " + smsMessages[i].getOriginatingAddress();
                    message += smsMessages[i].getMessageBody() + "\n";
                    Log.v(TAG,"--------------------> message: " + message);
                    Toast.makeText(context, message,Toast.LENGTH_LONG).show();

                }
            }
        }
    }
}
