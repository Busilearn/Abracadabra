package com.apero_area.aperoarea.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.apero_area.aperoarea.ApiClient;
import com.apero_area.aperoarea.R;
import com.apero_area.aperoarea.adapters.RecyclerAdapter;
import com.apero_area.aperoarea.helper.ApiInterface;
import com.apero_area.aperoarea.helper.Connectivity;
import com.apero_area.aperoarea.models.CenterRepository;
import com.apero_area.aperoarea.models.Money;
import com.apero_area.aperoarea.models.Product;
import com.apero_area.aperoarea.util.PreferenceHelper;
import com.apero_area.aperoarea.util.TinyDB;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;
    private List<Product> products;
    private ApiInterface apiInterface;
    private ProgressDialog progress;
    private AlertDialog alertDialog;
    private Menu mMenu;
    private TextView checkOutAmount, itemCountTextView;
    private BigDecimal checkoutAmount = new BigDecimal(BigInteger.ZERO);
    private int itemCount = 0;
    private DrawerLayout mDrawerLayout;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        layoutManager = new LinearLayoutManager (this);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setHasFixedSize(true);

        checkOutAmount = (TextView) findViewById(R.id.checkout_amount);
        //checkOutAmount.setSelected(true);
        //checkOutAmount.setText(Money.rupees(checkoutAmount).toString());

        itemCountTextView = (TextView) findViewById(R.id.item_count);

        if (itemCount != 0) {
            for (Product product : CenterRepository.getCenterRepository()
                    .getListOfProductsInShoppingList()) {

                updateCheckOutAmount(
                        BigDecimal.valueOf(Long.valueOf(product.getSellMRP())),
                        true);
            }
        }


        progress = ProgressDialog.show(this, "Chargement des données",
                "En cours de chargement, veuillez patienter", true);

        alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Pas de connexion");
        alertDialog.setMessage("Merci d'activer votre connexion internet");

        CenterRepository.getCenterRepository().setListOfProductsInShoppingList(
                new TinyDB(getApplicationContext()).getListObject(
                        PreferenceHelper.MY_CART_LIST_LOCAL, Product.class));

        itemCount = CenterRepository.getCenterRepository().getListOfProductsInShoppingList()
                .size();

        // Build of the retrofit object
        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        // Make the request
        Call<List<Product>> call = apiInterface.getProduct();
        //
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                progress.dismiss();

                products = response.body();
                if (products.size() != 0) {
                    adapter = new RecyclerAdapter(MainActivity.this, products, new RecyclerAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Product products) {
                            Log.d("test","click " + products);

                            Intent intent = new Intent(MainActivity.this, ProductViewActivity.class);
                            Gson gson = new Gson();
                            String productJson = gson.toJson(products);
                            intent.putExtra("productJson", productJson);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                progress.dismiss();
                alertDialog.show();
                Log.d("test", "echec" + t);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        mMenu = menu;
        return true;
    }


    //gère le click sur une action de l'ActionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_checkout:
                Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
                startActivity(intent);

            case R.id.cart:
                Intent intent2 = new Intent(this, CartActivity.class);
                this.startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void setBadgeCount(String count) {

        Context context = getApplicationContext();
        MenuItem itemCart = mMenu.findItem(R.id.action_cart);
        LayerDrawable icon = (LayerDrawable) itemCart.getIcon();

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }

    public void updateCheckOutAmount(BigDecimal amount, boolean increment) {

        if (increment) {
            checkoutAmount = checkoutAmount.add(amount);
        } else {
            if (checkoutAmount.signum() == 1)
                checkoutAmount = checkoutAmount.subtract(amount);
        }

        checkOutAmount.setText(Money.rupees(checkoutAmount).toString());
    }

    public void updateItemCount(boolean ifIncrement) {
        if (ifIncrement) {
            itemCount++;
            itemCountTextView.setText(String.valueOf(itemCount));

        } else {
            itemCountTextView.setText(String.valueOf(itemCount <= 0 ? 0
                    : --itemCount));
        }

        toggleBannerVisibility();
    }

    public BigDecimal getCheckoutAmount() {
        return checkoutAmount;
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Store Shopping Cart in DB
        new TinyDB(getApplicationContext()).putListObject(
                PreferenceHelper.MY_CART_LIST_LOCAL, CenterRepository
                        .getCenterRepository().getListOfProductsInShoppingList());
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Show Offline Error Message
        if (!Connectivity.isConnected(getApplicationContext())) {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.connection_dialog);
            Button dialogButton = (Button) dialog
                    .findViewById(R.id.dialogButtonOK);

            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            dialog.show();
        }

        // Show Whats New Features If Requires
        new WhatsNewDialog(this);
    }

    /*
     * Toggles Between Offer Banner and Checkout Amount. If Cart is Empty SHow
     * Banner else display total amount and item count
     */
    public void toggleBannerVisibility() {
        if (itemCount == 0) {

            findViewById(R.id.checkout_item_root).setVisibility(View.GONE);
            findViewById(R.id.new_offers_banner).setVisibility(View.VISIBLE);

        } else {
            findViewById(R.id.checkout_item_root).setVisibility(View.VISIBLE);
            findViewById(R.id.new_offers_banner).setVisibility(View.GONE);
        }
    }

    /*
     * Get Number of items in cart
     */
    public int getItemCount() {
        return itemCount;
    }

    /*
     * Get Navigation drawer
     */
    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

}
