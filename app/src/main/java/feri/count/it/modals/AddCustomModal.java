package feri.count.it.modals;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import feri.count.datalib.Entry;
import feri.count.datalib.User;
import feri.count.it.R;

public class AddCustomModal extends DialogFragment{
    public static final String TAG = AddCustomModal.class.getSimpleName();

    private EditText edtName;
    private EditText edtCarbs;
    private EditText edtFats;
    private EditText edtProteins;
    private EditText edtCalories;
    private CheckBox vegeterian;
    private CheckBox vegan;
    private CheckBox keto;
    private Button btnAdd;

    private FirebaseAuth mAuth;
    private DatabaseReference db;

    private void bindGui(View view) {
        edtName = (EditText) view.findViewById(R.id.edtNameCustomEntry);
        edtCarbs = (EditText) view.findViewById(R.id.edtCarbs);
        edtFats = (EditText) view.findViewById(R.id.edtFats);
        edtProteins = (EditText) view.findViewById(R.id.editTextNumberSigned3);
        edtCalories = (EditText) view.findViewById(R.id.edtCalories);
        vegeterian = (CheckBox) view.findViewById(R.id.checkBox);
        vegan = (CheckBox) view.findViewById(R.id.checkBox2);
        keto = (CheckBox) view.findViewById(R.id.checkBox3);
        btnAdd = (Button) view.findViewById(R.id.button);
    }

    public static AddCustomModal newInstance() {
        AddCustomModal frag = new AddCustomModal();
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.modal_add_custom, null);


        builder.setView(view);
        Dialog dialog = builder.create();

        bindGui(view);
//        Button btnApply = (Button) view.findViewById(R.id.button);

        db = FirebaseDatabase.getInstance().getReference("entries");

        //getting firebase authentication
        mAuth = FirebaseAuth.getInstance();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i(TAG, "i was clicked!!");

                String name = edtName.getText().toString();
                double carbs = 0, fats = 0, proteins = 0, calories = 0;
                String type = "";

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(getContext(), "Please fill in all field!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }

                if(TextUtils.isEmpty(edtCarbs.getText().toString())){
                    Toast.makeText(getContext(), "Please fill in all field!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }else{
                    carbs = Double.parseDouble(edtCarbs.getText().toString());
                }

                if(TextUtils.isEmpty(edtFats.getText().toString())){
                    Toast.makeText(getContext(), "Please fill in all field!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }else{
                    fats = Double.parseDouble(edtFats.getText().toString());
                }

                if(TextUtils.isEmpty(edtProteins.getText().toString())){
                    Toast.makeText(getContext(), "Please fill in all field!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }else{
                    proteins = Double.parseDouble(edtProteins.getText().toString());
                }

                if(TextUtils.isEmpty(edtCalories.getText().toString())){
                    Toast.makeText(getContext(), "Please fill in all field!", Toast.LENGTH_SHORT).show();
                    dismiss();
                }else{
                    calories = Double.parseDouble(edtCalories.getText().toString());
                }



                if(vegan.isChecked()){
                    type += "vegan";
                }else if(vegeterian.isChecked()){
                    type += "vegeterian";
                }else if(keto.isChecked()){
                    type += "keto";
                }else {
                    type += "none";
                }

                Entry newEntry = new Entry();
                newEntry.setName(name);
                newEntry.setCarbs(carbs);
                newEntry.setFats(fats);
                newEntry.setProtein(proteins);
                newEntry.setCalories(calories);
                newEntry.setType(type);
                newEntry.setCustom(true);

//                Log.i(TAG, "name " + name + "\n" +
//                        "carbs " + carbs + "\n" +
//                        "fats" + fats + "\n" +
//                        "proteins" + proteins + "\n" +
//                        "calories" + calories + "\n" +
//                        "calories" + type + "\n"
//                        );


//                String id= db.push().getKey();
//
//                db.child(id).setValue(newEntry);

                try{
                    if(carbs == 0 && calories == 0 && fats == 0 && proteins == 0){
                        Toast.makeText(getContext(), "Entry not added (FAILED)!", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "no adding!");
                    }else{
                        String id= db.push().getKey();
                        db.child(id).setValue(newEntry);

                        Toast.makeText(getContext(), "Added new entry!", Toast.LENGTH_SHORT).show();
                        Log.i(TAG,"New entry successfully added to database");
                    }
                }catch (Exception e){
                    Log.i(TAG,"Entry not added (FAILED!!!)");
                }



                dismiss();
            }
        });

        return dialog;
    }



}
