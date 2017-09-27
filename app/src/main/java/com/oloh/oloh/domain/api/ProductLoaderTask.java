package com.oloh.oloh.domain.api;

/**
 * Created by stran on 30/08/2017.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.oloh.oloh.domain.mock.WebServerSync;
import com.oloh.oloh.model.entities.Product;
import com.oloh.oloh.util.AppConstants;
import com.oloh.oloh.view.activities.MainActivity;
import com.oloh.oloh.view.adapter.ProductsInCategoryPagerAdapter;
import com.oloh.oloh.model.CenterRepository;
import com.oloh.oloh.view.fragment.ProductListFragment;
import com.google.gson.Gson;

import java.util.List;
import java.util.Set;

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
        setupViewPager();
    }

    @Override
    protected Void doInBackground(String... params) {
        WebServerSync.getWebServerSync().getAllProducts(AppConstants.CURRENT_CATEGORY);

        return null;
    }

    private void setupViewPager() {
                    ProductsInCategoryPagerAdapter adapter = new ProductsInCategoryPagerAdapter(
                            ((MainActivity) context).getSupportFragmentManager());


                    Set<String> keys = CenterRepository.getCenterRepository().getMapOfProductsInCategory()
                            .keySet();


                    for (String string : keys) {

                        adapter.addFrag(new ProductListFragment(string), string);

                    }

                    viewPager.setAdapter(adapter);
                    tabs.setupWithViewPager(viewPager);
    }



}
