package com.example.myquizapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myquizapp.Model.DapAn;
import com.example.myquizapp.Model.Question;
import com.example.myquizapp.R;
import com.example.myquizapp.Retrofit.APIQuiz;
import com.example.myquizapp.Retrofit.RetrofitClient;
import com.example.myquizapp.Utils.Constant;
import com.example.myquizapp.Utils.ReferenceManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TakeQuizActivity extends AppCompatActivity {
    private String category;
    Toolbar toolbar;
    TextView title,cauHoi,time;
    Button button;
    RadioButton cauA,cauB,cauC,cauD,r;
    ReferenceManager manager;
    ProgressBar timepg;
    int timeQuestion = 20000;
    CountDownTimer count;
    int i=0;
    APIQuiz apiQuiz;
    CompositeDisposable disposable = new CompositeDisposable();
    boolean kt = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_quiz);
        anhXa();
        getTool();
        getData();
        //Toast.makeText(this, checkInfoUser()+" TCR", Toast.LENGTH_SHORT).show();
    }

    private void getMain() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count.cancel();//dừng cây progress time lại
                if(i<Constant.listCauHoi.size()-1){//nếu vẫn chưa tới câu hỏi cuối cùng
                    if(kt==false){//nếu user chưa bật submit trước đó mà chừ mới bật
                        button.setText("Next");
                        //lưu lại đáp án câu thứ i vào mảng dapAn
                        Constant.dapAn.get(i).setDapAn(checkRadio());
                        //so sánh đáp án đã chọn với đáp án chuẩn
                        if(checkRadio().compareTo(Constant.listCauHoi.get(i).getAnswer())==0){
                            dapAnDung(checkRadio());
                        }else{
                            dapAnDung(Constant.listCauHoi.get(i).getAnswer());
                            dapAnSai(checkRadio());
                        }
                        kt = true;//đã submit
                    }else{//nếu đã submit trước đó rồi
                        clearDapAn();//lấy lại màu mặc định cho các đáp án
                        i++;// tăng câu hỏi lên 1

                        //r được tạo ra để cho các ô còn lại hiển thị chưa chọn
                        r.setChecked(true);//để cho ô này được chọn
                        getQuestion(i);//hiển thị lên câu hỏi tiếp theo
                        timeProgress();//đếm lại thời gian
                        button.setText("Submit");
                        kt = false;
                    }
                }
                else {
                    if(kt==false) {
                        Constant.dapAn.get(i).setDapAn(checkRadio());
                        if(checkRadio().compareTo(Constant.listCauHoi.get(i).getAnswer())==0){
                            dapAnDung(checkRadio());
                        }else{
                            dapAnDung(Constant.listCauHoi.get(i).getAnswer());
                            dapAnSai(checkRadio());
                        }
                        button.setText("Finish");
                        kt=true;
                    }else {
                        manager.putString("result", result() + "");
                        Date date = new Date();
                        long lasttime = date.getTime();
                        long time = lasttime - Constant.firsttime;
                        manager.putString("time",time+"");
                        disposable.add(apiQuiz.check_infor_user(manager.getString("u_id"),category)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        scoreModel -> {
                                            if(scoreModel.isSuccess()){
                                                disposable.add(apiQuiz.update_result(manager.getString("u_id"),category,result(),time,Constant.listCauHoi.size())
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(
                                                                receiverModel -> {
                                                                    if(receiverModel.isSuccess()){
                                                                        Toast.makeText(TakeQuizActivity.this, receiverModel.getResult()+"", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                },
                                                                throwable -> {
                                                                    Toast.makeText(TakeQuizActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                                                                }
                                                        ));
                                            }
                                            else{
                                                disposable.add(apiQuiz.push_result(manager.getString("u_id"),category,result(),time,Constant.listCauHoi.size())
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())
                                                        .subscribe(
                                                                receiverModel -> {
                                                                    if(receiverModel.isSuccess()){
                                                                        Toast.makeText(TakeQuizActivity.this, receiverModel.getResult()+"", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                },
                                                                throwable -> {
                                                                    Toast.makeText(TakeQuizActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                                                                }
                                                        ));
                                            }
                                        },
                                        throwable -> {
                                        }
                                ));

                        Intent intent = new Intent(TakeQuizActivity.this, ResultActivity.class);
                        intent.putExtra("data",category);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    private int result(){
        int kq = 0;
        for(int i=0;i<Constant.listCauHoi.size();i++)
            if (Constant.listCauHoi.get(i).getAnswer().compareTo(Constant.dapAn.get(i).getDapAn()) == 0) kq++;
        return kq;
    }

    private void getTool() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Take quiz");
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

    private void getData() {
        Intent intent = getIntent();
        Constant.listCauHoi = new ArrayList<>();
        Constant.dapAn = new ArrayList<>();
        category = intent.getStringExtra("data");
        disposable.add(apiQuiz.get_question_detail(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        questionDetailModel -> {
                            if(questionDetailModel.isSuccess()){
                                Constant.listCauHoi = questionDetailModel.getResult();
                                manager.putString("size",Constant.listCauHoi.size()+"");
                                getDapAn();
                                getQuestion(i);
                                timeProgress();
                                getMain();
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                ));

    }

    private void getDapAn() {
        for(int i=0;i<Constant.listCauHoi.size();i++){
            Constant.dapAn.add(new DapAn(0,""));
        }
    }

    private void getQuestion(int i) {
        title.setText("Question: "+""+(i+1)+"/"+Constant.listCauHoi.size());
        cauHoi.setText(Constant.listCauHoi.get(i).getName_question());
        cauA.setText("A. "+Constant.listCauHoi.get(i).getSentence_a());
        cauB.setText("B. "+Constant.listCauHoi.get(i).getSentence_b());
        cauC.setText("C. "+Constant.listCauHoi.get(i).getSentence_c());
        cauD.setText("D. "+Constant.listCauHoi.get(i).getSentence_d());
    }

    private String checkRadio() {
        if(cauA.isChecked()) return "A";
        if(cauB.isChecked()) return "B";
        if(cauC.isChecked()) return "C";
        if(cauD.isChecked()) return "D";
        return "";
    }

    private void dapAnSai(String s){
        Drawable drawable = getDrawable(R.drawable.background_radio3);
        if(s.compareTo("A")==0) cauA.setBackground(drawable);
        if(s.compareTo("B")==0) cauB.setBackground(drawable);
        if(s.compareTo("C")==0) cauC.setBackground(drawable);
        if(s.compareTo("D")==0) cauD.setBackground(drawable);
    }

    private void anhXa() {
        toolbar = findViewById(R.id.tool);
        title = findViewById(R.id.title);
        cauHoi = findViewById(R.id.cauhoi);
        cauA = findViewById(R.id.a);
        cauB = findViewById(R.id.b);
        cauC = findViewById(R.id.c);
        cauD = findViewById(R.id.d);
        button = findViewById(R.id.back);
        time = findViewById(R.id.time);
        timepg = findViewById(R.id.progressBar);
        r = findViewById(R.id.e);
        manager = new ReferenceManager(getApplicationContext());
        apiQuiz = RetrofitClient.getInstance(Constant.BASE_URL).create(APIQuiz.class);
    }

    private void timeProgress(){
        timepg.setMax(timeQuestion);
        timepg.setProgress(timeQuestion);
        count=new CountDownTimer(timeQuestion,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int t=timepg.getProgress();
                updateTime(t);
                timepg.setProgress(t-1000);
                if(t == 1000){
                    kt = true;
                    if(i==Constant.listCauHoi.size()-1) button.setText("Finnish");
                    else button.setText("Next");
                    Toast.makeText(TakeQuizActivity.this, "Run out of time!", Toast.LENGTH_SHORT).show();
                    dapAnDung(Constant.listCauHoi.get(i).getAnswer());//gán kết quả user chọn cho câu đó là ""
                    this.onFinish();
                }
            }
            @Override
            public void onFinish() {
            }
        };
        count.start();
    }

    private void updateTime(long t){
        int second = (int) ((t/1000)%60-1); //lấy thời gian dưới dạng giây
        String timeFormat = String.format(Locale.getDefault(),"%02d",second);//format time dạng giây
        time.setText(timeFormat);
        if(t<=6000) {// if time nhỏ hơn 6000 thì chữ sẽ màu đỏ cũng như cây progress thời gian
            time.setTextColor(Color.RED);
            Drawable drawable = getDrawable(R.drawable.custom_progess2);
            timepg.setProgressDrawable(drawable);
        }
        else { //màu mặc định
            time.setTextColor(Color.BLACK);
            Drawable drawable = getDrawable(R.drawable.custom_progess);
            timepg.setProgressDrawable(drawable);
        }
    }

    private void dapAnDung(String s){
        Drawable drawable = getDrawable(R.drawable.background_radio2);
        if(s.compareTo("A")==0) cauA.setBackground(drawable);
        if(s.compareTo("B")==0) cauB.setBackground(drawable);
        if(s.compareTo("C")==0) cauC.setBackground(drawable);
        if(s.compareTo("D")==0) cauD.setBackground(drawable);
    }

    //lấy lại mặc định màu
    private void clearDapAn(){
        Drawable drawable = getDrawable(R.drawable.background_radio);
        cauA.setBackground(drawable);
        cauB.setBackground(drawable);
        cauC.setBackground(drawable);
        cauD.setBackground(drawable);
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}