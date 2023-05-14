package com.example.myquizapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myquizapp.Adapter.AdapterQuestion;
import com.example.myquizapp.Adapter.AdapterUser;
import com.example.myquizapp.Model.Question;
import com.example.myquizapp.Model.User;
import com.example.myquizapp.R;
import com.example.myquizapp.Retrofit.APIQuiz;
import com.example.myquizapp.Retrofit.RetrofitClient;
import com.example.myquizapp.Utils.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ManagerQuestionActivity extends AppCompatActivity {
    Toolbar tool;
    SearchView search;
    RecyclerView listQuestion;
    AdapterQuestion adapter;
    List<Question> questionList;
    APIQuiz apiQuiz;
    CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_question);
        anhXa();
        getTool();
        display();
    }
    public void delete(String q_id){
        AlertDialog.Builder aldlog = new AlertDialog.Builder(this);
        aldlog.setIcon(R.drawable.ic_notifications);
        aldlog.setTitle("Notification!");
        aldlog.setMessage("Do you want to delete this question?");
        aldlog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                disposable.add(apiQuiz.delete_question(q_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                receiverModel -> {
                                    if(receiverModel.isSuccess()){
                                        display();
                                        Toast.makeText(ManagerQuestionActivity.this, receiverModel.getResult()+"", Toast.LENGTH_SHORT).show();
                                    }
                                },
                                throwable -> {
                                }
                        ));
            }
        });
        aldlog.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        aldlog.show();
    }

    private void getTool() {
        setSupportActionBar(tool);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Manager question list");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_question,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(ManagerQuestionActivity.this,MainActivity.class));
        }
        else if(item.getItemId() == R.id.add){
            startActivity(new Intent(ManagerQuestionActivity.this,AddQuestionActivity.class));
        }
        else if(item.getItemId() == R.id.search){
            search.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    private void display() {
        questionList = new ArrayList<>();
        disposable.add(apiQuiz.get_question()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        questionModel -> {
                            if(questionModel.isSuccess()){
                                questionList = questionModel.getResult();
                                adapter = new AdapterQuestion(ManagerQuestionActivity.this,questionList);
                                listQuestion.setAdapter(adapter);
                                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                    @Override
                                    public boolean onQueryTextSubmit(String query) {
                                        adapter.getFilter().filter(query);
                                        return false;
                                    }

                                    @Override
                                    public boolean onQueryTextChange(String newText) {
                                        adapter.getFilter().filter(newText);
                                        return false;
                                    }
                                });
                                search.setOnCloseListener(new SearchView.OnCloseListener() {
                                    @Override
                                    public boolean onClose() {
                                        search.setVisibility(View.GONE);
                                        return false;
                                    }
                                });
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void anhXa() {
        tool = findViewById(R.id.tool);
        search = findViewById(R.id.search);
        listQuestion = findViewById(R.id.list_question);
        apiQuiz = RetrofitClient.getInstance(Constant.BASE_URL).create(APIQuiz.class);
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}