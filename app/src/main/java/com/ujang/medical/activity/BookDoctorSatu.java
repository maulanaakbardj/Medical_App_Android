package com.ujang.medical.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ujang.medical.R;

public class BookDoctorSatu extends AppCompatActivity {
Button chit,boki;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        chit=findViewById(R.id.chit);
        chit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsapp();
            }
        });
        boki=findViewById(R.id.boki);
        boki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookDoctorSatu.this,BookDoktorActivity.class);
                startActivity(i);
            }
        });
    }
    private void openWhatsapp(){
        String number = "6282112271288"; //without "+"
        try{
            Intent sendWA = new Intent("android.intent.action.MAIN");
            sendWA.setAction(Intent.ACTION_SEND);
            sendWA.setType("text/palain");
            sendWA.putExtra(Intent.EXTRA_TEXT, "Hallo Dokter");
            sendWA.putExtra("jid",number+"@s.whatsapp.net");
            sendWA.setPackage("com.whatsapp");
            startActivity(sendWA);
        }
        catch (Exception e){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}
