package feri.count.it.fragments;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import feri.count.datalib.User;
import feri.count.it.R;
import feri.count.it.activities.MenuActivity;

public class AccountFragment extends Fragment {
    public static final String TAG = AccountFragment.class.getSimpleName();

    private Button buttonUpdate;
    private EditText edtEmail;
    private EditText edtUsername;
    private EditText edtCurrentWeight;
    private Switch switchWeightLoss;
    private CheckBox checkBoxVegan;
    private CheckBox checkBoxVegetarian;
    private CheckBox checkBoxKeto;

    private static final int RC_SIGN_IN = 100;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference db;

    private ArrayList<User> listOfUsers = new ArrayList<>();
    private int indexOfUserInList = -1;
    private ChildEventListener userDataListener;

    private void bindGui(View view) {
        buttonUpdate = (Button) view.findViewById(R.id.button2);
        edtEmail = (EditText) view.findViewById(R.id.edtEmailAccount);
        edtUsername = (EditText) view.findViewById(R.id.edtUsernameAccount);
        edtCurrentWeight = (EditText) view.findViewById(R.id.edtWeightAccount);
        switchWeightLoss = (Switch) view.findViewById(R.id.switch2);
        checkBoxVegan = (CheckBox) view.findViewById(R.id.checkBoxDiet);
        checkBoxVegetarian = (CheckBox) view.findViewById(R.id.checkBoxVegeterian);
        checkBoxKeto = (CheckBox) view.findViewById(R.id.checkBoxKeto);
    }

    //when found user that is logged in application, show his data in update form
    private void bindDataToGui() {
        User authenticatedUser = listOfUsers.get(indexOfUserInList);

        edtEmail.setText(authenticatedUser.getEmail());
        edtUsername.setText(authenticatedUser.getUsername());
        edtCurrentWeight.setText(String.valueOf(authenticatedUser.getWeight()));
        switchWeightLoss.setChecked(authenticatedUser.isWeightLoss());
        checkBoxVegan.setChecked(authenticatedUser.getDiet() != null && authenticatedUser.getDiet().toLowerCase() == "vegan");
        checkBoxVegetarian.setChecked(authenticatedUser.getDiet() != null && authenticatedUser.getDiet().toLowerCase() == "vegetarian");
        checkBoxKeto.setChecked(authenticatedUser.getDiet() != null && authenticatedUser.getDiet().toLowerCase() == "keto");
    }

    // KODA IZDELANA PO VZORU NA PREDAVANJE "REALNOČASOVNA BAZA" IZ PREDMETA "PLATFORMNO ODVISEN RAZVOJ APLIKACIJ" - AVTOR: MATEJ ČREPINŠEK
    private void initData() {
        db = FirebaseDatabase.getInstance().getReference();
        initUserDataListener();
        db.child(User.COLLECTION).addChildEventListener(userDataListener);
    }

