package com.example.stockwatch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class StockViewHolder extends RecyclerView.ViewHolder{
    public TextView company;
    public TextView symbol;
    public TextView latestPrice;
    public TextView change;
    public TextView changePercent;

    public StockViewHolder(View v){
        super(v);
        company = v.findViewById(R.id.company);
        symbol = v.findViewById(R.id.symbol);
        latestPrice = v.findViewById(R.id.latestPrice);
        change = v.findViewById(R.id.change);
        changePercent = v.findViewById(R.id.changePercent);
    }

}
