package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class AddFeedingPointWithId extends AppCompatActivity {

    Button btnAddFeedingPointWithId;
    EditText editTextFeedingPointID;
    ParseObject feedingPoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_feeding_point_with_id);
        btnAddFeedingPointWithId = findViewById(R.id.btnAddFeedingPointWithId);
        editTextFeedingPointID = findViewById(R.id.editTextFeedingPointID);
    }

    public void addFeedingPointWithId(View view){
        ParseQuery<ParseObject> q = ParseQuery.getQuery("FeedingPoint");
        q.whereEqualTo("objectId", editTextFeedingPointID.getText().toString());
        try {
            feedingPoint = q.find().get(0);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (feedingPoint == null){
            Toast.makeText(this,"There is no feeding point in that id!!!",Toast.LENGTH_LONG).show();
        }else {
            LatLng FPGeoPoint = new LatLng(feedingPoint.getParseGeoPoint("Location").getLatitude(),feedingPoint.getParseGeoPoint("Location").getLongitude());
            Intent intent = new Intent(this, AddingFeedingPoint.class);
            intent.putExtra("feedingPointGeoPoint",FPGeoPoint);
            startActivity(intent);
        }

    }
}
