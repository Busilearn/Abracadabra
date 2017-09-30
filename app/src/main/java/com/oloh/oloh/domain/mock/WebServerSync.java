package com.oloh.oloh.domain.mock;

import android.util.Log;

import com.oloh.oloh.domain.api.ApiClient;
import com.oloh.oloh.domain.api.ApiInterface;
import com.oloh.oloh.model.CenterRepository;
import com.oloh.oloh.model.entities.Product;
import com.oloh.oloh.model.entities.ProductCategoryModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;

/**
 * Created by stran on 05/09/2017.
 */

public class WebServerSync {
    private static WebServerSync webServerSync;
    private ConcurrentHashMap<String, ArrayList<Product>> productMap;

    public static WebServerSync getWebServerSync() {
        if (null == webServerSync) {
            webServerSync = new WebServerSync();
        }
        return webServerSync;
    }

    void initiateWebServerSync() {
        addCategory();
    }

    public void addCategory() {

        //Load category
        ArrayList<ProductCategoryModel> listRawCategory = new ArrayList<ProductCategoryModel>();
        ArrayList<ProductCategoryModel> listFinalCategory = new ArrayList<ProductCategoryModel>();
        ArrayList<ProductCategoryModel> listSubCategory = new ArrayList<ProductCategoryModel>();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        // Make the request
        Call<ArrayList<ProductCategoryModel>> callCat = apiInterface.getCategories();

        try {
            listRawCategory = callCat.execute().body();
            if (listRawCategory != null) {
                for (ProductCategoryModel productCategory : listRawCategory) {
                    if (productCategory.getParent().equals("0")) {
                        listFinalCategory.add(productCategory);
                    }else {
                        listSubCategory.add(productCategory);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Ajout dans la classe tampon des categories
        CenterRepository.getCenterRepository().setListOfCategory(listFinalCategory);
        CenterRepository.getCenterRepository().setListOfSubCategory(listSubCategory);

    }

    public void getWebProducts(int categoryPosition){
        // Load Product
        productMap = new ConcurrentHashMap<String, ArrayList<Product>>();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<Product>> callProduct = apiInterface.getProduct();

        try {
            ArrayList<Product> productlist = callProduct.execute().body();
            if (productlist != null) {
                    productMap.put(CenterRepository.getCenterRepository().getListOfSubCategory().get(categoryPosition).getProductCategoryName(), productlist);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        CenterRepository.getCenterRepository().setMapOfProductsInCategory(productMap);
    }

    public void getAllProducts(int categoryPosition) {
        //CenterRepository.getCenterRepository().getListOfCategory().get(categoryPosition).getId();
        getWebProducts(categoryPosition);

    }

}