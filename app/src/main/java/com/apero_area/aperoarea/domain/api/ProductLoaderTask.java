package com.apero_area.aperoarea.domain.api;

/**
 * Created by stran on 30/08/2017.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.apero_area.aperoarea.domain.helper.ApiInterface;
import com.apero_area.aperoarea.model.entities.Product;
import com.apero_area.aperoarea.view.activities.MainActivity;
import com.apero_area.aperoarea.view.adapter.ProductsInCategoryPagerAdapter;
import com.apero_area.aperoarea.domain.mock.FakeWebServer;
import com.apero_area.aperoarea.model.CenterRepository;
import com.apero_area.aperoarea.view.fragment.ProductListFragment;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * The Class ImageLoaderTask.
 */
public class ProductLoaderTask extends AsyncTask<String, Void, Void> {

    private Context context;
    private ViewPager viewPager;
    private TabLayout tabs;
    private ApiInterface apiInterface;
    private List<Product> products;
    private AlertDialog alertDialog;

    public ProductLoaderTask(RecyclerView listView, Context context,
                             ViewPager viewpager, TabLayout tabs) {

        this.viewPager = viewpager;
        this.tabs = tabs;
        this.context = context;

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        if (null != ((MainActivity) context).getProgressBar())
            ((MainActivity) context).getProgressBar().setVisibility(
                    View.VISIBLE);

    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (null != ((MainActivity) context).getProgressBar())
            ((MainActivity) context).getProgressBar().setVisibility(
                    View.GONE);

        setUpUi();

    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //FakeWebServer.getFakeWebServer().getProducts();

        // Build of the retrofit object
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        ConcurrentHashMap<String, ArrayList<Product>> productMap = new ConcurrentHashMap<String, ArrayList<Product>>();
        //ArrayList<Product> productlist = new ArrayList<Product>((Collection<? extends Product>) call);
        ArrayList<Product> productlist = new ArrayList<Product>();
        List<Product> call = new ArrayList<Product>(Collections.unmodifiableCollection(new ArrayList<Product>(productlist)));
        productMap.put("Chouille", productlist);


        //HashSet<Product> call = new HashSet<Product>(productlist);
        //List<Product> userList = new ArrayList<Product>(call);

        //Collection<User> userCollection = new HashSet<User>(usersArrayList);

        //List<User> userList = new ArrayList<User>(userCollection );

        CenterRepository.getCenterRepository().setMapOfProductsInCategory(productMap);

        return null;
    }


    private void setUpUi() {

        setupViewPager();

        // bitmap = BitmapFactory
        // .decodeResource(getResources(), R.drawable.header);
        //
        // Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
        // @SuppressWarnings("ResourceType")
        // @Override
        // public void onGenerated(Palette palette) {
        //
        // int vibrantColor = palette.getVibrantColor(R.color.primary_500);
        // int vibrantDarkColor = palette
        // .getDarkVibrantColor(R.color.primary_700);
        // collapsingToolbarLayout.setContentScrimColor(vibrantColor);
        // collapsingToolbarLayout
        // .setStatusBarScrimColor(vibrantDarkColor);
        // }
        // });

        // tabLayout
        // .setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
        // @Override
        // public void onTabSelected(TabLayout.Tab tab) {
        //
        // viewPager.setCurrentItem(tab.getPosition());
        //
        // switch (tab.getPosition()) {
        // case 0:
        //
        // header.setImageResource(R.drawable.header);
        //
        // bitmap = BitmapFactory.decodeResource(
        // getResources(), R.drawable.header);
        //
        // Palette.from(bitmap).generate(
        // new Palette.PaletteAsyncListener() {
        // @SuppressWarnings("ResourceType")
        // @Override
        // public void onGenerated(Palette palette) {
        //
        // int vibrantColor = palette
        // .getVibrantColor(R.color.primary_500);
        // int vibrantDarkColor = palette
        // .getDarkVibrantColor(R.color.primary_700);
        // collapsingToolbarLayout
        // .setContentScrimColor(vibrantColor);
        // collapsingToolbarLayout
        // .setStatusBarScrimColor(vibrantDarkColor);
        // }
        // });
        // break;
        // case 1:
        //
        // header.setImageResource(R.drawable.header_1);
        //
        // bitmap = BitmapFactory.decodeResource(
        // getResources(), R.drawable.header_1);
        //
        // Palette.from(bitmap).generate(
        // new Palette.PaletteAsyncListener() {
        // @SuppressWarnings("ResourceType")
        // @Override
        // public void onGenerated(Palette palette) {
        //
        // int vibrantColor = palette
        // .getVibrantColor(R.color.primary_500);
        // int vibrantDarkColor = palette
        // .getDarkVibrantColor(R.color.primary_700);
        // collapsingToolbarLayout
        // .setContentScrimColor(vibrantColor);
        // collapsingToolbarLayout
        // .setStatusBarScrimColor(vibrantDarkColor);
        // }
        // });
        //
        // break;
        // case 2:
        //
        // header.setImageResource(R.drawable.header2);
        //
        // Bitmap bitmap = BitmapFactory.decodeResource(
        // getResources(), R.drawable.header2);
        //
        // Palette.from(bitmap).generate(
        // new Palette.PaletteAsyncListener() {
        // @SuppressWarnings("ResourceType")
        // @Override
        // public void onGenerated(Palette palette) {
        //
        // int vibrantColor = palette
        // .getVibrantColor(R.color.primary_500);
        // int vibrantDarkColor = palette
        // .getDarkVibrantColor(R.color.primary_700);
        // collapsingToolbarLayout
        // .setContentScrimColor(vibrantColor);
        // collapsingToolbarLayout
        // .setStatusBarScrimColor(vibrantDarkColor);
        // }
        // });
        //
        // break;
        // }
        // }
        //
        // @Override
        // public void onTabUnselected(TabLayout.Tab tab) {
        //
        // }
        //
        // @Override
        // public void onTabReselected(TabLayout.Tab tab) {
        //
        // }
        // });

    }


    private void setupViewPager() {



                    ProductsInCategoryPagerAdapter adapter = new ProductsInCategoryPagerAdapter(
                            ((MainActivity) context).getSupportFragmentManager());

                    Gson gson = new Gson();
                    String productJson = gson.toJson(products);

                    Set<String> keys = CenterRepository.getCenterRepository().getMapOfProductsInCategory()
                            .keySet();

                    for (String string : keys) {

                        adapter.addFrag(new ProductListFragment(string), string);

                    }

                    viewPager.setAdapter(adapter);

//		viewPager.setPageTransformer(true,
//				new );

                    tabs.setupWithViewPager(viewPager);



    }



}
