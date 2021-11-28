package com.example.favouritemovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyAdapter adapter;
    ArrayList<Movie> movieList=new ArrayList<Movie>();
    RecyclerView recyclerView;
    DatabaseHelper dbhelper;
    FloatingActionButton add;
    EditText searchEdtx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=(RecyclerView)findViewById(R.id.movieRecycylerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbhelper=new DatabaseHelper(getApplicationContext());
        add=(FloatingActionButton)findViewById(R.id.addMovie);
        searchEdtx=(EditText)findViewById(R.id.searchEdtx);

        adapter=new MyAdapter(movieList,getApplicationContext());
        recyclerView.setAdapter(adapter);

        getData();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddMovie.class);
                startActivity(intent);
            }
        });


        searchEdtx.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchFilter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty()){
                    searchFilter(s.toString());
                }else {
                    movieList.clear();
                    getData();
                }
            }
        });
    }

    private void getData(){
        dbhelper.getData();
        Cursor cursor=dbhelper.getData();

        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "No entry exists",
                    Toast.LENGTH_SHORT).show();
        }else {

            while (cursor.moveToNext()) {
                String name=cursor.getString(0);

                String actor=cursor.getString(1);
                String actoress=cursor.getString(2);
                String director=cursor.getString(3);
                String releaseDate=cursor.getString(4);
                String language=cursor.getString(5);
                String country=cursor.getString(6);

                movieList.add(new Movie(name,actor,actoress,director,releaseDate,language,country));

            }
            Log.i("cursor",movieList.toString());
        }
    }
    private void searchFilter(String text){
        String searchTxt=text.trim();
        ArrayList<Movie> FilterdList = new ArrayList<>();

        //looping through existing elements
        for (Movie s : movieList) {
            //if the existing elements contains the search input
            if (s.getName().toLowerCase().contains(searchTxt.toLowerCase()) || s.getActor().toLowerCase().contains(searchTxt.toLowerCase())
                    || s.getActress().toLowerCase().contains(searchTxt.toLowerCase()) ||  s.getDirector().toLowerCase().contains(searchTxt.toLowerCase())
                    || s.getReleaseDate().toLowerCase().contains(searchTxt.toLowerCase()) || s.getCountry().toLowerCase().contains(searchTxt.toLowerCase())
                    || s.getLanguage().toLowerCase().contains(searchTxt.toLowerCase()) ) {
                //adding the element to filtered list
                FilterdList.add(s);
            }
        }
        adapter.setFilter(FilterdList);

    }
}