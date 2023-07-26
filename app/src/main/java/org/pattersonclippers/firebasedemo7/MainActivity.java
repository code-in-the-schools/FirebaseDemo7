package org.pattersonclippers.firebasedemo7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView retrieveTV;
    Button sendBTN, retrieveBTN;
    EditText flavorET;
    public static final String TAG = "AAAAAAAAAAAAAAA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        retrieveTV = (TextView) findViewById(R.id.retrieveTV);
        retrieveBTN = (Button) findViewById(R.id.retrieveBTN);
        sendBTN = (Button) findViewById(R.id.sendBTN);
        flavorET = (EditText) findViewById(R.id.flavorET);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        sendBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Write a message to the database
                String faveFlavor = flavorET.getText().toString();
                myRef.setValue(faveFlavor);
            }
        });

        retrieveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Read from the database
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String iceCreamFlavorFromFB = dataSnapshot.getValue(String.class);
                        Log.d(TAG, "Value is: " + iceCreamFlavorFromFB);
                        retrieveTV.setText(iceCreamFlavorFromFB);
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });
    }
}