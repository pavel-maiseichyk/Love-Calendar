package com.paulmais.lovecalendar.auth.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.auth.presentation.AuthType
import com.paulmais.lovecalendar.core.presentation.ui.theme.dark_gray
import com.paulmais.lovecalendar.core.presentation.ui.theme.jakarta
import com.paulmais.lovecalendar.core.presentation.ui.theme.light_gray
import com.paulmais.lovecalendar.core.presentation.ui.theme.red

@Composable
fun AuthBottomSection(
    modifier: Modifier = Modifier,
    authType: AuthType,
    onChangeAuthTypeClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignText(
            modifier = Modifier.fillMaxWidth(),
            authType = authType,
            onChangeAuthTypeClick = onChangeAuthTypeClick
        )
    }
}

@Composable
private fun SignText(
    modifier: Modifier = Modifier,
    authType: AuthType,
    onChangeAuthTypeClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val (firstTextRes, secondTextRes) = when (authType) {
            AuthType.Login -> R.string.don_t_have_an_account to R.string.sign_up
            AuthType.Register -> R.string.already_have_an_account to R.string.sign_in
        }
        Text(
            text = stringResource(firstTextRes),
            fontSize = 16.sp,
            color = dark_gray,
            fontWeight = FontWeight.Normal,
            fontFamily = jakarta
        )
        Spacer(Modifier.width(4.dp))
        Text(
            modifier = Modifier.clickable { onChangeAuthTypeClick() },
            text = stringResource(secondTextRes),
            fontSize = 16.sp,
            color = red,
            fontWeight = FontWeight.SemiBold,
            fontFamily = jakarta,
        )
    }
}

@Composable
private fun ContinueWithoutAccountButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(48.dp),
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = dark_gray,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = light_gray
        )
    ) {
        Text(
            text = stringResource(R.string.continue_without_an_account),
            fontFamily = jakarta,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
    }
}