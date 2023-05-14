package com.example.myquizapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myquizapp.Adapter.AdapterCategory;
import com.example.myquizapp.Model.Category;
import com.example.myquizapp.R;
import com.example.myquizapp.Retrofit.APIQuiz;
import com.example.myquizapp.Retrofit.RetrofitClient;
import com.example.myquizapp.Utils.Constant;
import com.example.myquizapp.Utils.ReferenceManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CategoryActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    AdapterCategory adapter;
    List<Category> list;
    public APIQuiz apiQuiz;
    public ReferenceManager manager;
    public CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        anhXa();
        getTool();
        getCategory();
    }

    private void getTool() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Question category");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCategory() {
        list = new ArrayList<>();
        disposable.add(apiQuiz.get_category()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        categoryModel -> {
                            if(categoryModel.isSuccess()){
                                list = categoryModel.getResult();
                                adapter = new AdapterCategory(CategoryActivity.this,list);
                                recyclerView.setAdapter(adapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(this, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                ));
    }

    private void anhXa() {
        toolbar = findViewById(R.id.tool);
        recyclerView = findViewById(R.id.recycler);
        manager = new ReferenceManager(this);
        apiQuiz = RetrofitClient.getInstance(Constant.BASE_URL).create(APIQuiz.class);
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}