package com.example.myquizapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquizapp.R;
import com.example.myquizapp.Retrofit.APIQuiz;
import com.example.myquizapp.Retrofit.RetrofitClient;
import com.example.myquizapp.Utils.Constant;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AddUserActivity extends AppCompatActivity {
    Toolbar tool;
    EditText name,email,address,pass;
    TextView error;
    Button confirm;
    APIQuiz apiQuiz;
    CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        anhXa();
        getTool();
        add();
    }
    private void add() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isKiemTra()) {
                    String name1 = name.getText().toString().trim();
                    String email1 = email.getText().toString().trim();
                    String pass1 = pass.getText().toString().trim();
                    String address1 = address.getText().toString().trim();
                    disposable.add(apiQuiz.push_user(name1,email1,pass1,address1,1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    receiverModel -> {
                                        if(receiverModel.isSuccess()){
                                            Toast.makeText(AddUserActivity.this, receiverModel.getResult()+"", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(getApplicationContext(),ManagerUserActivity.class));
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(AddUserActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }
    private void anhXa() {
        tool = findViewById(R.id.tool);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        address = findViewById(R.id.address);
        pass = findViewById(R.id.pass);
        error = findViewById(R.id.error);
        confirm = findViewById(R.id.xacNhan);
        apiQuiz = RetrofitClient.getInstance(Constant.BASE_URL).create(APIQuiz.class);
    }

    private void getTool() {
        setSupportActionBar(tool);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Add user");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(getApplicationContext(),ManagerUserActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    boolean isKiemTra(){
        if(name.getText().toString().trim().isEmpty()){
            showToast("Please re-type the name");
            return false;
        }else if(email.getText().toString().trim().isEmpty()){
            showToast("Please re-type the email");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()) {
            showToast("Please re-type the email");
            return false;
        }else if(pass.getText().toString().trim().isEmpty()){
            showToast("Please re-type the password");
            return false;
        }else if(address.getText().toString().trim().isEmpty()){
            showToast("Please re-type the address");
            return false;
        }else return true;
    }

    void showToast(String value){
        error.setText(value);
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}