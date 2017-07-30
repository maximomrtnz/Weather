package com.puduapps.weather.activities;

import android.app.SearchManager;
import android.content.Context;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.puduapps.weather.R;
import com.puduapps.weather.utilities.YahooWeatherAPIManager;

public class AddCityActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, LoaderManager.LoaderCallbacks<YahooWeatherAPIManager.YahooResponse>{

    private static final String TAG = AddCityActivity.class.getSimpleName();

    private ProgressBar mLoadingIndicator;
    private String cityName;
    private RecyclerView mRecyclerView;

    private static final int CITY_LOADER_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        // my_child_toolbar is defined in the layout file
        Toolbar myChildToolbar = (Toolbar) findViewById(R.id.add_city_activity_toolbar);
        setSupportActionBar(myChildToolbar);

        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();

        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         */
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_city_activity_actions, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search_cities).getActionView();

        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        if(query.length() == 0){
            return false;
        }

        Log.d(TAG,"onQueryTextSubmit =>"+query);

        /*
         * This ID will uniquely identify the Loader. We can use it, for example, to get a handle
         * on our Loader at a later point in time through the support LoaderManager.
         */
        int loaderId = CITY_LOADER_ID;

        cityName = query;

        /*
         * From MainActivity, we have implemented the LoaderCallbacks interface with the type of
         * YahooWeatherAPIManager.YahooResponse. (implements LoaderCallbacks<YahooWeatherAPIManager.YahooResponse>) The variable callback is passed
         * to the call to initLoader below. This means that whenever the loaderManager has
         * something to notify us of, it will do so through this callback.
         */
        LoaderManager.LoaderCallbacks<YahooWeatherAPIManager.YahooResponse> callback = AddCityActivity.this;

        /*
         * The second parameter of the initLoader method below is a Bundle. Optionally, you can
         * pass a Bundle to initLoader that you can then access from within the onCreateLoader
         * callback. In our case, we don't actually use the Bundle, but it's here in case we wanted
         * to.
         */
        Bundle bundleForLoader = null;

        /*
         * Ensures a loader is initialized and active. If the loader doesn't already exist, one is
         * created and (if the activity/fragment is currently started) starts the loader. Otherwise
         * the last created loader is re-used.
         */
        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG,"onQueryTextChange =>"+newText);
        return false;
    }

    @Override
    public Loader<YahooWeatherAPIManager.YahooResponse> onCreateLoader(int id, Bundle args) {

        return new AsyncTaskLoader<YahooWeatherAPIManager.YahooResponse>(this){

            /**
             * Subclasses of AsyncTaskLoader must implement this to take care of loading their data.
             */
            @Override
            protected void onStartLoading() {
                mLoadingIndicator.setVisibility(View.VISIBLE);
                forceLoad();
            }

            /**
             * This is the method of the AsyncTaskLoader that will load and parse the JSON data
             * from OpenWeatherMap in the background.
             *
             * @return Weather data from OpenWeatherMap as an array of Strings.
             *         null if an error occurs
             */
            @Override
            public YahooWeatherAPIManager.YahooResponse loadInBackground() {

                try{
                    return YahooWeatherAPIManager.getPlacesByCityName(cityName);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }

            }


        };
    }

    @Override
    public void onLoadFinished(Loader<YahooWeatherAPIManager.YahooResponse> loader, YahooWeatherAPIManager.YahooResponse data) {

        mLoadingIndicator.setVisibility(View.INVISIBLE);

        Log.d(TAG,data.getQuery().getResults().getPlace().size()+"");

        for(){

        }

    }

    @Override
    public void onLoaderReset(Loader<YahooWeatherAPIManager.YahooResponse> loader) {



    }

}
