package com.buller.ckkal.ui.screens.states

data class DishState(
    val id: String = "",
    val finalKcal: Double = 0.0,
    val finalProteins: Double = 0.0,
    val finalFats: Double = 0.0,
    val finalCarbs: Double = 0.0,
    val totalWeight: Double = 0.0,
    val name:String = ""
)