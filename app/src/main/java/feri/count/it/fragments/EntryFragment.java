package feri.count.it.fragments;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import feri.count.datalib.Entry;
import feri.count.datalib.User;
import feri.count.it.R;
import feri.count.it.activities.MenuActivity;
import feri.count.it.adapters.EntryAdapter;
import feri.count.it.modals.FilterModal;
import feri.count.it.modals.TagModal;

public class EntryFragment extends Fragment {
    public static final String TAG = EntryFragment.class.getSimpleName();

    private EditText edtSearch;
    private Button buttonSearch;
    private Button buttonTag;
    private Button btnOpenFilterModal;
    private RecyclerView recyclerViewFood;

    private EntryAdapter entryAdapter;

    private static final int RC_SIGN_IN = 100;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference db;

    private ArrayList<User> listOfUsers = new ArrayList<>();
    private ArrayList<Entry> listOfEntries = new ArrayList<Entry>();
    private int indexOfUserInList = -1;
    private ChildEventListener userDataListener;
    private ChildEventListener entryDataListener;

    private void bindGui(View view) {
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        buttonSearch = (Button) view.findViewById(R.id.buttonSearch);
        buttonTag = (Button) view.findViewById(R.id.buttonTag);
        btnOpenFilterModal = (Button) view.findViewById(R.id.buttonFilter);
        recyclerViewFood = (RecyclerView) view.findViewById(R.id.recyclerViewFood);
    }

    private void initRecyclerView() {
        String searchString = edtSearch.getText().toString();
        this.listOfEntries = new ArrayList<>();

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()) {
                    Entry data = ds.getValue(Entry.class);

                    if(searchString != "") {
                        if (data.toString().contains(searchString))
                            listOfEntries.add(data);
                    }
                    else
                        listOfEntries.add(data);
                }

                entryAdapter = new EntryAdapter(listOfEntries);
                recyclerViewFood.setAdapter(entryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_entry, null);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((MenuActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.maximum_blue, ((MenuActivity) requireActivity()).getTheme()));
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((MenuActivity) requireActivity()).getWindow().setStatusBarColor(getResources().getColor(R.color.maximum_blue));
        }
        ((MenuActivity) requireActivity()).getSupportActionBar().hide();

        bindGui(rootView);

        db = FirebaseDatabase.getInstance().getReference("entries");

        //getting firebase authentication
        mAuth = FirebaseAuth.getInstance();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecyclerView();
                //Log.i(TAG, "called button search");
            }
        });

        buttonTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTagModal();
            }
        });

        btnOpenFilterModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilterModal();
            }
        });

        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getActivity()));

        initRecyclerView();

        return rootView;
    }

    public void openFilterModal() {
        FilterModal modal = FilterModal.newInstance();
        modal.show(getFragmentManager(), FilterModal.TAG);
    }

    public void openTagModal() {
        TagModal modal = TagModal.newInstance();
        modal.show(getFragmentManager(), TagModal.TAG);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}