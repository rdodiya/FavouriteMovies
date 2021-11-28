package com.example.favouritemovies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MovieInformation extends AppCompatActivity {


    Button editBtn,deleteBtn,updateBtn;
    EditText mi_actor,mi_actress,mi_director,mi_releaseDate,mi_language,mi_country;
    TextView movieName;
    DatabaseHelper databaseHelper;
    String name,actor,actress,director,releaseDate,language,country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_information);


        name=getIntent().getStringExtra("name");

        movieName=(TextView)findViewById(R.id.mi_name);
        mi_actor=(EditText)findViewById(R.id.mi_actor);
        mi_actress=(EditText)findViewById(R.id.mi_actress);
        mi_director=(EditText)findViewById(R.id.mi_director);
        mi_releaseDate=(EditText)findViewById(R.id.mi_releaseDate);
        mi_language=(EditText)findViewById(R.id.mi_language);
        mi_country=(EditText)findViewById(R.id.mi_country);

        editBtn=(Button)findViewById(R.id.editBtn);
        deleteBtn=(Button)findViewById(R.id.deleteBtn);
        updateBtn=(Button)findViewById(R.id.updateBtn);

        databaseHelper=new DatabaseHelper(this);

        Cursor cursor=databaseHelper.getData(name);
        ArrayList<String> arrayList=new ArrayList<String>();
        if(cursor.getCount()==0){
            Toast.makeText(getApplicationContext(), "No entry exists",
                    Toast.LENGTH_SHORT).show();
        }else {

            while (cursor.moveToNext()) {
                arrayList.add(cursor.getString(1));
                arrayList.add(cursor.getString(2));
                arrayList.add(cursor.getString(3));
                arrayList.add(cursor.getString(4));
                arrayList.add(cursor.getString(5));
                arrayList.add(cursor.getString(6));

            }
            Log.i("cursor",arrayList.toString());
        }

        mi_actor.setText(arrayList.get(0));
        mi_actress.setText(arrayList.get(1));
        mi_director.setText(arrayList.get(2));
        mi_releaseDate.setText(arrayList.get(3));
        mi_language.setText(arrayList.get(4));
        mi_country.setText(arrayList.get(5));


        movieName.setText(getIntent().getStringExtra("name"));

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mi_actor.setFocusableInTouchMode(true);
                mi_actress.setFocusableInTouchMode(true);
                mi_director.setFocusableInTouchMode(true);
                mi_releaseDate.setFocusableInTouchMode(true);
                mi_language.setFocusableInTouchMode(true);
                mi_country.setFocusableInTouchMode(true);

                deleteBtn.setVisibility(View.GONE);
                editBtn.setVisibility(View.GONE);
                updateBtn.setVisibility(View.VISIBLE);

            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=movieName.getText().toString();
                actor=mi_actor.getText().toString();
                actress=mi_actress.getText().toString();
                director=mi_director.getText().toString();
                releaseDate=mi_releaseDate.getText().toString();
                language=mi_language.getText().toString();
                country=mi_country.getText().toString();


                if(!actor.isEmpty() && !actress.isEmpty() && !director.isEmpty() && !releaseDate.isEmpty()
                        && !language.isEmpty() && !country.isEmpty()){
                    if(databaseHelper.updateData(name,actor,actress,director,releaseDate,language,country)){
                        Toast.makeText(getApplicationContext(), "Data update successfull", Toast.LENGTH_SHORT).show();
                        mi_actor.setFocusableInTouchMode(false);
                        mi_actress.setFocusableInTouchMode(false);
                        mi_director.setFocusableInTouchMode(false);
                        mi_releaseDate.setFocusableInTouchMode(false);
                        mi_language.setFocusableInTouchMode(false);
                        mi_country.setFocusableInTouchMode(false);

                        deleteBtn.setVisibility(View.VISIBLE);
                        editBtn.setVisibility(View.VISIBLE);
                        updateBtn.setVisibility(View.GONE);
                    }else{
                        Toast.makeText(getApplicationContext(), "Data updatiaon failed ,Please click again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(databaseHelper.deleteData(name)){
                    Toast.makeText(getApplicationContext(), "Delete user successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }else {
                    Toast.makeText(getApplicationContext(), "Delete user unsuccesful .Please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}