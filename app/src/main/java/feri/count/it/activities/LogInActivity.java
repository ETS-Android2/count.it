package feri.count.it.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import feri.count.datalib.User;
import feri.count.it.R;

public class LogInActivity extends AppCompatActivity {
    public static final String TAG = LogInActivity.class.getSimpleName();
    public static final int REGISTRATION_ACTIVITY_ID = 1001;
    public static final int LOGIN_ACTIVITY_ID = 1002;
    private Button buttonLogin;
    private Button buttonRegister;
    private EditText edtEmail;
    private EditText edtPassword;

    private static final int RC_SIGN_IN = 100;
    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;
    private DatabaseReference db;

    private ArrayList<User> listOfUsers = new ArrayList<>();
    private ChildEventListener userDataListener;


    private void bindGui() {
        buttonLogin = (Button) findViewById(R.id.buttonLoginUser);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        edtEmail = (EditText) findViewById(R.id.edtEmailLog);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
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

                listOfUsers.add(data);
                Log.i(TAG, "Add:"+dataSnapshot.getKey()+" "+data);
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

    //Ahamadullah Saikat, stack overflow
    // https://stackoverflow.com/questions/29069070/completely-transparent-status-bar-and-navigation-bar-on-lollipop#31596735
    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        transparentStatusAndNavigation();
        getSupportActionBar().hide();

        bindGui();
        initData();

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginUser(v);
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity(v);
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    //CHECKING IF INPUT FIELDS EXISTS IN LIST OF USERS
    private boolean checkIfUserIsRegistered() {
        for(int i = 0; i < listOfUsers.size(); i++) {
            Log.w(TAG, "email input:  " + edtEmail.getText().toString() + ", password input:   " + edtPassword.getText().toString());
            if (edtEmail.getText().toString().equals(listOfUsers.get(i).getEmail()) || edtPassword.getText().toString().equals(listOfUsers.get(i).getPassword())) {
                return true;
            }
        }
        return false;
    }

    //MATERIAL FROM : YouTube "https://www.youtube.com/watch?v=Z-RE1QuUWPg" AND "https://youtu.be/KB2BIm_m1Os"
    public void LoginUser(View view){
        String email = this.edtEmail.getText().toString(),
                password = this.edtPassword.getText().toString();

//        Log.w(TAG, "createUserWithEmail:failure   " + password);
        if(TextUtils.isEmpty(email)){
            Toast.makeText(getBaseContext(), "E-mail is a required input field!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(getBaseContext(), "Password is a required input field!", Toast.LENGTH_SHORT).show();
            return;
        }

        // CALL FUNCTION TO LIST THROUGH EXISTING USERS AND SEE IF INPUT EMAIL&PASSWORD ARE CORRECT
        if(checkIfUserIsRegistered()){
            Toast.makeText(LogInActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        }else{
            Toast.makeText(getBaseContext(), "User does not exist!", Toast.LENGTH_SHORT).show();
        }

        //authenticate user
//        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if(task.isSuccessful()){
//                   Toast.makeText(LogInActivity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
//
//                    startActivity(new Intent(getApplicationContext(), MenuActivity.class));
//                }else{
//                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                    Toast.makeText(getBaseContext(), "Error logging in! ", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }

    public void openRegisterActivity(View view) {
        this.startActivityForResult(
            new Intent(this.getBaseContext(), RegisterActivity.class),
            REGISTRATION_ACTIVITY_ID
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REGISTRATION_ACTIVITY_ID) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getBaseContext(), "New user successfully added to database", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}