package com.csming.percent.data;

import android.content.Context;

import com.csming.percent.data.dao.PlanDao;
import com.csming.percent.data.vo.Plan;
import com.csming.percent.data.vo.Record;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Plan.class, Record.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

   public static AppDatabase getDefault(Context context) {
      return buildDatabase(context);
   }

   private static AppDatabase buildDatabase(Context context) {
      return Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "hundredpercent.db")
              .allowMainThreadQueries()
              .build();
   }

   public abstract PlanDao getPlanDao();

   public abstract PlanDao getRecordDao();
   
}