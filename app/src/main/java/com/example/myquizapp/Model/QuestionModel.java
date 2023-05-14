package com.example.myquizapp.Model;

import java.util.List;

public class QuestionModel {
    private boolean success;
    private List<Question> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Question> getResult() {
        return result;
    }

    public void setResult(List<Question> result) {
        this.result = result;
    }
}
