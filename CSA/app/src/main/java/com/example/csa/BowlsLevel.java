package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class BowlsLevel extends AppCompatActivity {

    ParseObject feedingpoint;
    ParseObject relation;
    TextView txtPercentage;
    TextView txtLevel;
    TextView txtFeedingPointName;
    ImageView imgBowl;
    int waterPercentage;
    int foodPercentage;
    double feedingPointWaterCapacity;
    double feedingPointFoodCapacity;
    double feedingPointWaterWeight;
    double feedingPointFoodWeight;
    String objectId;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.deletemenu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.Delete){
            ParseQuery<ParseObject> q = ParseQuery.getQuery("Relation");
            q.whereEqualTo("FeedingPoint",feedingpoint);
            q.whereEqualTo("user", ParseUser.getCurrentUser());
            try {
                relation = q.find().get(0);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                relation.delete();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Intent intent = new Intent(this, FeedingPoints.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bowls_level);

        txtFeedingPointName = findViewById(R.id.txtFeedingPointName);
        txtLevel = findViewById(R.id.txtLevel);
        txtPercentage = findViewById(R.id.txtPercentage);
        imgBowl = findViewById(R.id.imgBowl);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            objectId = extras.getString("objectId");
        }
        ParseQuery<ParseObject> q = ParseQuery.getQuery("FeedingPoint");
        q.whereMatches("objectId",objectId);
        try {
            feedingpoint = q.find().get(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (feedingpoint != null){
            txtFeedingPointName.setText(feedingpoint.getString("Name"));
            feedingPointFoodCapacity = feedingpoint.getInt("FoodCapacity");
            feedingPointWaterCapacity = feedingpoint.getInt("WaterCapacity");
            feedingPointFoodWeight = feedingpoint.getInt("FoodWeight");
            feedingPointWaterWeight = feedingpoint.getInt("WaterWeight");
            try {
                foodPercentage = (int)((feedingPointFoodWeight/feedingPointFoodCapacity)*100);
                waterPercentage = (int)((feedingPointWaterWeight/feedingPointWaterCapacity)*100);
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }

    public void showWaterLevel(View view){
        txtLevel.setText("Water Level");
        txtPercentage.setText("%" + waterPercentage);
    }
    public void showFoodLevel(View view){
        txtLevel.setText("Food Level");
        txtPercentage.setText("%" + foodPercentage);
    }
    public void showBowlStatistics(View view){
        /*Intent intent = new Intent(getApplicationContext(), StatisticsOfBowls.class);
        intent.putExtra("objectId", objectId);
        startActivity(intent);
*/
    }
    public void aboutFP(View view){
        LatLng FPGeoPoint = new LatLng(feedingpoint.getParseGeoPoint("Location").getLatitude(),feedingpoint.getParseGeoPoint("Location").getLongitude());
        Intent intent = new Intent(this, AddingFeedingPoint.class);
        intent.putExtra("feedingPointGeoPoint",FPGeoPoint);
        intent.putExtra("fromBowl",1);
        startActivity(intent);

    }
}
