package com.buller.ckkal.ui.screens

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.buller.ckkal.domain.utils.StringUtil

@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    maxLength: Int = 10,
    isNumeric: Boolean = false,
    onShowError: (Boolean) -> Unit,
    onNext: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var isError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

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
                errorMessage = "Превышен лимит символов (максимум $maxLength)"
            }
        },
        label = {
            Text(label, style = MaterialTheme.typography.bodyLarge.copy(color = Color.Gray))
        },
        isError = isError,
        modifier = modifier.fillMaxWidth().focusRequester(focusRequester),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black,
            focusedLabelColor = Color(0xFF6200EE),
            unfocusedLabelColor = Color.Gray,
            focusedIndicatorColor = if (isError) Color.Red else Color(0xFF6200EE),
            unfocusedIndicatorColor = if (isError) Color.Red else Color.LightGray,
            cursorColor = if (isError) Color.Red else Color(0xFF6200EE),
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            errorContainerColor = Color.Transparent
        ),
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        ),
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