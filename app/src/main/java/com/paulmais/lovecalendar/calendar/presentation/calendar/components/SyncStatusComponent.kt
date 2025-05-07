package com.paulmais.lovecalendar.calendar.presentation.calendar.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.calendar.domain.model.SyncStatus
import com.paulmais.lovecalendar.core.presentation.ui.theme.blue
import com.paulmais.lovecalendar.core.presentation.ui.theme.blue_container
import com.paulmais.lovecalendar.core.presentation.ui.theme.green
import com.paulmais.lovecalendar.core.presentation.ui.theme.green_container
import kotlinx.coroutines.delay

@Composable
fun SyncStatusComponent(
    syncStatus: SyncStatus,
    onClick: () -> Unit
) {
    val containerColor = when (syncStatus) {
        SyncStatus.Synced -> green_container
        SyncStatus.Pending -> blue_container
    }

    val contentColor = when (syncStatus) {
        SyncStatus.Synced -> green
        SyncStatus.Pending -> blue
    }

    val text = when (syncStatus) {
        SyncStatus.Synced -> stringResource(R.string.data_synced)
        SyncStatus.Pending -> stringResource(R.string.data_pending)
    }

    var visible by remember { mutableStateOf(true) }

    LaunchedEffect(syncStatus) {
        visible = true
        if (syncStatus == SyncStatus.Synced) {
            delay(3000)
            visible = false
        }
    }

    AnimatedVisibility(
        visible = visible
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(containerColor)
                .clickable(enabled = syncStatus == SyncStatus.Pending) {
                    if (syncStatus == SyncStatus.Pending) onClick()
                }
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = contentColor,
                style = TextStyle(fontSize = 14.sp)
            )
        }
    }
}