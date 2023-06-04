package com.myproject.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.myproject.expensemanager.databinding.ActivitySignUpBinding;

import com.google.firebase.auth.FirebaseAuth;

public class SignUp extends AppCompatActivity {

    private ActivitySignUpBinding binding;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignUpBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        auth=FirebaseAuth.getInstance();
    }
    public void signUpClicked(View view) {

        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();

        if (email.equals("") || password.equals("")) {

            Toast.makeText(this, "Enter email and Password", Toast.LENGTH_LONG).show();
        } else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {

                Intent intent=new Intent(SignUp.this,MainActivity.class);
                startActivity(intent);
                finish();

            }).addOnFailureListener(e -> {

                Toast.makeText(SignUp.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();

            });
        }

    }

}