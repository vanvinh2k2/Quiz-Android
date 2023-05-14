package com.example.myquizapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquizapp.R;
import com.example.myquizapp.Utils.ReferenceManager;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    ReferenceManager manager;
    Button button1,button2;
    TextView nametxt;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = new ReferenceManager(MainActivity.this);
        anhXa();
        getTool();
        start();
    }

    private void getTool() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("     Home");
        }
    }

    private void start() {
        nametxt.setText(manager.getString("name"));
        if(manager.getString("loai").compareTo("1")==0){
            button2.setVisibility(View.GONE);
            button1.setText("Start to play");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),CategoryActivity.class));
                }
            });

        }else{
            button2.setText("Manager question list");
            button1.setText("Manager user list");
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),ManagerUserActivity.class));
                }
            });
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(),ManagerQuestionActivity.class));
                }
            });
        }
    }

    private void anhXa() {
        nametxt = findViewById(R.id.name);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        toolbar = findViewById(R.id.tool);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            manager.clear();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}