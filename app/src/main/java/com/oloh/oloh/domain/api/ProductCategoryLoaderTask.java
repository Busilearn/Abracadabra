package com.oloh.oloh.domain.api;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.oloh.oloh.R;
import com.oloh.oloh.domain.mock.WebServerSync;
import com.oloh.oloh.model.CenterRepository;
import com.oloh.oloh.model.entities.Category;
import com.oloh.oloh.model.entities.Products;
import com.oloh.oloh.view.activities.MainActivity;
import com.oloh.oloh.view.adapter.CategoryListAdapter;
import com.oloh.oloh.util.AppConstants;
import com.oloh.oloh.util.Utils;
import com.oloh.oloh.view.fragment.ProductOverviewFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;

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
            ((MainActivity) context).getProgressBar().setVisibility(View.VISIBLE);
        }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);

        if (null != ((MainActivity) context).getProgressBar())
            ((MainActivity) context).getProgressBar().setVisibility(View.GONE);

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
        WebServerSync.getWebServerSync().addCategory();
        return null;
    }

}