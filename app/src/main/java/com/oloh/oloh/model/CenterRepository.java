package com.oloh.oloh.model;

import com.oloh.oloh.domain.api.ApiInterface;
import com.oloh.oloh.model.entities.Category;
import com.oloh.oloh.model.entities.Products;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by stran on 24/08/2017.
 */

public class CenterRepository {

    private static CenterRepository centerRepository;
    private ApiInterface apiInterface;

    private ArrayList<Category> listOfCategory = new ArrayList<Category>();
    private ArrayList<Category> listOfSubCategory = new ArrayList<Category>();
    private ConcurrentHashMap<String, ArrayList<Products>> mapOfProductsInCategory = new ConcurrentHashMap<String, ArrayList<Products>>();
    private List<Products> listOfProductsInShoppingList = Collections.synchronizedList(new ArrayList<Products>());
    private List<Set<String>> listOfItemSetsForDataMining = new ArrayList<>();

    public static CenterRepository getCenterRepository() {

        if (null == centerRepository) {
            centerRepository = new CenterRepository();
        }
        return centerRepository;
    }


    public List<Products> getListOfProductsInShoppingList() {
        return listOfProductsInShoppingList;
    }

    public void setListOfProductsInShoppingList(ArrayList<Products> getShoppingList) {
        this.listOfProductsInShoppingList = getShoppingList;
    }

    public Map<String, ArrayList<Products>> getMapOfProductsInCategory() {

        return mapOfProductsInCategory;
    }

        public void setMapOfProductsInCategory(ConcurrentHashMap<String, ArrayList<Products>> mapOfProductsInCategory) {

        this.mapOfProductsInCategory = mapOfProductsInCategory;
    }

    public ArrayList<Category> getListOfCategory() {

        return listOfCategory;
    }

    public void setListOfCategory(ArrayList<Category> listOfCategory) {
        this.listOfCategory = listOfCategory;
    }

    public ArrayList<Category> getListOfSubCategory() {
        return listOfSubCategory;
    }

    public void setListOfSubCategory(ArrayList<Category> listOfSubCategory) {
        this.listOfSubCategory = listOfSubCategory;
    }
}