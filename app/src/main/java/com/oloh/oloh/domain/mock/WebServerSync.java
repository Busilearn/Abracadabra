package com.oloh.oloh.domain.mock;

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

        ArrayList<ProductCategoryModel> listOfCategory = new ArrayList<ProductCategoryModel>();

        listOfCategory
                .add(new ProductCategoryModel(
                        "Pour les fêtards",
                        "Envie de partager un moment arrosé, tu es au bon endroit",
                        "10%",
                        "https://apero-area.com/wp-content/uploads/2017/09/trinke-fetard.jpg"));

        CenterRepository.getCenterRepository().setListOfCategory(listOfCategory);
    }

    public void getWebProducts(){
        final ConcurrentHashMap<String, ArrayList<Product>> productMap = new ConcurrentHashMap<String, ArrayList<Product>>();

        // Build of the retrofit object
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        // Make the request
        Call<ArrayList<Product>> call = apiInterface.getProduct();

        try {
            ArrayList<Product> productlist = call.execute().body();
            if (productlist != null) {
                productMap.put("Alcool", productlist);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        CenterRepository.getCenterRepository().setMapOfProductsInCategory(productMap);
    }

    public void getAllProducts(int productCategory) {

        if (productCategory == 0) {
            getWebProducts();

        } else {
            // No other categorie for now
        }
    }

}