package com.buller.ckkal.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.buller.ckkal.data.dto.DishDto
import com.buller.ckkal.data.dto.DishWithIngredients
import com.buller.ckkal.data.dto.IngredientDto
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDish(dish: DishDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredient(ingredient: IngredientDto)

    @Transaction
    @Query("SELECT * FROM dishes WHERE id = :dishId")
    fun getDishWithIngredients(dishId: String): Flow<DishWithIngredients?>

    @Transaction
    @Query("SELECT * FROM dishes")
    fun getAllDishesWithIngredients(): Flow<List<DishWithIngredients>>

    @Delete
    suspend fun deleteDish(dish: DishDto)

    @Update
    suspend fun updateDish(dish: DishDto)
}