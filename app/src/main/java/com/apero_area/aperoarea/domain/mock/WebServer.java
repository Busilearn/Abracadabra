package com.apero_area.aperoarea.domain.mock;

import android.util.Log;

import com.apero_area.aperoarea.domain.api.ApiClient;
import com.apero_area.aperoarea.domain.api.CallbackT;
import com.apero_area.aperoarea.domain.helper.ApiInterface;
import com.apero_area.aperoarea.model.CenterRepository;
import com.apero_area.aperoarea.model.entities.Product;
import com.apero_area.aperoarea.model.entities.ProductCategoryModel;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.media.CamcorderProfile.get;

/**
 * Created by stran on 05/09/2017.
 */

public class WebServer {


    private static WebServer webServer;

    public static WebServer getWebServer() {

        if (null == webServer) {
            webServer = new WebServer();
        }
        return webServer;
    }

    void initiateWebServer() {

        addCategory();

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


    public void getWebProducts(final CallbackT<ArrayList<Product>> callback) {

        final ConcurrentHashMap<String, ArrayList<Product>> productMap = new ConcurrentHashMap<String, ArrayList<Product>>();
        final ArrayList<Product> productlist = new ArrayList<Product>();


            // Build of the retrofit object
            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
            // Make the request
            Call<ArrayList<Product>> call = apiInterface.getProduct();
            //        apiInterface.getProduct().enqueue();
            call.enqueue(new Callback<ArrayList<Product>>() {
                @Override
                public void onResponse(Call<ArrayList<Product>> call, Response<ArrayList<Product>> response) {



                        ArrayList<Product> productlist = response.body();

                        for (Product item : productlist) {
                        Log.i("Body %s", item.getItemName());
                        }


                    productMap.put("Microwave oven", productlist);
                    CenterRepository.getCenterRepository().setMapOfProductsInCategory(productMap);
                        //callback.next(productlist);

                }



                @Override
                public void onFailure(Call<ArrayList<Product>> call, Throwable t) {

                    //alertDialog.show();
                    Log.d("test", "echec" + t);
                }
            });

            /*productlist
                    .add(new Product(
                            "Solo Microwave Oven",
                            "<ul>\\n<li>1 bouteille de Label 5 70cl</li>\\n<li>1 bouteille de Coca 1,5L</li>\\n<li>x5 gobelets</li>\\n<li>Springles</li>\\n</ul>\\n",
                            "Explore the joys of cooking with IFB 17PM MEC1 Solo Microwave Oven. The budget-friendly appliance has several nifty features including Multi Power Levels and Speed Defrost to make cooking a fun-filled experience.",
                            "5490",
                            "10",
                            "4290",
                            "0",
                            "http://img6a.flixcart.com/image/microwave-new/3/3/z/ifb-17pmmec1-400x400-imae4g4uzzjsumhk.jpeg",
                            "oven_1"));*/



            productMap.put("Microwave oven", productlist);
            CenterRepository.getCenterRepository().setMapOfProductsInCategory(productMap);



    }

    public void getAllProducts(int productCategory) {

        if (productCategory == 0) {
            getWebProducts(new CallbackT<ArrayList<Product>>() {

                @Override
                public void next(ArrayList<Product> productlist) {

                               final ConcurrentHashMap<String, ArrayList<Product>> productMap = new ConcurrentHashMap<String, ArrayList<Product>>();




                           }
                       }
            );

        } else {

        }



    }


}
