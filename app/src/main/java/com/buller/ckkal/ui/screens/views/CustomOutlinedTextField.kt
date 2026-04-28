package com.buller.ckkal.ui.screens.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.buller.ckkal.domain.utils.StringUtil
import com.buller.ckkal.ui.theme.LightRed

//TODO change this file, I need one textField

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLength: Int = 10,
    isNumeric: Boolean = false,
    onShowError: (Boolean) -> Unit = {},
    onNext: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    var isError by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (newValue.length <= maxLength) {
                if (isNumeric) {
                    val filteredValue = StringUtil.filterToNumeric(newValue)
                    onValueChange.invoke(filteredValue)
                } else {
                    onValueChange.invoke(newValue)
                }
                isError = false
            } else {
                isError = true
            }
            onShowError.invoke(isError)
        },
        label = {
            Text(label, style = MaterialTheme.typography.bodyMedium)
        },
        isError = isError,
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = Color.Gray,
            focusedIndicatorColor = if (isError) LightRed else MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = if (isError) LightRed else Color.LightGray,
            cursorColor = if (isError) LightRed else MaterialTheme.colorScheme.secondary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        ),
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        keyboardOptions = if (isNumeric) {
            KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        } else {
            KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            )
        },
        keyboardActions = KeyboardActions(
            onNext = {
                onNext()
            }
        ),
        trailingIcon = {
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Error",
                    tint = Color.Red
                )
            }
        }
    )
}

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    maxLength: Int = 10,
    isNumeric: Boolean = false,
    onShowError: (Boolean) -> Unit = {},
    onNext: () -> Unit = {}
) {
    val focusRequester = remember { FocusRequester() }
    var isError by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            val newText = newValue.text
            if (newText.length <= maxLength) {
                val filteredText = if (isNumeric) {
                    StringUtil.filterToNumeric(newText)
                } else {
                    newText
                }
                val newSelection = TextRange(filteredText.length)
                onValueChange(
                    TextFieldValue(
                        text = filteredText,
                        selection = newSelection
                    )
                )
                isError = false
            } else {
                isError = true
            }
            onShowError(isError)
        },
        label = {
            Text(label, style = MaterialTheme.typography.bodyMedium)
        },
        isError = isError,
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onBackground,
            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
            focusedLabelColor = MaterialTheme.colorScheme.secondary,
            unfocusedLabelColor = Color.Gray,
            focusedIndicatorColor = if (isError) LightRed else MaterialTheme.colorScheme.secondary,
            unfocusedIndicatorColor = if (isError) LightRed else Color.LightGray,
            cursorColor = if (isError) LightRed else MaterialTheme.colorScheme.secondary,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        ),
        textStyle = MaterialTheme.typography.bodyMedium,
        shape = RoundedCornerShape(20.dp),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = if (isNumeric) KeyboardType.Number else KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(
            onNext = { onNext() }
        ),
        trailingIcon = {
            if (isError) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Error",
                    tint = Color.Red
                )
            }
        }
    )
}
