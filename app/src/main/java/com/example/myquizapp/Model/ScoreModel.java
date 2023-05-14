package com.example.myquizapp.Model;

import java.util.List;

public class ScoreModel {
    private boolean success;
    private List<Integer> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Integer> getResult() {
        return result;
    }

    public void setResult(List<Integer> result) {
        this.result = result;
    }
}