    // KODA IZDELANA PO VZORU NA PREDAVANJE "REALNOČASOVNA BAZA" IZ PREDMETA "PLATFORMNO ODVISEN RAZVOJ APLIKACIJ" - AVTOR: MATEJ ČREPINŠEK
    private void initUserDataListener() {
        userDataListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User data = dataSnapshot.getValue(User.class);
                data.setId(dataSnapshot.getKey());

                //add to list if that user is not already on list
                if(listOfUsers.indexOf(data) < 0 || listOfUsers.indexOf(data) >= listOfUsers.size())
                    listOfUsers.add(data);

                //TODO: replace with user email that he entered in login activity - instance of class ApplicationMy
                //When found user that is logged in, save his index in list to instance variable indexOfUserInList and show his data in update form
                if(data.getEmail().equals("viktorijastevanoska@gmail.com")) {
                    indexOfUserInList = listOfUsers.indexOf(data);
                    bindDataToGui();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User data = dataSnapshot.getValue(User.class);
                data.setId(dataSnapshot.getKey());

                //listOfUsers.put(dataSnapshot.getKey(), data);
                for(int i = 0; i < listOfUsers.size(); i++) {
                    if (data.getId().equals(listOfUsers.get(i).getId())) {
                        listOfUsers.set(i,data);
                        break;
                    }
                }

                //TODO: replace with user email that he entered in login activity
                //after changing show current users data in update form
                if(data.getEmail().equals("viktorijastevanoska@gmail.com")) {
                    indexOfUserInList = listOfUsers.indexOf(data);
                    bindDataToGui();
                }

                Log.i(TAG, "Changed:"+dataSnapshot.getKey()+" "+data);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                Log.i(TAG, "Removed:"+dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        final ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_account, null);
        transparentStatusAndNavigation();

        bindGui(rootView);

        //init from firebase
        initData();

        //getting firebase authentication
        mAuth = FirebaseAuth.getInstance();

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserSettings();
            }
        });

        return rootView;
    }

    //called when clicked button "UPDATE"
    private void updateUserSettings() {
        User user = listOfUsers.get(indexOfUserInList);

        double weight = Double.parseDouble(edtCurrentWeight.getText().toString());
        String email = this.edtEmail.getText().toString(),
                username = this.edtUsername.getText().toString(),
                diet = "";
        Boolean isWeightLoss = this.switchWeightLoss.isChecked();
        Pattern emailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE),
                passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$");

        //email cannot be null
        if (email.isEmpty()) {
            Toast.makeText(getActivity(), "E-mail is a required input field!", Toast.LENGTH_SHORT).show();
            return;
        }

        //username cannot be null
        if (username.isEmpty()) {
            Toast.makeText(getActivity(), "Username is a required input field!", Toast.LENGTH_SHORT).show();
            return;
        }

        //check if email pattern is correct
        if(! (emailPattern.matcher(email)).matches()) {
            Toast.makeText(getActivity(), "Please enter a valid e-mail address!", Toast.LENGTH_SHORT).show();
            return;
        }

        //TODO we are saving only one value for Diet - maybe remove from checkbox to radio box
        if(checkBoxKeto.isChecked())
            diet = "keto";
        else if(checkBoxVegetarian.isChecked())
            diet = "vegetarian";
        else if(checkBoxVegan.isChecked())
            diet = "vegan";
        else
            diet = "";

        user.setUsername(username);
        user.setEmail(email);
        user.setWeight(weight);
        user.setDiet(diet);
        user.setWeightLoss(isWeightLoss);

        //if object that is being edited has same email or username like any other users object, prevent updating
        if(doesUserAlreadyExistInDb(user)) {
            Toast.makeText(getActivity(), "A user with that username or e-mail address already exists in the database!", Toast.LENGTH_SHORT).show();
            return;
        }

        Task ref = db.child(User.COLLECTION).child(user.getId()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //subject to change - on successful updating notify user with message
                Toast.makeText(getActivity(), "User data changed successfully!", Toast.LENGTH_SHORT).show();
                bindDataToGui();
                Log.i(TAG,"New user successfully added to database");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Something went wrog while saving user data...", Toast.LENGTH_SHORT).show();
                bindDataToGui();
                Log.i(TAG,"User not registered (FAILED!!!)");
            }
        });
    }

    // check if we changed email or username, we cannot have duplicate of those fields
    private boolean doesUserAlreadyExistInDb(User user) {
        for(int i = 0; i < listOfUsers.size(); i++) {
            //skip object that is currently being edited
            if(i == this.indexOfUserInList)
                continue;

            //if object that is being edited has same email or username, prevent updating
            if (user.getEmail().equals(listOfUsers.get(i).getEmail()) || user.getUsername().equals(listOfUsers.get(i).getUsername())) {
                return true;
            }
        }

        return false;
    }

    //Ahamadullah Saikat, stack overflow
    // https://stackoverflow.com/questions/29069070/completely-transparent-status-bar-and-navigation-bar-on-lollipop#31596735
    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            ((MenuActivity) requireActivity()).getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            ((MenuActivity) requireActivity()).getWindow().setStatusBarColor(Color.TRANSPARENT);
            ((MenuActivity) requireActivity()).getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = ((MenuActivity) requireActivity()).getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    // KODA IZDELANA PO VZORU NA PREDAVANJE "REALNOČASOVNA BAZA" IZ PREDMETA "PLATFORMNO ODVISEN RAZVOJ APLIKACIJ" - AVTOR: MATEJ ČREPINŠEK
    @Override
    public void onStart() {
        super.onStart();

        //needed for working with firebase
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder().build(),
                    new AuthUI.IdpConfig.PhoneBuilder().build(),
                    new AuthUI.IdpConfig.GoogleBuilder().build()
            );

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);

        }
        else updateUI(currentUser);
    }

    // KODA IZDELANA PO VZORU NA PREDAVANJE "REALNOČASOVNA BAZA" IZ PREDMETA "PLATFORMNO ODVISEN RAZVOJ APLIKACIJ" - AVTOR: MATEJ ČREPINŠEK
    private void updateUI(FirebaseUser currentUser) {
        db = FirebaseDatabase.getInstance().getReference();

        initUserDataListener();
        db.child(User.COLLECTION).addChildEventListener(userDataListener);

        //initDataCarListener();
        //mDatabase.child(MyDataCars.COLLECTION).addChildEventListener(myDataCarListener);
    }
}