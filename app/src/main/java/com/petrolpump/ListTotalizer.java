package com.petrolpump;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.petrolpump.util.Totalizer;
import com.petrolpump.util.TotalizerAdapter;

public class ListTotalizer extends AppCompatActivity {

    RecyclerView recyclerView;
    TotalizerAdapter totalizerAdapter;

    TextView totalVolume, totalAmount;
    FirebaseRecyclerOptions<Totalizer> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_totalizer);

        recyclerView = findViewById(R.id.recyclerTotalizer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        options = new FirebaseRecyclerOptions.Builder<Totalizer>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("totalizer"), Totalizer.class)
                .build();

        totalizerAdapter = new TotalizerAdapter(options);
        recyclerView.setAdapter(totalizerAdapter);

        totalVolume = findViewById(R.id.totalVolume);
        totalAmount = findViewById(R.id.totalAmount);
//        totalVolume.setText("Total Vol : 55000");
//        totalAmount.setText("Total Amt : 123654");


    }

    public void updateTotalAmountAndVolume(String vol, String amount) {
        totalVolume.setText("Total Vol : " + vol);
        totalAmount.setText("Total Amt : " + amount);
    }

    @Override
    protected void onStart() {
        super.onStart();
        totalizerAdapter.startListening();


//        Log.i("Tag Size", String.valueOf(options.getSnapshots().size()));


    }

    @Override
    protected void onStop() {

        super.onStop();
        totalizerAdapter.stopListening();
    }
}
