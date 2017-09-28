package com.oloh.oloh.model.entities;


import android.text.Html;
import android.text.Spanned;

import java.util.List;


public class Product {

    private int id;
    private String productId;
    private String name;
    private String description;
    private String price;
    private String average_rating;
    private String rating_count;
    private List<Images> images;
    private String short_description;
    private String orderQty;
    private String discount;
    private String sku;
    private String imageUrl;

    //getters & setters

    public String getId() {
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getItemName() {
        return name;
    }

    public void setItemName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItemDetail() {
        return String.valueOf(fromHtml(description));
    }

    public void setItemDetail(String description) {
        this.description = description;
    }

    public String getAverage_rating() {
        return average_rating;
    }

    public void setAverage_rating(String average_rating) {
        this.average_rating = average_rating;
    }

    public String getRating_count() {
        return rating_count;
    }

    public void setRating_count(String rating_count) {
        this.rating_count = rating_count;
    }

    public String getItemShortDesc() {
        return String.valueOf(fromHtml(short_description));
    }

    public void setItemShortDesc(String short_description) { this.short_description = short_description;
    }

    public String getSellMRP() {
        return price;
    }

    public void setSellMRP(String sellMRP) {
        this.price = sellMRP;
    }

    public List<Images> getImages() {
        return images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public String getQuantity() {
        return orderQty;
    }

    public void setQuantity(String quantity) {
        this.orderQty = quantity;
    }

    public String getMRP() {
        return this.sku;
    }

    public void setMRP(String MRP) {
        this.sku = MRP;
    }

    public String getDiscount() {
        //TODO Recupérer la valeur du server
        return /*discount*/ "" + "%";
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImageUrl() {
        imageUrl = this.getImages().get(0).getSrc();
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    @SuppressWarnings("deprecation")
    private static Spanned fromHtml(String html){
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY);
        } else {
            result = Html.fromHtml(html);
        }
        return result;
    }

}
