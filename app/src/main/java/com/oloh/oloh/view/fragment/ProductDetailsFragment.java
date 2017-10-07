package com.oloh.oloh.view.fragment;

/**
 * Created by stran on 29/08/2017.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oloh.oloh.R;
import com.oloh.oloh.model.entities.Products;
import com.oloh.oloh.view.activities.MainActivity;
import com.oloh.oloh.view.customview.ClickableViewPager;
import com.oloh.oloh.view.customview.LabelView;
import com.oloh.oloh.view.customview.TextDrawable;
import com.oloh.oloh.model.CenterRepository;
import com.oloh.oloh.model.entities.Money;
import com.oloh.oloh.util.ColorGenerator;
import com.oloh.oloh.util.Utils;


import java.math.BigDecimal;

/**
 * Fragment that appears in the "content_frame", shows a animal.
 */
public class ProductDetailsFragment extends Fragment {

    private int productListNumber;
    private ImageView itemImage;
    private TextView itemSellPrice;
    private TextView itemName;
    private TextView quanitity;
    private TextView itemdescription;
    private TextDrawable.IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private String subcategoryKey;
    private boolean isFromCart;
    private ClickableViewPager similarProductsPager;
    private ClickableViewPager topSellingPager;
    private Toolbar mToolbar;

    /**
     * Instantiates a new product details fragment.
     */
    public ProductDetailsFragment(String subcategoryKey, int productNumber,
                                  boolean isFromCart) {

        this.subcategoryKey = subcategoryKey;
        this.productListNumber = productNumber;
        this.isFromCart = isFromCart;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_product_detail,
                container, false);

        mToolbar = (Toolbar) rootView.findViewById(R.id.htab_toolbar);
        if (mToolbar != null) {
            ((MainActivity) getActivity()).setSupportActionBar(mToolbar);
        }

        if (mToolbar != null) {
            ((MainActivity) getActivity()).getSupportActionBar()
                    .setDisplayHomeAsUpEnabled(true);

            mToolbar.setNavigationIcon(R.drawable.ic_drawer);

        }

