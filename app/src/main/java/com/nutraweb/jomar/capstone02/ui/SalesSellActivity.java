package com.nutraweb.jomar.capstone02.ui;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.adapter.SaleSellAdapter;
import com.nutraweb.jomar.capstone02.data.ProductContract;
import com.nutraweb.jomar.capstone02.data.StockContract;
import com.nutraweb.jomar.capstone02.data.UserContract;
import com.nutraweb.jomar.capstone02.model.ProductEntity;
import com.nutraweb.jomar.capstone02.model.SaleEntity;
import com.nutraweb.jomar.capstone02.model.StockEntity;
import com.nutraweb.jomar.capstone02.model.UserEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SalesSellActivity extends AppCompatActivity implements SaleSellAdapter.MenuAdapterClickHandler{
//aqui

    private SaleSellAdapter saleSellAdapter;
    private List<StockEntity> stockEntities;
    private List<ProductEntity> saleList;
    private SaleEntity sale;
    private GridLayoutManager layoutManager;
    private int totalSale;

    @BindView(R.id.list_stcok_sales_recycler_view)
    RecyclerView mRecyclerView;
    //aqui
    @BindView(R.id.sales_sell_toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_users)
    Spinner spinner;
    @BindView(R.id.totalSale)
    TextView total;

    List<UserEntity> usersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_sellr);
    saleList = new ArrayList<>();
    sale = new SaleEntity();
        ButterKnife.bind(this);
        usersList = getCustomers();
        ArrayAdapter<UserEntity> dataAdapter = new ArrayAdapter<>(this,
		android.R.layout.simple_spinner_item, usersList);
	    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(dataAdapter);
        toolbar.setTitle(R.string.sales_sell_toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(),R.color.colorAccent));

        setSupportActionBar(toolbar);
        layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        saleSellAdapter = new SaleSellAdapter(this);
        mRecyclerView.setAdapter(saleSellAdapter);

        stockEntities =  getItensInStock();
        saleSellAdapter.setList(stockEntities);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.new_sale_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserEntity u = new UserEntity();
                u = (UserEntity) ( (Spinner) findViewById(R.id.spinner_users) ).getSelectedItem();
                Snackbar.make(view, "Replace with your own mumu" + u.get_id() + u.getName() + u.getPhoneNumber(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private List<UserEntity> getCustomers() {
        List<UserEntity> listItens = new ArrayList<>();
         UserEntity u =null;
        Cursor itemCursor = getContentResolver().query(
                UserContract.UserEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (itemCursor != null && itemCursor.moveToFirst()) {
            do {
               u = new UserEntity();
               u.set_id(itemCursor.getInt(UserContract.UserEntry.COLUMN_INDEX_USER_ID));
               u.setName(itemCursor.getString(UserContract.UserEntry.COLUMN_INDEX_USER_NAME));
               u.setPhoneNumber(itemCursor.getInt(UserContract.UserEntry.COLUMN_INDEX_USER_PHONE));
               u.setEmail(itemCursor.getString(UserContract.UserEntry.COLUMN_INDEX_USER_EMAIL));
               listItens.add(u);
            } while (itemCursor.moveToNext());

            itemCursor.close();
            return listItens;
        } else {
            return listItens;
        }

    }

    private List<StockEntity> getItensInStock() {
        List<StockEntity> listItens = new ArrayList<StockEntity>();
        StockEntity s =null;
        Cursor itemCursor = getContentResolver().query(
                StockContract.StockEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (itemCursor != null && itemCursor.moveToFirst()) {
            do {
                s  = new StockEntity();
                s.set_id(itemCursor.getInt(StockContract.StockEntry.COLUMN_INDEX_STOCK_PRODUCT_ID));
                s.setProductId(itemCursor.getString(StockContract.StockEntry.COLUMN_INDEX_STOCK_PRODUCTID));
                s.setProductName(itemCursor.getString(StockContract.StockEntry.COLUMN_INDEX_STOCK_PRODUCT_NAME));
                s.setThumb(itemCursor.getString(StockContract.StockEntry.COLUMN_INDEX_STOCK_THUMB));
                s.setQty(itemCursor.getInt(StockContract.StockEntry.COLUMN_INDEX_STOCK_QTY));
                listItens.add(s);
            } while (itemCursor.moveToNext());

            itemCursor.close();
            return listItens;
        } else {
            return listItens;
        }
    }

    private void decrementStockItem(int id, int qty) {
        ContentValues cv = new ContentValues();
        qty--;
        cv.put(StockContract.StockEntry.COLUMN_STOCK_QTY, qty);


        getContentResolver().update(
            StockContract.StockEntry.CONTENT_URI,
            cv,
            StockContract.StockEntry.COLUMN_STOCK_PRODUCTID + " =? " ,
                new String[]{String.valueOf(id)}


        );

    }
    private String getProductValue(StockEntity item){
        String value= "";
        Cursor itemCursor = getContentResolver().query(
                ProductContract.ProductEntry.CONTENT_URI,
                new String[]{String.valueOf(ProductContract.ProductEntry.COLUMN__PRODUCT_PRICE)},
                ProductContract.ProductEntry.COLUMN_PRODUCT_PRODUCTID + " = ?" ,
                new String[]{String.valueOf(item.getProductId())},
                null);

        if (itemCursor != null ) {
            if (itemCursor.moveToFirst()){
                value =itemCursor.getString(itemCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN__PRODUCT_PRICE));
            }

            itemCursor.close();
            return value;

        } else {
            return value;
        }
    }
    @Override
    public void onClick(StockEntity item) {

            int price = Integer.valueOf(getProductValue(item));
            addItemOnSale(item, price);
//        decrementStockItem(Integer.valueOf(item.getProductId()), item.getQty());
    }

    private void addItemOnSale(StockEntity item, int price) {
        //decrement itemStock qty
            ProductEntity p = new ProductEntity();
            p.setTitulo(item.getProductName().toString());
            p.setValor(price);
            saleList.add(p);
            sale.setItens(saleList);

            totalSale = sumOrderTotal();
            total.setText(String.valueOf(totalSale));
    }



    private int sumOrderTotal(){
        totalSale = 0;
        for (ProductEntity pr : saleList){
            totalSale += pr.getValor();
        }
        return  totalSale;
    }
//    Criar venda com usuário e itens da venda
//    Incrementar contador de compras do usuário
//    Decrementar stock
//    Enviar email para usuario informando da venda bem sucedida

//    public void sendEmail(View view) {
//
//        String emailMessage = getString(R.string.email_message);
//
//        // Use an intent to launch an email app.
//        // Send the order summary in the email body.
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_SUBJECT,
//                getString(R.string.order_summary_email_subject));
//        intent.putExtra(Intent.EXTRA_TEXT, emailMessage);
//
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//
//        }
//    }
}
