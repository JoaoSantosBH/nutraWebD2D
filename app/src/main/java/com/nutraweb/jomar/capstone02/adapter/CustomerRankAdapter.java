package com.nutraweb.jomar.capstone02.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.model.UserEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joaosantos on 19/01/19.
 */

@SuppressWarnings("DefaultFileTemplate")
public class CustomerRankAdapter extends RecyclerView.Adapter<CustomerRankAdapter.MenuViewHolder> {

    private List<UserEntity> list;

    private String SUFIX = " orders";
    private final CustomerRankAdapter.MenuAdapterClickHandler mClickHandler;

    public interface MenuAdapterClickHandler {
        void onClick(UserEntity u);
    }


    public CustomerRankAdapter(CustomerRankAdapter.MenuAdapterClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }


    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.rank_list_user_name)
        TextView name;
        @BindView(R.id.rank_list_user_orders)
        TextView rank;
        @BindView(R.id.rank_sufix_textView)
        TextView sufix;


        MenuViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            UserEntity user = list.get(position);
            mClickHandler.onClick(user);
        }
    }


    @Override
    public CustomerRankAdapter.MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_rank, parent, false);
        return new CustomerRankAdapter.MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomerRankAdapter.MenuViewHolder menuViewHolder, int position) {
        Context context = menuViewHolder.itemView.getContext();

        UserEntity u = list.get(position);
        menuViewHolder.name.setText(u.getName());
        menuViewHolder.rank.setText( String.valueOf(u.getRank()) );
        menuViewHolder.sufix.setText(SUFIX);
        Typeface customFont = Typeface.createFromAsset(context.getAssets(), "fonts/Montserrat-Bold.ttf");

        menuViewHolder.name.setTypeface(customFont);


    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    public void setList(List<UserEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}