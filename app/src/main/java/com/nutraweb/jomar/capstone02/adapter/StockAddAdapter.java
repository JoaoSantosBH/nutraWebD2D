package com.nutraweb.jomar.capstone02.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.model.ProductEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jomar on 24/04/18.
 */

public class StockAddAdapter extends RecyclerView.Adapter<StockAddAdapter.MenuViewHolder>{


    private List<ProductEntity> list;

    private final StockAddAdapter.MenuAdapterClickHandler mClickHandler;

    public interface MenuAdapterClickHandler {
        void onClick(ProductEntity product);
    }


    public StockAddAdapter(StockAddAdapter.MenuAdapterClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }


    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.add_stock_menu_thumb)
        ImageView thumb;
        @BindView(R.id.add_stock_menu_title)
        TextView name;


        MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            thumb.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            thumb.setAdjustViewBounds(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            ProductEntity product = list.get(position);
            mClickHandler.onClick(product);
        }
    }


    @Override
    public StockAddAdapter.MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_add_stock, parent, false);
        return new StockAddAdapter.MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StockAddAdapter.MenuViewHolder menuViewHolder, int position) {
        Context context = menuViewHolder.itemView.getContext();
        ProductEntity productEntity = list.get(position);

        Typeface customFont = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Bold.ttf");
        menuViewHolder.name.setText(productEntity.getTitulo());
        menuViewHolder.name.setTypeface(customFont);
        menuViewHolder.name.setTextSize(12);


        Picasso.with(context)
                .load(productEntity.getUrl())
                .placeholder(R.mipmap.cap_quest_icon)
                .error(R.mipmap.cap_error_icon)
                .into(menuViewHolder.thumb);

    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    public void setList(List<ProductEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
