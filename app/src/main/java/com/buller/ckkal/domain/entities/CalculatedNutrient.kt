package com.buller.ckkal.domain.entities

data class CalculatedNutrient(
    val finalKcal: Double = 0.0,
    val finalProteins: Double = 0.0,
    val finalFats: Double = 0.0,
    val finalCarbs: Double = 0.0,
    val totalWeight: Double = 0.0,
)