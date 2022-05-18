//package edu.sharif.ce.android_weather_app.Model;
//
//import android.content.Context;
//
//import androidx.room.Database;
//import androidx.room.Room;
//import androidx.room.RoomDatabase;
//
//
//@Database(entities = {DataToDB.class}, version = 1, exportSchema = false)
//public abstract class RoomDB extends RoomDatabase {
//
//    public abstract DataDao dataDao();
//
//    private static RoomDB instance;
//
//    public static RoomDB getInstance(Context context) {
//        if (instance != null) {
//            return instance;
//        }
//
//        synchronized (RoomDB.class) {
//            if (instance == null) {
//                instance = Room.databaseBuilder(context.getApplicationContext(),
//                        RoomDB.class,
//                        "cash"
//                ).fallbackToDestructiveMigration()
//                        .build();
//            }
//        }
//        return instance;
//    }
//}
