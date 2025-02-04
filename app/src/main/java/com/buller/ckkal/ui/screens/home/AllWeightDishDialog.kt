package com.buller.ckkal.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.buller.ckkal.ui.screens.CustomOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllWeightDishDialog(onDismiss: () -> Unit, onAddAllWeight: (Double) -> Unit) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ) {
        WeightForm(onDismiss = onDismiss, onAddAllWeight = onAddAllWeight)
    }
}

@Composable
fun WeightForm(onDismiss: () -> Unit, onAddAllWeight: (Double) -> Unit) {
    var weight by remember { mutableStateOf("") }
    val focusRequesterWeight = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequesterWeight.requestFocus()
        keyboardController?.show()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth().weight(1f), contentAlignment = Alignment.Center) {
            CustomOutlinedTextField(
                value = weight,
                onValueChange = { weight = it },
                label = "All weight",
                modifier = Modifier
                    .padding(16.dp)
                    .focusRequester(focusRequesterWeight),
                isNumeric = true,
                maxLength = 6,
                onShowError = {

                },
                onNext = {
                    if (weight.isNotEmpty() && weight.all { it.isDigit() || it == '.' }) {
                        onAddAllWeight.invoke(weight.toDouble())
                    } else {
                        //TODO Do something if weight not correct
                    }
                    onDismiss.invoke()
                }
            )
        }
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp),
                contentPadding = PaddingValues(16.dp),
                onClick = onDismiss
            ) {
                Text("Cancel")
            }
            Button(
                modifier = Modifier
                    .weight(1f),
                contentPadding = PaddingValues(16.dp),
                onClick = {
                    if (weight.isNotEmpty() && weight.all { it.isDigit() || it == '.' }) {
                        onAddAllWeight.invoke(weight.toDouble())
                    } else {
                        //TODO Do something if weight not correct
                    }
                    onDismiss.invoke()
                },
                enabled = weight.isNotBlank()
            ) {
                Text("Calculate")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AllWeightDishDialogPreview() {
    WeightForm(onDismiss = {}, onAddAllWeight = {})
}