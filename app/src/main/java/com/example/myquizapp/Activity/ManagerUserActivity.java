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

import com.example.myquizapp.Adapter.AdapterUser;
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

public class ManagerUserActivity extends AppCompatActivity {
    Toolbar tool;
    SearchView search;
    RecyclerView listUser;
    AdapterUser adapter;
    List<User> userList;
    APIQuiz apiQuiz;
    CompositeDisposable disposable = new CompositeDisposable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_user);
        anhXa();
        getTool();
        display();
    }

    public void delete(String u_id){
        AlertDialog.Builder aldlog = new AlertDialog.Builder(this);
        aldlog.setIcon(R.drawable.ic_notifications);
        aldlog.setTitle("Notification!");
        aldlog.setMessage("Do you want to delete this user?");
        aldlog.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                disposable.add(apiQuiz.delete_user(u_id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                receiverModel -> {
                                    if(receiverModel.isSuccess()){
                                        display();
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
            getSupportActionBar().setTitle("Manager user list");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(ManagerUserActivity.this,MainActivity.class));
        }
        else if(item.getItemId() == R.id.add){
            startActivity(new Intent(ManagerUserActivity.this,AddUserActivity.class));
        }
        else if(item.getItemId() == R.id.search){
            search.setVisibility(View.VISIBLE);
        }
        return super.onOptionsItemSelected(item);
    }

    private void display() {
        userList = new ArrayList<>();

        disposable.add(apiQuiz.get_user()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user2Model -> {
                            if(user2Model.isSuccess()){
                                for(int i=0;i<user2Model.getResult().size();i++){
                                    userList.add(new User(user2Model.getResult().get(i).getU_id(),
                                            user2Model.getResult().get(i).getName(),
                                            user2Model.getResult().get(i).getEmail(),
                                            user2Model.getResult().get(i).getPass(),
                                            user2Model.getResult().get(i).getAddress()));
                                }
                                adapter = new AdapterUser(ManagerUserActivity.this,userList);
                                listUser.setAdapter(adapter);
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
        listUser = findViewById(R.id.list_user);
        apiQuiz = RetrofitClient.getInstance(Constant.BASE_URL).create(APIQuiz.class);
    }

    @Override
    protected void onDestroy() {
        disposable.clear();
        super.onDestroy();
    }
}