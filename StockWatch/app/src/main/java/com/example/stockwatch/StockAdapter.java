package com.example.stockwatch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockViewHolder> {
    private static final String TAG = "StockAdapter";   //For debugging
    private List<Stock> stockList;
    private MainActivity mainActivity;

    public StockAdapter(List<Stock> sl, MainActivity ma){
        this.stockList = sl;
        mainActivity = ma;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.stock_list_row, parent, false);
        itemView.setOnClickListener(mainActivity);
        itemView.setOnLongClickListener((mainActivity));
        return new StockViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position){
        Stock S = stockList.get(position);

        String arrow;
        int color;
        if(S.getChange() >= 0.0){
            arrow = "▲";
            color = 0xFF22DD22;
        } else {
            arrow = "▼";
            color = 0xFFDD2222;
        }

        //Correct color based on stock going up or down
        holder.symbol.setTextColor(color);
        holder.company.setTextColor(color);
        holder.latestPrice.setTextColor(color);
        holder.change.setTextColor(color);
        holder.changePercent.setTextColor(color);

        holder.symbol.setText(S.getSymbol());
        holder.company.setText(S.getCompany());

        String strLatestPrice = doubleToString(S.getLatestPrice());
        holder.latestPrice.setText(strLatestPrice);

        String strChange = doubleToString(S.getChange());
        holder.change.setText(arrow + " " + strChange);

        String strChangePercent = doubleToString(S.getChangePercent());
        holder.changePercent.setText("(" + strChangePercent + "%)");
    }

    public static String doubleToString(double d){
        String s = String.format("%.2f", d);
        return s;
    }

    @Override
    public int getItemCount(){
        return stockList.size();
    }
}