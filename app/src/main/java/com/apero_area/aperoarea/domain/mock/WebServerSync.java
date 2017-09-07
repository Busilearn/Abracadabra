package com.apero_area.aperoarea.domain.mock;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.apero_area.aperoarea.R;
import com.apero_area.aperoarea.domain.api.ApiClient;
import com.apero_area.aperoarea.domain.api.CallbackT;
import com.apero_area.aperoarea.domain.helper.ApiInterface;
import com.apero_area.aperoarea.model.CenterRepository;
import com.apero_area.aperoarea.model.entities.Product;
import com.apero_area.aperoarea.model.entities.ProductCategoryModel;
import com.apero_area.aperoarea.util.AppConstants;
import com.apero_area.aperoarea.util.Utils;
import com.apero_area.aperoarea.view.activities.MainActivity;
import com.apero_area.aperoarea.view.adapter.CategoryListAdapter;
import com.apero_area.aperoarea.view.fragment.ProductOverviewFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                        "Pour les fêtard",
                        "Envie de s'amuser partager un moment de délire, trouve tout ce dont tu as envie ici",
                        "10%",
                        "http://www.filsantejeunes.com/wp-content/uploads/2012/06/alcool.jpg"));

        /*listOfCategory
                .add(new ProductCategoryModel(
                        "Furnitures",
                        "Furnitures Items",
                        "15%",
                        "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRaUR5_wzLgBOuNtkWjOxhgaYaPBm821Hb_71xTyQ-OdUd-ojMMvw"));*/

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

            Log.i("test", "Inside try" + productlist.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        CenterRepository.getCenterRepository().setMapOfProductsInCategory(productMap);

    }




    /*public void getAllFurnitures() {

        Log.i("test", "Dans getAllfourniture");

        ConcurrentHashMap<String, ArrayList<Product>> productMap = new ConcurrentHashMap<String, ArrayList<Product>>();

        ArrayList<Product> productlist = new ArrayList<Product>();

        // Table
        productlist
                .add(new Product(
                        " Wood Coffee Table",
                        "Royal Oak Engineered Wood Coffee Table",
                        "With a contemporary design and gorgeous finish, this coffee table will be a brilliant addition to modern homes and even offices. The table has a glass table top with a floral print, and a pull-out drawer in the center.",
                        "10200",
                        "12",
                        "7000",
                        "0",
                        "http://img6a.flixcart.com/image/coffee-table/q/f/4/ct15bl-mdf-royal-oak-dark-400x400-imaeehkd8xuheh2u.jpeg",
                        "table_1"));


        productMap.put("Tables", productlist);


        productlist = new ArrayList<Product>();

        // Chair
        productlist
                .add(new Product(
                        "l Collapsible Wardrobe",
                        "Everything Imported Carbon Steel Collapsible Wardrobe",
                        "Portable Wardrobe Has Hanging Space And Shelves Which Are Very Practical And The Roll Down Cover Keeps The Dust Out",
                        "2999",
                        "20",
                        "1999",
                        "0",
                        "http://img5a.flixcart.com/image/collapsible-wardrobe/h/h/g/best-quality-3-5-feet-foldable-storage-cabinet-almirah-shelf-400x400-imaees5fq7wbndak.jpeg",
                        "almirah_1"));

        productlist
                .add(new Product(
                        "l Collapsible Wardrobe",
                        "Everything Imported Carbon Steel Collapsible Wardrobe",
                        "Portable Wardrobe Has Hanging Space And Shelves Which Are Very Practical And The Roll Down Cover Keeps The Dust Out",
                        "2999",
                        "20",
                        "1999",
                        "0",
                        "http://img6a.flixcart.com/image/collapsible-wardrobe/d/n/s/cb265-carbon-steel-cbeeso-dark-beige-400x400-imaefn9vha2hm9qk.jpeg",
                        "almirah_2"));


        productMap.put("Almirah", productlist);

        productMap.put("Almirah", productlist);

        CenterRepository.getCenterRepository().setMapOfProductsInCategory(productMap);

    }*/



    public void getAllProducts(int productCategory) {

        if (productCategory == 0) {
            getWebProducts();


        } else {
            //getAllFurnitures();
        }
    }

}