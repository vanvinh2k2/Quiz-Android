package com.example.myquizapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myquizapp.Activity.CategoryActivity;
import com.example.myquizapp.Activity.ResultActivity;
import com.example.myquizapp.Activity.TakeQuizActivity;
import com.example.myquizapp.Listener.Listener_question;
import com.example.myquizapp.Model.Category;
import com.example.myquizapp.R;
import com.example.myquizapp.Retrofit.APIQuiz;
import com.example.myquizapp.Retrofit.RetrofitClient;
import com.example.myquizapp.Utils.Constant;
import com.example.myquizapp.Utils.ReferenceManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AdapterCategory extends RecyclerView.Adapter<AdapterCategory.HoderCategory> {
    CategoryActivity context;
    List<Category> list;

    public AdapterCategory(CategoryActivity context, List<Category> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HoderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category,parent,false);
        return new HoderCategory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoderCategory holder, int position) {
        holder.text.setText(list.get(position).getDescription());
        holder.setListener_question(new Listener_question() {
            @Override
            public void setOnItemClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick){
                    Date date = new Date();
                    Constant.firsttime = date.getTime();
                    context.disposable.add(context.apiQuiz.check_infor_user(context.manager.getString("u_id"),list.get(pos).getC_id())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    scoreModel -> {
                                        if(scoreModel.isSuccess()){
                                            context.disposable.add(context.apiQuiz.get_result(context.manager.getString("u_id"),list.get(pos).getC_id())
                                                    .subscribeOn(Schedulers.io())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribe(
                                                            resultModel -> {
                                                                if(resultModel.isSuccess()){
                                                                    context.manager.putString("size",resultModel.getResult().get(0).getSize()+"");
                                                                    context.manager.putString("time",resultModel.getResult().get(0).getTime()+"");
                                                                    context.manager.putString("result",resultModel.getResult().get(0).getScore()+"");
                                                                    Intent intent = new Intent(context, ResultActivity.class);
                                                                    intent.putExtra("data",list.get(pos).getC_id());
                                                                    context.startActivity(intent);
                                                                }
                                                            },
                                                            throwable -> {
                                                                Toast.makeText(context, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                                                            }
                                                    ));
                                        }else{
                                            Intent intent = new Intent(context, TakeQuizActivity.class);
                                            intent.putExtra("data",list.get(pos).getC_id());
                                            context.startActivity(intent);
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(context, throwable.getMessage()+"", Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class HoderCategory extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView text;
        Listener_question listener_question;
       public HoderCategory(@NonNull View itemView) {
           super(itemView);
           text = itemView.findViewById(R.id.text);
           itemView.setOnClickListener(this);
       }

        @Override
        public void onClick(View v) {
            listener_question.setOnItemClick(v,getAdapterPosition(),false);
        }

        public void setListener_question(Listener_question listener_question) {
            this.listener_question = listener_question;
        }
    }
}
