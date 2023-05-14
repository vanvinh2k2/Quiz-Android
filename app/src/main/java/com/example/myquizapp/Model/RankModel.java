package com.example.myquizapp.Model;

import java.util.List;

public class RankModel {
    private boolean success;
    private List<Rank> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Rank> getResult() {
        return result;
    }

    public void setResult(List<Rank> result) {
        this.result = result;
    }
}
