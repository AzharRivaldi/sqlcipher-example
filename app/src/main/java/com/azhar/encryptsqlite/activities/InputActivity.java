package com.azhar.encryptsqlite.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.azhar.encryptsqlite.R;
import com.azhar.encryptsqlite.database.DatabaseHelper;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class InputActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText etNama, etNoHP;
    ExtendedFloatingActionButton fabCreate;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        databaseHelper = new DatabaseHelper(this);

        etNama = findViewById(R.id.etNama);
        etNoHP = findViewById(R.id.etNoHP);
        fabCreate = findViewById(R.id.fabCreate);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        fabCreate.setOnClickListener(v -> {
            if (etNama.getText().toString().isEmpty()) {
                Toast.makeText(InputActivity.this, "Nama harus diisi!",
                        Toast.LENGTH_SHORT).show();
            } else if (etNoHP.getText().toString().isEmpty()) {
                Toast.makeText(InputActivity.this, "Telepon harus diisi!",
                        Toast.LENGTH_SHORT).show();
            } else {
                databaseHelper = DatabaseHelper.getInstance(InputActivity.this);
                databaseHelper.addUserDetail(etNama.getText().toString(), etNoHP.getText().toString());
                etNama.setText("");
                etNoHP.setText("");
                Toast.makeText(InputActivity.this, "Data berhasil disimpan!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}