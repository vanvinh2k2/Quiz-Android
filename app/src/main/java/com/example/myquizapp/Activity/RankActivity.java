package com.example.myquizapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.myquizapp.Adapter.AdapterRank;
import com.example.myquizapp.Model.Rank;
import com.example.myquizapp.R;
import com.example.myquizapp.Retrofit.APIQuiz;
import com.example.myquizapp.Retrofit.RetrofitClient;
import com.example.myquizapp.Utils.Constant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RankActivity extends AppCompatActivity {
    SearchView search;
    RecyclerView listRank;
    Toolbar toolbar;
    List<Rank> rankList;
    AdapterRank adapter;
    String category;
    APIQuiz apiQuiz;
    CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        anhXa();
        getTool();
        getData();
        rank();
    }

    private void getData() {
        Intent intent = getIntent();
        category = intent.getStringExtra("data");
    }

    private void rank() {
        rankList = new ArrayList<>();
        adapter = new AdapterRank(this,rankList);
        listRank.setAdapter(adapter);
        disposable.add(apiQuiz.get_rank(category)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        rankModel -> {
                            if(rankModel.isSuccess()){
                                rankList = rankModel.getResult();
                                Collections.sort(rankList, new Comparator<Rank>() {
                                    @Override
                                    public int compare(Rank o1, Rank o2) {
                                        return o1.getScore().compareTo(o2.getScore());
                                    }
                                });
                                adapter = new AdapterRank(this,rankList);
                                listRank.setAdapter(adapter);
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
        search = findViewById(R.id.search);
        search.setVisibility(View.GONE);
        toolbar = findViewById(R.id.tool1);
        listRank = findViewById(R.id.list_rank);
        apiQuiz = RetrofitClient.getInstance(Constant.BASE_URL).create(APIQuiz.class);
    }

    private void getTool() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle("Rank");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(getApplicationContext(),CategoryActivity.class));
        }
        if(item.getItemId() == R.id.search){
            search.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }
}