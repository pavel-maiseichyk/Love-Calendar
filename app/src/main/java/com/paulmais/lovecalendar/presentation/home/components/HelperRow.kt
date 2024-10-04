package com.paulmais.lovecalendar.presentation.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Done
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.paulmais.lovecalendar.presentation.home.HomeAction
import com.paulmais.lovecalendar.presentation.ui.theme.LoveCalendarTheme

@Composable
fun HelperRow(
    isInEditMode: Boolean,
    daysLeftText: String,
    onAction: (HomeAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(top = 16.dp, bottom = 20.dp)
    ) {
        HelperButton(
            onClick = { if (isInEditMode) onAction(HomeAction.OnUndoEditClick) },
            imageVector = if (isInEditMode) Icons.Rounded.Close else Icons.Rounded.Settings
        )
        Spacer(modifier = Modifier.weight(1f))
        DaysLeftItem(
            text = if (isInEditMode) "..." else daysLeftText,
        )
        Spacer(modifier = Modifier.weight(1f))
        HelperButton(
            onClick = {
                when (isInEditMode) {
                    true -> onAction(HomeAction.OnConfirmEditClick)
                    false -> onAction(HomeAction.OnEditClick)
                }
            },
            imageVector = if (isInEditMode) Icons.Rounded.Done else Icons.Rounded.Add
        )
    }
}

@Preview
@Composable
private fun HelperRowPreview() {
    LoveCalendarTheme {
        HelperRow(
            isInEditMode = false,
            daysLeftText = "24 days",
            onAction = {}
        )
    }
}