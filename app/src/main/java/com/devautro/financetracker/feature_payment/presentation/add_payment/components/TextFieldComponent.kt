package com.devautro.financetracker.feature_payment.presentation.add_payment.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.BackgroundColor

@Composable
fun TextFieldComponent(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    labelText: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    supportingText: @Composable() (() -> Unit)? = null
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .padding(top = 10.dp),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = labelText) },
        trailingIcon = trailingIcon,
        readOnly = readOnly,
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BackgroundColor,
            unfocusedBorderColor = AccentBlue,
            focusedLabelColor = AccentBlue,
            unfocusedLabelColor = AccentBlue
        ),
        supportingText = if (value.isBlank() || value.isEmpty()) supportingText else null
    )
}