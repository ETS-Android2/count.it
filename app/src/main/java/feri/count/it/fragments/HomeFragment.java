package feri.count.it.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import feri.count.datalib.Entry;
import feri.count.datalib.User;
import feri.count.it.R;
import feri.count.it.activities.MenuActivity;
import feri.count.it.application.CountItApplication;
import feri.count.it.modals.AddCustomModal;

public class HomeFragment extends Fragment {
    public static final String TAG = HomeFragment.class.getSimpleName();

    private Button buttonRegister2;
    private Button buttonRegister3;
    private Button buttonRegister4;
    private Button buttonRegister5;
    private TextView textViewDailyCalories;
    private ProgressBar progressBar2;
    private TextView textViewConsumedCalories;
    private TextView txtBreakfastCalories;
    private TextView txtLunchCalories;
    private TextView txtLunchCalories2;
    private TextView txtLunchCalories3;


    private FirebaseAuth mAuth;
    private DatabaseReference db;

    private CountItApplication app;

    private ArrayList<Entry> today = new ArrayList<>();



    private void bindGui(View view) {
        buttonRegister2 = (Button) view.findViewById(R.id.buttonRegister2);
        buttonRegister3 = (Button) view.findViewById(R.id.buttonRegister3);
        buttonRegister4 = (Button) view.findViewById(R.id.buttonRegister4);
        buttonRegister5 = (Button) view.findViewById(R.id.buttonRegister5);
        textViewDailyCalories = (TextView) view.findViewById(R.id.textViewDailyCalories);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);
        textViewConsumedCalories = (TextView) view.findViewById(R.id.textViewConsumedCalories);
        txtBreakfastCalories = (TextView) view.findViewById(R.id.txtBreakfastCalories);
        txtLunchCalories = (TextView) view.findViewById(R.id.txtLunchCalories);
        txtLunchCalories2 = (TextView) view.findViewById(R.id.txtLunchCalories2);
        txtLunchCalories3 = (TextView) view.findViewById(R.id.txtLunchCalories3);

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


        this.app = (CountItApplication) getActivity().getApplication();
        bindGui(rootView);

        db = FirebaseDatabase.getInstance().getReference("entries");

        //getting firebase authentication
        mAuth = FirebaseAuth.getInstance();

        updateCalorieGUI();

        buttonRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "called button search");
                openEntryFragment("Breakfast");
            }
        });

        buttonRegister3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "called button search");
                openEntryFragment("Lunch");
            }
        });

        buttonRegister4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "called button search");
                openEntryFragment("Dinner");
            }
        });

        buttonRegister5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "called button search");
                openEntryFragment("Snack");
            }
        });

        return rootView;
    }

    public void openEntryFragment(String meal){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        EntryFragment entryFragment = new EntryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("meal", meal);
        entryFragment.setArguments(bundle);
        ((MenuActivity) requireActivity()).navView
                .setSelectedItemId(R.id.navigation_dashboard);
        fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, entryFragment).commit();

    }

    public void updateCalorieGUI(){
        User user = app.getLoggedUser();
        updateTodayEntries();
        int calorieIntake = 0;
        if(!user.isWeightLoss())
            calorieIntake = (int)user.getWeight()*30;
        else
            calorieIntake = (int)user.getWeight()*20;

        progressBar2.setMax(calorieIntake);
        textViewDailyCalories.setText(String.valueOf(calorieIntake) + " kcal");
        int consumedCalories = (int)getConsumedCalories("");
        progressBar2.setProgress(consumedCalories);
        textViewConsumedCalories.setText(String.valueOf(consumedCalories) + " kcal");
        txtBreakfastCalories.setText((String.valueOf((int)getConsumedCalories("Breakfast")) + " kcal"));
        txtLunchCalories.setText(String.valueOf((int)getConsumedCalories("Lunch")) + " kcal");
        txtLunchCalories2.setText(String.valueOf((int)getConsumedCalories("Dinner")) + " kcal");
        txtLunchCalories3.setText(String.valueOf((int)getConsumedCalories("Snack")) + " kcal");

    }



    public void updateTodayEntries(){
        today.clear();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String searchDate = formatter.format(date);
        for(Entry e : app.getLoggedUser().getEntries()){
            if(e.getDate().equals(searchDate))
                today.add(e);
        }
    }


    public double getConsumedCalories(String meal){
        double result = 0;
        if(meal.equals(""))
            for(Entry e : today)
                result += e.getCalories()*e.getQuantity();
        else {
            for (Entry e : today) {
                if (e.getMeal().equals(meal))
                    result += e.getCalories() * e.getQuantity();

            }
        }

        return result;
    }

}