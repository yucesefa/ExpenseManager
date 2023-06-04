package com.myproject.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.myproject.expensemanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth auth;
    private TextView signUpHere;
    private  TextView forgetPassword;
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);

        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser()!=null){

            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        FirebaseUser user=auth.getCurrentUser();

        if(user != null){

            Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
            finish();
        }

        LoginDetails();
    }

    public void signInClicked(View view){

        String email=binding.emailText.getText().toString();
        String password=binding.passwordText.getText().toString();

        if(email.equals("")|| password.equals("")){

            Toast.makeText(this,"Enter email and password",Toast.LENGTH_LONG).show();
        }else{

            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(authResult -> {

                Intent intent=new Intent(MainActivity.this, HomeActivity.class);
                        startActivity(intent);
                finish();

            }).addOnFailureListener(e -> Toast.makeText(MainActivity.this,
                    e.getLocalizedMessage(),Toast.LENGTH_LONG).show());

        }

    }

    private void LoginDetails(){

        signUpHere=findViewById(R.id.dontAccountText);
        signUpHere.setOnClickListener(v -> {

            Intent intent=new Intent(MainActivity.this,SignUp.class);
            startActivity(intent);

        });




    }




}