package com.apero_area.aperoarea.domain.api;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.apero_area.aperoarea.R;
import com.apero_area.aperoarea.domain.mock.WebServerSync;
import com.apero_area.aperoarea.view.activities.MainActivity;
import com.apero_area.aperoarea.view.adapter.CategoryListAdapter;
import com.apero_area.aperoarea.domain.mock.WebServer;
import com.apero_area.aperoarea.util.AppConstants;
import com.apero_area.aperoarea.util.Utils;
import com.apero_area.aperoarea.view.fragment.ProductOverviewFragment;

/**
 * The Class ImageLoaderTask.
 */
public class ProductCategoryLoaderTask extends AsyncTask<String, Void, Void> {

    private static final int NUMBER_OF_COLUMNS = 2;
    private Context context;
    private RecyclerView recyclerView;

    public ProductCategoryLoaderTask(RecyclerView listView, Context context) {

        this.recyclerView = listView;
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

        if (recyclerView != null) {
            CategoryListAdapter simpleRecyclerAdapter = new CategoryListAdapter(
                    context);

            recyclerView.setAdapter(simpleRecyclerAdapter);

            simpleRecyclerAdapter
                    .SetOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {

                        @Override
                        public void onItemClick(View view, int position) {

                            AppConstants.CURRENT_CATEGORY = position;

                            Utils.switchFragmentWithAnimation(
                                    R.id.frag_container,
                                    new ProductOverviewFragment(),
                                    ((MainActivity) context), null,
                                    Utils.AnimationType.SLIDE_LEFT);

                        }
                    });
        }

    }

    @Override
    protected Void doInBackground(String... params) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        WebServerSync.getWebServerSync().addCategory();
        WebServerSync.getWebServerSync().getWebProducts();


        return null;
    }

}