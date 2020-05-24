package com.petrolpump.util;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.petrolpump.ListTotalizer;
import com.petrolpump.R;
import com.petrolpump.TotalizerActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TotalizerAdapter extends FirebaseRecyclerAdapter<Totalizer, TotalizerAdapter.TotalizerViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TotalizerAdapter(@NonNull FirebaseRecyclerOptions<Totalizer> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TotalizerViewHolder holder, int position, @NonNull Totalizer model) {

        holder.tStart.setText(String.valueOf(model.getStartTotalizer()));
        holder.tEnd.setText(String.valueOf(model.getEndTotalizer()));
        holder.tRate.setText(String.valueOf(model.getFuelPrice()) + " ₹/l");
        holder.tDate.setText(getDateFormatted(model.getCreatedDate()));
        double dVol = model.getEndTotalizer() - model.getStartTotalizer();
        double dAmount = (model.getEndTotalizer() - model.getStartTotalizer()) * model.getFuelPrice();
        holder.dayVolume.setText("V: " + String.valueOf(dVol));
        holder.dayAmount.setText("₹: " + String.valueOf(dAmount));

//        ListTotalizer ta = new ListTotalizer();
//        ta.updateTotalAmountAndVolume(String.valueOf(dVol),String.valueOf(dAmount));


    }




    @NonNull
    @Override
    public TotalizerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new instance of the ViewHolder, in this case we are using a custom
        // layout called R.layout.message for each item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.totalizer, parent, false);

        return new TotalizerViewHolder(view);
    }

    class TotalizerViewHolder extends RecyclerView.ViewHolder {

        TextView tStart, tEnd, tRate, tDate, dayVolume, dayAmount, totalVolume, totalAmount;


        public TotalizerViewHolder(@NonNull View itemView) {
            super(itemView);
            tStart = itemView.findViewById(R.id.totalizerStart);
            tEnd = itemView.findViewById(R.id.totalizerEnd);
            tRate = itemView.findViewById(R.id.totalizerRate);
            tDate = itemView.findViewById(R.id.totalizerDate);
            dayVolume = itemView.findViewById(R.id.dayVolume);
            dayAmount = itemView.findViewById(R.id.dayAmount);


        }
    }

    //DATE FORMATTER
    private String getDateFormatted(Date date) {
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        Date dateFormatted = date;
        String strDate = "";

        try {
            dateFormatted = formatter.parse(formatter.format(dateFormatted));
            strDate = date.toString().substring(0, 11);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.i("Date Formattedd", strDate);
        return strDate;
    }

}

