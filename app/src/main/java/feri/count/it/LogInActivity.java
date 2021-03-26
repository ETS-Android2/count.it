package feri.count.it;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {
    private Button buttonRegister;
    public static final int REGISTRATION_ACTIVITY_ID = 1001;

    private void bindGui() {
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        bindGui();

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity(v);
            }
        });
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