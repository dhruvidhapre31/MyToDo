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
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    EditText emailETV, passwordETV, re_passwordETV;
    Button signupBTN;
    FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getSupportActionBar().hide();

        Auth = FirebaseAuth.getInstance();
        emailETV = findViewById(R.id.signup_email);
        passwordETV = findViewById(R.id.signup_password);
        re_passwordETV = findViewById(R.id.signup_repassword);
        signupBTN = findViewById(R.id.signup_btn);

        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email, Password, rePassword;
                Email = emailETV.getText().toString();
                Password = passwordETV.getText().toString();
                rePassword = re_passwordETV.getText().toString();

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
                if(TextUtils.isEmpty(rePassword)){
                    re_passwordETV.setError("Confirm password");
                    return;
                }
                if(!Password.contentEquals(rePassword)){
                    re_passwordETV.setError("Password didn't match");
                }
                Auth
                .createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast toast = Toast.makeText(context, "Sign up successful", duration);
                            toast.show();
                            Intent intent = new Intent(SignUp.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else{
                            Toast toast = Toast.makeText(context, "Sign up failed", duration);
                            toast.show();
                        }
                    }
                });
            }
        });
    }
}