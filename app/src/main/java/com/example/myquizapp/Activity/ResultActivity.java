package com.example.myquizapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myquizapp.R;
import com.example.myquizapp.Utils.Constant;
import com.example.myquizapp.Utils.ReferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ResultActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView name,result,timetxt;
    Button feedback,againPlay;
    ReferenceManager manager;
    ImageView image;
    String category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        anhXa();
        getTool();
        getData();
        result();
        functionButton();
    }

    private void getData() {
        Intent intent = getIntent();
        category = intent.getStringExtra("data");
    }

    private void functionButton() {
        feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),FeedbackActivity.class);
                startActivity(intent);
            }
        });
        againPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                Constant.firsttime = date.getTime();
                Intent intent = new Intent(ResultActivity.this,TakeQuizActivity.class);
                intent.putExtra("data",category);
                startActivity(intent);
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),RankActivity.class);
                intent.putExtra("data",category);
                startActivity(intent);
            }
        });
    }

    private void result() {
        result.setText("Your score is "+" "+manager.getString("result")+"/"+manager.getString("size"));
        name.setText("Congratulation "+manager.getString("name"));
        long t = Long.parseLong(manager.getString("time")); // lấy tổng thời gian dưới dang militime
        int seconds = (int) ((t/1000)%60); //chuyển sang giây
        int minutes = (int) ((t/1000)/60);//chuyển sang phút
        String timeFormat = String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
        timetxt.setText("Time: "+timeFormat);//hiển thị time
    }

    private void anhXa() {
        toolbar = findViewById(R.id.tool);
        name = findViewById(R.id.name);
        result = findViewById(R.id.kq1);
        timetxt = findViewById(R.id.time1);
        feedback = findViewById(R.id.home);
        againPlay = findViewById(R.id.lamLai);
        manager = new ReferenceManager(getApplicationContext());
        image = findViewById(R.id.image);
    }

    private void getTool() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Result");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(getApplicationContext(),CategoryActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}