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
import android.widget.Toast;

import com.example.myquizapp.Model.Question;
import com.example.myquizapp.R;
import com.example.myquizapp.Retrofit.APIQuiz;
import com.example.myquizapp.Retrofit.RetrofitClient;
import com.example.myquizapp.Utils.Constant;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateQuestionActivity extends AppCompatActivity {
    EditText questiontxt, sentenceA, sentenceB, sentenceC, sentenceD, answer;
    Button confirm;
    TextView error;
    Toolbar toolbar;
    CompositeDisposable disposable = new CompositeDisposable();
    APIQuiz apiQuiz;
    private String q_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_question);
        anhXa();
        getTool();
        getData();
        update();
    }

    private void getTool() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Update question");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(getApplicationContext(),ManagerQuestionActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        Intent intent = getIntent();
        Question question = (Question) intent.getSerializableExtra("data");
        q_id = question.getQ_id();
        questiontxt.setText(question.getName_question());
        sentenceA.setText(question.getSentence_a());
        sentenceB.setText(question.getSentence_b());
        sentenceC.setText(question.getSentence_c());
        sentenceD.setText(question.getSentence_d());
        answer.setText(question.getAnswer());
    }

    private void update() {
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isKiemTra()) {
                    String question1 = questiontxt.getText().toString().trim();
                    String sena1 = sentenceA.getText().toString().trim();
                    String senb1 = sentenceB.getText().toString().trim();
                    String senc1 = sentenceC.getText().toString().trim();
                    String send1 = sentenceD.getText().toString().trim();
                    String answer1 = answer.getText().toString().trim();
                    disposable.add(apiQuiz.update_question(q_id,question1,sena1,senb1,senc1,send1,answer1)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    receiverModel -> {
                                        if(receiverModel.isSuccess()){
                                            Toast.makeText(UpdateQuestionActivity.this, receiverModel.getResult()+"", Toast.LENGTH_LONG).show();
                                            Toast.makeText(UpdateQuestionActivity.this, receiverModel.getResult()+"", Toast.LENGTH_LONG).show();
                                            Toast.makeText(UpdateQuestionActivity.this, receiverModel.getResult()+"", Toast.LENGTH_LONG).show();
                                            Toast.makeText(UpdateQuestionActivity.this, receiverModel.getResult()+"", Toast.LENGTH_LONG).show();
                                            //startActivity(new Intent(getApplicationContext(),ManagerQuestionActivity.class));
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(UpdateQuestionActivity.this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void anhXa() {
        questiontxt = findViewById(R.id.question);
        sentenceA = findViewById(R.id.sena);
        sentenceB = findViewById(R.id.senb);
        sentenceC = findViewById(R.id.senc);
        sentenceD = findViewById(R.id.send);
        answer = findViewById(R.id.answer);
        error = findViewById(R.id.error);
        toolbar = findViewById(R.id.tool);
        confirm = findViewById(R.id.xacNhan);
        apiQuiz = RetrofitClient.getInstance(Constant.BASE_URL).create(APIQuiz.class);
    }

    boolean isKiemTra(){
        if(questiontxt.getText().toString().trim().isEmpty()){
            showToast("Please re-type the question");
            return false;
        }else if(questiontxt.length()<=5){
            showToast("Please re-type the question");
            return false;
        }else if(questiontxt.length()>=151){
            showToast("Please re-type the question");
            return false;
        }else if(sentenceA.getText().toString().trim().isEmpty()){
            showToast("Please re-type the choice sentences");
            return false;
        }else if(sentenceA.length()>=151){
            showToast("Please re-type the choice sentences");
            return false;
        }else if(sentenceB.getText().toString().trim().isEmpty()) {
            showToast("Please re-type the choice sentences");
            return false;
        }else if(sentenceB.length()>=151){
            showToast("Please re-type the choice sentences");
            return false;
        }else if(sentenceC.getText().toString().trim().isEmpty()){
            showToast("Please re-type the choice sentences");
            return false;
        }else if(sentenceC.length()>=151){
            showToast("Please re-type the choice sentences");
            return false;
        }else if(sentenceD.getText().toString().trim().isEmpty()){
            showToast("Please re-type the choice sentences");
            return false;
        }else if(sentenceD.length()>=151){
            showToast("Please re-type the choice sentences");
            return false;
        }else if(answer.getText().toString().trim().isEmpty()){
            showToast("Please re-type the answer");
            return false;
        }else if(answer.length()!=1){
            showToast("Please re-type the answer");
            return false;
        }else if(answer.getText().toString().compareTo("A")==0 || answer.getText().toString().compareTo("B")==0|| answer.getText().toString().compareTo("C")==0 || answer.getText().toString().compareTo("D")==0){
            return true;
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