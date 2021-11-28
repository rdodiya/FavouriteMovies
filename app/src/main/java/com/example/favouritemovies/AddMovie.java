package com.example.favouritemovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddMovie extends AppCompatActivity {

    EditText add_name,add_actor,add_actress,add_director,add_releaseDate,add_language,add_country;

    DatabaseHelper databaseHelper;
    String name,actor,actress,director,releaseDate,language,country;
    Button addBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);

        add_name=(EditText) findViewById(R.id.add_name);
        add_actor=(EditText)findViewById(R.id.add_actor);
        add_actress=(EditText)findViewById(R.id.add_actress);
        add_director=(EditText)findViewById(R.id.add_director);
        add_releaseDate=(EditText)findViewById(R.id.add_releaseDate);
        add_language=(EditText)findViewById(R.id.add_language);
        add_country=(EditText)findViewById(R.id.add_country);

        addBtn=(Button)findViewById(R.id.add_Btn);

        databaseHelper=new DatabaseHelper(this);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=add_name.getText().toString();
                actor=add_actor.getText().toString();
                actress=add_actress.getText().toString();
                releaseDate=add_releaseDate.getText().toString();
                director=add_director.getText().toString();
                language=add_language.getText().toString();
                country=add_country.getText().toString();

                if(!name.isEmpty() && !actor.isEmpty() && !actress.isEmpty() && !releaseDate.isEmpty() && !director.isEmpty()
                        && !language.isEmpty() && !country.isEmpty()){
                    if(databaseHelper.insertData(name,actor,actress,director,releaseDate,language,country)){
                        Toast.makeText(AddMovie.this, "Data Inserted successfully", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AddMovie.this,MainActivity.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(AddMovie.this, "Data does not inserted. Please click on submit button again", Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(AddMovie.this, "Please enter details.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}