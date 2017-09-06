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

public class WebServerSync extends AsyncTask<String, Void, Void> {


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

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);


    }

    @Override
    protected Void doInBackground(String... params) {
        getRemoteProducts();

        return null;
    }

    public void addCategory() {

        ArrayList<ProductCategoryModel> listOfCategory = new ArrayList<ProductCategoryModel>();

        listOfCategory
                .add(new ProductCategoryModel(
                        "Electronic",
                        "Electric Items",
                        "10%",
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSeNSONF3fr9bZ6g0ztTAIPXPRCYN9vtKp1dXQB2UnBm8n5L34r"));

        listOfCategory
                .add(new ProductCategoryModel(
                        "Furnitures",
                        "Furnitures Items",
                        "15%",
                        "https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRaUR5_wzLgBOuNtkWjOxhgaYaPBm821Hb_71xTyQ-OdUd-ojMMvw"));

        CenterRepository.getCenterRepository().setListOfCategory(listOfCategory);
    }

    public void getRemoteProducts(){

        final ConcurrentHashMap<String, ArrayList<Product>> productMap = new ConcurrentHashMap<String, ArrayList<Product>>();


        // Build of the retrofit object
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        // Make the request
        Call<ArrayList<Product>> call = apiInterface.getProduct();

        try {
            ArrayList<Product> productlist = call.execute().body();
            productMap.put("Alcool", productlist);

            Log.i("test", "Inside try" + productlist.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        CenterRepository.getCenterRepository().setMapOfProductsInCategory(productMap);

    }


    public void getfakeProducts() {

        final ConcurrentHashMap<String, ArrayList<Product>> productMap = new ConcurrentHashMap<String, ArrayList<Product>>();
        final ArrayList<Product> productlist = new ArrayList<Product>();

            productlist
                    .add(new Product(
                            "Solo Microwave Oven",
                            "IFB 17PMMEC1 17 L Solo Microwave Oven",
                            "Explore the joys of cooking with IFB 17PM MEC1 Solo Microwave Oven. The budget-friendly appliance has several nifty features including Multi Power Levels and Speed Defrost to make cooking a fun-filled experience.",
                            "5490",
                            "10",
                            "4290",
                            "0",
                            "http://img6a.flixcart.com/image/microwave-new/3/3/z/ifb-17pmmec1-400x400-imae4g4uzzjsumhk.jpeg",
                            "oven_1"));

        productMap.put("Alcool", productlist);
        CenterRepository.getCenterRepository().setMapOfProductsInCategory(productMap);


            //Log.i("test", "Inside center repo getWebProducts" + CenterRepository.getCenterRepository().getMapOfProductsInCategory().tostring());
            //Log.i("test", "Inside center repo getWebProducts" + productlist.get(0).getItemName());
        }



    public void getAllProducts(int productCategory) {

        if (productCategory == 0) {
            //getRemoteProducts();
            //Log.i("test", "Inside center repo getWebProducts next" + CenterRepository.getCenterRepository().getMapOfProductsInCategory().get("Alcool").toString());

        } else {
            getfakeProducts();
        }



    }


}
