package com.oloh.oloh.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.oloh.oloh.model.entities.Products;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by dany on 24/08/2017.
 */

public class TinyDB {

    private SharedPreferences preferences;

    public TinyDB(Context appContext) {
        preferences = PreferenceManager.getDefaultSharedPreferences(appContext);
    }

    // Get the product in DB
    public ArrayList<String> getListString(String key) {
        return new ArrayList<String>(Arrays.asList(TextUtils.split(preferences.getString(key, ""), "‚‗‚")));
    }

    public ArrayList<Products> getListObject(String key, Class<?> mClass) {
        Gson gson = new Gson();

        ArrayList<String> objStrings = getListString(key);
        ArrayList<Products> objects = new ArrayList<Products>();

        for (String jObjString : objStrings) {
            Products value = (Products) gson.fromJson(jObjString, mClass);
            objects.add(value);
        }
        return objects;
    }

    // Put the product in DB
    public void putListString(String key, ArrayList<String> stringList) {
        checkForNullKey(key);
        String[] myStringList = stringList.toArray(new String[stringList.size()]);
        preferences.edit().putString(key, TextUtils.join("‚‗‚", myStringList)).apply();
    }

    public void putListObject(String key, List<Products> list) {
        checkForNullKey(key);
        Gson gson = new Gson();
        ArrayList<String> objStrings = new ArrayList<String>();
        for (Object obj : list) {
            objStrings.add(gson.toJson(obj));
        }
        putListString(key, objStrings);
    }

    // Retrieve all product in db
    public Map<String, ?> getAll() {
        return preferences.getAll();
    }

    // null keys would corrupt the shared pref file and make them unreadable
    public void checkForNullKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
    }

    // Put the location
    public void putLocation(String loc){
        preferences.edit().putString("Loc", loc).apply();
    }

    // Get the location
    public String getLocation(){
        return preferences.getString("Loc", "");
    }

}
