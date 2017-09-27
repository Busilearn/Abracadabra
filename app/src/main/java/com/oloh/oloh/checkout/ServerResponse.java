package com.oloh.oloh.checkout;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by micka on 15-Sep-17.
 */

public class ServerResponse implements Serializable {

        @SerializedName("response_code")
        private int responseCode;

        public int getResponseCode() {
            return responseCode;
        }

        public void setResponseCode(int responseCode) {
            this.responseCode = responseCode;
        }
}
