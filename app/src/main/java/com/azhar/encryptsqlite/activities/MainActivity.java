package com.azhar.encryptsqlite.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.encryptsqlite.R;
import com.azhar.encryptsqlite.adapter.MainAdapter;
import com.azhar.encryptsqlite.database.DatabaseHelper;
import com.azhar.encryptsqlite.model.ModelMain;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import net.sqlcipher.database.SQLiteDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvData;
    MainAdapter mainAdapter;
    ArrayList<ModelMain> modelMainArrayList;
    DatabaseHelper databaseHelper;
    ExtendedFloatingActionButton fabAdd;
    TextView tvNoData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //untuk mengaktifkan library sqlchipher
        SQLiteDatabase.loadLibs(this);

        rvData = findViewById(R.id.rvData);
        fabAdd = findViewById(R.id.fabAdd);
        tvNoData = findViewById(R.id.tvNoData);
        mainAdapter = new MainAdapter(this);

        databaseHelper = new DatabaseHelper(this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        rvData.setLayoutManager(layoutManager);
        rvData.setAdapter(mainAdapter);

        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, InputActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (databaseHelper.getAllUsers().size() > 0) {
            tvNoData.setVisibility(View.GONE);
            rvData.setVisibility(View.VISIBLE);
        } else {
            tvNoData.setVisibility(View.VISIBLE);
            rvData.setVisibility(View.GONE);
        }

        modelMainArrayList = databaseHelper.getAllUsers();
        mainAdapter.setListData(modelMainArrayList);
        mainAdapter.notifyDataSetChanged();
    }

}