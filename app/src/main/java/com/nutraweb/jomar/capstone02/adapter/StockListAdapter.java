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
 * Created by joaosantos on 10/01/19.
 */

public class StockListAdapter extends RecyclerView.Adapter<StockListAdapter.MenuViewHolder>{

    private List<StockEntity> list;

    private final StockListAdapter.MenuAdapterClickHandler mClickHandler;

    public interface MenuAdapterClickHandler {
        void onClick(StockEntity item);
    }


    public StockListAdapter(StockListAdapter.MenuAdapterClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }


    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.list_user_stock_thumb)
        ImageView thumb;
        @BindView(R.id.list_user_stock_title)
        TextView title;
        @BindView(R.id.list_user_stock_qty)
        TextView qty;


        MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            thumb.setScaleType(ImageView.ScaleType.FIT_CENTER);
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
    public StockListAdapter.MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_itens_user_stock, parent, false);
        return new StockListAdapter.MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StockListAdapter.MenuViewHolder menuViewHolder, int position) {
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
