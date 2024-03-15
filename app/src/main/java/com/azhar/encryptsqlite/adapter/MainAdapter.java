package com.azhar.encryptsqlite.adapter;

/*
 * Created by Azhar Rivaldi on 05-12-2023
 * Youtube Channel : https://bit.ly/2PJMowZ
 * Github : https://github.com/AzharRivaldi
 * Twitter : https://twitter.com/azharrvldi_
 * Instagram : https://www.instagram.com/azhardvls_
 * LinkedIn : https://www.linkedin.com/in/azhar-rivaldi
 */

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.azhar.encryptsqlite.R;
import com.azhar.encryptsqlite.activities.MainActivity;
import com.azhar.encryptsqlite.activities.UpdateActivity;
import com.azhar.encryptsqlite.database.DatabaseHelper;
import com.azhar.encryptsqlite.model.ModelMain;
import com.google.android.material.button.MaterialButton;

import java.io.Serializable;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    ArrayList<ModelMain> modelMains = new ArrayList<>();
    Activity activity;
    DatabaseHelper databaseHelper;

    public MainAdapter(Activity activity) {
        this.activity = activity;
        databaseHelper = new DatabaseHelper(activity);
    }

    public void setListData(ArrayList<ModelMain> modelMainArrayList) {
        if (modelMainArrayList.size() > 0) {
            this.modelMains.clear();
        }
        this.modelMains.addAll(modelMainArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_main, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvNama.setText(modelMains.get(position).getName());
        holder.tvNoTelp.setText(modelMains.get(position).getTlp());
        holder.btnEdit.setOnClickListener((View v) -> {
            Intent intent = new Intent(activity, UpdateActivity.class);
            intent.putExtra("user", (Serializable) modelMains.get(position));
            activity.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener((View v) -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);

            builder.setTitle("Hapus Data");
            builder.setMessage("Apakah yakin ingin hapus data ini?");

            builder.setPositiveButton("Ya", (dialog, which) -> {
                databaseHelper = DatabaseHelper.getInstance(activity);
                databaseHelper.deleteUser(modelMains.get(position).getId());

                Toast.makeText(activity, "Hapus berhasil!", Toast.LENGTH_SHORT).show();
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
            });

            builder.setNegativeButton("Batal", (dialog, which) -> dialog.dismiss());

            AlertDialog alert = builder.create();
            alert.show();

        });
    }

    @Override
    public int getItemCount() {
        return modelMains.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNama, tvNoTelp;
        MaterialButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvNoTelp = itemView.findViewById(R.id.tvNoTelp);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}