package com.azhar.encryptsqlite.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.azhar.encryptsqlite.R;
import com.azhar.encryptsqlite.database.DatabaseHelper;
import com.azhar.encryptsqlite.model.ModelMain;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class UpdateActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    EditText etNama, etNoHP;
    ExtendedFloatingActionButton fabUpdate;
    Toolbar toolbar;
    ModelMain modelMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        databaseHelper = new DatabaseHelper(this);

        etNama = findViewById(R.id.etNama);
        etNoHP = findViewById(R.id.etNoHP);
        fabUpdate = findViewById(R.id.fabUpdate);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Intent intent = getIntent();
        modelMain = (ModelMain) intent.getSerializableExtra("user");

        etNama.setText(modelMain.getName());
        etNoHP.setText(modelMain.getTlp());

        fabUpdate.setOnClickListener(v -> {
            if (etNama.getText().toString().isEmpty()) {
                Toast.makeText(UpdateActivity.this, "Nama harus diisi!",
                        Toast.LENGTH_SHORT).show();
            } else if (etNoHP.getText().toString().isEmpty()) {
                Toast.makeText(UpdateActivity.this, "Telepon harus diisi!",
                        Toast.LENGTH_SHORT).show();
            } else {
                databaseHelper = DatabaseHelper.getInstance(UpdateActivity.this);
                databaseHelper.updateUser(modelMain.getId(),
                        etNama.getText().toString(), etNoHP.getText().toString());
                Toast.makeText(UpdateActivity.this, "Data berhasil disimpan!",
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