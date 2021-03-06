package com.nutraweb.jomar.capstone02.ui;

import android.annotation.SuppressLint;
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
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.adapter.SaleSellAdapter;
import com.nutraweb.jomar.capstone02.data.ProductContract;
import com.nutraweb.jomar.capstone02.data.RankContract;
import com.nutraweb.jomar.capstone02.data.SaleContract;
import com.nutraweb.jomar.capstone02.data.StockContract;
import com.nutraweb.jomar.capstone02.data.UserContract;
import com.nutraweb.jomar.capstone02.model.ProductEntity;
import com.nutraweb.jomar.capstone02.model.SaleEntity;
import com.nutraweb.jomar.capstone02.model.StockEntity;
import com.nutraweb.jomar.capstone02.model.UserEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class    SalesSellActivity extends AppCompatActivity implements SaleSellAdapter.MenuAdapterClickHandler {


    private SaleSellAdapter saleSellAdapter;
    private List<StockEntity> stockEntities;
    private List<ProductEntity> saleList;
    private int totalSale;

    @BindView(R.id.list_stcok_sales_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.sales_sell_toolbar)
    Toolbar toolbar;
    @BindView(R.id.spinner_users)
    Spinner spinner;
    @BindView(R.id.totalSale)
    TextView total;
    @BindView(R.id.instruct_sale_textView)
    TextView instruct;
    @BindView(R.id.new_sale_fab)
    FloatingActionButton fab ;
    @BindView(R.id.no_itens)
    TextView isEmpty;
    private List<UserEntity> usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_sellr);
        saleList = new ArrayList<>();
        SaleEntity sale = new SaleEntity();
        ButterKnife.bind(this);
        usersList = getCustomers();
        ArrayAdapter<UserEntity> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, usersList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        toolbar.setTitle(R.string.sales_sell_toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

        setSupportActionBar(toolbar);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        saleSellAdapter = new SaleSellAdapter(this);
        mRecyclerView.setAdapter(saleSellAdapter);
        stockEntities = getItensInStock();
        if (stockEntities.size() ==0){
            isEmpty.setVisibility(View.VISIBLE);
        } else {
            isEmpty.setVisibility(View.GONE);
        }
        saleSellAdapter.setList(stockEntities);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (totalSale > 0){
                    UserEntity u = new UserEntity();
                    u = (UserEntity) ((Spinner) findViewById(R.id.spinner_users)).getSelectedItem();
                    submmitOrder(u);
                    clearSale();
                } else {
                    Snackbar.make(view, R.string.sale_validate, Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
    @Override
    public void onClick(StockEntity item) {

            String qty = getStockQty(item);
            Integer value = Integer.valueOf(qty);
            if (value > 0 ) {
                int price = Integer.valueOf(getProductValue(item));
                addItemOnSale(item, price);
                decrementStockItem(item.get_id(), item.getQty());
                stockEntities = getItensInStock();
                saleSellAdapter.setList(stockEntities);
            } else {
                stockEntities = getItensInStock();
                saleSellAdapter.setList(stockEntities);
                Toast.makeText(this, R.string.no_item_available, Toast.LENGTH_LONG).show();
            }

    }
    private void clearSale(){
        totalSale = 0;
        saleList.clear();
        total.setText(String.valueOf(totalSale));
    }
    private void submmitOrder(UserEntity u) {

        String orderNumber = buyOrderNumber();
        Calendar c = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat s = new SimpleDateFormat("dd-MM-yy");
        String now = s.format(today.getTime());
        SaleEntity sale = new SaleEntity();
        sale.setItens(saleList);
        sale.setQty(1);
        sale.setDate(now);
        sale.setNumberSale(Integer.valueOf(orderNumber));
        sale.setUserId(u.get_id());
        int total = 0;
        for (ProductEntity p : saleList) {
            total += p.getValor();
        }
        sale.setTotal(total);
        sale.getDate();
        for (StockEntity st : stockEntities) {
            int qtyInStock = Integer.valueOf(getStockQty(st));
            if (qtyInStock == 0) {
                deleteItemStock(st.get_id());
            }
        }
        sendEmail(sale);

        createSale(sale);
        userRank(sale.getUserId());
    }


    private void userRank(int userId) {
        UserEntity rankUser = getUser(userId);
        int rank = rankUser.getRank();
        rank++;
        ContentValues valuesProd = new ContentValues();

        valuesProd.put(UserContract.UserEntry.COLUMN_USER_RANK,
                rank);
        getContentResolver().update(
                UserContract.UserEntry.CONTENT_URI,
                valuesProd,
                UserContract.UserEntry.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)}
        );
    }

    private UserEntity getUser(int userId) {
                 UserEntity u = null;
            Cursor itemCursor = getContentResolver().query(
                    UserContract.UserEntry.CONTENT_URI,
                    null,
                    UserContract.UserEntry.COLUMN_USER_ID + " = ?",
                    new String[]{String.valueOf(userId)},
                    null);

            if (itemCursor != null && itemCursor.moveToFirst()) {
                do {
                    u = new UserEntity();
                    u.set_id(itemCursor.getInt(UserContract.UserEntry.COLUMN_INDEX_USER_ID));
                    u.setName(itemCursor.getString(UserContract.UserEntry.COLUMN_INDEX_USER_NAME));
                    u.setPhoneNumber(itemCursor.getInt(UserContract.UserEntry.COLUMN_INDEX_USER_PHONE));
                    u.setEmail(itemCursor.getString(UserContract.UserEntry.COLUMN_INDEX_USER_EMAIL));
                    u.setRank(itemCursor.getInt(UserContract.UserEntry.COLUMN_INDEX_USER_RANK));
                } while (itemCursor.moveToNext());

            }
            itemCursor.close();
            return u;
    }


    private boolean verifyUserRegister(int userId) {
        boolean exist = false;
        Cursor itemCursor = getContentResolver().query(
                RankContract.RankEntry.CONTENT_URI,
                new String[]{String.valueOf(RankContract.RankEntry.COLUMN_USER_ID)},
                RankContract.RankEntry.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)},
                null);

        if (itemCursor != null) {
            if (itemCursor.moveToFirst()) {
                exist = true;
                itemCursor.close();
            }
        }
        return exist;
    }

    private static String buyOrderNumber() {
        Calendar c = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String now = s.format(today.getTime());
        String brokenDate[] = now.split(" ");
        String dateWhitoutHour = brokenDate[0];
        String dataPre[] = dateWhitoutHour.split("-");
        String yS = dataPre[0];
        String mS = dataPre[1];
        String dS = dataPre[2];
        String result = yS + mS + dS +  today.get(Calendar.MINUTE);
        return result;
    }

    private List<UserEntity> getCustomers() {
        List<UserEntity> listItens = new ArrayList<>();
        UserEntity u = null;
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
        }
        itemCursor.close();
        return listItens;
    }
    private ProductEntity getProductWihtItemProductId(String id) {
        ProductEntity p = null;
        Cursor itemCursor = getContentResolver().query(
                ProductContract.ProductEntry.CONTENT_URI,
                ProductContract.ProductEntry.MAIN_PRODUCT_PROJECTION,
                ProductContract.ProductEntry.COLUMN_PRODUCT_PRODUCTID + " = ?",
                new String[]{String.valueOf(id)},
                null);

        if (itemCursor != null && itemCursor.moveToFirst()) {
            do {
                p = new ProductEntity();
                p.setProductid(itemCursor.getString(ProductContract.ProductEntry.COLUMN_INDEX_PRODUCT_PRODUCTID));
                p.setTitulo(itemCursor.getString(ProductContract.ProductEntry.COLUMN_INDEX_PRODUCT_TITLE));
                p.setDescricao(itemCursor.getString(ProductContract.ProductEntry.COLUMN_INDEX_PRODUCT_DESCRIPTION));
                p.setUrl(itemCursor.getString(ProductContract.ProductEntry.COLUMN_INDEX_PRODUCT_THUMB));
                p.setValor(itemCursor.getInt(ProductContract.ProductEntry.COLUMN_INDEX_PRODUCT_PRICE));

            } while (itemCursor.moveToNext());
        }
        itemCursor.close();
        return p;
    }

    private ProductEntity getProduct(int id) {
        ProductEntity p;
        Cursor itemCursor = getContentResolver().query(
                ProductContract.ProductEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (itemCursor != null && itemCursor.moveToFirst()) {
            do {
                p = new ProductEntity();
                p.setProductid(itemCursor.getString(ProductContract.ProductEntry.COLUMN_INDEX_PRODUCT_PRODUCTID));
                p.setTitulo(itemCursor.getString(ProductContract.ProductEntry.COLUMN_INDEX_PRODUCT_TITLE));
                p.setDescricao(itemCursor.getString(ProductContract.ProductEntry.COLUMN_INDEX_PRODUCT_DESCRIPTION));
                p.setUrl(itemCursor.getString(ProductContract.ProductEntry.COLUMN_INDEX_PRODUCT_THUMB));
                p.setValor(itemCursor.getInt(ProductContract.ProductEntry.COLUMN_INDEX_PRODUCT_PRICE));

            } while (itemCursor.moveToNext());
            itemCursor.close();
            return p;
        }
        return null; //ToDo
    }

    private List<StockEntity> getItensInStock() {
        List<StockEntity> listItens = new ArrayList<>();
        StockEntity s = null;
        Cursor itemCursor = getContentResolver().query(
                StockContract.StockEntry.CONTENT_URI,
                null,
                null,
                null,
                null);

        if (itemCursor != null && itemCursor.moveToFirst()) {
            do {
                s = new StockEntity();
                s.set_id(itemCursor.getInt(StockContract.StockEntry.COLUMN_INDEX_STOCK_PRODUCT_ID));
                s.setProductId(itemCursor.getString(StockContract.StockEntry.COLUMN_INDEX_STOCK_PRODUCTID));
                s.setProductName(itemCursor.getString(StockContract.StockEntry.COLUMN_INDEX_STOCK_PRODUCT_NAME));
                s.setThumb(itemCursor.getString(StockContract.StockEntry.COLUMN_INDEX_STOCK_THUMB));
                s.setQty(itemCursor.getInt(StockContract.StockEntry.COLUMN_INDEX_STOCK_QTY));
                listItens.add(s);
            } while (itemCursor.moveToNext());
        }
        itemCursor.close();
        return listItens;
    }

    private void decrementStockItem(int id, int qty) {
        ContentValues cv = new ContentValues();
        qty--;
        cv.put(StockContract.StockEntry.COLUMN_STOCK_QTY, qty);

        getContentResolver().update(
                StockContract.StockEntry.CONTENT_URI,
                cv,
                StockContract.StockEntry.COLUMN_STOCK_ID + " =? ",
                new String[]{String.valueOf(id)}
        );
    }

    private void deleteItemStock(int id) {
        ContentValues cv = new ContentValues();
        cv.put(StockContract.StockEntry.COLUMN_STOCK_ID, id);
        getContentResolver().delete(
                StockContract.StockEntry.CONTENT_URI,
                StockContract.StockEntry.COLUMN_STOCK_ID + " =? ",
                new String[]{String.valueOf(id)}
        );
    }

    private String getStockQty(StockEntity item) {
        String value = "";
        Cursor itemCursor = getContentResolver().query(
                StockContract.StockEntry.CONTENT_URI,
                new String[]{String.valueOf(StockContract.StockEntry.COLUMN_STOCK_QTY)},
                StockContract.StockEntry.COLUMN_STOCK_ID + " = ?",
                new String[]{String.valueOf(item.get_id())},
                null);

        if (itemCursor != null) {
            if (itemCursor.moveToFirst()) {
                value = itemCursor.getString(itemCursor.getColumnIndex(StockContract.StockEntry.COLUMN_STOCK_QTY));
            }
        }
        itemCursor.close();
        if (value.equals("")){
            value = "0";
        }
        return value;
    }

    private String getProductValue(StockEntity item) {
        String value = "";
        Cursor itemCursor = getContentResolver().query(
                ProductContract.ProductEntry.CONTENT_URI,
                new String[]{String.valueOf(ProductContract.ProductEntry.COLUMN__PRODUCT_PRICE)},
                ProductContract.ProductEntry.COLUMN_PRODUCT_PRODUCTID + " = ?",
                new String[]{String.valueOf(item.getProductId())},
                null);

        if (itemCursor != null) {
            if (itemCursor.moveToFirst()) {
                value = itemCursor.getString(itemCursor.getColumnIndex(ProductContract.ProductEntry.COLUMN__PRODUCT_PRICE));
            }

        }
        itemCursor.close();
        return value;
    }




    private void addItemOnSale(StockEntity item, int price) {
        //decrement itemStock qty
        ProductEntity p = getProductWihtItemProductId(item.getProductId());
        saleList.add(p);
        totalSale = sumOrderTotal();
        total.setText(String.valueOf(totalSale));
    }


    private int sumOrderTotal() {
        totalSale = 0;
        for (ProductEntity pr : saleList) {
            totalSale += pr.getValor();
        }
        return totalSale;
    }

    private String getUserEmail(int userId) {
        String value = "";
        Cursor itemCursor = getContentResolver().query(
                UserContract.UserEntry.CONTENT_URI,
                new String[]{String.valueOf(UserContract.UserEntry.COLUMN_USER_EMAIL)},
                UserContract.UserEntry.COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(userId)},
                null);

        if (itemCursor != null) {
            if (itemCursor.moveToFirst()) {
                value = itemCursor.getString(itemCursor.getColumnIndex(UserContract.UserEntry.COLUMN_USER_EMAIL));
            }

            itemCursor.close();


        }
        return value;
    }
    private void createSale(SaleEntity sale){

        ContentValues valuesProd = new ContentValues();
        valuesProd.put(SaleContract.SaleEntry.COLUMN_SALE_QTY, sale.getQty());
        valuesProd.put(SaleContract.SaleEntry.COLUMN_SALE_TOTAL, sale.getTotal());
        valuesProd.put(SaleContract.SaleEntry.COLUMN_SALE_NUMBER, sale.getNumberSale());
        valuesProd.put(SaleContract.SaleEntry.COLUMN_SALE_USER_ID,sale.getUserId());
        valuesProd.put(SaleContract.SaleEntry.COLUMN_SALE_DATE,sale.getDate());
        getContentResolver().insert(
                SaleContract.SaleEntry.CONTENT_URI,
                valuesProd);
    }
    private void sendEmail(SaleEntity sale) {
        String email = getUserEmail(sale.getUserId());
        String subject = getString(R.string.email_subject);
        subject += " " + sale.getNumberSale();
        StringBuilder emailMessage = new StringBuilder();
        String total = String.valueOf(sale.getTotal());

        for (ProductEntity p : sale.getItens()) {
            emailMessage.append(p.getTitulo()).append(getString(R.string.email_format));
            emailMessage.append(p.getValor()).append("\n");
        }

        emailMessage.append(getString(R.string.email_total)).append(total);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setType("message/rfc822");
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email} );
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, emailMessage.toString());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);

        }
    }


}
