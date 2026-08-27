package com.buller.ckkal.ui.screens.views

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buller.ckkal.R
import com.buller.ckkal.ui.screens.TextWithIconButton
import com.buller.ckkal.ui.theme.CKkalTheme

const val LEFT_BUTTON = 1
const val RIGHT_BUTTON = 2

@Composable
fun DualButtonPanel(
    modifier: Modifier = Modifier,
    enabledLeftButton: Boolean = true,
    enabledRightButton: Boolean = true,
    leftButtonIcon: ImageVector,
    @StringRes leftButtonText: Int,
    rightButtonIcon: ImageVector,
    @StringRes rightButtonText: Int,
    onClickButton: (Int) -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextWithIconButton(
            modifier = modifier.weight(1f),
            buttonColors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ,
            icon = leftButtonIcon,
            text = leftButtonText,
            enabled = enabledLeftButton
        )
        {
            onClickButton.invoke(LEFT_BUTTON)
        }

        TextWithIconButton(
            modifier = modifier.weight(1f),
            buttonColors =
                ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ,
            icon = rightButtonIcon,
            text = rightButtonText,
            enabled = enabledRightButton,
        ) {
            onClickButton.invoke(RIGHT_BUTTON)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DualButtonPanelPreview() {
    CKkalTheme() {
        DualButtonPanel(
            leftButtonIcon = Icons.Default.Add,
            leftButtonText = R.string.add,
            rightButtonIcon = Icons.Default.Check,
            rightButtonText = R.string.next_step
        ) { }

    }
}