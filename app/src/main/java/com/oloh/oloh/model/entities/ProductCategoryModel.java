package com.oloh.oloh.model.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by stran on 25/08/2017.
 */

public class ProductCategoryModel {
    private String id;
    private String parent;
    @SerializedName("name")
    private String categoryName;
    @SerializedName("description")
    private String categoryDescription;
    private String categoryDiscount = "0"; // A implémenter plus tard
    private Images image = new Images("");
    private String categoryImageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getProductCategoryName() {
        return categoryName;
    }

    public void setProductCategoryName(String idproductcategory) {
        this.categoryName = idproductcategory;
    }

    public String getProductCategoryDescription() {
        return categoryDescription;
    }

    public void setProductCategoryDescription(String productDescription) {
        this.categoryDescription = productDescription;
    }

    public String getProductCategoryDiscount() {
        return categoryDiscount;
    }

    public void setProductCategoryDiscount(String productDiscount) {
        this.categoryDiscount = productDiscount;
    }

    public void setProductCategoryImageUrl(String productUrl) {
        this.categoryImageUrl = productUrl;
    }

    public Images getImage() {
        return image;
    }

    public String getProductCategoryImageUrl() {
        categoryImageUrl = this.getImage().getSrc();
        return categoryImageUrl;
    }
}
