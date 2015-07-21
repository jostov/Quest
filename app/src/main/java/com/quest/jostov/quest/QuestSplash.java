package com.quest.jostov.quest;

import android.app.Activity;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;



public class QuestSplash extends Activity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{

    GoogleApiClient mGoogleClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
    boolean waitingForQuest=false;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_splash);
        Button b = (Button) findViewById(R.id.join_quest);
        Log.d("GPS", "TOMMYCAN YOU HEAR ME");
        buildClient();
        mGoogleClient.connect();
        createLocationRequest();
        b.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new StartQuest().execute(3);
                Button b = (Button) findViewById(R.id.join_quest);
                b.setVisibility(View.INVISIBLE);
                TextView t = (TextView) findViewById(R.id.waiting);
                t.setVisibility(View.VISIBLE);
                waitingForQuest = true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_quest_splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle)
    {
        startUpdates();
    }
    public void startUpdates(){
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleClient, mLocationRequest, this);
        Log.d("GEEPEEESS", "anything?");
        if (mLastLocation != null && !waitingForQuest)
        {
            findViewById(R.id.join_quest).setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onConnectionSuspended(int i)
    {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult)
    {
        Log.d("GPS", "Shit broke");

    }
    protected void createLocationRequest()
    {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(3600);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
    }
    protected synchronized void buildClient(){
        mGoogleClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
        if (mLastLocation != null) {
            Button b = (Button) findViewById(R.id.join_quest);
            b.setVisibility(View.VISIBLE);
        }


    }


    private class StartQuest extends AsyncTask<Integer, Integer, Integer>{
        protected Integer doInBackground(Integer... locations){
            Location quest;
            try
            {
                URL url = new URL(priv.getTarget());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //Set headers and the like.
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Accept", "application/json");
                conn.setRequestMethod("POST");
                //Create the JSON - Opinion, JSONs are such a cool fucking idea wow. Programs talking to eachother, in a way they understand kinda!
                JSONObject data = new JSONObject();
                data.put("coordinate1", String.valueOf(mLastLocation.getLongitude()));
                data.put("coordinate2", String.valueOf(mLastLocation.getLatitude()));
                OutputStreamWriter os = new OutputStreamWriter(conn.getOutputStream());
                os.write(data.toString());
                os.close();
                Log.d("Getttest", String.valueOf(conn.getContent()));
                Log.d("Getttest", "Finished some shit");

                return 9;

            } catch (Exception e) { e.printStackTrace();}

            return 3;
        }

    }
}
