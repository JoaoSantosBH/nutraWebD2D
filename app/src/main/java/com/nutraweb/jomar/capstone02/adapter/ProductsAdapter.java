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
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jomar on 10/04/18.
 */

public class ProductsAdapter  extends RecyclerView.Adapter<ProductsAdapter.ProductViewHolder>{

    private final ProductAdapterClickHandler mClickHandler;
    private List<ProductEntity> list;

    public interface ProductAdapterClickHandler{
        void onClick(ProductEntity product);
    }

    public ProductsAdapter(ProductAdapterClickHandler mClickHandler){
        this.mClickHandler = mClickHandler;
    }


    public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.product_thumbnail)
        ImageView thumb;
        @BindView(R.id.product_title)
        TextView title;

        public ProductViewHolder(View itemView) {
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
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_new_order, parent,false);

        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {

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

    public void setList(List<ProductEntity> list){
        this.list = list;
        notifyDataSetChanged();
    }

}
