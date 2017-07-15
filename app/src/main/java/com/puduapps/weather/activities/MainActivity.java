package com.puduapps.weather.activities;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.puduapps.weather.R;
import com.puduapps.weather.utilities.YahooWeatherAPIManager;

public class MainActivity extends AppCompatActivity {

    static final int ADD_CITY_REQUEST = 1;  // The request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_activity_actions, menu);

        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.action_add_city:
                // User chose the "Add City" action, display AddCityActivity
                addCity();
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void addCity() {
        Intent addCityIntent = new Intent(this, AddCityActivity.class);
        startActivityForResult(addCityIntent, ADD_CITY_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == AddCityActivity.RESULT_OK){
                String result=data.getStringExtra("result");
            }
            if (resultCode == AddCityActivity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}
