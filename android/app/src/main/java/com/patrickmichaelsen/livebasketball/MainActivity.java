package com.patrickmichaelsen.livebasketball;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.patrickmichaelsen.livebasketball.R.id.rv;

public class MainActivity extends AppCompatActivity {

    private List<Person> persons;
    private Leagues leagues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = new Intent(this,RegistrationService.class);
        this.startService(i);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://10.0.2.2:8080/livebasketball/leagues";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Gson gson = new Gson();
                        leagues = gson.fromJson(response, Leagues.class);
                        RecyclerView rv = (RecyclerView)findViewById(R.id.rv);
                        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                        rv.setLayoutManager(llm);
                        Collection collection = leagues.getLeagues().values();
                        List list = new ArrayList(collection);
                        Collections.sort(list);
                        RVLeagueAdapter adapter = new RVLeagueAdapter(list);
                        rv.setAdapter(adapter);
                        Log.e("REST", response.substring(0,500));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("REST", error.getMessage());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);

        //RecyclerView rv = (RecyclerView)findViewById(rv);
        //LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        //rv.setLayoutManager(llm);
        //this.initializeData();
        //RVAdapter adapter = new RVAdapter(persons);
        //rv.setAdapter(adapter);


        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void initializeData(){
        persons = new ArrayList<>();
        persons.add(new Person("Emma Wilson", "23 years old", R.drawable.ic_feedback_black_48dp));
        persons.add(new Person("Lavery Maiss", "25 years old", R.drawable.ic_feedback_black_48dp));
        persons.add(new Person("Lillie Watts", "35 years old", R.drawable.ic_feedback_black_48dp));
        persons.add(new Person("Patrick James", "15 years old", R.drawable.ic_feedback_black_48dp));
        persons.add(new Person("Paul Parker", "38 years old", R.drawable.ic_feedback_black_48dp));
        persons.add(new Person("Dan Fortworth", "21 years old", R.drawable.ic_feedback_black_48dp));
    }
}
