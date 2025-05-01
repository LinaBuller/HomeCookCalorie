package com.buller.ckkal.ui.screens

import androidx.compose.animation.core.copy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults.Container
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.semantics.text
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.buller.ckkal.domain.utils.StringUtil
import com.buller.ckkal.ui.theme.LightRed

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBasicTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    prefix: @Composable (() -> Unit)? = null,
    suffix: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    isError: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.medium,
    colors: TextFieldColors =  TextFieldDefaults.colors(
        focusedTextColor =MaterialTheme.colorScheme.onBackground,
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
    isNumeric: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    onNext: () -> Unit = {},
    onShowError: (Boolean) -> Unit = {}
) {
    val keyboardOptionsNew = if (isNumeric) {
        keyboardOptions.copy(keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next)
    } else {
        keyboardOptions
    }
    val keyboardActionsNew = if (isNumeric) {
        KeyboardActions(
            onNext = {
                onNext()
            }
        )
    } else {
        keyboardActions
    }

    BasicTextField(
        value = value,
        onValueChange = {
            if (it.text.length <= maxLength) {
                onValueChange(it)
            }
            onShowError.invoke(isError)
        },
        modifier = modifier
            .defaultMinSize(
                minWidth = OutlinedTextFieldDefaults.MinWidth,
                minHeight = OutlinedTextFieldDefaults.MinHeight
            ),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptionsNew,
        keyboardActions = keyboardActionsNew,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        interactionSource = interactionSource,
        visualTransformation = visualTransformation,
        decorationBox = @Composable { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = value.text,
                innerTextField = innerTextField,
                enabled = enabled,
                singleLine = singleLine,
                visualTransformation = visualTransformation,
                interactionSource = interactionSource,
                isError = isError,
                label = label,
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                prefix = prefix,
                suffix = suffix,
                supportingText = supportingText,
                colors = colors,
                container = {
                    Container(
                        enabled = enabled,
                        isError = false,
                        interactionSource = interactionSource,
                        colors = colors,
                        shape = shape,
                    )
                }
            )
        },
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
    )
}
