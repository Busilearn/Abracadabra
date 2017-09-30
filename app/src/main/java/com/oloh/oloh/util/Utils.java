package com.oloh.oloh.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;

import com.oloh.oloh.R;
import com.oloh.oloh.view.activities.MainActivity;
import com.oloh.oloh.view.fragment.ContactUsFragment;
import com.oloh.oloh.view.fragment.HomeFragment;
import com.oloh.oloh.view.fragment.MyCartFragment;
import com.oloh.oloh.view.fragment.ProductOverviewFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by stran on 29/08/2017.
 */

public class Utils {

    public static final String ATTRIBUTE_TTF_KEY = "ttf_name";

    public static final String ATTRIBUTE_SCHEMA = "http://schemas.android.com/apk/lib/com.oloh.oloh.util";

    public final static String SHOPPING_LIST_TAG = "SHoppingListFragment";
    public static final String PRODUCT_OVERVIEW_FRAGMENT_TAG = "ProductOverView";
    public static final String MY_CART_FRAGMENT = "MyCartFragment";
    public static final String MY_ORDERS_FRAGMENT = "MYOrdersFragment";
    public static final String HOME_FRAGMENT = "HomeFragment";
    public static final String SEARCH_FRAGMENT_TAG = "SearchFragment";
    public static final String SETTINGS_FRAGMENT_TAG = "SettingsFragment";
    public static final String OTP_LOGIN_TAG = "OTPLogingFragment";
    public static final String CONTACT_US_FRAGMENT = "ContactUs";
    private static final String PREFERENCES_FILE = "materialsample_settings";
    private static String CURRENT_TAG = null;
    private static Map<String, Typeface> TYPEFACE = new HashMap<String, Typeface>();

    public static void switchFragmentWithAnimation(int id, Fragment fragment,FragmentActivity activity, String TAG, AnimationType transitionStyle) {

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction();

        if (transitionStyle != null) {
            switch (transitionStyle) {
                case SLIDE_DOWN:

                    // Exit from down
                    fragmentTransaction.setCustomAnimations(R.anim.slide_up,
                            R.anim.slide_down);
                    break;

                case SLIDE_UP:

                    // Enter from Up
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_up,
                            R.anim.slide_out_up);
                    break;

                case SLIDE_LEFT:

                    // Enter from left
                    fragmentTransaction.setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_out_left);
                    break;

                // Enter from right
                case SLIDE_RIGHT:
                    fragmentTransaction.setCustomAnimations(R.anim.slide_right,
                            R.anim.slide_out_right);
                    break;

                case FADE_IN:
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,
                            R.anim.fade_out);

                case FADE_OUT:
                    fragmentTransaction.setCustomAnimations(R.anim.fade_in,
                            R.anim.donot_move);
                    break;

                case SLIDE_IN_SLIDE_OUT:

                    fragmentTransaction.setCustomAnimations(R.anim.slide_left,
                            R.anim.slide_out_left);
                    break;

                default:
                    break;
            }
        }

        CURRENT_TAG = TAG;

        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.addToBackStack(TAG);
        fragmentTransaction.commit();
    }

    public static void switchContent(int id, String TAG,FragmentActivity baseActivity, AnimationType transitionStyle) {

        Fragment fragmentToReplace = null;

        FragmentManager fragmentManager = baseActivity
                .getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // If our current fragment is null, or the new fragment is different, we
        // need to change our current fragment
        if (CURRENT_TAG == null || !TAG.equals(CURRENT_TAG)) {

            if (transitionStyle != null) {
                switch (transitionStyle) {
                    case SLIDE_DOWN:
                        // Exit from down
                        transaction.setCustomAnimations(R.anim.slide_up,
                                R.anim.slide_down);
                        break;
                    case SLIDE_UP:
                        // Enter from Up
                        transaction.setCustomAnimations(R.anim.slide_in_up,
                                R.anim.slide_out_up);
                        break;
                    case SLIDE_LEFT:
                        // Enter from left
                        transaction.setCustomAnimations(R.anim.slide_left,
                                R.anim.slide_out_left);
                        break;
                    // Enter from right
                    case SLIDE_RIGHT:
                        transaction.setCustomAnimations(R.anim.slide_right,
                                R.anim.slide_out_right);
                        break;
                    case FADE_IN:
                        transaction.setCustomAnimations(R.anim.fade_in,
                                R.anim.fade_out);
                    case FADE_OUT:
                        transaction.setCustomAnimations(R.anim.fade_in,
                                R.anim.donot_move);
                        break;
                    case SLIDE_IN_SLIDE_OUT:
                        transaction.setCustomAnimations(R.anim.slide_left,
                                R.anim.slide_out_left);
                        break;
                    default:
                        break;
                }
            }

            // Try to find the fragment we are switching to
            Fragment fragment = fragmentManager.findFragmentByTag(TAG);

            // If the new fragment can't be found in the manager, create a new
            // one
            if (fragment == null) {

                if (TAG.equals(HOME_FRAGMENT)) {
                    fragmentToReplace = new HomeFragment();
                } else if (TAG.equals(SHOPPING_LIST_TAG)) {
                    fragmentToReplace = new MyCartFragment();
                } else if (TAG.equals(CONTACT_US_FRAGMENT)) {
                    fragmentToReplace = new ContactUsFragment();
                } else if (TAG.equals(PRODUCT_OVERVIEW_FRAGMENT_TAG)) {
                    fragmentToReplace = new ProductOverviewFragment();
                } else if (TAG.equals(SHOPPING_LIST_TAG)) {
                    fragmentToReplace = new MyCartFragment();
                }

            } else

            {
                if (TAG.equals(HOME_FRAGMENT)) {
                    fragmentToReplace = (HomeFragment) fragment;
                } else if (TAG.equals(SHOPPING_LIST_TAG)) {
                    fragmentToReplace = (MyCartFragment) fragment;
                } else if (TAG.equals(PRODUCT_OVERVIEW_FRAGMENT_TAG)) {
                    fragmentToReplace = (ProductOverviewFragment) fragment;
                } else if (TAG.equals(CONTACT_US_FRAGMENT)) {
                    fragmentToReplace = (ContactUsFragment) fragment;
                }
            }
            CURRENT_TAG = TAG;
            // Replace our current fragment with the one we are changing to
            transaction.replace(id, fragmentToReplace, TAG);
            transaction.commit();

        } else

        {
            // Do nothing we are already on the fragment
        }
    }

    public static void vibrate(Context context) {
        // Get instance of Vibrator 400 milliseconds
        ((Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE)).vibrate(100);
    }

    public static String getVersion(Context context) {
        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_META_DATA);
            return String.valueOf(pInfo.versionCode) + " " + pInfo.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            return "1.0.1";
        }
    }

    public static Typeface getFonts(Context context, String fontName) {
        Typeface typeface = TYPEFACE.get(fontName);
        if (typeface == null) {
            typeface = Typeface.createFromAsset(context.getAssets(), "font/"
                    + fontName);
            TYPEFACE.put(fontName, typeface);
        }
        return typeface;
    }

    public enum AnimationType {
        SLIDE_LEFT, SLIDE_RIGHT, SLIDE_UP, SLIDE_DOWN, FADE_IN, SLIDE_IN_SLIDE_OUT, FADE_OUT
    }
}
