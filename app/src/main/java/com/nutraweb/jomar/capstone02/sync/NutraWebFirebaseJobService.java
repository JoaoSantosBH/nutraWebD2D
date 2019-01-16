package com.nutraweb.jomar.capstone02.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
/**
 * Created by joaosantos on 14/01/19.
 */

public class NutraWebFirebaseJobService extends JobService {

    private AsyncTask<Void, Void, Void> mFetchProductsTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mFetchProductsTask = new AsyncTask<Void, Void, Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                Context context = getApplicationContext();
                NutraWebSyncTask.syncData(context);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                jobFinished(jobParameters, false);
            }
        };

        mFetchProductsTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mFetchProductsTask != null) {
            mFetchProductsTask.cancel(true);
        }
        return true;
    }
}