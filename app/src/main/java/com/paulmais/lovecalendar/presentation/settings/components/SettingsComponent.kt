package com.paulmais.lovecalendar.presentation.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme
import com.paulmais.lovecalendar.presentation.ui.theme.montserrat

@Composable
fun SettingsComponent(
    date: String,
    textFieldState: TextFieldState,
    title: String,
    isEditing: Boolean,
    onComponentClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textStyle = TextStyle(
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 20.sp,
        fontFamily = montserrat,
        textAlign = TextAlign.End
    )
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                border = BorderStroke(1.dp, Color.Black), shape = RoundedCornerShape(16.dp)
            )
            .animateContentSize()
            .clickable(enabled = !isEditing, onClick = onComponentClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = title,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 20.sp,
                    fontFamily = montserrat
                )
            )
            if (isEditing) {
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                SpecialDateTextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    textFieldState = textFieldState,
                    textStyle = textStyle.copy(color = textStyle.color.copy(alpha = 0.5f))
                )
            }
            if (!isEditing) {
                Text(
                    text = date.ifEmpty { "MM.DD.YYYY" },
                    style = textStyle
                )
            }
        }
        if (isEditing) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SettingsButton(
                    modifier = Modifier.weight(1f),
                    text = "Cancel",
                    onClick = onCancelClick
                )
                SettingsButton(
                    modifier = Modifier.weight(1f),
                    text = "Done",
                    onClick = onDoneClick
                )
            }
        }
    }
}

@Preview
@Composable
private fun SettingsComponentPreview() {
    LoveCalendarTheme {
        SettingsComponent(
            title = "Special Date",
            isEditing = false,
            textFieldState = rememberTextFieldState(""),
            onCancelClick = {},
            onDoneClick = {},
            onComponentClick = {},
            date = ""
        )
    }
}