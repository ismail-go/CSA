package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseRelation;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {

    EditText nameText,ageText,emailText,passwordText;
    RadioGroup genderRadioGroup;
    String gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_activity);
        nameText = findViewById(R.id.txtNameForSignup);
        genderRadioGroup = findViewById(R.id.radioGroupGender);
        ageText = findViewById(R.id.txtAgeForSignup);
        emailText = findViewById(R.id.txtEmail);
        passwordText = findViewById(R.id.txtPassword);
        genderRadioGroup.setOnCheckedChangeListener(this);

    }

    public void signUp2(View view){



        ParseUser user = new ParseUser();
        user.setEmail(emailText.getText().toString());
        user.setUsername(nameText.getText().toString());
        user.setPassword(passwordText.getText().toString());
        user.put("age",ageText.getText().toString());
        user.put("gender",gender);


        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Toast.makeText(getApplicationContext(),e.getLocalizedMessage() + "Kaydedilmedi!",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplicationContext(),"Kayıt Başarılı!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.radiobtnMale:
                gender = "Male";
                break;
            case R.id.radiobtnFemale:
                gender = "Female";
                break;
        }
    }
}
