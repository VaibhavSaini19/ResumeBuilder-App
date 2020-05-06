package com.example.resumebuilder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_signup;
    private TextInputLayout til_signup_email, til_signup_password;
    private EditText et_signup_email, et_signup_password;
    private TextView tv_login;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        btn_signup = findViewById(R.id.btn_signup);
        tv_login = findViewById(R.id.tv_login);
        et_signup_email = findViewById(R.id.et_signup_email);
        et_signup_password = findViewById(R.id.et_signup_password);
        til_signup_email = findViewById(R.id.til_signup_email);
        til_signup_password = findViewById(R.id.til_signup_password);

        btn_signup.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_signup){
            registerUser();
        }
        if (view == tv_login){
            finish();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private void registerUser(){
        String email = et_signup_email.getText().toString().trim();
        String password = et_signup_password.getText().toString().trim();

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);

        if(TextUtils.isEmpty(email)){
            til_signup_email.setError("Email is required");
            return;
        }else{
            til_signup_email.setError(null);
        }
        if(!pat.matcher(email).matches()){
            Toast.makeText(this, "Please enter a valid Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            til_signup_password.setError("Password is required");
            return;
        }else{
            til_signup_password.setError(null);
        }
        if(password.length() < 6){
            Toast.makeText(this, "Password must be atleast 6 chars", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registering user...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                } else {
//                    Toast.makeText(getApplicationContext(), "Failed to register...Try again" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Failed to register...Try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
