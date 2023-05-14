package com.example.myquizapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquizapp.R;
import com.example.myquizapp.Retrofit.APIQuiz;
import com.example.myquizapp.Retrofit.RetrofitClient;
import com.example.myquizapp.Utils.Constant;
import com.example.myquizapp.Utils.ReferenceManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SignUpActivity extends AppCompatActivity {
    EditText email,pass,name,address;
    TextView dangNhap,error;
    APIQuiz apiQuiz;
    CompositeDisposable disposable = new CompositeDisposable();
    Button xacNhan;
    ReferenceManager manager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        anhXa();
        signUp();
    }

    public void signUp(){
        dangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });
        xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isKiemTra()){
                    String name1 = name.getText().toString().trim();
                    String email1 = email.getText().toString().trim();
                    String pass1 = pass.getText().toString().trim();
                    String address1 = address.getText().toString().trim();
                    disposable.add(apiQuiz.push_user(name1,pass1,email1,address1,1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if(userModel.isSuccess()){
                                            manager.putString("u_id",userModel.getData()+"");
                                            manager.putString("email",email1);
                                            manager.putString("name",name1);
                                            manager.putString("loai","1");
                                            manager.putString("pass",pass1);
                                            Toast.makeText(SignUpActivity.this, userModel.getResult()+"", Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(SignUpActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void anhXa() {
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        address = findViewById(R.id.address);
        name = findViewById(R.id.name);
        dangNhap = findViewById(R.id.dangNhap);
        xacNhan = findViewById(R.id.xacNhan);
        error = findViewById(R.id.error);
        apiQuiz = RetrofitClient.getInstance(Constant.BASE_URL).create(APIQuiz.class);
        manager = new ReferenceManager(SignUpActivity.this);
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