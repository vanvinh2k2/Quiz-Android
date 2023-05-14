package com.example.myquizapp.Model;

import java.util.List;

public class ResultModel {
    private boolean success;
    private List<Result> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}
