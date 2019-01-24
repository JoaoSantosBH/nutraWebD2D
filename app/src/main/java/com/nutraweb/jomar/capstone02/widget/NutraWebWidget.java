package com.nutraweb.jomar.capstone02.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;

import com.nutraweb.jomar.capstone02.R;
import com.nutraweb.jomar.capstone02.data.SaleContract;
import com.nutraweb.jomar.capstone02.data.UserContract;
import com.nutraweb.jomar.capstone02.ui.SalesHistoryActivity;

/**
 * Implementation of App Widget functionality.
 */
public class NutraWebWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        int total = 0;
        Cursor itemCursor = context.getContentResolver().query(

                SaleContract.SaleEntry.CONTENT_URI,
                new String[]{"COUNT(" + SaleContract.SaleEntry.COLUMN_SALE_ID+ ")"},
                null,
                null,
                null);


        if (itemCursor != null) {
            if (itemCursor.moveToFirst()) {
                total = itemCursor.getInt(0);
            }

            itemCursor.close();

        }
        CharSequence tot = String.valueOf(total);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.nutra_web_widget);
        views.setTextViewText(R.id.appwidget_text_qty,tot );

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);

            Intent intent = new Intent(context, SalesHistoryActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.nutra_web_widget);
            views.setOnClickPendingIntent(R.id.appwidget_text_qty, pendingIntent);

            // Tell the AppWidgetManager to perform an update on the current app widget

            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

