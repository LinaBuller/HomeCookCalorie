package com.buller.ckkal.ui.screens

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.buller.ckkal.R

@Composable
fun <T> DeleteDialog(
    item: T,
    title: String,
    confirmationText: String,
    onConfirm: (T) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = title) },
        confirmButton = {
            Button(onClick = { onConfirm(item) }) {
                Text(text = confirmationText)
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}