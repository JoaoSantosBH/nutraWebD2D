package com.nutraweb.jomar.capstone02.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.model.SaleEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by joaosantos on 21/01/19.
 */

public class SaleHistoryAdapter extends RecyclerView.Adapter<SaleHistoryAdapter.SalesHistViewHolder>{

    private final SaleHistoryAdapter.SalesAdapterClickHandler mClickHandler;
    private List<SaleEntity> list;
    private int total, number;
    private String date;
    public interface SalesAdapterClickHandler{
        void onClick(SaleEntity sale);
    }

    public SaleHistoryAdapter(SaleHistoryAdapter.SalesAdapterClickHandler mClickHandler){
        this.mClickHandler = mClickHandler;
    }


    public class SalesHistViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.hist_sales_order_number)
        TextView orderNumber;
        @BindView(R.id.hist_sales_date_textview)
        TextView orderDate;
        @BindView(R.id.hist_sales_total_textview)
        TextView orderTotal;

        public SalesHistViewHolder(View itemView) {
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
        View view = inflater.inflate(R.layout.content_sales_history, parent,false);

        return new SaleHistoryAdapter.SalesHistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SaleHistoryAdapter.SalesHistViewHolder holder, int position) {

        number = list.get(position).getNumberSale();
        holder.orderNumber.setText(String.valueOf(number));
        date = list.get(position).getDate();
        holder.orderDate.setText(String.valueOf(date));
        total = list.get(position).getTotal();
        holder.orderTotal.setText(String.valueOf(total));


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

