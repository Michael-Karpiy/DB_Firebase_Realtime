package db_firebase_realtime;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ActivityAdd extends AppCompatActivity {

    EditText et_position, et_id, et_name, et_description;
    CardView cancel, cv_accept;
    TextView tv_accept;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        et_position = findViewById(R.id.et_position);
        et_id = findViewById(R.id.et_id);
        et_name = findViewById(R.id.et_name);
        et_description = findViewById(R.id.et_description);

        tv_accept = findViewById(R.id.tv_accept);
        cv_accept = findViewById(R.id.cv_accept);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(view -> onBackPressed());

        cv_accept.setOnClickListener(view -> {
            if (et_position.getText().toString().trim().length() > 0) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference();

                Model model = new Model(et_position.getText().toString(),"@" + et_id.getText().toString(),et_name.getText().toString(),et_description.getText().toString());

                databaseReference.child("DB_Firebase_Realtime").child(et_position.getText().toString()).setValue(model);
            }
        });

        et_position.addTextChangedListener(etWatcher);
        et_id.addTextChangedListener(etWatcher);
        et_name.addTextChangedListener(etWatcher);
    }

    private final TextWatcher etWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            cv_accept.setEnabled(false);
            cv_accept.setCardBackgroundColor(getResources().getColor(R.color.system_accent1_50));
            tv_accept.setTextColor(getResources().getColor(R.color.system_accent1_100));
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String positionInput = et_position.getText().toString().trim();
            String idInput = et_id.getText().toString().trim();
            String nameInput = et_name.getText().toString().trim();

            if(!positionInput.isEmpty() && !idInput.isEmpty() && !nameInput.isEmpty()){
                cv_accept.setEnabled(true);
                cv_accept.setCardBackgroundColor(getResources().getColor(R.color.system_accent1_300));
                tv_accept.setTextColor(getResources().getColor(R.color.black));
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}