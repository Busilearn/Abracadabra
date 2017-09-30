package com.oloh.oloh.domain.helper;

/**
 * Created by stran on 25/08/2017.
 */


public interface NetworkConstants {
    public boolean DEBUGABLE = true;

    public static final String SERVER_URL = "https://apero-area.com";

    public static final String SERVER_WOO_ENDPOINT = "/wp-json/wc/v2/";

    public static final String SERVER_OLOH_ENDPOINT = "/wp-content/plugins/apero-area/payment_api/";

    public static final String URL_GET_ALL_CATEGORY = SERVER_URL + SERVER_WOO_ENDPOINT + "categories";

    public static final String URL_GET_PRODUCTS_MAP = SERVER_URL + SERVER_WOO_ENDPOINT + "products";

    public static final String URL_POST_OLOH_CHARGE= SERVER_URL + SERVER_OLOH_ENDPOINT + "charge.php";
}
