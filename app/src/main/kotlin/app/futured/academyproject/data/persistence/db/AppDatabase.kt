package app.futured.academyproject.data.persistence.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import app.futured.academyproject.data.model.local.CulturalPlace
import app.futured.academyproject.data.model.local.Event
import app.futured.academyproject.data.model.local.TouristPlace
import app.futured.academyproject.data.persistence.db.culture.CulturalPlaceDao
import app.futured.academyproject.data.persistence.db.event.EventDao
import app.futured.academyproject.data.persistence.db.tourism.TouristPlaceDao

/**
 * Database class with a singleton Instance object.
 */
@Database(entities = [CulturalPlace::class, TouristPlace::class, Event::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun culturalPlaceDao(): CulturalPlaceDao
    abstract fun touristPlaceDao(): TouristPlaceDao
    abstract fun eventDao(): EventDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}