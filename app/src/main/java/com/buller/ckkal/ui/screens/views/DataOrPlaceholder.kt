package com.buller.ckkal.ui.screens.views

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun  DataOrPlaceholder(
    modifier: Modifier = Modifier,
    isNotEmpty: Boolean,
    @DrawableRes icon: Int,
    @StringRes title: Int,
    content: @Composable () -> Unit
) {
    if (isNotEmpty) {
        content()
    } else {
        Box(
            modifier = modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(icon),
                    contentDescription = stringResource(title)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(title),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}