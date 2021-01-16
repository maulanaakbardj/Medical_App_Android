package com.ujang.medical.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ujang.medical.R;

public class BookDoctorDua extends AppCompatActivity {
Button chiti,bokii;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_a);
        chiti=findViewById(R.id.chiti);
        chiti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWhatsapp();
            }
        });
        bokii=findViewById(R.id.bokii);
        bokii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BookDoctorDua.this,BookDoktorActivity_a.class);
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
