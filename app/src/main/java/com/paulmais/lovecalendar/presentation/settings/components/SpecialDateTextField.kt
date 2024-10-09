package com.paulmais.lovecalendar.presentation.settings.components

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.OutputTransformation
import androidx.compose.foundation.text.input.TextFieldBuffer
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.insert
import androidx.compose.foundation.text.input.maxLength
import androidx.compose.foundation.text.input.placeCursorAtEnd
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.paulmais.lovecalendar.presentation.ui.theme.montserrat
import java.util.Locale.filter

@Composable
fun SpecialDateTextField(
    textFieldState: TextFieldState,
) {
    val textStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 20.sp,
        fontFamily = montserrat
    )

    if (textFieldState.text.isEmpty()) {
        Text(
            text = "MM.DD.YYYY",
            style = textStyle,
            color = Color.Black.copy(0.5f)
        )
    }
    BasicTextField(
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