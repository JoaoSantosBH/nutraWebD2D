package com.nutraweb.jomar.capstone02.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
/**
 * Created by joaosantos on 14/01/19.
 */

public class NutraWebFirebaseJobService extends JobService {

    //  COMPLETED (4) Declare an ASyncTask field called mFetchProductsTask
    private AsyncTask<Void, Void, Void> mFetchProductsTask;

//  COMPLETED (5) Override onStartJob and within it, spawn off a separate ASyncTask to sync weather data
    /**
     * The entry point to your Job. Implementations should offload work to another thread of
     * execution as soon as possible.
     *
     * This is called by the Job Dispatcher to tell us we should start our job. Keep in mind this
     * method is run on the application's main thread, so we need to offload work to a background
     * thread.
     *
     * @return whether there is more work remaining.
     */
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