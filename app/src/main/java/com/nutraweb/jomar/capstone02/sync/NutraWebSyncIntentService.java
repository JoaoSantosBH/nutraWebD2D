package com.nutraweb.jomar.capstone02.sync;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by joaosantos on 14/01/19.
 */

public class NutraWebSyncIntentService  extends IntentService {

    public NutraWebSyncIntentService() {
        super("NutraWebSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        NutraWebSyncTask.syncData(this);
    }
}