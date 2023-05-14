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
import android.widget.TextView;

import com.example.myquizapp.R;
import com.example.myquizapp.Utils.ReferenceManager;

public class FeedbackActivity extends AppCompatActivity {
    TextView error;
    Button send;
    Toolbar toolbar;
    EditText message;
    ReferenceManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        anhXa();
        getTool();
        sendFeedBack();
    }
    private void sendFeedBack() {
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(message.getText().toString().trim().isEmpty()){//kiểm tra nội dung nhập có rỗng ko
                    error.setText("Please enter content!");
                }
                else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("message/html");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "vinhngo240302@email.address" });//email gơi tới
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback from App");//chủ đề
                    intent.putExtra(Intent.EXTRA_TEXT, "Name: " + manager.getString("name")
                            + "\n"+"Message: " + message.getText().toString());//nội dung cần gởi
                    try {
                        startActivity(Intent.createChooser(intent, ""));//bắt đầu thục hiện lệnh
                    } catch (android.content.ActivityNotFoundException e) {
                        error.setText("There are no Email Clients");
                    }
                }
            }
        });
    }

    private void getTool() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Feedback");
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
    private void anhXa() {
        error = findViewById(R.id.erorr);
        send = findViewById(R.id.xacNhan);
        message = findViewById(R.id.pass);
        toolbar = findViewById(R.id.tool);
        manager = new ReferenceManager(FeedbackActivity.this);
    }
}