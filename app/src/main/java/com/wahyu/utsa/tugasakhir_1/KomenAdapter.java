package com.wahyu.utsa.tugasakhir_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class KomenAdapter extends RecyclerView.Adapter<KomenAdapter.ViewHolder> {

    private ArrayList<KomenModel> komenModelArrayList;
    private Context context;

    public KomenAdapter(ArrayList<KomenModel> komenModelArrayList, Context context) {
        this.komenModelArrayList = komenModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public KomenAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.komen_list,parent,false);
        return new KomenAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KomenAdapter.ViewHolder holder, int position) {
        KomenModel komen = komenModelArrayList.get(position);
        holder.tvDisplayName.setText(komen.getUsername());
        holder.tvKomentar.setText(komen.getKomentar());
        holder.tvCryptoName.setText(komen.getCryptoName());
    }

    @Override
    public int getItemCount() {
        return komenModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDisplayName, tvKomentar, tvCryptoName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDisplayName = itemView.findViewById(R.id.tvDisplayName);
            tvKomentar = itemView.findViewById(R.id.tvKomentar);
            tvCryptoName = itemView.findViewById(R.id.tvCryptoName);
        }
    }

}
