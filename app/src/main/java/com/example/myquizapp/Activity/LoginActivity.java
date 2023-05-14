package com.example.myquizapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquizapp.Model.Login;
import com.example.myquizapp.R;
import com.example.myquizapp.Retrofit.APIQuiz;
import com.example.myquizapp.Retrofit.RetrofitClient;
import com.example.myquizapp.Utils.Constant;
import com.example.myquizapp.Utils.ReferenceManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity {
    EditText email,pass;
    TextView dangKi,error;
    Button xacNhan;
    ReferenceManager manager;
    CompositeDisposable disposable = new CompositeDisposable();
    APIQuiz apiQuiz;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        login();
    }

    public void login(){
        if(manager.getString("email")!=null && manager.getString("pass")!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        dangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                startActivity(intent);
            }
        });

        xacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1 = email.getText().toString().trim();
                String pass1 = pass.getText().toString().trim();
                if(isKiemTra()){
                    disposable.add(apiQuiz.check_user(email1,pass1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    loginModel -> {
                                        if(loginModel.isSuccess()){
                                            Login login = loginModel.getResult().get(0);
                                            manager.putString("u_id",login.getU_id());
                                            manager.putString("email",login.getEmail());
                                            manager.putString("name",login.getName());
                                            manager.putString("loai",login.getLoai()+"");
                                            manager.putString("pass",login.getPass());
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(LoginActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }

            }
        });
    }

    private void anhXa() {
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        dangKi = findViewById(R.id.dangki);
        xacNhan = findViewById(R.id.xacNhan);
        error = findViewById(R.id.error);
        apiQuiz = RetrofitClient.getInstance(Constant.BASE_URL).create(APIQuiz.class);
        manager = new ReferenceManager(LoginActivity.this);
    }
    boolean isKiemTra(){
        if(email.getText().toString().trim().isEmpty() && pass.getText().toString().trim().isEmpty()){
            showToast("Please re-type the email");
            return false;
        }else if(email.getText().toString().trim().isEmpty()){
            showToast("Please re-type the email");
            return false;
        }else if(pass.getText().toString().trim().isEmpty()){
            showToast("Please re-type the password");
            return false;
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            showToast("Please re-type the password");
            return false;
        }else return true;
    }

    void showToast(String value) {
        error.setText(value);
    }

    private int checkInfoUser(){
        int diem = -1;
        //lấy thông tin uesr ra với iduser

        return diem;
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}