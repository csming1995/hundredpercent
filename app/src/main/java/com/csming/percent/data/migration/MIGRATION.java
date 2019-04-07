package com.csming.percent.data.migration;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @author Created by csming on 2019/4/7.
 */
public class MIGRATION {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE plans ADD COLUMN last_update INTEGER DEFAULT 0");
        }
    };
}
