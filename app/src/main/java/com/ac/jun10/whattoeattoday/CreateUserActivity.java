package com.ac.jun10.whattoeattoday;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateUserActivity extends AppCompatActivity {

    private EditText createEmail;
    private EditText createPassword;
    private EditText createRePassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        mAuth = FirebaseAuth.getInstance();

        createEmail = (EditText)findViewById(R.id.createEmail);
        createPassword = (EditText)findViewById(R.id.createPassword);
        createRePassword = (EditText)findViewById(R.id.createRePassword);
        Button cuButton = (Button)findViewById(R.id.cuButton);

        cuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(createPassword.getText().toString().equals(createRePassword.getText().toString())) {
                    createUser(createEmail.getText().toString(), createPassword.getText().toString());
                }
                else{
                    Toast.makeText(CreateUserActivity.this, "입력하신 비밀번호가 서로 다릅니다.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void createUser(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(CreateUserActivity.this, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                            Intent myintent = new Intent(CreateUserActivity.this, FavoriteActivity.class);
                            myintent.putExtra("exist", 0);
                            startActivity(myintent);

                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(CreateUserActivity.this, "회원가입이 실패하였습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
