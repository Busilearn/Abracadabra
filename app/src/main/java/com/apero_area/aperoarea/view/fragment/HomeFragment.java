package com.apero_area.aperoarea.view.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import android.support.v7.graphics.Palette;

import com.apero_area.aperoarea.R;
import com.apero_area.aperoarea.domain.api.ProductCategoryLoaderTask;
import com.apero_area.aperoarea.domain.api.ProductLoaderTask;
import com.apero_area.aperoarea.domain.mock.WebServerSync;
import com.apero_area.aperoarea.model.CenterRepository;
import com.apero_area.aperoarea.view.activities.MainActivity;
import com.apero_area.aperoarea.util.Utils;
import com.apero_area.aperoarea.view.adapter.CategoryListAdapter;

/**
 * Created by dany on 29/08/2017.
 */

public class HomeFragment extends Fragment {
    int mutedColor = R.attr.colorPrimary;
    private CollapsingToolbarLayout collapsingToolbar;
    private RecyclerView recyclerView;
    /**
     * The double back to exit pressed once.
     */
    private boolean doubleBackToExitPressedOnce;
    private final Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            doubleBackToExitPressedOnce = false;
        }
    };
    private Handler mHandler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_product_category, container, false);

        view.findViewById(R.id.search_item).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        Utils.switchFragmentWithAnimation(R.id.frag_container,
                                new SearchProductFragment(),
                                ((MainActivity) getActivity()), null,
                                Utils.AnimationType.SLIDE_UP);

                    }
                });

        final Toolbar toolbar = (Toolbar) view.findViewById(R.id.anim_toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationIcon(R.drawable.ic_drawer);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        collapsingToolbar = (CollapsingToolbarLayout) view
                .findViewById(R.id.collapsing_toolbar);

        collapsingToolbar.setTitle("Categories");

        ImageView header = (ImageView) view.findViewById(R.id.header);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.header);

        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {

                mutedColor = palette.getMutedColor(R.color.primary_500);
                collapsingToolbar.setContentScrimColor(mutedColor);
                collapsingToolbar.setStatusBarScrimColor(R.color.black_trans80);
            }
        });

        recyclerView = (RecyclerView) view.findViewById(R.id.scrollableview);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        new ProductCategoryLoaderTask(recyclerView, getActivity()).execute();

//
/*        CategoryListAdapter simpleRecyclerAdapter = null;
        if (simpleRecyclerAdapter == null) {
			simpleRecyclerAdapter = new CategoryListAdapter(getActivity());
			recyclerView.setAdapter(simpleRecyclerAdapter);

			simpleRecyclerAdapter
					.SetOnItemClickListener(new CategoryListAdapter.OnItemClickListener() {

						@Override
						public void onItemClick(View view, int position) {

							if (position == 0) {
                                WebServerSync.getWebServerSync()
										.getWebProducts();
							} else if (position == 1) {
                                WebServerSync.getWebServerSync()
                                        .getAllFurnitures();
							}
							Utils.switchFragmentWithAnimation(
									R.id.frag_container,
									new ProductOverviewFragment(),
									((MainActivity) getActivity()), null,
									Utils.AnimationType.SLIDE_LEFT);

						}
					});
		}*/

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (doubleBackToExitPressedOnce) {
                        // super.onBackPressed();

                        if (mHandler != null) {
                            mHandler.removeCallbacks(mRunnable);
                        }

                        getActivity().finish();

                        return true;
                    }

                    doubleBackToExitPressedOnce = true;
                    Toast.makeText(getActivity(),
                            "Please click BACK again to exit",
                            Toast.LENGTH_SHORT).show();

                    mHandler.postDelayed(mRunnable, 2000);

                }
                return true;
            }
        });

        return view;

    }

}
