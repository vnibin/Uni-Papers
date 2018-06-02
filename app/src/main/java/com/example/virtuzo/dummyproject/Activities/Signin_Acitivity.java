package com.example.virtuzo.dummyproject.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.virtuzo.dummyproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class Signin_Acitivity extends AppCompatActivity implements View.OnClickListener {


 //   private Button buttonregister;
    //private EditText  editText_name;
    private EditText editText_email;
    private EditText editText_password;
    private FirebaseAuth mAuth;
    private TextView textView_login;
   // private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin__acitivity);
        mAuth = FirebaseAuth.getInstance();

        //buttonregister=(Button)findViewById(R.id.btn_signup);
        //editText_name=(EditText)findViewById(R.id.input_name);
        editText_email=(EditText)findViewById(R.id.input_email);
        editText_password=(EditText)findViewById(R.id.input_password);
        //textView_login=(TextView)findViewById(R.id.link_login);

        findViewById(R.id.btn_signup).setOnClickListener(this);
        findViewById(R.id.link_login).setOnClickListener(this);
    }

    private  void registerUser()
    {
        //String name=editText_name.getText().toString().trim();
        String email=editText_email.getText().toString().trim();
        String password=editText_password.getText().toString().trim();

//        if(name.isEmpty())
//        {
//            editText_email.setError(" Username  is required");
//            editText_email.requestFocus();
//            return;
//        }

        if(email.isEmpty())
        {
            editText_email.setError(" Email is required");
            editText_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            editText_email.setError("please enter a valid email address");
            editText_email.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            editText_email.setError(" Password is required");
            editText_email.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            editText_password.setError("Minimum length of password should be atleast 6");
            return;
        }
        //progressBar.setVisibility(View.VISIBLE);




        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                      //  progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(TAG, "createUserWithEmail:success");
                           // FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext()," User registered successfully",Toast.LENGTH_SHORT).show();
                            //updateUI(user);
                        } else {
                            //progressBar.setVisibility(View.GONE);
                           if (task.getException()instanceof FirebaseAuthUserCollisionException) {
                               // If sign in fails, display a message to the user.
                               // Log.w(TAG, "createUserWithEmail:failure", task.getException());
                               Toast.makeText(getApplicationContext(), "you are already registered", Toast.LENGTH_SHORT).show();
                           }
                           else
                           {
                               Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                           }
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_signup:
                registerUser();
                break;
            case R.id.link_login:
                startActivity(new Intent(Signin_Acitivity.this,Login_Acitivity.class));
                break;
        }
    }
}
