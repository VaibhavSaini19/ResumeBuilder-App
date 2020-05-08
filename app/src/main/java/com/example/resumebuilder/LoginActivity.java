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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btn_login;
    private TextInputLayout til_login_email, til_login_password;
    private TextInputEditText et_login_email, et_login_password;
    private TextView tv_signup, tv_forgot_password;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        btn_login = findViewById(R.id.btn_login);
        tv_signup = findViewById(R.id.tv_signup);
        tv_forgot_password = findViewById(R.id.tv_forgot_password);
        et_login_email = findViewById(R.id.et_login_email);
        et_login_password = findViewById(R.id.et_login_password);
        til_login_email = findViewById(R.id.til_login_email);
        til_login_password = findViewById(R.id.til_login_password);

        btn_login.setOnClickListener(this);
        tv_signup.setOnClickListener(this);
        tv_forgot_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == btn_login){
            loginUser();
        }
        if (view == tv_signup){
            finish();
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        }
        if (view == tv_forgot_password){
            String email = et_login_email.getText().toString().trim();
            if(TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Please enter Email address to reset Password", Toast.LENGTH_SHORT).show();
            }else{
                firebaseAuth.sendPasswordResetEmail(email);
                Toast.makeText(this, "Reset link sent. Check Email to reset Password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loginUser(){
        String email = et_login_email.getText().toString().trim();
        String password = et_login_password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            til_login_email.setError("Email is required");
//            Toast.makeText(this, "Please enter an Email", Toast.LENGTH_SHORT).show();
            return;
        }else{
            til_login_email.setError(null);
        }
        if (TextUtils.isEmpty(password)){
            til_login_password.setError("Password is required");
//            Toast.makeText(this, "Please enter a Password", Toast.LENGTH_SHORT).show();
            return;
        }else{
            til_login_password.setError(null);
        }

        progressDialog.setMessage("Signing in...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful()){
                    finish();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Inavlid Email / Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
