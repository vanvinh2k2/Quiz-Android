package com.example.myquizapp.Model;

import java.util.List;

public class QuestionDetailModel {
    private boolean success;
    private List<QuestionDetail> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<QuestionDetail> getResult() {
        return result;
    }

    public void setResult(List<QuestionDetail> result) {
        this.result = result;
    }
}
