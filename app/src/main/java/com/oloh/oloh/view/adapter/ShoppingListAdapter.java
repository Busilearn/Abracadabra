package com.oloh.oloh.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.oloh.oloh.R;
import com.oloh.oloh.view.activities.MainActivity;
import com.oloh.oloh.view.customview.ItemTouchHelperViewHolder;
import com.oloh.oloh.view.customview.ItemTouchHelperAdapter;
import com.oloh.oloh.view.customview.OnStartDragListener;
import com.oloh.oloh.view.customview.TextDrawable;
import com.oloh.oloh.view.fragment.MyCartFragment;
import com.oloh.oloh.model.CenterRepository;
import com.oloh.oloh.model.entities.Money;
import com.oloh.oloh.model.entities.Products;
import com.oloh.oloh.util.ColorGenerator;
import com.oloh.oloh.util.Utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.oloh.oloh.R.id.remove_item;

/**
 * Created by stran on 29/08/2017.
 */

public class ShoppingListAdapter extends
        RecyclerView.Adapter<ShoppingListAdapter.ItemViewHolder> implements
        ItemTouchHelperAdapter {

    private static OnItemClickListener clickListener;
    private final OnStartDragListener mDragStartListener;
    private ColorGenerator mColorGenerator = ColorGenerator.MATERIAL;
    private TextDrawable.IBuilder mDrawableBuilder;
    private TextDrawable drawable;
    private String ImageUrl;
    private List<Products> productsList = new ArrayList<Products>();
    private Context context;

    public ShoppingListAdapter(Context context,
                               OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;

        this.context = context;

        productsList = CenterRepository.getCenterRepository().getListOfProductsInShoppingList();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_product_list, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder holder, final int position) {
        holder.itemName.setText(productsList.get(position).getItemName());

        holder.itemDesc.setText(productsList.get(position).getItemShortDesc());

        String sellCostString = Money.euro(
                BigDecimal.valueOf(Double.valueOf(productsList.get(position)
                        .getSellMRP()))).toString()
                + "  ";

        /*String buyMRP = Money.rupees(
                BigDecimal.valueOf(Long.valueOf(productsList.get(position)
                        .getMRP()))).toString();*/

        String costString = sellCostString /*+ buyMRP*/;

        holder.itemCost.setText(costString, TextView.BufferType.SPANNABLE);

        Spannable spannable = (Spannable) holder.itemCost.getText();

        spannable.setSpan(new StrikethroughSpan(), sellCostString.length(),
                costString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mDrawableBuilder = TextDrawable.builder().beginConfig().withBorder(4)
                .endConfig().roundRect(10);

        drawable = mDrawableBuilder.build(String.valueOf(productsList
                .get(position).getItemName().charAt(0)), mColorGenerator
                .getColor(productsList.get(position).getItemName()));

        ImageUrl = productsList.get(position).getImageUrl();

        holder.quanitity.setText(CenterRepository.getCenterRepository()
                .getListOfProductsInShoppingList().get(position).getQuantity());

        Glide.with(context).load(ImageUrl).placeholder(drawable)
                .error(drawable).animate(R.anim.base_slide_right_in)
                .centerCrop().into(holder.imagView);

        // Start a drag whenever the handle view it touched
        holder.imagView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });

        holder.addItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                CenterRepository
                        .getCenterRepository()
                        .getListOfProductsInShoppingList()
                        .get(position)
                        .setQuantity(
                                String.valueOf(

                                        Integer.valueOf(CenterRepository
                                                .getCenterRepository().getListOfProductsInShoppingList()
                                                .get(position).getQuantity()) + 1));

                holder.quanitity.setText(CenterRepository.getCenterRepository()
                        .getListOfProductsInShoppingList().get(position).getQuantity());

                Utils.vibrate(context);

                ((MainActivity) context).updateCheckOutAmount(
                        BigDecimal.valueOf(Double.valueOf(CenterRepository
                                .getCenterRepository().getListOfProductsInShoppingList()
                                .get(position).getSellMRP())), true);

            }
        });

        holder.removeItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (Integer.valueOf(CenterRepository.getCenterRepository()
                        .getListOfProductsInShoppingList().get(position).getQuantity()) > 2) {

                    CenterRepository
                            .getCenterRepository()
                            .getListOfProductsInShoppingList()
                            .get(position)
                            .setQuantity(
                                    String.valueOf(

                                            Integer.valueOf(CenterRepository
                                                    .getCenterRepository()
                                                    .getListOfProductsInShoppingList().get(position)
                                                    .getQuantity()) - 1));

                    holder.quanitity.setText(CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(position).getQuantity());

                    ((MainActivity) context).updateCheckOutAmount(
                            BigDecimal.valueOf(Double.valueOf(CenterRepository
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(position).getSellMRP())), false);

                    Utils.vibrate(context);
                } else if (Integer.valueOf(CenterRepository.getCenterRepository()
                        .getListOfProductsInShoppingList().get(position).getQuantity()) == 1) {
                    ((MainActivity) context).updateItemCount(false);

                    ((MainActivity) context).updateCheckOutAmount(
                            BigDecimal.valueOf(Double.valueOf(CenterRepository
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(position).getSellMRP())), false);

                    CenterRepository.getCenterRepository().getListOfProductsInShoppingList()
                            .remove(position);

                    notifyItemRemoved(position);

                    if (((MainActivity) context)
                            .getItemCount() == 0) {
                        MyCartFragment.updateMyCartFragment(false);

                    }

                    Utils.vibrate(context);

                } else if (Integer.valueOf(CenterRepository.getCenterRepository()
                        .getListOfProductsInShoppingList().get(position).getQuantity()) == 2) {

                    CenterRepository
                            .getCenterRepository()
                            .getListOfProductsInShoppingList()
                            .get(position)
                            .setQuantity(
                                    String.valueOf(

                                            Integer.valueOf(CenterRepository
                                                    .getCenterRepository()
                                                    .getListOfProductsInShoppingList().get(position)
                                                    .getQuantity()) - 1));

                    holder.quanitity.setText(CenterRepository
                            .getCenterRepository().getListOfProductsInShoppingList()
                            .get(position).getQuantity());

                    ((MainActivity) context).updateCheckOutAmount(
                            BigDecimal.valueOf(Double.valueOf(CenterRepository
                                    .getCenterRepository().getListOfProductsInShoppingList()
                                    .get(position).getSellMRP())), false);

                    Utils.vibrate(context);
                }

            }
        });
    }



    @Override
    public void onItemDismiss(int position) {

        ((MainActivity) context).updateItemCount(false);

        ((MainActivity) context).updateCheckOutAmount(
                BigDecimal.valueOf(Double.valueOf(CenterRepository
                        .getCenterRepository().getListOfProductsInShoppingList().get(position)
                        .getSellMRP())), false);

        CenterRepository.getCenterRepository().getListOfProductsInShoppingList().remove(position);

        if (Integer.valueOf(((MainActivity) context).getItemCount()) == 0) {

            MyCartFragment.updateMyCartFragment(false);

        }

        Utils.vibrate(context);

        productsList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {

        Collections.swap(productsList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return productsList.size();

    }

    public void SetOnItemClickListener(
            final OnItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    /**
     * Simple example of a view holder that implements
     * {@link ItemTouchHelperViewHolder} and has a "handle" view that initiates
     * a drag event when touched.
     */
    public static class ItemViewHolder extends RecyclerView.ViewHolder
            implements ItemTouchHelperViewHolder, View.OnClickListener {

        // public final ImageView handleView;

        TextView itemName, itemDesc, itemCost, availability, quanitity,
                addItem, removeItem;
        ImageView imagView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            // handleView = (ImageView) itemView.findViewById(R.id.handle);

            itemName = (TextView) itemView.findViewById(R.id.item_name);

            itemDesc = (TextView) itemView.findViewById(R.id.item_short_desc);

            itemCost = (TextView) itemView.findViewById(R.id.item_price);

            availability = (TextView) itemView
                    .findViewById(R.id.iteam_avilable);

            quanitity = (TextView) itemView.findViewById(R.id.iteam_amount);

            itemName.setSelected(true);

            imagView = ((ImageView) itemView.findViewById(R.id.product_thumb));

            addItem = ((TextView) itemView.findViewById(R.id.add_item));

            removeItem = ((TextView) itemView.findViewById(remove_item));

            itemView.setOnClickListener(this);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }

        @Override
        public void onClick(View v) {

            clickListener.onItemClick(v, getPosition());
        }
    }
}
