package com.example.csa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.csa.AddFeedingPointWithId;
import com.example.csa.BowlsLevel;
import com.example.csa.MainActivity;
import com.example.csa.MapsActivity;
import com.example.csa.Me;
import com.example.csa.R;
import com.parse.LogOutCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class FeedingPoints extends AppCompatActivity {

    Button btnFeedingPoints;
    ListView listViewFeedingPoints;
    ArrayList<String> feedingPoints = new ArrayList<String>();
    ArrayList<String> feedingPointsId = new ArrayList<String>();
    List<ParseObject> objects;
    ParseObject parseObject;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == R.id.add_FP_by_map){
            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.add_FP_by_code){
            Intent intent = new Intent(getApplicationContext(), AddFeedingPointWithId.class);
            startActivity(intent);
        }
        if(item.getItemId() == R.id.Logout){
            ParseUser.logOutInBackground(new LogOutCallback() {
                @Override
                public void done(ParseException e) {
                    if (e != null)
                        Toast.makeText(getApplicationContext(),"çıkış yapılamadı!!!\n" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    else{
                        Toast.makeText(getApplicationContext(),"çıkış yapıldı", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
        setContentView(R.layout.activity_feeding_points);
        btnFeedingPoints = findViewById(R.id.btnFeedingPoints);
        btnFeedingPoints.setEnabled(false);
        listViewFeedingPoints = (ListView) findViewById(R.id.listViewFeedingPoints);

        ParseQuery<ParseObject> q = ParseQuery.getQuery("Relation");
        q.whereEqualTo("user",ParseUser.getCurrentUser());
        q.include("FeedingPoint");
        try {
            objects = q.find();
            for (ParseObject object:objects) {
                parseObject = (ParseObject) (object.get("FeedingPoint"));
                feedingPoints.add(parseObject.getString("Name"));
                feedingPointsId.add(parseObject.getObjectId());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (feedingPoints.size()>0) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, feedingPoints);
            listViewFeedingPoints.setAdapter(arrayAdapter);
            listViewFeedingPoints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(getApplicationContext(), BowlsLevel.class);
                    intent.putExtra("objectId", feedingPointsId.get(position));
                    startActivity(intent);
                }
            });
        }
    }

    public void setToMe(View view){
        Intent intent = new Intent(this, Me.class);
        startActivity(intent);
    }
}
