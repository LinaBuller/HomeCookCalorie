package com.buller.ckkal.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.buller.ckkal.data.DatabaseConstants
import com.buller.ckkal.data.dto.DishDto
import com.buller.ckkal.data.dto.IngredientDto

@Database(
    entities = [DishDto::class, IngredientDto::class], version = DatabaseConstants.DATABASE_VERSION
)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getDao(): DishDao

    companion object {
        fun newInstance(context: Context): LocalDatabase {
            return Room.databaseBuilder(
                context = context, LocalDatabase::class.java, DatabaseConstants.DATABASE_NAME
            ).fallbackToDestructiveMigration()
                .setJournalMode(JournalMode.TRUNCATE)
                .build()
        }
    }
}