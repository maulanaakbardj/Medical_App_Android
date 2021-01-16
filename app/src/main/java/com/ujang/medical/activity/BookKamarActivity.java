package com.ujang.medical.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ujang.medical.R;
import com.ujang.medical.database.DatabaseHelper;
import com.ujang.medical.session.SessionManager;

import java.util.Calendar;
import java.util.HashMap;

public class BookKamarActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Spinner spinRmhsk, spinKelas, spinOrang, spinHari;
    SessionManager session;
    String email;
    int id_book;
    public String sRmhsk, sKelas, sTanggal, sOrang, sHari;
    int jmlOrang, jmlHari;
    int hargaOrang, hargaHari;
    int hargaTotalOrang, hargaTotalHari, hargaTotal;
    private EditText etTanggal;
    private DatePickerDialog dpTanggal;
    Calendar newCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_kamar);

        dbHelper = new DatabaseHelper(BookKamarActivity.this);
        db = dbHelper.getReadableDatabase();

        final String[] rmhsk = {"Rumah Sakit Mang Oleh", "Rumah Sakit Muara Tiga", "Rumah Sakit Pelek Mania", "Rumah Sakit Macaniyah"};
        final String[] kelas = {"VVIP", "VIP", "Zamrud", "Isolasi"};
        final String[] orang = {"0", "1", "2", "3", "4", "5"};
        final String[] hari = {"0", "1", "2", "3", "4", "5","6","7"};

        spinRmhsk = findViewById(R.id.rmhsk);
        spinKelas = findViewById(R.id.kelas);
        spinOrang = findViewById(R.id.orang);
        spinHari = findViewById(R.id.hari);

        ArrayAdapter<CharSequence> adapterRmhsk = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, rmhsk);
        adapterRmhsk.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinRmhsk.setAdapter(adapterRmhsk);

        ArrayAdapter<CharSequence> adapterKelas = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, kelas);
        adapterKelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKelas.setAdapter(adapterKelas);

        ArrayAdapter<CharSequence> adapterOrang = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, orang);
        adapterOrang.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinOrang.setAdapter(adapterOrang);

        ArrayAdapter<CharSequence> adapterHari = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, hari);
        adapterHari.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinHari.setAdapter(adapterHari);

        spinRmhsk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sRmhsk = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sKelas = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinOrang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sOrang = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinHari.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sHari = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnBook = findViewById(R.id.book);

        etTanggal = findViewById(R.id.tanggal_berangkat);
        etTanggal.setInputType(InputType.TYPE_NULL);
        etTanggal.requestFocus();
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);
        setDateTimeField();

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perhitunganHarga();
                if (sRmhsk != null && sKelas != null && sTanggal != null && sOrang != null) {
                    {
                        AlertDialog dialog = new AlertDialog.Builder(BookKamarActivity.this)
                                .setTitle("Ingin booking kamar rawat inap sekarang?")
                                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        try {
                                            db.execSQL("INSERT INTO TB_BOOK (rmhsk, kelas, tanggal, orang) VALUES ('" +
                                                    sRmhsk + "','" +
                                                    sKelas + "','" +
                                                    sTanggal + "','" +
                                                    sOrang + "');");
                                            cursor = db.rawQuery("SELECT id_book FROM TB_BOOK ORDER BY id_book DESC", null);
                                            cursor.moveToLast();
                                            if (cursor.getCount() > 0) {
                                                cursor.moveToPosition(0);
                                                id_book = cursor.getInt(0);
                                            }
                                            db.execSQL("INSERT INTO TB_HARGA (username, id_book, harga_orang, harga_total) VALUES ('" +
                                                    email + "','" +
                                                    id_book + "','" +
                                                    hargaTotalOrang + "','" +
                                                    hargaTotal + "');");
                                            Toast.makeText(BookKamarActivity.this, "Booking berhasil", Toast.LENGTH_LONG).show();
                                            finish();
                                        } catch (Exception e) {
                                            Toast.makeText(BookKamarActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                })
                                .setNegativeButton("Tidak", null)
                                .create();
                        dialog.show();
                    }
                } else {
                    Toast.makeText(BookKamarActivity.this, "Mohon lengkapi data pemesanan!", Toast.LENGTH_LONG).show();
                }
            }
        });

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbKrl);
        toolbar.setTitle("Form Booking");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void perhitunganHarga() {
        if (sRmhsk.equalsIgnoreCase("Rumah Sakit Mang Oleh") && sKelas.equalsIgnoreCase("VVIP")) {
            hargaOrang = 5000000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Mang Oleh") && sKelas.equalsIgnoreCase("VIP")) {
            hargaOrang = 3500000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Mang Oleh") && sKelas.equalsIgnoreCase("Zamrud")) {
            hargaOrang = 3000000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Mang Oleh") && sKelas.equalsIgnoreCase("Isolasi")) {
            hargaOrang = 2800000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Muara Tiga") && sKelas.equalsIgnoreCase("VVIP")) {
            hargaOrang = 4000000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Muara Tiga") && sKelas.equalsIgnoreCase("VIP")) {
            hargaOrang = 3000000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Muara Tiga") && sKelas.equalsIgnoreCase("Zamrud")) {
            hargaOrang = 2500000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Muara Tiga") && sKelas.equalsIgnoreCase("Isolasi")) {
            hargaOrang = 1800000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Pelek Mania") && sKelas.equalsIgnoreCase("VVIP")) {
            hargaOrang = 4500000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Pelek Mania") && sKelas.equalsIgnoreCase("VIP")) {
            hargaOrang = 3200000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Pelek Mania") && sKelas.equalsIgnoreCase("Zamrud")) {
            hargaOrang = 2600000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Pelek Mania") && sKelas.equalsIgnoreCase("Isolasi")) {
            hargaOrang = 2200000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Macaniyah") && sKelas.equalsIgnoreCase("VVIP")) {
            hargaOrang = 100000000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Macaniyah") && sKelas.equalsIgnoreCase("VIP")) {
            hargaOrang = 12000000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Macaniyah") && sKelas.equalsIgnoreCase("Zamrud")) {
            hargaOrang = 8000000;

        } else if (sRmhsk.equalsIgnoreCase("Rumah Sakit Macaniyah") && sKelas.equalsIgnoreCase("Isolasi")) {
            hargaOrang = 6700000;

        }

        jmlOrang = Integer.parseInt(sOrang);
        jmlHari = Integer.parseInt(sHari);

        hargaTotalOrang = jmlOrang * hargaOrang;
        hargaTotalHari = jmlHari ;
        hargaTotal = hargaTotalOrang + hargaTotalHari;
    }

    private void setDateTimeField() {
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpTanggal.show();
            }
        });

        dpTanggal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei",
                        "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
                sTanggal = dayOfMonth + " " + bulan[monthOfYear] + " " + year;
                etTanggal.setText(sTanggal);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}