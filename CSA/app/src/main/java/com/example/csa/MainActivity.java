package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity{

    Intent intent;
    EditText loginEmailText,loginPasswordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginEmailText = findViewById(R.id.txtLoginEmail);
        loginPasswordText = findViewById(R.id.txtLoginPassword);
        ParseUser user = ParseUser.getCurrentUser();
        if (user != null){
            Intent intent = new Intent(getApplicationContext(),FeedingPoints.class);
            startActivity(intent);

        }

    }



    public void signUp1(View view){
        intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
    public void login(View view){
        String email = loginEmailText.getText().toString();

            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("email", email);
            query.getFirstInBackground(new GetCallback<ParseUser>() {
                @Override
                public void done(ParseUser object, ParseException e) {
                    if (object != null) {

                        ParseUser.logInInBackground(object.getString("username"), loginPasswordText.getText().toString(), new LogInCallback() {
                            public void done(ParseUser user, ParseException e) {
                                if (e != null) {

                                    Toast.makeText(getApplicationContext(),"giriş başarısız!" + e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                                } else {

                                    Toast.makeText(getApplicationContext(),"giriş başarılı!",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(),FeedingPoints.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            });


    }
    public void changeThePassword(View view){
        intent = new Intent(this,ChangingPassword.class);
        startActivity(intent);
    }


}
