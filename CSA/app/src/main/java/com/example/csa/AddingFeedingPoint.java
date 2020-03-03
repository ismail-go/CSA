package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddingFeedingPoint extends AppCompatActivity {

    ParseGeoPoint feedingPointGeoPoint;
    Geocoder geocoder;
    List<Address> addresses;
    LatLng latLng;
    double latitude;
    double longitude;
    String selectedFeedingPointID;
    String feedingPointId;
    ParseObject selectedFeedingPoint;
    List<ParseObject> selectedFeedingPointList;
    List<ParseObject> FeedingPointList;
    int numberOfVolunteer;
    Button addButton;
    TextView feedingPointName;
    TextView feedingPointCode;
    TextView feedingPointAddress;
    TextView feedingPointNumOfVol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_feeding_point);
        feedingPointName = findViewById(R.id.txtFeedingPointName);
        feedingPointCode = findViewById(R.id.txtFeedingPointCode);
        feedingPointAddress = findViewById(R.id.txtFeedingPointAddress);
        feedingPointNumOfVol = findViewById(R.id.txtFeedingPointNumOfVol);
        addButton = findViewById(R.id.btnAdd);
        geocoder = new Geocoder(this, Locale.getDefault());

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            latLng = extras.getParcelable("feedingPointGeoPoint");
            latitude = latLng.latitude;
            longitude = latLng.longitude;
            feedingPointGeoPoint = new ParseGeoPoint(latitude,longitude);
        }
        if (extras.getInt("fromBowl") == 1){
            addButton.setVisibility(View.INVISIBLE);
        }

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0);
        feedingPointAddress.setText(address);


        ParseQuery<ParseObject> query = ParseQuery.getQuery("FeedingPoint");
        query.whereNear("Location",feedingPointGeoPoint);

        try {
            selectedFeedingPointList = query.find();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        selectedFeedingPoint = selectedFeedingPointList.get(0);
        selectedFeedingPointID = selectedFeedingPoint.getObjectId();
        feedingPointName.setText(selectedFeedingPoint.getString("Name"));
        feedingPointCode.setText(selectedFeedingPointID);
        ParseQuery<ParseObject> q2 = ParseQuery.getQuery("Relation");
        q2.whereEqualTo("FeedingPoint",selectedFeedingPoint);
        try {

            numberOfVolunteer = q2.find().size();

        } catch (ParseException e) {
            e.printStackTrace();
        }


        feedingPointNumOfVol.setText(String.valueOf(numberOfVolunteer));
    }
    public void add(View view){

        ParseQuery<ParseObject> q = ParseQuery.getQuery("Relation");
        q.whereEqualTo("user",ParseUser.getCurrentUser());
        q.whereEqualTo("FeedingPoint",selectedFeedingPoint);
        q.include("FeedingPoint");
        try {
            FeedingPointList = q.find();

        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (FeedingPointList.size() == 0) {
            ParseObject relation = new ParseObject("Relation");
            relation.put("user", ParseUser.getCurrentUser());
            relation.put("FeedingPoint", selectedFeedingPoint);
            relation.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null)
                        Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_LONG);

                }
            });
        }else{
            Toast.makeText(this,"Zaten bu feeding point ekli tekrar ekleyemessiniz!!!", Toast.LENGTH_LONG).show();
        }
        Intent intent = new Intent(this,FeedingPoints.class);
        startActivity(intent);
    }
}
