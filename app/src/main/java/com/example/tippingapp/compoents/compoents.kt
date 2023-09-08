package com.example.tippingapp.compoents

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AttachMoney
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>, // content which you are entering in field
    labelId: String, // field's id
    enabled: Boolean, // is field active
    isSingleLine: Boolean, // is field have single line or not
    keyboardType: KeyboardType, // which keyboard should be appear when field is active(such as number or normal keyboard)
    imeAction: ImeAction, // what should be the function of a special key is that for next or is that enter
    onAction: KeyboardActions = KeyboardActions.Default //provides additional keyboard actions for the input field.
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { newValue ->
            valueState.value = newValue
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.AttachMoney,
                contentDescription = "MoneyIcon"
            )
        },
        label = { Text(text = labelId)},
        modifier = modifier.padding(bottom = 10.dp, start = 10.dp, end = 10.dp, top = 5.dp)
            .fillMaxWidth(),
        singleLine = isSingleLine,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onBackground
        ),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = onAction
    )




}

