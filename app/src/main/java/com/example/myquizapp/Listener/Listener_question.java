package com.example.myquizapp.Listener;

import android.view.View;

import com.example.myquizapp.Model.QuestionDetail;
import com.example.myquizapp.Model.User;

public interface Listener_question {
    void setOnItemClick(View view, int pos, boolean isLongClick);
}
