package ht.queeny.taxirapide2;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.*;

public class RegisterDriver extends AppCompatActivity {

    private EditText editText_password, editText_email;
    Button registerButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_driver);


        firestore = FirebaseFirestore.getInstance();
        // Set up the login form.
        editText_email = (EditText) findViewById(R.id.register_emaildr);
        editText_password = (EditText) findViewById(R.id.register_passwordr);
        registerButton = (Button) findViewById(R.id.email_sign_in_buttondr);

//        registerButton.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ParseUser user = new ParseUser();
//                user.setUsername(editText_email.getText().toString());
//                user.setPassword(editText_password.getText().toString());
//                user.signUpInBackground(new SignUpCallback() {
//                    @Override
//                    public void done(ParseException e) {
//                        if (e == null) {
//                            //Register Successful
//                            //you can display sth or do sth
//                            Toast.makeText(RegisterActivity.this, "Register", Toast.LENGTH_SHORT).show();
//                        } else {
//                            //Register Fail
//                            //get error by calling e.getMessage()
//                            Toast.makeText(RegisterActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//            }
//        });


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    // The user has not logged in
                }else{
                    // The user has logged in
                }
            }
        };


        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
//                String email = editText_email.getText().toString();
//                String pass = editText_password.getText().toString();
//
//                if(email.isEmpty() || pass.isEmpty()){
//                    editText_email.setError("Is empty");
//                    return;
//                }
//
//                if(pass.isEmpty()){
//                    editText_password.setError("Is empty");
//                    return;
//                }
//
//                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
//                            startActivity(intent);
//                            Toast.makeText(RegisterActivity.this, "Sign Up", Toast.LENGTH_SHORT).show();
//
//                        }else {
//                            Toast.makeText(RegisterActivity.this, "Not Sign Up", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }
        });
    }


    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    public void Register()
    {


        String email = editText_email.getText().toString();
        String pass = editText_password.getText().toString();

        if(email.isEmpty() || pass.isEmpty()){
            editText_email.setError("Is empty");
            return;
        }

        if(pass.isEmpty()){
            editText_password.setError("Is empty");
            return;
        }


        java.util.Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("email", email);
        hashMap.put("password", pass);

        firestore.collection("users").add(hashMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(RegisterDriver.this, "save", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterDriver.this, "not save", Toast.LENGTH_SHORT).show();
            }
        });

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent = new Intent(RegisterDriver.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(RegisterDriver.this, "Sign Up", Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(RegisterDriver.this, "Not Sign Up", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
