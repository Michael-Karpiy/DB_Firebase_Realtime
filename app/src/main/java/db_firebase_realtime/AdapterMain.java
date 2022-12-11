package db_firebase_realtime;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class AdapterMain extends FirebaseRecyclerAdapter<Model, AdapterMain.ViewHolder> {

    public AdapterMain(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Model model) {
        holder.position.setText(model.getPosition());
        holder.id.setText(model.getId());
        holder.name.setText(model.getName());

        holder.cardview.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), ActivitySecond.class);
            intent.putExtra("position", model.getPosition());
            view.getContext().startActivity(intent);
        });

        holder.delete.setOnClickListener(view -> {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            database.getReference().child("DB_Firebase_Realtime").child(model.getPosition()).removeValue();
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView position, id, name;
        CardView cardview, delete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            position = itemView.findViewById(R.id.position);
            id = itemView.findViewById(R.id.id);
            name = itemView.findViewById(R.id.name);
            cardview = itemView.findViewById(R.id.cardview);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}