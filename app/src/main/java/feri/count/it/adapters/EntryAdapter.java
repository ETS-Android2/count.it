package feri.count.it.adapters;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import feri.count.datalib.Entry;
import feri.count.it.R;
import feri.count.it.application.CountItApplication;
import feri.count.it.events.OnEntryAdd;

import org.greenrobot.eventbus.EventBus;

public class EntryAdapter extends RecyclerView.Adapter {
    private ArrayList<Entry> entries;

//    private void bindGui(View view) {
//        buttonAdd = (Button) view.findViewById(R.id.buttonAdd);
//    }

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

        entryHolder.buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Entry e = entries.get(position);
                e.setCustom(false);
                if(TextUtils.isEmpty(entryHolder.edtQuantity.getText().toString()))
                    e.setQuantity(1);
                else
                    e.setQuantity(Double.valueOf(entryHolder.edtQuantity.getText().toString()));
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = new Date();
                e.setDate(formatter.format(date));
                formatter = new SimpleDateFormat("HH:mm:ss");
                e.setTime(formatter.format(date));
                Log.i("Adap click", "clicked");
                EventBus.getDefault().post(new OnEntryAdd(e));
            }
        });
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
        private Button buttonAdd;
        private EditText edtQuantity;


        public EntryViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtMealCalories = itemView.findViewById(R.id.txtMealCalories);
            buttonAdd = (Button) itemView.findViewById(R.id.buttonAdd);
            edtQuantity = (EditText) itemView.findViewById(R.id.edtQuantity);

        }
    }
}

