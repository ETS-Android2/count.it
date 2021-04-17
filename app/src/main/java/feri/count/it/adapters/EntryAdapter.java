package feri.count.it.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import java.util.ArrayList;

import feri.count.datalib.Entry;
import feri.count.it.R;

public class EntryAdapter extends RecyclerView.Adapter {
    private ArrayList<Entry> entries;

    public EntryAdapter(ArrayList<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public EntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_entry, parent, false);
        EntryViewHolder entryViewHolder = new EntryViewHolder(view);

        return new EntryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        EntryViewHolder entryHolder = (EntryViewHolder)holder;
        Entry model = entries.get(position);

        entryHolder.txtName.setText(model.getName());
        entryHolder.txtMealCalories.setText(model.getCalories() + " kcal");
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    // Sub Class to create references of the views in Crad
    // view (here "person.xml")
    class EntryViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtMealCalories;

        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = itemView.findViewById(R.id.txtName);
            txtMealCalories = itemView.findViewById(R.id.txtMealCalories);
        }
    }
}

