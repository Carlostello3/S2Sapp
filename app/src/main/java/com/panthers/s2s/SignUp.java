package com.panthers.s2s;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SignUp extends AppCompatActivity {

    EditText getUserName, getEmail, getEmailVerify, getPassword, getPasswordVerify;
    Button accountButton;
    FirebaseAuth mFirebaseAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mFirebaseAuth = FirebaseAuth.getInstance();
        getUserName = findViewById(R.id.newUserName);
        getEmail = findViewById(R.id.newEmail);
        getEmailVerify = findViewById(R.id.newEmailVerify);
        getPassword = findViewById(R.id.newPassword);
        getPasswordVerify = findViewById(R.id.newPasswordVerify);
        accountButton = findViewById(R.id.accountButton);
        accountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user = getUserName.getText().toString();
                String email = getEmail.getText().toString();
                String emailVerify = getEmailVerify.getText().toString();
                String pwd = getPassword.getText().toString();
                String pwdVerify = getPasswordVerify.getText().toString();

                if (user.isEmpty()) {
                    getUserName.setError("Please enter a name");
                    getUserName.requestFocus();
                }
                else if(email.isEmpty()){
                    getEmail.setError("Please enter email address");
                    getEmail.requestFocus();
                }
                else if (!email.equals(emailVerify)){
                    getEmailVerify.setError("Please enter a valid email address");
                    getEmailVerify.requestFocus();
                }

                else if (pwd.isEmpty()){
                    getPassword.setError("Please enter a password");
                    getPassword.requestFocus();
                }
                else if (!pwd.equals(pwdVerify)){
                    getPasswordVerify.setError("Passwords do not match");
                    getPassword.requestFocus();
                    getPasswordVerify.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(SignUp.this,"Fields are empty!",Toast.LENGTH_SHORT).show();
                }

                else if (!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(SignUp.this, "Unable to create account, please try again!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(user).build();
                                mUser.updateProfile(profileChangeRequest).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            startActivity(new Intent(SignUp.this, MainActivity.class));
                                        }
                                        else {
                                            Toast.makeText(SignUp.this,"Unable to update account", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUp.this, MainActivity.class));
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
                else {
                    Toast.makeText(SignUp.this, "Error Occurred!", Toast.LENGTH_SHORT).show();
                }




            }
        });




    }
}
