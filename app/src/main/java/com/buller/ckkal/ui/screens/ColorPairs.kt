package com.buller.ckkal.ui.screens

import androidx.compose.ui.graphics.Color
import com.buller.ckkal.ui.theme.BrightViolet
import com.buller.ckkal.ui.theme.DeepViolet
import com.buller.ckkal.ui.theme.LightGreen
import com.buller.ckkal.ui.theme.NeutralBlack
import com.buller.ckkal.ui.theme.SalmonRed
import com.buller.ckkal.ui.theme.YellowD

object ColorPairs {
    private val brightViolet = Pair(Color.White, BrightViolet)
    private val neutralBlack = Pair(Color.White, NeutralBlack)
    private val lightGreen = Pair(NeutralBlack, LightGreen)
    private val salmonRed = Pair(Color.White, SalmonRed)
    private val deepViolet = Pair(Color.White, DeepViolet)
    private val yellowD = Pair(NeutralBlack, YellowD)
    private val listColors = listOf(brightViolet, neutralBlack, lightGreen, salmonRed, deepViolet, yellowD)

    fun getColors(): List<Pair<Color, Color>> {
        return listColors
    }

    fun getCaloriePairColor(): Pair<Color, Color> {
        return lightGreen
    }

    fun getProteinsPairColor(): Pair<Color, Color> {
        return yellowD
    }

    fun getFatsPairColor(): Pair<Color, Color> {
        return salmonRed
    }

    fun getCarbohydratesPairColor(): Pair<Color, Color> {
        return deepViolet
    }

}

