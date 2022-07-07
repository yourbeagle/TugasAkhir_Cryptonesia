package com.wahyu.utsa.tugasakhir_1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class WatchlistAdapter extends RecyclerView.Adapter<WatchlistAdapter.ViewHolder> {

    private ArrayList<WatchlistModel> watchlistModelArrayList;
    private Context context;

    public WatchlistAdapter(ArrayList<WatchlistModel> watchlistModelArrayList, Context context) {
        this.watchlistModelArrayList = watchlistModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public WatchlistAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.watchlist_list, parent, false);
        return new WatchlistAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WatchlistAdapter.ViewHolder holder, int position) {
        WatchlistModel watchlist = watchlistModelArrayList.get(position);
        holder.tvCryptoWatchlist.setText(watchlist.getCryptoName());
        Glide.with(holder.ivCryptoWatchlist.getContext()).load("https://s2.coinmarketcap.com/static/img/coins/64x64/"+ watchlist.getCoinId() + ".png").into(holder.ivCryptoWatchlist);
    }

    @Override
    public int getItemCount() {
        return watchlistModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivCryptoWatchlist;
        TextView tvCryptoWatchlist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCryptoWatchlist = itemView.findViewById(R.id.ivImageWatchlist);
            tvCryptoWatchlist = itemView.findViewById(R.id.tvNameWatchlist);
        }
    }
}
