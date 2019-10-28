package br.com.charleston.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.com.charleston.data.local.dao.FavoriteDao
import br.com.charleston.data.local.dao.MakeDao
import br.com.charleston.data.local.dao.VehicleDao
import br.com.charleston.data.local.entity.FavoriteEntity
import br.com.charleston.data.local.entity.MakeEntity
import br.com.charleston.data.local.entity.VehicleEntity
import br.com.charleston.data.local.migrations.Migration1to2
import br.com.charleston.data.local.migrations.Migration2to3

@Database(
    version = 3,
    exportSchema = false,
    entities = [
        VehicleEntity::class,
        FavoriteEntity::class,
        MakeEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun vehicleDao(): VehicleDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun makeDao(): MakeDao

    companion object {
        private const val DATABASE_NAME = "charleston_motors.db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME
            )
                .allowMainThreadQueries()
                .addMigrations(
                    Migration1to2(),
                    Migration2to3()
                )
                .build()
    }
}