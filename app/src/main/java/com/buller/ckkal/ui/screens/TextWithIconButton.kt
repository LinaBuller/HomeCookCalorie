package com.buller.ckkal.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buller.ckkal.R

@Composable
fun TextWithIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    @StringRes text: Int,
    enabled: Boolean = true,
    buttonColors: ButtonColors = ButtonDefaults.buttonColors(),
    onClick: () -> Unit,

    ) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(16.dp),
        colors = buttonColors,
        enabled = enabled,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = icon,
                contentDescription = stringResource(text),
                tint = LocalContentColor.current
            )
            Text(
                text = stringResource(text),
                fontSize = 14.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = LocalContentColor.current
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextWithIconButtonPreview() {
    TextWithIconButton(
        icon = Icons.Default.Add,
        text = R.string.add,
        enabled = true,
        onClick = {})
}