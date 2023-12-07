package com.meryem.mlogin;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

     private EditText signupName, signupEmail, signupUsername, signupPassword, signupPasswordVerify;
     private TextView loginRedirectText;
     private Button singupButton;
     private ProgressBar loadingPB;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);


        signupName =  findViewById(R.id.singup_name);
        signupEmail =  findViewById(R.id.signup_email);
        signupUsername =  findViewById(R.id.signup_username);
        signupPassword =  findViewById(R.id.signup_password);
        signupPasswordVerify =  findViewById(R.id.signup_password_verify);
        singupButton =  findViewById(R.id.signup_button);
        loginRedirectText =  findViewById(R.id.loginRedirectText);
        loadingPB = findViewById(R.id.idLoadingPB);



        singupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (signupName.getText().toString().isEmpty() && signupEmail.getText().toString().isEmpty() && signupUsername.getText().toString().isEmpty() && signupPassword.getText().toString().isEmpty()
                        && signupPasswordVerify.getText().toString().isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("postData");
                    postData(signupName.getText().toString(), signupEmail.getText().toString(), signupUsername.getText().toString(), signupPassword.getText().toString(), signupPasswordVerify.getText().toString());
                }
            }


        });


        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        });


    }


    private void postData(String name, String email, String username, String password, String passwordVerify) {



        if (signupUsername.getText().toString().isEmpty() ||
                signupEmail.getText().toString().isEmpty() ||
                signupPassword.getText().toString().isEmpty()) {
            Toast.makeText(SignupActivity.this, "Please enter username, email, and password", Toast.LENGTH_SHORT).show();
            loadingPB.setVisibility(View.GONE);
            return;
        }


        email = signupEmail.getText().toString();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(SignupActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            loadingPB.setVisibility(View.GONE);
            return;
        }

        password = signupPassword.getText().toString();
        passwordVerify = signupPasswordVerify.getText().toString();
        if (!password.equals(passwordVerify)) {
            Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            loadingPB.setVisibility(View.GONE);
            return;
        }



        loadingPB.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.5.175:5000/")

                .addConverterFactory(GsonConverterFactory.create())

                .build();

        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);

        DataModal modal;

        modal = new DataModal(name,email,username,password, passwordVerify);

        Call<DataModal> call = retrofitAPI.createPost(modal);

        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                if (response.code() == 200) {
                    Toast.makeText(SignupActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();

                    loadingPB.setVisibility(View.GONE);
                    signupEmail.setText("");
                    signupName.setText("");
                    signupUsername.setText("");
                    signupPassword.setText("");
                    signupPasswordVerify.setText("");
                    Intent intent =new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();



                }


            }


            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

                System.out.println("Error"+t.getMessage());
            }
        });
    }
}