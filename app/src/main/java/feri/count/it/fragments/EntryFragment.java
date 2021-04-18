package feri.count.it.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import feri.count.it.application.CountItApplication;
import feri.count.it.modals.AddCustomModal;
import feri.count.it.modals.FilterModal;
import feri.count.it.modals.TagModal;

public class EntryFragment extends Fragment {
    public static final String TAG = EntryFragment.class.getSimpleName();

    private EditText edtSearch;
    private Button buttonSearch;
    private Button buttonTag;
    private Button btnOpenFilterModal;
    private Button buttonRegister2;
    private Button buttonRegister3;
    private Button buttonRegister4;
    private Button buttonRegister5;
    private FloatingActionButton fabAddCustom;
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

    private CountItApplication app;

    private boolean isSelectedVegan;
    private boolean isSelectedVegetarian;
    private boolean isSelectedKeto;
    private boolean isSelectedRecipe;
    private boolean isSelectedFood;

    private boolean isSelectedBreakfast;
    private boolean isSelectedLunch;
    private boolean isSelectedDinner;
    private boolean isSelectedSnack;

    private void bindGui(View view) {
        edtSearch = (EditText) view.findViewById(R.id.edtSearch);
        buttonSearch = (Button) view.findViewById(R.id.buttonSearch);
        buttonTag = (Button) view.findViewById(R.id.buttonTag);
        btnOpenFilterModal = (Button) view.findViewById(R.id.buttonFilter);
        fabAddCustom = (FloatingActionButton) view.findViewById(R.id.fabAddCustom);
        recyclerViewFood = (RecyclerView) view.findViewById(R.id.recyclerViewFood);
    }

    private void initRecyclerView() {
        String searchString = edtSearch.getText().toString();
        this.listOfEntries = new ArrayList<>();

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()) {
                    boolean displayCurrentRecord = true;
                    Entry data = ds.getValue(Entry.class);

                    if(searchString != "") {
                        if (data.toString().contains(searchString))
                            displayCurrentRecord = true;
                        else
                            displayCurrentRecord = false;
                    }
                    else
                        displayCurrentRecord = true;

                    //if(displayCurrentRecord && isSelectedVegan && data.get)

                    if(displayCurrentRecord)
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

        this.app = (CountItApplication) getActivity().getApplication();

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

        fabAddCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "called button search");
                openAddCustomModal();
            }
        });

        recyclerViewFood.setLayoutManager(new LinearLayoutManager(getActivity()));

        initRecyclerView();

        return rootView;
    }

    public void openFilterModal() {
        FilterModal modal = FilterModal.newInstance(isSelectedVegan || isSelectedVegetarian || isSelectedKeto, isSelectedRecipe, isSelectedFood);

        modal.builder = new AlertDialog.Builder(getActivity());
        modal.inflater = getActivity().getLayoutInflater();
        modal.view = modal.inflater.inflate(R.layout.modal_filter, null);

        modal.builder.setView(modal.view);
        modal.dialog = modal.builder.create();

        modal.bindGui(modal.view);
        modal.displayDataInGui();

        modal.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.setSelectedOptionsFromForm();

                isSelectedVegan = modal.isSelectedDiet() && app.getLoggedUser() != null && app.getLoggedUser().getDiet().toLowerCase().equals("vegan");
                isSelectedVegetarian = modal.isSelectedDiet() && app.getLoggedUser() != null && app.getLoggedUser().getDiet().toLowerCase().equals("vegetarian");
                isSelectedKeto = modal.isSelectedDiet() && app.getLoggedUser() != null && app.getLoggedUser().getDiet().toLowerCase().equals("keto");
                isSelectedRecipe = modal.isSelectedRecipes();
                isSelectedFood = modal.isSelectedFood();

                Log.i(TAG, "vegan: " + isSelectedVegan + ", vegetarian: " + isSelectedVegetarian + ", keto: " + isSelectedKeto + ", recipe: " + isSelectedRecipe + ", food: " + isSelectedFood);

                initRecyclerView();

                modal.dismiss();
            }
        });

        modal.show(getFragmentManager(), FilterModal.TAG);
    }

    public void openTagModal() {

        TagModal modal = TagModal.newInstance(isSelectedBreakfast, isSelectedLunch, isSelectedDinner, isSelectedSnack);

        modal.builder = new AlertDialog.Builder(getActivity());
        modal.inflater = getActivity().getLayoutInflater();
        modal.view = modal.inflater.inflate(R.layout.modal_tag, null);

        modal.builder.setView(modal.view);
        modal.dialog = modal.builder.create();

        modal.bindGui(modal.view);
        modal.displayDataInGui();

        modal.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modal.setSelectedOptionsFromForm();

                isSelectedBreakfast = modal.isSelectedBreakfast();
                isSelectedLunch = modal.isSelectedLunch();
                isSelectedDinner = modal.isSelectedDinner();
                isSelectedSnack = modal.isSelectedSnack();

                initRecyclerView();

                Log.i(TAG, "breakfast: " + isSelectedBreakfast + ", lunch: " + isSelectedLunch + ", dinner: " + isSelectedDinner + ", snack: " + isSelectedSnack);

                modal.dismiss();
            }
        });

        modal.show(getFragmentManager(), TagModal.TAG);
    }

    public void openAddCustomModal(){
        AddCustomModal modal = AddCustomModal.newInstance();
        modal.show(getFragmentManager(), AddCustomModal.TAG);
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