package com.wahyu.utsa.tugasakhir_1;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CryptoAdapter extends RecyclerView.Adapter<CryptoAdapter.ViewHolder> {

    private ArrayList<CryptoModal> cryptoModals;
    private Context context;
    private static DecimalFormat df2 = new DecimalFormat("#.####");

    public CryptoAdapter(ArrayList<CryptoModal> cryptoModals, Context context) {
        this.cryptoModals = cryptoModals;
        this.context = context;
    }

    public void filterList(ArrayList<CryptoModal> filteredList){
        cryptoModals = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CryptoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        return new CryptoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptoAdapter.ViewHolder holder, int position) {
        CryptoModal cryptoModal = cryptoModals.get(position);
        holder.tvName.setText(cryptoModal.getName());
        holder.tvSymbol.setText(cryptoModal.getSymbol());
        holder.tvPrice.setText("$ "+df2.format(cryptoModal.getPrice()));
        Glide.with(holder.ivImage.getContext()).load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+ cryptoModal.getCoinId() + ".png").into(holder.ivImage);
        Glide.with(holder.ivSparkline.getContext()).load("https://s3.coinmarketcap.com/generated/sparklines/web/7d/usd/" + cryptoModal.getCoinId() + ".png").into(holder.ivSparkline);
        if (cryptoModal.getPercentChange24h() > 0){
            holder.tvPercent24h.setText("24h : "+String.format("%.02f",cryptoModal.getPercentChange24h())+"%");
            holder.tvPercent24h.setTextColor(Color.parseColor("#65fa5a"));
        }else {
            holder.tvPercent24h.setText("24h : "+String.format("%.02f",cryptoModal.getPercentChange24h())+"%");
            holder.tvPercent24h.setTextColor(Color.parseColor("#bd2f2f"));
        }
        if (cryptoModal.getPercentChange1h() > 0){
            holder.tvPercent1h.setText("1h : "+String.format("%.02f",cryptoModal.getPercentChange1h())+"%");
            holder.tvPercent1h.setTextColor(Color.parseColor("#65fa5a"));
        }else {
            holder.tvPercent1h.setText("1h : "+String.format("%.02f",cryptoModal.getPercentChange1h())+"%");
            holder.tvPercent1h.setTextColor(Color.parseColor("#bd2f2f"));
        }
        if (cryptoModal.getPercentChange7d() > 0){
            holder.tvPercent7d.setText("7d : "+String.format("%.02f",cryptoModal.getPercentChange7d())+"%");
            holder.tvPercent7d.setTextColor(Color.parseColor("#65fa5a"));
        }else {
            holder.tvPercent7d.setText("7d : "+String.format("%.02f",cryptoModal.getPercentChange7d())+"%");
            holder.tvPercent7d.setTextColor(Color.parseColor("#bd2f2f"));
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), detailCrypto.class);
                intent.putExtra("id", cryptoModal.getCoinId());
                intent.putExtra("name", cryptoModal.getName());
                intent.putExtra("price", cryptoModal.getPrice());
                intent.putExtra("percent", cryptoModal.getPercentChange1h());
                intent.putExtra("symbol", cryptoModal.getSymbol());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cryptoModals.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName,tvSymbol,tvPrice, tvPercent24h, tvPercent1h, tvPercent7d;
        private ImageView ivImage, ivSparkline;
        private CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSymbol = itemView.findViewById(R.id.tvSymbol);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivImage = itemView.findViewById(R.id.ivImage);
            cardView = itemView.findViewById(R.id.cardView);
            tvPercent24h = itemView.findViewById(R.id.percent24h);
            tvPercent1h = itemView.findViewById(R.id.percent1h);
            tvPercent7d = itemView.findViewById(R.id.percent7d);
            ivSparkline = itemView.findViewById(R.id.ivSparkline);
        }
    }
}
