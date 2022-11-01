package com.example.nextpage;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSms extends AppCompatActivity {

    //Initialize variable
    EditText etPhoneNumber, etSms;
    Button btnSend, btnClear;

    public final static String PHONENUM ="com.exampe.nextpage.PHONENUM";
    public final static String MSG  ="com.example.nextpage.MSG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        etPhoneNumber = findViewById(R.id.etPhoneNum);
        etSms = findViewById(R.id.etSms);
        btnSend = findViewById(R.id.btnSend);
        btnClear = findViewById(R.id.btnClear);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etPhoneNumber.setText("");
                etSms.setText("");
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check condition for permission
                if (ContextCompat.checkSelfPermission(SendSms.this, Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {
                    //When Permission is granted
                    //Create method
                    sendSMS();
                } else {
                    //when permission is not granted
                    //request for permission
                    ActivityCompat.requestPermissions(SendSms.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,@NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        //check condition
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //Permission is granted
            //call method
            sendSMS();

        }else {
            //when permission is denied
            //displayn toast msg
            Toast.makeText(this,"Permission Denied!", Toast.LENGTH_SHORT).show();

        }

    }

            private void sendSMS() {
                //get value from et
                String phone = etPhoneNumber.getText().toString();
                String message = etSms.getText().toString();

                //check condtion if string is empty or not
                if(!phone.isEmpty()&& !message.isEmpty()){
                    //initialize Sms manager
                    SmsManager smsManager = SmsManager.getDefault();
                    //Send sms
                    smsManager.sendTextMessage(phone,null,message,null,null);
                    //display Toast

                    Toast.makeText(this,"Number Phone : "+phone+"\nMessage : "+message, Toast.LENGTH_SHORT).show();

                    String phonenum = etPhoneNumber.getText().toString();
                    String msg = etSms.getText().toString();



                    Intent intent = new Intent(this,ReceiveSms.class);
                        intent.putExtra(PHONENUM,phonenum);
                        intent.putExtra(MSG,msg);
                        startActivity(intent);


                }else{
                    //when string is empty toast msg

                    Toast.makeText(this,"Please enter phone and message", Toast.LENGTH_SHORT).show();
                }
            }

        };






