package com.example.mytodo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    EditText emailETV, passwordETV;
    Button loginBTN;
    TextView sign_up;
    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        getSupportActionBar().hide();

        Auth = FirebaseAuth.getInstance();
        emailETV = findViewById(R.id.login_email);
        passwordETV = findViewById(R.id.login_password);
        loginBTN = findViewById(R.id.login_btn);
        sign_up = findViewById(R.id.login_signup);

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email, Password;

                Email = emailETV.getText().toString();
                Password = passwordETV.getText().toString();
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_SHORT;

                if(TextUtils.isEmpty(Email)){
                    emailETV.setError("Enter Email");
                    return;
                }
                if(TextUtils.isEmpty(Password)){
                    passwordETV.setError("Enter password");
                    return;
                }

                Auth
                        .signInWithEmailAndPassword(Email, Password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast toast = Toast.makeText(context, "Login successful", duration);
                                    toast.show();
                                    Intent intent = new Intent(LogIn.this, MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    Toast toast = Toast.makeText(context, "Login failed", duration);
                                    toast.show();
                                }
                            }
                        });
            }
        });

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this, SignUp.class);
                startActivity(intent);
            }
        });
    }
}