        mToolbar.setTitleTextColor(Color.WHITE);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).getmDrawerLayout()
                        .openDrawer(GravityCompat.START);
            }
        });

        ((MainActivity) getActivity()).getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        itemSellPrice = ((TextView) rootView
                .findViewById(R.id.category_discount));

        quanitity = ((TextView) rootView.findViewById(R.id.iteam_amount));

        itemName = ((TextView) rootView.findViewById(R.id.product_name));

        itemdescription = ((TextView) rootView
                .findViewById(R.id.product_description));

        itemImage = (ImageView) rootView.findViewById(R.id.product_image);

        fillProductData();

        rootView.findViewById(R.id.add_item).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (isFromCart) {

                            //Update Quantity on shopping List
                            CenterRepository
                                    .getCenterRepository()
                                    .getListOfProductsInShoppingList()
                                    .get(productListNumber)
                                    .setQuantity(
                                            String.valueOf(

                                                    Integer.valueOf(CenterRepository
                                                            .getCenterRepository()
                                                            .getListOfProductsInShoppingList()
                                                            .get(productListNumber)
                                                            .getQuantity()) + 1));


                            //Update Ui
                            quanitity.setText(CenterRepository
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(productListNumber).getQuantity());

                            Utils.vibrate(getActivity());

                            //Update checkout amount on screen
                            ((MainActivity) getActivity()).updateCheckOutAmount(
                                    BigDecimal.valueOf(Double
                                            .valueOf(CenterRepository
                                                    .getCenterRepository()
                                                    .getListOfProductsInShoppingList()
                                                    .get(productListNumber)
                                                    .getSellMRP())), true);

                        } else {

                            // current object
                            Products tempObj = CenterRepository
                                    .getCenterRepository().getMapOfProductsInCategory()
                                    .get(subcategoryKey).get(productListNumber);

                            // if current object is lready in shopping list
                            if (CenterRepository.getCenterRepository()
                                    .getListOfProductsInShoppingList().contains(tempObj)) {

                                // get position of current item in shopping list
                                int indexOfTempInShopingList = CenterRepository
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .indexOf(tempObj);

                                // increase quantity of current item in shopping
                                // list
                                if (Integer.parseInt(tempObj.getQuantity()) == 0) {

                                    ((MainActivity) getContext())
                                            .updateItemCount(true);

                                }

                                // update quanity in shopping list
                                CenterRepository
                                        .getCenterRepository()
                                        .getListOfProductsInShoppingList()
                                        .get(indexOfTempInShopingList)
                                        .setQuantity(
                                                String.valueOf(Integer
                                                        .valueOf(tempObj
                                                                .getQuantity()) + 1));

                                // update checkout amount
                                ((MainActivity) getContext()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Double
                                                .valueOf(CenterRepository
                                                        .getCenterRepository()
                                                        .getMapOfProductsInCategory()
                                                        .get(subcategoryKey)
                                                        .get(productListNumber)
                                                        .getSellMRP())), true);

                                // update current item quanitity
                                quanitity.setText(tempObj.getQuantity());

                            } else {

                                ((MainActivity) getContext())
                                        .updateItemCount(true);

                                tempObj.setQuantity(String.valueOf(1));

                                quanitity.setText(tempObj.getQuantity());

                                CenterRepository.getCenterRepository()
                                        .getListOfProductsInShoppingList().add(tempObj);

                                ((MainActivity) getContext()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Double
                                                .valueOf(CenterRepository
                                                        .getCenterRepository()
                                                        .getMapOfProductsInCategory()
                                                        .get(subcategoryKey)
                                                        .get(productListNumber)
                                                        .getSellMRP())), true);

                            }

                            Utils.vibrate(getContext());

                        }
                    }

                });

        rootView.findViewById(R.id.remove_item).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (isFromCart)

                        {

                            if (Integer.valueOf(CenterRepository
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(productListNumber).getQuantity()) > 2) {

                                CenterRepository
                                        .getCenterRepository()
                                        .getListOfProductsInShoppingList()
                                        .get(productListNumber)
                                        .setQuantity(
                                                String.valueOf(

                                                        Integer.valueOf(CenterRepository
                                                                .getCenterRepository()
                                                                .getListOfProductsInShoppingList()
                                                                .get(productListNumber)
                                                                .getQuantity()) - 1));

                                quanitity.setText(CenterRepository
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .get(productListNumber).getQuantity());

                                ((MainActivity) getActivity()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Double
                                                .valueOf(CenterRepository
                                                        .getCenterRepository()
                                                        .getListOfProductsInShoppingList()
                                                        .get(productListNumber)
                                                        .getSellMRP())), false);

                                Utils.vibrate(getActivity());
                            } else if (Integer.valueOf(CenterRepository
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(productListNumber).getQuantity()) == 1) {
                                ((MainActivity) getActivity())
                                        .updateItemCount(false);

                                ((MainActivity) getActivity()).updateCheckOutAmount(
                                        BigDecimal.valueOf(Double
                                                .valueOf(CenterRepository
                                                        .getCenterRepository()
                                                        .getListOfProductsInShoppingList()
                                                        .get(productListNumber)
                                                        .getSellMRP())), false);

                                CenterRepository.getCenterRepository()
                                        .getListOfProductsInShoppingList()
                                        .remove(productListNumber);

                                if ((((MainActivity) getActivity())
                                                .getItemCount()) == 0) {

                                    MyCartFragment.updateMyCartFragment(false);

                                }

                                Utils.vibrate(getActivity());

                            }

                        } else {

                            Products tempObj = CenterRepository
                                    .getCenterRepository().getMapOfProductsInCategory()
                                    .get(subcategoryKey).get(productListNumber);

                            if (CenterRepository.getCenterRepository()
                                    .getListOfProductsInShoppingList().contains(tempObj)) {

                                int indexOfTempInShopingList = CenterRepository
                                        .getCenterRepository().getListOfProductsInShoppingList()
                                        .indexOf(tempObj);

                                if (Integer.valueOf(tempObj.getQuantity()) != 0) {

                                    CenterRepository
                                            .getCenterRepository()
                                            .getListOfProductsInShoppingList()
                                            .get(indexOfTempInShopingList)
                                            .setQuantity(
                                                    String.valueOf(Integer.valueOf(tempObj
                                                            .getQuantity()) - 1));

                                    ((MainActivity) getContext()).updateCheckOutAmount(
                                            BigDecimal.valueOf(Double
                                                    .valueOf(CenterRepository
                                                            .getCenterRepository()
                                                            .getMapOfProductsInCategory()
                                                            .get(subcategoryKey)
                                                            .get(productListNumber)
                                                            .getSellMRP())),
                                            false);

                                    quanitity.setText(CenterRepository
                                            .getCenterRepository()
                                            .getListOfProductsInShoppingList()
                                            .get(indexOfTempInShopingList)
                                            .getQuantity());

                                    Utils.vibrate(getContext());

                                    if (Integer.valueOf(CenterRepository
                                            .getCenterRepository()
                                            .getListOfProductsInShoppingList()
                                            .get(indexOfTempInShopingList)
                                            .getQuantity()) == 0) {

                                        CenterRepository
                                                .getCenterRepository()
                                                .getListOfProductsInShoppingList()
                                                .remove(indexOfTempInShopingList);

                                        ((MainActivity) getContext())
                                                .updateItemCount(false);

                                    }

                                }

                            } else {

                            }

                        }

                    }

                });

        rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                    if (isFromCart) {

                        Utils.switchContent(R.id.frag_container,
                                Utils.SHOPPING_LIST_TAG,
                                ((MainActivity) (getActivity())),
                                Utils.AnimationType.SLIDE_UP);
                    } else {

                        Utils.switchContent(R.id.frag_container,
                                Utils.PRODUCT_OVERVIEW_FRAGMENT_TAG,
                                ((MainActivity) (getActivity())),
                                Utils.AnimationType.SLIDE_RIGHT);
                    }

                }
                return true;
            }
        });

        return rootView;
    }

    public void fillProductData() {

        if (!isFromCart) {


            //Fetch and display item from Gloabl Data Model

            itemName.setText(CenterRepository.getCenterRepository()
                    .getMapOfProductsInCategory().get(subcategoryKey).get(productListNumber)
                    .getItemName());

            quanitity.setText(CenterRepository.getCenterRepository()
                    .getMapOfProductsInCategory().get(subcategoryKey).get(productListNumber)
                    .getQuantity());

            itemdescription.setText(CenterRepository.getCenterRepository()
                    .getMapOfProductsInCategory().get(subcategoryKey).get(productListNumber)
                    .getItemDetail());

            String sellCostString = Money.euro(
                    BigDecimal.valueOf(Double.valueOf(CenterRepository
                            .getCenterRepository().getMapOfProductsInCategory()
                            .get(subcategoryKey).get(productListNumber)
                            .getSellMRP()))).toString()
                    + "  ";

            /*String buyMRP = Money.rupees(
                    BigDecimal.valueOf(Long.valueOf(CenterRepository
                            .getCenterRepository().getMapOfProductsInCategory()
                            .get(subcategoryKey).get(productListNumber)
                            .getMRP()))).toString();*/

            String costString = sellCostString /*+ buyMRP*/;

            itemSellPrice.setText(costString, TextView.BufferType.SPANNABLE);

            Spannable spannable = (Spannable) itemSellPrice.getText();

            spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
                    costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mDrawableBuilder = TextDrawable.builder().beginConfig()
                    .withBorder(4).endConfig().roundRect(10);

            drawable = mDrawableBuilder.build(
                    String.valueOf(CenterRepository.getCenterRepository()
                            .getMapOfProductsInCategory().get(subcategoryKey)
                            .get(productListNumber).getItemName().charAt(0)),
                    mColorGenerator.getColor(CenterRepository
                            .getCenterRepository().getMapOfProductsInCategory()
                            .get(subcategoryKey).get(productListNumber)
                            .getItemName()));


            Glide.with(getActivity()).load(CenterRepository.getCenterRepository().getMapOfProductsInCategory()
                    .get(subcategoryKey).get(productListNumber)
                    .getImageUrl()).placeholder(drawable)
                    .error(drawable).animate(R.anim.base_slide_right_in)
                    .centerCrop().into(itemImage);

            LabelView label = new LabelView(getActivity());

            label.setText(CenterRepository.getCenterRepository().getMapOfProductsInCategory()
                    .get(subcategoryKey).get(productListNumber).getDiscount());
            label.setBackgroundColor(0xffE91E63);

        } else {


            //Fetch and display products from Shopping list

            itemName.setText(CenterRepository.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber).getItemName());

            quanitity.setText(CenterRepository.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber).getQuantity());

            itemdescription.setText(CenterRepository.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber).getItemDetail());

            String sellCostString = Money.euro(
                    BigDecimal.valueOf(Double.valueOf(CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(productListNumber).getSellMRP()))).toString()
                    + "  ";

            /*String buyMRP = Money.rupees(
                    BigDecimal.valueOf(Long.valueOf(CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(productListNumber).getMRP()))).toString();*/

            String costString = sellCostString /*+ buyMRP*/;

            itemSellPrice.setText(costString, TextView.BufferType.SPANNABLE);

            Spannable spannable = (Spannable) itemSellPrice.getText();

            spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
                    costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            mDrawableBuilder = TextDrawable.builder().beginConfig()
                    .withBorder(4).endConfig().roundRect(10);

            drawable = mDrawableBuilder.build(
                    String.valueOf(CenterRepository.getCenterRepository()
                            .getListOfProductsInShoppingList().get(productListNumber)
                            .getItemName().charAt(0)),
                    mColorGenerator.getColor(CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(productListNumber).getItemName()));

            Glide.with(getActivity()).load(CenterRepository.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber)
                    .getImageUrl()).placeholder(drawable)
                    .error(drawable).animate(R.anim.base_slide_right_in)
                    .centerCrop().into(itemImage);

            LabelView label = new LabelView(getActivity());

            label.setText(CenterRepository.getCenterRepository()
                    .getListOfProductsInShoppingList().get(productListNumber).getDiscount());
            label.setBackgroundColor(0xffE91E63);

            label.setTargetView(itemImage, 10, LabelView.Gravity.RIGHT_TOP);

        }
    }


}
