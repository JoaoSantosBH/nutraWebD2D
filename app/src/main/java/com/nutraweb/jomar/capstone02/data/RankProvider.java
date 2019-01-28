package com.nutraweb.jomar.capstone02.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by joaosantos on 17/01/19.
 */

public class RankProvider extends ContentProvider {
    private static final int ITEM = 0;
    private static final int ID = 1;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private NutraWebDbHelper mOpenDbHelper;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authorithy = RankContract.RankEntry.CONTENT_AUTHORITY;
        matcher.addURI(authorithy, RankContract.RankEntry.PATH, ITEM);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenDbHelper = new NutraWebDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenDbHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)){

            case ITEM:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values){
                        long _id = db.insert(RankContract.RankEntry.TABLE_NAME, null,value);
                        if (_id != -1){
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                }finally {
                    db.endTransaction();
                }
                if (rowsInserted>0){
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return rowsInserted;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {

            case ITEM: {
                cursor = mOpenDbHelper.getReadableDatabase().query(
                        RankContract.RankEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null, null,
                        sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknow uri" + uri);

        }
        if (null != getContext())cursor
                .setNotificationUri(getContext()
                        .getContentResolver(), uri);

        return cursor;
    }
    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
        if (null == selection) selection = "1";

        switch(sUriMatcher.match(uri)){

            case ITEM:
                numRowsDeleted = mOpenDbHelper.getWritableDatabase().delete(
                        RankContract.RankEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknow uri" + uri);
        }

        if (numRowsDeleted != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;
    }
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (sUriMatcher.match(uri)){
            case ITEM:
                return RankContract.RankEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mOpenDbHelper.getWritableDatabase();
        Uri returnedUri;

        switch (sUriMatcher.match(uri)){
            case ITEM:
                long _id = db.insert(RankContract.RankEntry.TABLE_NAME, null,values);
                if (_id>0){
                    returnedUri = RankContract.RankEntry.buildUri(_id);
                } else {
                    throw new SQLException("Failed to insert row nto " + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknoown uri: " + uri);
        }

        return null;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsUpdated;
        switch (sUriMatcher.match(uri)){
            case ID:
                numRowsUpdated = mOpenDbHelper.getWritableDatabase().update(
                        RankContract.RankEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown Uri" + uri);
        }
        if (numRowsUpdated != 0 && getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsUpdated;
    }
    @Override
    @TargetApi(11)
    public void shutdown(){
        mOpenDbHelper.close();
        super.shutdown();
    }
}
