package com.buller.ckkal.ui.screens.views

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buller.ckkal.R

@Composable
fun LabeledValue(value: String, @StringRes label: Int, styleColor: Color) {
    Text(
        text = value,
        style = TextStyle(fontSize = 26.sp),
        color = styleColor,
        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = stringResource(label),
        color = styleColor,
        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily
    )
}

@Preview(showBackground = true)
@Composable
fun LabeledValuePreview() {
    Column {
        LabeledValue(value = "value", label = R.string.fats, styleColor = Color.Black)
        LabeledValue(value = "value", label = R.string.fats, styleColor = Color.Black)
    }
}