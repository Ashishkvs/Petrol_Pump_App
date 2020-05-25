package com.petrolpump;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TotalizerViewHolder2 extends RecyclerView.ViewHolder {

    TextView tStart, tEnd, tRate, tDate, dayVolume, dayAmount, totalVolume, totalAmount;


    public TotalizerViewHolder2(@NonNull View itemView) {
        super(itemView);
        tStart = itemView.findViewById(R.id.totalizerStart);
        tEnd = itemView.findViewById(R.id.totalizerEnd);
        tRate = itemView.findViewById(R.id.totalizerRate);
        tDate = itemView.findViewById(R.id.totalizerDate);
        dayVolume = itemView.findViewById(R.id.dayVolume);
        dayAmount = itemView.findViewById(R.id.dayAmount);


    }
}
