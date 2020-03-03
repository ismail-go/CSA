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
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class ChangingPassword extends AppCompatActivity {

    EditText emailText,passwordText;
    ParseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changing_password);
        emailText = findViewById(R.id.txtChangPasswEmail);
        passwordText = findViewById(R.id.txtChangPasswPassword);
    }


    public void change(View view){

        final String email = emailText.getText().toString();

        ParseUser.requestPasswordResetInBackground(email,new RequestPasswordResetCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // An email was successfully sent with reset instructions.

                    ParseQuery<ParseUser> q = ParseUser.getQuery();
                    q.whereMatches("email",email);
                    try {
                        user = q.find().get(0);
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    user.setPassword(passwordText.getText().toString());
                    try {
                        user.save();
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),"Şifre değitirildi!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(),"Şifre değitirilemedi!" + e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                    // Something went wrong. Look at the ParseException to see what's up.
                }
            }
        });
    }
}
