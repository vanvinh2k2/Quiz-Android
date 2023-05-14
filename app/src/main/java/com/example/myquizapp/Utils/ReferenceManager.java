package com.example.myquizapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ReferenceManager {
    final private SharedPreferences preferences;

    public ReferenceManager(Context context) {
        this.preferences = context.getSharedPreferences("save",Context.MODE_PRIVATE);
    }

    public String getString(String key){
        return preferences.getString(key,null);
    }

    public void putString(String key, String value){
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString(key,value);
        edit.apply();
    }

    public void clear(){
        SharedPreferences.Editor edit = preferences.edit();
        edit.clear();
        edit.apply();
    }
}
