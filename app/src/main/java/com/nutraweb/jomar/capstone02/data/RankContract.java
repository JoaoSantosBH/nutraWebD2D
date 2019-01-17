package com.nutraweb.jomar.capstone02.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by joaosantos on 17/01/19.
 */

public class RankContract {

    public static final class RankEntry implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.nutraweb.jomar.capstone02C";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH = "ranks";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH)
                .build();

        public static final String TABLE_NAME = "ranks";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH;

       public static final  String COLUMN_RANK_ID = "_id";
        public static final String COLUMN_USER_ID = "user_id";
        public static final String COLUMN_USER_RANK = "user_rank";


        public static Uri buildUri(Long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static final String[] MAIN_RANK_PROJECTION = {
                RankEntry.COLUMN_RANK_ID,
                RankEntry.COLUMN_USER_ID,
                RankEntry.COLUMN_USER_RANK
        };

        public static final int COLUMN_INDEX_RANK_ID = 0;
        public static final int COLUMN_INDEX_USER_ID = 1;
        public static final int COLUMN_INDEX_USER_RANK = 2;
    }
}
