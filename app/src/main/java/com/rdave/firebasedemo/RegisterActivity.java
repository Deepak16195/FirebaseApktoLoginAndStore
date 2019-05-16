package com.rdave.firebasedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public class RegisterActivity extends BaseActivity {


    EditText  etextEmail,etextPass;
    Button btnRegis;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);



        etextEmail = findViewById(R.id.textViewEmail);
        etextPass = findViewById(R.id.textViewPass);
        btnRegis = findViewById(R.id.usrRegis);



        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String regEmail = etextEmail.getText().toString().trim();
                String regPass = etextPass.getText().toString().trim();

                RegisterUser(regEmail,regPass);
             }
        });


    }

    private void RegisterUser(String regEmail, String regPass) {
        mAuth.createUserWithEmailAndPassword(regEmail,regPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Successfull",Toast.LENGTH_SHORT).show();
                    Intent r = new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(r);
                }
                else{
                    Toast.makeText(RegisterActivity.this,""+task.getException(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}


