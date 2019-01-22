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
import com.nutraweb.jomar.capstone02.model.StockEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joaosantos on 13/01/19.
 */

public class SaleSellAdapter  extends RecyclerView.Adapter<SaleSellAdapter.MenuViewHolder>{

    private List<StockEntity> list;
    private final SaleSellAdapter.MenuAdapterClickHandler mClickHandler;

    public interface MenuAdapterClickHandler {
        void onClick(StockEntity item);
    }


    public SaleSellAdapter(SaleSellAdapter.MenuAdapterClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }


    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.list_user_stock_sale_thumb)
        ImageView thumb;
        @BindView(R.id.list_user_stock_sale_title)
        TextView title;
        @BindView(R.id.list_user_stock_sale_qty)
        TextView qty;


        MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //thumb.setScaleType(ImageView.ScaleType.);
            thumb.setAdjustViewBounds(true);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            StockEntity item = list.get(position);
            mClickHandler.onClick(item);

        }
    }


    @Override
    public SaleSellAdapter.MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.content_sell_new, parent, false);
        return new SaleSellAdapter.MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SaleSellAdapter.MenuViewHolder menuViewHolder, int position) {
        Context context = menuViewHolder.itemView.getContext();
        StockEntity stockEntity = list.get(position);

        Typeface customFont = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Bold.ttf");
        menuViewHolder.title.setText(stockEntity.getProductName());
        menuViewHolder.title.setTypeface(customFont);
        menuViewHolder.title.setTextSize(12);

        menuViewHolder.qty.setText(String.valueOf(stockEntity.getQty()));
        menuViewHolder.qty.setTypeface(customFont);
        menuViewHolder.qty.setTextSize(12);

        Picasso.with(context)
                .load(stockEntity.getThumb())
                .placeholder(R.mipmap.cap_quest_icon)
                .resize(128,128)
                .error(R.mipmap.cap_error_icon)
                .into(menuViewHolder.thumb);

    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    public void setList(List<StockEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
