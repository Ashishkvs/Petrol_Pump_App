package com.petrolpump;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.petrolpump.util.Totalizer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;

public class TotalizerActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "MyActivity";


    private EditText totalizerStart;
    private EditText totalizerEnd;
    private Button saveButton;
    private Spinner rateSpinner;
    private Spinner paiseSpinner;
//    FirebaseDatabase database;
    DatabaseReference totalizerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_totalizer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(TotalizerActivity.this, ListTotalizer.class);
                TotalizerActivity.this.startActivity(intent);
            }
        });

        //Bind ui to backend
        totalizerStart = (EditText) findViewById(R.id.totalizerEnd);
        totalizerEnd = (EditText) findViewById(R.id.totalizerStart);

        saveButton = findViewById(R.id.save_btn);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double tStart = Double.parseDouble(totalizerStart.getText().toString());
                double tEnd = Double.parseDouble(totalizerEnd.getText().toString());
                double rate = Double.parseDouble(rateSpinner.getSelectedItem().toString()) +
                        Double.parseDouble(paiseSpinner.getSelectedItem().toString())/100;

                // save to db
                saveTotalizer(tStart, tEnd, rate);

            }
        });


        rateSpinner = (Spinner) findViewById(R.id.rateOfDay);
        paiseSpinner = findViewById(R.id.paiseSpinner);


        //user logged in
        LoginActivity loginActivity = new LoginActivity();
        final FirebaseUser currentUser = loginActivity.getCurrentUser();
        if (currentUser != null) {
            Toast.makeText(TotalizerActivity.this, currentUser.getEmail(), Toast.LENGTH_SHORT).show();
        } else {

        }

        //database
        totalizerRef = FirebaseDatabase.getInstance().getReference("totalizer");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Rates drop selector
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(TotalizerActivity.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    void saveTotalizer(double tStart, double tEnd, double rate) {
//        rateSpinner.setOnItemSelectedListener(this);
        Log.i(TAG, String.valueOf(tStart));
        Log.i(TAG, String.valueOf(tEnd));
        Log.i(TAG, String.valueOf(rate));
        Totalizer totalizer = new Totalizer();
        totalizer.setStartTotalizer(tStart);
        totalizer.setEndTotalizer(tEnd);
        totalizer.setFuelPrice(rate);
        totalizer.setCreatedDate(new Date());
        saveToDatabase("totalizer", totalizer);

    }

    //database
    // Write a message to the database
    public void saveToDatabase(String databaseName, Object obj) {

        String id = totalizerRef.push().getKey();
        totalizerRef.child(id).setValue(obj);
        Toast.makeText(TotalizerActivity.this,"Totalizer Added",Toast.LENGTH_SHORT).show();
    }

    public void readFromDatatBase(DatabaseReference myRef) {
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }


}
