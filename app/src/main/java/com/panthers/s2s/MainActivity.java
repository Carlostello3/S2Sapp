package com.panthers.s2s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    EditText userName, passWord;
    Button loginButton;
    TextView createAccount, resetPassword;
    FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    Toast backToast;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        userName = findViewById(R.id.userName);
        passWord = findViewById(R.id.passWord);
        createAccount = findViewById(R.id.createAccount);
        loginButton = findViewById(R.id.loginButton);
        resetPassword = findViewById(R.id.resetPassword);
        spinner = findViewById(R.id.progressBar);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                if(firebaseUser != null){
                    //Toast.makeText(MainActivity.this,"You are logged in", Toast.LENGTH_SHORT).show();
                    Intent succesfullLogin = new Intent(MainActivity.this, HomeScreen.class);
                    startActivity(succesfullLogin);
                }
            }
        };

        spinner.setVisibility(View.GONE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginButton.setVisibility(View.GONE);
                spinner.setVisibility(View.VISIBLE);
                String user = userName.getText().toString();
                String pwd = passWord.getText().toString();

                if (user.isEmpty()){
                    userName.setError("Please enter a valid email address");
                    userName.requestFocus();
                }
                else if (pwd.isEmpty()){
                    passWord.setError("Please enter password");
                    passWord.requestFocus();
                }
                else if (user.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this,"Fields are empty", Toast.LENGTH_SHORT).show();
                }
                else if(!(user.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(user, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Invalid Email/Password, Please Login Again", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Intent goHome = new Intent(MainActivity.this, HomeScreen.class);
                                startActivity(goHome);
                            }
                        }
                    });


                }
            }
        });



        resetPassword.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View v) {
                String userPwReset = userName.getText().toString();
                if (!userPwReset.isEmpty()){
                    mFirebaseAuth.sendPasswordResetEmail(userPwReset).addOnCompleteListener(MainActivity.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "No account found, please try again", Toast.LENGTH_SHORT).show();
                                userName.requestFocus();
                            }
                            else {
                                Toast.makeText(MainActivity.this, "Reset Password Email sent", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this,"Enter account email address", Toast.LENGTH_SHORT).show();
                    userName.requestFocus();
                }
            }
        });


        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createUser = new Intent(MainActivity.this, SignUp.class);
                startActivity(createUser);
            }
        });
    }

    @Override
    public void onBackPressed() {


        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();

    }


    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
}
