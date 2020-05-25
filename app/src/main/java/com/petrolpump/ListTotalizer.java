package com.petrolpump;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.petrolpump.util.Totalizer;
import com.petrolpump.util.TotalizerAdapter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListTotalizer extends AppCompatActivity {

    RecyclerView recyclerView;
    TotalizerAdapter totalizerAdapter;

    TextView totalVolume, totalAmount;
    FirebaseRecyclerOptions<Totalizer> options;
    FirebaseRecyclerAdapter<Totalizer, TotalizerViewHolder2> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_totalizer);

        recyclerView = findViewById(R.id.recyclerTotalizer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        options = new FirebaseRecyclerOptions.Builder<Totalizer>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("totalizer"), Totalizer.class)
                .build();

//        totalizerAdapter = new TotalizerAdapter(options);
//        recyclerView.setAdapter(totalizerAdapter);

        totalVolume = findViewById(R.id.totalVolume);
        totalAmount = findViewById(R.id.totalAmount);
//        totalVolume.setText("Total Vol : 55000");
//        totalAmount.setText("Total Amt : 123654");


    }

    double tempTotalVolume = 0.0d;
    double tempTotalAmount = 0.0d;
    public void updateTotalAmountAndVolume(double vol, double amount) {

        tempTotalAmount += amount;
        tempTotalVolume += vol;
        totalVolume.setText("Total Vol : " + tempTotalVolume);
        totalAmount.setText("Total Amt : " + tempTotalAmount);


    }

    @Override
    protected void onStart() {
        super.onStart();
//        totalizerAdapter.startListening();

        final List<Totalizer> list =new ArrayList<>();
        Log.i("Tag Size", String.valueOf(options.getSnapshots().size()));
        firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Totalizer, TotalizerViewHolder2>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TotalizerViewHolder2 holder, int position, @NonNull Totalizer model) {
                holder.tStart.setText(String.valueOf(model.getStartTotalizer()));
                holder.tEnd.setText(String.valueOf(model.getEndTotalizer()));
                holder.tRate.setText(String.valueOf(model.getFuelPrice()) + " ₹/l");
                holder.tDate.setText(getDateFormatted(model.getCreatedDate()));
                double dVol = model.getEndTotalizer() - model.getStartTotalizer();
                double dAmount = (model.getEndTotalizer() - model.getStartTotalizer()) * model.getFuelPrice();
                holder.dayVolume.setText("V: " + String.valueOf(dVol));
                holder.dayAmount.setText("₹: " + String.valueOf(dAmount));

                if(!list.contains(model)) {
                    list.add(model);
                    updateTotalAmountAndVolume(dVol, dAmount);

                }
            }

            @NonNull
            @Override
            public TotalizerViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.totalizer, parent, false);

                return new TotalizerViewHolder2(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
//        totalizerAdapter = new TotalizerAdapter(options);
        Log.i("List Size : ",String.valueOf(firebaseRecyclerAdapter.getItemCount()));
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }



    @Override
    protected void onStop() {

        super.onStop();
        firebaseRecyclerAdapter.stopListening();
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
  /*  {
        //temp to fecth all datat
        GenericTypeIndicator<Map<String, List<Totalizer>>> genericTypeIndicator = new GenericTypeIndicator<Map<String, List<Totalizer>>>() {};
        Map<String, List<Totalizer>> hashMap = options.getSnapshots().getValue(genericTypeIndicator);

        for (Map.Entry<String,List<Totalizer>> entry : hashMap.entrySet()) {
            List<Totalizer> totalizers = entry.getValue();
            for (Totalizer totalizer: totalizers){
                Log.i("LIST OF DATASET", String.valueOf(totalizer.getFuelPrice()));
            }
        }*/
    }
