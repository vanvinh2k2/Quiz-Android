package com.example.myquizapp.Model;

import java.util.List;

public class LoginModel {
    private boolean success;
    private List<Login> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Login> getResult() {
        return result;
    }

    public void setResult(List<Login> result) {
        this.result = result;
    }
}
