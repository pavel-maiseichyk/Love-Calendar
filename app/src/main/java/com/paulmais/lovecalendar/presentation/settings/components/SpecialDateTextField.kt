package com.paulmais.lovecalendar.presentation.settings.components

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.insert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.core.text.isDigitsOnly

@Composable
fun SpecialDateTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState,
    textStyle: TextStyle
) {
    BasicTextField(
        modifier = modifier.wrapContentWidth(),
        state = textFieldState,
        outputTransformation = MyOutputTransformation,
        inputTransformation = MyInputTransformation,
        textStyle = textStyle
    )
}

@Stable
object MyOutputTransformation : OutputTransformation {
    override fun TextFieldBuffer.transformOutput() {
        if (length > 2) insert(2, ".")
        if (length > 5) insert(5, ".")
    }
}

@Stable
object MyInputTransformation : InputTransformation {

    override val keyboardOptions: KeyboardOptions =
        KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)

    override fun TextFieldBuffer.transformInput() {
        if (!asCharSequence().isDigitsOnly() || length > 8) {
            revertAllChanges()
        }
    }
}