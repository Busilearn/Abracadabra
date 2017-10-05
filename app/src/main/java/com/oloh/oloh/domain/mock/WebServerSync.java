package com.oloh.oloh.domain.mock;

import android.util.Log;

import com.oloh.oloh.domain.api.ApiClient;
import com.oloh.oloh.domain.api.ApiInterface;
import com.oloh.oloh.model.CenterRepository;
import com.oloh.oloh.model.entities.Category;
import com.oloh.oloh.model.entities.Product;
import com.oloh.oloh.view.adapter.CategoryListAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.realm.Realm;
import io.realm.RealmResults;
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
        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        //Load category
        ArrayList<Category> listRawCategory = new ArrayList<Category>();
        ArrayList<Category> listFinalCategory = new ArrayList<Category>();
        ArrayList<Category> listFinalSubCategory = new ArrayList<Category>();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        // Make the request
        Call<ArrayList<Category>> callCat = apiInterface.getCategories();

        try {
            //Load Category
            RealmResults<Category> catRealm = realm.where(Category.class).findAll();
            realm.beginTransaction();
                catRealm.deleteAllFromRealm();
            realm.commitTransaction();

            listRawCategory = callCat.execute().body();
                for (Category productCategory : listRawCategory) {

                    //Enregistrement des cat en base
                    realm.beginTransaction();
                    Category listRealmFinalCategory = realm.copyToRealmOrUpdate(productCategory);
                    realm.commitTransaction();

                   if (productCategory.getParent() == 0){
                        listFinalCategory.add(productCategory);
                    }
                }

            //Ajout dans la classe tampon des categories
            CenterRepository.getCenterRepository().setListOfCategory(listFinalCategory);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
    }

    public void getAllProducts(int categoryPosition) {
        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        // Load Product
        RealmResults<Category> resultCat = realm.where(Category.class).findAll();
        ArrayList<Product> listFinalProduct = new ArrayList<Product>();

        productMap = new ConcurrentHashMap<String, ArrayList<Product>>();
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ArrayList<Product>> callProduct = apiInterface.getProduct();
        RealmResults<Category> subCat = null;

        try {
            ArrayList<Product> productlist = callProduct.execute().body();
            if (productlist != null) {
                realm.beginTransaction();
                    List<Product> productRealm = realm.copyToRealmOrUpdate(productlist);
                realm.commitTransaction();

                for (Product product : productlist) {
                    if (product.getCategories() != null){
                        //if (product.getCategories().get(0).getId() == 0){

                            subCat = realm.where(Category.class).equalTo("id", product.getCategories().get(0).getId()).findAll();

                            if ((subCat.get(0).getParent() == resultCat.get(categoryPosition).getId())) {
                                listFinalProduct.add(product);
                            }
                        //}
                    }
                }

                Log.e("test",listFinalProduct.toString());
                Log.e("test",listFinalProduct.get(0).getCategories().get(0).getName());

                productMap.put(listFinalProduct.get(0).getCategories().get(0).getName(), listFinalProduct);
                CenterRepository.getCenterRepository().setMapOfProductsInCategory(productMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            realm.close();
        }
    }
}