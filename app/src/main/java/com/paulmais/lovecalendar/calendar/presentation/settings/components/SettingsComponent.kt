package com.paulmais.lovecalendar.calendar.presentation.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.core.presentation.ui.theme.dark_gray
import com.paulmais.lovecalendar.core.presentation.ui.theme.jakarta
import com.paulmais.lovecalendar.core.presentation.ui.theme.light_gray
import com.paulmais.lovecalendar.core.presentation.ui.theme.medium_gray

@Composable
fun SettingsComponent(
    date: String,
    onDateChange: (String) -> Unit,
    title: String,
    isEditing: Boolean,
    onComponentClick: () -> Unit,
    onCancelClick: () -> Unit,
    onDoneClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val dateTextStyle = TextStyle(
        color = dark_gray,
        fontSize = 20.sp,
        fontFamily = jakarta,
        textAlign = TextAlign.End
    )
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(
                border = BorderStroke(1.dp, light_gray), shape = RoundedCornerShape(16.dp)
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
                    color = dark_gray,
                    fontSize = 20.sp,
                    fontFamily = jakarta
                )
            )
            if (isEditing) {
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }
                SpecialDateTextField(
                    modifier = Modifier.focusRequester(focusRequester),
                    text = date,
                    onTextChange = onDateChange,
                    textStyle = dateTextStyle
                )
            } else {
                val customDate = when {
                    date.length == 8 -> date.mapIndexed { index, c ->
                        if (index == 1 || index == 3) "$c." else c
                    }.joinToString("")
                    else -> "MM.DD.YYYY"
                }
                Text(
                    text = customDate,
                    style = dateTextStyle
                )
            }
        }
        AnimatedVisibility(visible = isEditing) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SettingsButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.cancel),
                    onClick = onCancelClick,
                    containerColor = medium_gray,
                    contentColor = dark_gray
                )
                SettingsButton(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.done),
                    onClick = onDoneClick,
                    containerColor = dark_gray,
                    contentColor = medium_gray
                )
            }
        }
    }
}