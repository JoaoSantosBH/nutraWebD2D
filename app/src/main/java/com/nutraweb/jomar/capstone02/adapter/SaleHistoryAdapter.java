package com.nutraweb.jomar.capstone02.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.model.ProductEntity;
import com.nutraweb.jomar.capstone02.model.SaleEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joaosantos on 21/01/19.
 */

public class SaleHistoryAdapter extends RecyclerView.Adapter<SaleHistoryAdapter.SalesHistViewHolder>{

    private final SaleHistoryAdapter.SalesAdapterClickHandler mClickHandler;
    private List<SaleEntity> list;

    public interface SalesAdapterClickHandler{
        void onClick(SaleEntity sale);
    }

    public SalesAdapter(SaleHistoryAdapter.SalesAdapterClickHandler mClickHandler){
        this.mClickHandler = mClickHandler;
    }


    public class SalesHistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.)
        ImageView thumb;
        @BindView(R.id.)
        TextView title;

        public SaleHistViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            mClickHandler.onClick(list.get(position));
        }
    }
    @Override
    public SaleHistoryAdapter.SalesHistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout., parent,false);

        return new SaleHistoryAdapter.SalesHistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SaleHistoryAdapter.SalesHistViewHolder holder, int position) {

        Context context = holder.itemView.getContext();
        String titulo = list.get(position).getTitulo();
        String imagePath = list.get(position).getUrl();
        holder.title.setText(titulo);

        Picasso.with(context)
                .load(imagePath)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.thumb);
    }

    @Override
    public int getItemCount() {
        if (list ==null) return 0;
        return list.size();
    }

    public void setList(List<SaleEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }

}

