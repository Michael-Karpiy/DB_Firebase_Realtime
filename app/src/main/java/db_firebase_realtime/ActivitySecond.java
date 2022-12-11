package db_firebase_realtime;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class ActivitySecond extends AppCompatActivity {

    EditText position, id, name, description;
    CardView cancel, edit, accept;

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        position = findViewById(R.id.position);
        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);

        edit = findViewById(R.id.edit);
        accept = findViewById(R.id.accept);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(view -> onBackPressed());

        GetData(position, "position");
        GetData(id, "id");
        GetData(name, "name");
        GetData(description, "description");

        Intent intent = getIntent();
        String position = intent.getStringExtra("position");

        edit.setOnClickListener(view -> {

            id.setEnabled(true);
            id.setTextColor(getResources().getColor(R.color.black));

            name.setEnabled(true);
            name.setTextColor(getResources().getColor(R.color.black));

            description.setEnabled(true);
            description.setTextColor(getResources().getColor(R.color.black));

            accept.setVisibility(View.VISIBLE);
            edit.setVisibility(View.GONE);
        });

        accept.setOnClickListener(view -> {

            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference();

            databaseReference.child("DB_Firebase_Realtime").child(position).child("id").setValue(id.getText().toString());
            databaseReference.child("DB_Firebase_Realtime").child(position).child("name").setValue(name.getText().toString());
            databaseReference.child("DB_Firebase_Realtime").child(position).child("description").setValue(description.getText().toString());

            id.setEnabled(false);
            id.setTextColor(getResources().getColor(android.R.color.system_accent1_300));

            name.setEnabled(false);
            name.setTextColor(getResources().getColor(android.R.color.system_accent1_300));

            description.setEnabled(false);
            description.setTextColor(getResources().getColor(android.R.color.system_accent1_300));

            accept.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
        });
    }

    public void GetData(EditText value, String key)  {

        Intent intent = getIntent();
        String position = intent.getStringExtra("position");

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("DB_Firebase_Realtime").child(position).child(key);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // ...
            }
        });
    }
}