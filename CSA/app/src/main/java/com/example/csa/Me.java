package com.example.csa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Me extends AppCompatActivity{

    Button btnMe;
    TextView nameText,ageText,genderText;
    ImageView profilePhoto;
    Bitmap choosenImage;
    ParseUser user = ParseUser.getCurrentUser();
    Spinner partOfDayForMonday;
    Spinner partOfDayForTuesday;
    Spinner partOfDayForWednesday;
    Spinner partOfDayForThursday;
    Spinner partOfDayForFriday;
    Spinner partOfDayForSaturday;
    Spinner partOfDayForSunday;
    static Bitmap profileImage;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logoutmenu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.Logout){
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null)
                        Toast.makeText(getApplicationContext(),"çıkış yapılamadı!!!\n" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    else{
                        Toast.makeText(getApplicationContext(),"çıkış yapıldı", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);

        nameText = findViewById(R.id.txtNameForMe);
        ageText = findViewById(R.id.txtAgeForMe);
        genderText = findViewById(R.id.txtGenderForMe);
        btnMe = findViewById(R.id.btnMe);
        btnMe.setEnabled(false);
        nameText.setText(user.getUsername());
        ageText.setText(user.getString("age"));
        genderText.setText(user.getString("gender"));
        profilePhoto = findViewById(R.id.imageViewProfilePhoto);

        if(profileImage != null)
            profilePhoto.setImageBitmap(profileImage);

        if(user.getParseFile("image") != null && profileImage == null){
            ParseFile parseFile = user.getParseFile("image");
            parseFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] data, ParseException e) {
                    if(e == null && data != null){
                        profileImage = BitmapFactory.decodeByteArray(data,0,data.length);
                        profilePhoto.setImageBitmap(profileImage);
                    }
                }
            });
        }

        partOfDayForMonday = findViewById(R.id.spinnerMonday);
        ArrayAdapter<String> adapterForMonday = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.PartsOfDay));
        adapterForMonday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partOfDayForMonday.setAdapter(adapterForMonday);
        partOfDayForMonday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put("PartForMonday",position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        partOfDayForMonday.setSelection(user.getInt("PartForMonday"));

        partOfDayForTuesday = findViewById(R.id.spinnerTuesday);
        ArrayAdapter<String> adapterForTuesday = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.PartsOfDay));
        adapterForTuesday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partOfDayForTuesday.setAdapter(adapterForTuesday);
        partOfDayForTuesday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put("PartForTuesday",position);
                try {
                    user.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        partOfDayForTuesday.setSelection(user.getInt("PartForTuesday"));

        partOfDayForWednesday = findViewById(R.id.spinnerWednesday);
        ArrayAdapter<String> adapterForWednesday = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.PartsOfDay));
        adapterForWednesday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partOfDayForWednesday.setAdapter(adapterForWednesday);
        partOfDayForWednesday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put("PartForWednesday",position);
                try {
                    user.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        partOfDayForWednesday.setSelection(user.getInt("PartForWednesday"));

        partOfDayForThursday = findViewById(R.id.spinnerThursday);
        ArrayAdapter<String> adapterForThursday = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.PartsOfDay));
        adapterForWednesday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partOfDayForThursday.setAdapter(adapterForThursday);
        partOfDayForThursday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put("PartForThursday",position);
                try {
                    user.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        partOfDayForThursday.setSelection(user.getInt("PartForThursday"));

        partOfDayForFriday = findViewById(R.id.spinnerFriday);
        ArrayAdapter<String> adapterForFriday = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.PartsOfDay));
        adapterForWednesday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partOfDayForFriday.setAdapter(adapterForFriday);
        partOfDayForFriday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put("PartForFriday",position);
                try {
                    user.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        partOfDayForFriday.setSelection(user.getInt("PartForFriday"));

        partOfDayForSaturday = findViewById(R.id.spinnerSaturday);
        ArrayAdapter<String> adapterForSaturday = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.PartsOfDay));
        adapterForWednesday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partOfDayForSaturday.setAdapter(adapterForSaturday);
        partOfDayForSaturday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put("PartForSaturday",position);
                try {
                    user.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        partOfDayForSaturday.setSelection(user.getInt("PartForSaturday"));

        partOfDayForSunday = findViewById(R.id.spinnerSunday);
        ArrayAdapter<String> adapterForSunday = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.PartsOfDay));
        adapterForWednesday.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partOfDayForSunday.setAdapter(adapterForSunday);
        partOfDayForSunday.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                user.put("PartForSunday",position);
                try {
                    user.save();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        partOfDayForSunday.setSelection(user.getInt("PartForSunday"));





    }
    public void setToFeedingPoints(View view){
        Intent intent = new Intent(this,FeedingPoints.class);
        startActivity(intent);
    }
    public void logout(View view){
        /*ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null)
                    Toast.makeText(getApplicationContext(),"çıkış yapılamadı!!!\n" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                else{
                    Toast.makeText(getApplicationContext(),"çıkış yapıldı", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
            }
        });*/
    }
    public void upload(View view){

    }

    public void chooseImage(View view){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},2);
        }else{
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent,1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,1);
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 1 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                choosenImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
                profileImage = choosenImage;
                profilePhoto.setImageBitmap(profileImage);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                choosenImage.compress(Bitmap.CompressFormat.PNG,50,byteArrayOutputStream);
                byte[] bytes = byteArrayOutputStream.toByteArray();
                ParseFile parseFile = new ParseFile("profilephoto.png",bytes);
                user.put("image",parseFile);
                user.saveInBackground();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
