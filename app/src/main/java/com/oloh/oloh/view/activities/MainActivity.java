package com.oloh.oloh.view.activities;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.oloh.oloh.R;
import com.oloh.oloh.checkout.PayActivity;
import com.oloh.oloh.util.AppConstants;
import com.oloh.oloh.view.fragment.HomeFragment;
import com.oloh.oloh.domain.helper.Connectivity;
import com.oloh.oloh.model.CenterRepository;
import com.oloh.oloh.model.entities.Money;
import com.oloh.oloh.model.entities.Product;
import com.oloh.oloh.util.PreferenceHelper;
import com.oloh.oloh.util.TinyDB;
import com.oloh.oloh.util.Utils;
import com.stripe.android.Stripe;
import com.wang.avi.AVLoadingIndicatorView;

import java.math.BigDecimal;
import java.math.BigInteger;

public class MainActivity extends AppCompatActivity {
    Stripe stripe;

    public static final double MINIMUM_SUPPORT = 0.1;
    private static final String TAG = MainActivity.class.getSimpleName();
    private int itemCount = 0;
    private BigDecimal checkoutAmount = new BigDecimal(BigInteger.ZERO);
    private DrawerLayout mDrawerLayout;

    private TextView checkOutAmount, itemCountTextView;
    private TextView offerBanner;
    private AVLoadingIndicatorView progressBar;

    private NavigationView mNavigationView;
    private Boolean CheckoutDisable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView checkout = (TextView)findViewById(R.id.checkout);
        CheckoutDisable = getIntent().getExtras().getBoolean("CheckoutDisable");

        stripe = new Stripe(getApplicationContext());
        stripe.setDefaultPublishableKey(AppConstants.PUBLISHABLE_KEY);

        CenterRepository.getCenterRepository().setListOfProductsInShoppingList(

                new TinyDB(getApplicationContext()).getListObject(
                        PreferenceHelper.MY_CART_LIST_LOCAL, Product.class));

        itemCount = CenterRepository.getCenterRepository().getListOfProductsInShoppingList()
                .size();

        offerBanner = ((TextView) findViewById(R.id.new_offers_banner));

        itemCountTextView = (TextView) findViewById(R.id.item_count);
        itemCountTextView.setSelected(true);
        itemCountTextView.setText(String.valueOf(itemCount));

        checkOutAmount = (TextView) findViewById(R.id.checkout_amount);
        checkOutAmount.setSelected(true);
        checkOutAmount.setText(Money.euro(checkoutAmount).toString());
        offerBanner.setSelected(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        progressBar = (AVLoadingIndicatorView) findViewById(R.id.loading_bar);

        checkOutAmount.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Utils.vibrate(getApplicationContext());

                Utils.switchContent(R.id.frag_container,
                        Utils.SHOPPING_LIST_TAG, MainActivity.this,
                        Utils.AnimationType.SLIDE_UP);

            }
        });

        if (itemCount != 0) {
            for (Product product : CenterRepository.getCenterRepository()
                    .getListOfProductsInShoppingList()) {

                updateCheckOutAmount(
                        BigDecimal.valueOf(Double.valueOf(product.getSellMRP())),
                        true);
            }
        }

        findViewById(R.id.item_counter).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                            Utils.vibrate(getApplicationContext());
                            Utils.switchContent(R.id.frag_container,
                                    Utils.SHOPPING_LIST_TAG,
                                    MainActivity.this, Utils.AnimationType.SLIDE_UP);
                    }
                });

        findViewById(R.id.checkout).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (CheckoutDisable) {
                                Toast.makeText(getBaseContext(), "Vous n'êtes pas dans la zone de livraison", Toast.LENGTH_LONG).show();
                            }
                            else {
                            BigDecimal minimumCheckout = new BigDecimal(0);

                            if (checkoutAmount.compareTo(minimumCheckout) < 0) {
                                Toast.makeText(getBaseContext(), "Le minimum d'achat est à 15 euros", Toast.LENGTH_LONG).show();
                            } else if (checkoutAmount.compareTo(minimumCheckout) > 0) {

                                Utils.vibrate(getApplicationContext());

                                Intent buyIntent = new Intent(MainActivity.this, PayActivity.class);
                                buyIntent.putExtra("plan_price", Money.euro(checkoutAmount).toStringForStripe());
                                startActivity(buyIntent);
                        }
                    }
                }});

        Utils.switchFragmentWithAnimation(R.id.frag_container,
                new HomeFragment(), this, Utils.HOME_FRAGMENT,
                Utils.AnimationType.SLIDE_UP);

        toggleBannerVisibility();

        mNavigationView
                .setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.home:

                                mDrawerLayout.closeDrawers();

                                Utils.switchContent(R.id.frag_container,
                                        Utils.HOME_FRAGMENT,
                                        MainActivity.this,
                                        Utils.AnimationType.SLIDE_LEFT);

                                return true;

                            case R.id.my_cart:

                                mDrawerLayout.closeDrawers();

                                Utils.switchContent(R.id.frag_container,
                                        Utils.SHOPPING_LIST_TAG,
                                        MainActivity.this,
                                        Utils.AnimationType.SLIDE_LEFT);
                                return true;

                            case R.id.contact_us:

                                mDrawerLayout.closeDrawers();

                                Utils.switchContent(R.id.frag_container,
                                        Utils.CONTACT_US_FRAGMENT,
                                        MainActivity.this,
                                        Utils.AnimationType.SLIDE_LEFT);
                                return true;

                            default:
                                return true;
                        }
                    }
                });

    }

    public AVLoadingIndicatorView getProgressBar() {
        return progressBar;
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

    public void updateCheckOutAmount(BigDecimal amount, boolean increment) {

        if (increment) {
            checkoutAmount = checkoutAmount.add(amount);
        } else {
            if (checkoutAmount.signum() == 1)
                checkoutAmount = checkoutAmount.subtract(amount);
        }

        checkOutAmount.setText(Money.euro(checkoutAmount).toString());
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
    }

    /*
     * Toggles Between Offer Banner and Checkout Amount. If Cart is Empty SHow
     * Banner else display total amount and item count
     */
    public void toggleBannerVisibility() {
        if (itemCount == 0) {

            findViewById(R.id.checkout_item_root).setVisibility(View.VISIBLE);
            findViewById(R.id.new_offers_banner).setVisibility(View.GONE);

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
