package com.meryem.mlogin;

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

public class LoginActivity extends AppCompatActivity {

    EditText  loginUsername, loginPassword;
    TextView signupRedirectText;;
    Button  loginButton;

    private ProgressBar loadingPB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        loginUsername =  findViewById(R.id.login_username);
        loginPassword =  findViewById(R.id.login_password);
        signupRedirectText =  findViewById(R.id.signupRedirectText);
        loginButton = findViewById(R.id.login_button);
        loadingPB = findViewById(R.id.idLoadingPB);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (loginUsername.getText().toString().isEmpty() && loginPassword.getText().toString().isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Please enter both the values", Toast.LENGTH_SHORT).show();
                } else {

                    postData(loginUsername.getText().toString(), loginPassword.getText().toString());
                }
            }

        });
        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);

            }
        });

    }

    private void postData(String name, String password) {

        if (loginUsername.getText().toString().isEmpty() || loginPassword.getText().toString().isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
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

        modal = new DataModal(name,password);

        Call<DataModal> call = retrofitAPI.createLogin(modal);

        call.enqueue(new Callback<DataModal>() {
            @Override
            public void onResponse(Call<DataModal> call, Response<DataModal> response) {
                if (response.code() == 200) {
                    Toast.makeText(LoginActivity.this, "Login Successful ", Toast.LENGTH_SHORT).show();

                    loadingPB.setVisibility(View.GONE);
                    loginUsername.setText("");
                    loginPassword.setText("");
                    Intent intent =new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else if (response.code() == 401) {

                    Toast.makeText(LoginActivity.this, "Password or username entered incorrectly  ", Toast.LENGTH_SHORT).show();


                }
            }


            @Override
            public void onFailure(Call<DataModal> call, Throwable t) {

                System.out.println("Error"+t.getMessage());
            }
        });
    }
}
