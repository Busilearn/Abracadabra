package com.oloh.oloh.domain.helper;

/**
 * Created by stran on 25/08/2017.
 */


public interface NetworkConstants {

    String SERVER_URL = "https://oloh.fr";

    String SERVER_WOO_ENDPOINT = "/wp-json/wc/v2/";

    String SERVER_OLOH_ENDPOINT = "/wp-content/plugins/oloh/";

    String URL_GET_ALL_CATEGORY = SERVER_URL + SERVER_WOO_ENDPOINT + "products/categories?per_page=100";

    String URL_GET_PRODUCTS_MAP = SERVER_URL + SERVER_WOO_ENDPOINT + "products?per_page=100";

    String URL_POST_OLOH_CHARGE= SERVER_URL + SERVER_OLOH_ENDPOINT + "payment_api/charge.php";

    String URL_GET_OLOH_MINIMUM_ORDER= SERVER_URL + SERVER_OLOH_ENDPOINT + "MinimumOrder.php";

    String AUTH_URL = "http://oloh.fr:9080/auth";
    String REALM_URL = "realm://oloh.fr:9080/~/oloh";

}
