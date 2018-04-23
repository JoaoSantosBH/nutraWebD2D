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
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jomar on 21/04/18.
 */

public class CustomerDashBoardAdapter extends RecyclerView.Adapter<CustomerDashBoardAdapter.MenuViewHolder> {


    private static final String ADD_CUSTOMER = "ADD CUSTOMER";
    private static final String LIST_RANK = "RANK LIST";

    private List<String> list;


    private final MenuAdapterClickHandler mClickHandler;

    public interface MenuAdapterClickHandler {
        void onClick(String string);
    }


    public CustomerDashBoardAdapter(MenuAdapterClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }


    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.customer_menu_thumb)
        ImageView thumb;
        @BindView(R.id.customer_menu_title)
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
            String string = list.get(position);
            mClickHandler.onClick(string);
        }
    }


    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_customer_dash_board, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder menuViewHolder, int position) {
        Context context = menuViewHolder.itemView.getContext();
        String name = list.get(position);
        int img;

        switch (name) {
            case ADD_CUSTOMER:
                img = R.mipmap.cap_add_icon;
                break;
            case LIST_RANK:
                img = R.mipmap.cap_rank_icon;
                break;

            default:
                img = R.mipmap.ic_launcher;
        }

        Typeface customFont = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Bold.ttf");
        menuViewHolder.name.setText(name);
        menuViewHolder.name.setTypeface(customFont);

        Picasso.with(context)
                .load(img)
                .placeholder(R.mipmap.cap_quest_icon)
                .error(R.mipmap.cap_error_icon)
                .into(menuViewHolder.thumb);

    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    public void setList(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}