package com.buller.ckkal.data.room

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.buller.ckkal.data.DatabaseConstants
import com.buller.ckkal.data.dto.DishDto
import com.buller.ckkal.data.dto.IngredientDto

@Database(
    entities = [DishDto::class, IngredientDto::class],
    version = DatabaseConstants.DATABASE_VERSION,
    autoMigrations = [AutoMigration(from = 2, to = 3)],
    exportSchema = true
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getDao(): DishDao

    companion object {
        fun newInstance(context: Context): LocalDatabase {
            return Room
                .databaseBuilder(
                    context = context,
                    klass = LocalDatabase::class.java,
                    name = DatabaseConstants.DATABASE_NAME)
                .setJournalMode(JournalMode.TRUNCATE)
                .build()
        }
    }
}
