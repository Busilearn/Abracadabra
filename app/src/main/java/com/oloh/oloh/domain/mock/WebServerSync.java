package com.oloh.oloh.domain.mock;


import android.util.Log;


import com.oloh.oloh.domain.api.ApiClient;
import com.oloh.oloh.domain.api.ApiInterface;

import com.oloh.oloh.model.CenterRepository;
import com.oloh.oloh.model.entities.Category;
import com.oloh.oloh.model.entities.Images;
import com.oloh.oloh.model.entities.Products;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;


/**
 * Created by micka on 05/09/2017.
 */

public class WebServerSync {

    private static WebServerSync webServerSync;

    public static WebServerSync getWebServerSync() {
        if (null == webServerSync) {
            webServerSync = new WebServerSync();
        }
        return webServerSync;
    }

    public void addCategory() {

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        //Load category
        ArrayList<Category> listRawCategory = new ArrayList<Category>();
        ArrayList<Category> listFinalCategory = new ArrayList<Category>();
        List<Products> productsRealm = null;

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        // Make the request

        try {

            RealmResults<Images> images = realm.where(Images.class).findAll();
            realm.beginTransaction();
            images.deleteAllFromRealm();
            realm.commitTransaction();

                //Load Product in bdd
                Call<ArrayList<Products>> callProduct = apiInterface.getProduct();
                ArrayList<Products> productlist = callProduct.execute().body();


                if (productlist != null && productlist.size() != 0) {
                    realm.beginTransaction();
                    productsRealm = realm.copyToRealmOrUpdate(productlist);
                    realm.commitTransaction();
                }

                //Load Category
                Call<ArrayList<Category>> callCat = apiInterface.getCategories();
                listRawCategory = callCat.execute().body();

                if (listRawCategory != null && listRawCategory.size() != 0) {
                    //Register cat in bdd
                    realm.beginTransaction();
                    realm.copyToRealmOrUpdate(listRawCategory);
                    realm.commitTransaction();

                    //Check if it's a main category to display
                    for (Category productCategory : listRawCategory) {
                        if (productCategory.getParent() == 0) {
                            listFinalCategory.add(productCategory);
                        }
                    }
                }

                //Link category and product
                if (productsRealm != null) {
                    for (Products products : productsRealm) {
                        if (products.getCategories().size() != 0 && products.getCategories().get(0).getId() != 0) {

                            Category subCat = realm.where(Category.class)
                                    .equalTo("id", products.getCategories().get(0).getId())
                                    .findFirst();

                            if (subCat != null && subCat.getParent() != 0) {

                                realm.beginTransaction();
                                subCat.setParentCategories(realm.where(Category.class).equalTo("id", products.getCategories().get(0).getParent()).findFirst());
                                realm.commitTransaction();
                            }
                        }
                    }
                }

                //Display the category
                CenterRepository.getCenterRepository().setListOfCategory(listFinalCategory);

            } catch(IOException e){
                e.printStackTrace();
            } finally{
                realm.close();
            }
        }

    public void getAllProducts(int categoryPosition) {

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();
        ConcurrentHashMap<String, ArrayList<Products>> productMap = new ConcurrentHashMap<String, ArrayList<Products>>();

        try {

            List<Category> categoryParentRealm = realm.where(Category.class)
                    .equalTo("parent", 0)
                    .findAll();

            List<Category> categorySubRealm = realm.where(Category.class)
                    .equalTo("parent", categoryParentRealm.get(categoryPosition).getId())
                    .findAll();

            for (Category category : categorySubRealm){
                ArrayList<Products> listFinalProducts = new ArrayList<Products>();

                List<Products> productRealm = realm.where(Products.class)
                        .equalTo("categories.id", category.getId())
                        .findAll();

                if (productRealm.size() !=0) {
                    for (Products products : productRealm) {
                        listFinalProducts.add(realm.copyFromRealm(products));
                        Log.e("test","prod lié " + listFinalProducts.size());

                    }
                }
                productMap.put(category.getProductCategoryName(), listFinalProducts);
            }
            CenterRepository.getCenterRepository().setMapOfProductsInCategory(productMap);

        } finally {
            realm.close();
        }

    }
}