package com.ujang.medical.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ujang.medical.R;
import com.ujang.medical.adapter.AlertDialogManager;
import com.ujang.medical.session.SessionManager;

public class MainDoctorActivity extends AppCompatActivity {

    AlertDialogManager alert = new AlertDialogManager();
    SessionManager session;
    Button btnLogout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doctor);

        session = new SessionManager(getApplicationContext());
        session.checkLogin();

        btnLogout = findViewById(R.id.out);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                AlertDialog dialog = new AlertDialog.Builder(MainDoctorActivity.this)
                        .setTitle("Anda yakin ingin keluar ?")
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                session.logoutUser();
                            }
                        })
                        .setNegativeButton("Tidak", null)
                        .create();
                dialog.show();
            }
        });
    }

    public void profile1(View v) {
        Intent i = new Intent(this, BookDoctorSatu.class);
        startActivity(i);
    }

    public void profile2(View v) {
        Intent i = new Intent(this, BookDoctorDua.class);
        startActivity(i);
    }


    public void profile3(View v) {
        Intent i = new Intent(this, BookDoctorTiga.class);
        startActivity(i);
    }


    public void profile4(View v) {
        Intent i = new Intent(this, BookDoctorEmpat.class);
        startActivity(i);
    }
}
