package com.example.myquizapp.Model;

import java.util.List;

public class CategoryModel {
    private boolean success;
    List<Category> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Category> getResult() {
        return result;
    }

    public void setResult(List<Category> result) {
        this.result = result;
    }
}
