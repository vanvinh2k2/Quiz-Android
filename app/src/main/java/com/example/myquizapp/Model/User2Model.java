package com.example.myquizapp.Model;

import java.util.List;

public class User2Model {
    private boolean success;
    private List<User> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<User> getResult() {
        return result;
    }

    public void setResult(List<User> result) {
        this.result = result;
    }
}
