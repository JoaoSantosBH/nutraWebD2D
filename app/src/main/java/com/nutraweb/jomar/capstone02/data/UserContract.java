package com.nutraweb.jomar.capstone02.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import com.nutraweb.jomar.capstone02.model.UserEntity;

/**
 * Created by jomar on 28/04/18.
 */

public class UserContract {


    public static final class UserEntry implements BaseColumns {

        public static final String CONTENT_AUTHORITY = "com.nutraweb.jomar.capstone02a";
        public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
        public static final String PATH_USERS = "users";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_USERS)
                .build();

        public static final String TABLE_NAME = "users";
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USERS;

        public static Uri buildUsrUri(Long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
        public static final String COLUMN_USER_ID = "_id";
        public static final String COLUMN_USER_NAME = "name";
        public static final String COLUMN_USER_EMAIL = "email";
        public static final String COLUMN_USER_PHONE = "phone";
        public static final String COLUMN_USER_RANK = "rank";


        public static final String[] MAIN_USER_PROJECTION = {
                UserEntry.COLUMN_USER_ID,
                UserEntry.COLUMN_USER_NAME,
                UserEntry.COLUMN_USER_EMAIL,
                UserEntry.COLUMN_USER_PHONE,
                UserEntry.COLUMN_USER_RANK
        };
        public static final int COLUMN_INDEX_USER_ID = 0;
        public static final int COLUMN_INDEX_USER_NAME = 1;
        public static final int COLUMN_INDEX_USER_EMAIL = 2;
        public static final int COLUMN_INDEX_USER_PHONE = 3;
        public static final int COLUMN_INDEX_USER_RANK = 4;



    }
}
