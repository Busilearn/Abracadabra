package com.oloh.oloh.model.entities;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.LinkingObjects;
import io.realm.annotations.PrimaryKey;

/**
 * Created by stran on 25/08/2017.
 */

public class Category extends RealmObject {
    @PrimaryKey
    private int id;
    private int parent;
    @SerializedName("name")
    private String categoryName;
    @SerializedName("description")
    private String categoryDescription;
    private String categoryDiscount = "0"; //TODO A implémenter plus tard
    private Images image;
    private String categoryImageUrl;
    private Category categoriesParent;
    @LinkingObjects("categories")
    private final RealmResults<Products> products = null;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
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

    public Category getParentCategories() {
        return categoriesParent;
    }

    public void setParentCategories(Category subCategories) {
        this.categoriesParent = subCategories;
    }

}
