package feri.count.it.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import feri.count.datalib.Entry;
import feri.count.it.R;
import feri.count.it.activities.MenuActivity;
import feri.count.it.modals.AddCustomModal;

public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();

    private Button buttonRegister2;
    private Button buttonRegister3;
    private Button buttonRegister4;
    private Button buttonRegister5;

    private FirebaseAuth mAuth;
    private DatabaseReference db;


    private void bindGui(View view) {
        buttonRegister2 = (Button) view.findViewById(R.id.buttonRegister2);
        buttonRegister3 = (Button) view.findViewById(R.id.buttonRegister3);
        buttonRegister4 = (Button) view.findViewById(R.id.buttonRegister4);
        buttonRegister5 = (Button) view.findViewById(R.id.buttonRegister5);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_home, null);

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

        buttonRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "called button search");
                openAddCustomModal();
            }
        });

        buttonRegister3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "called button search");
                openAddCustomModal();
            }
        });

        buttonRegister4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "called button search");
                openAddCustomModal();
            }
        });

        buttonRegister5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "called button search");
                openAddCustomModal();
            }
        });

        return rootView;
    }

    public void openAddCustomModal(){
        AddCustomModal modal = AddCustomModal.newInstance();
        modal.show(getFragmentManager(), AddCustomModal.TAG);
    }
}