package com.paulmais.lovecalendar.auth.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.paulmais.lovecalendar.R
import com.paulmais.lovecalendar.auth.presentation.AuthType
import com.paulmais.lovecalendar.core.presentation.component.AppButton
import com.paulmais.lovecalendar.core.presentation.ui.theme.dark_gray
import com.paulmais.lovecalendar.core.presentation.ui.theme.jakarta
import com.paulmais.lovecalendar.core.presentation.ui.theme.red

@Composable
fun AuthMiddleSection(
    modifier: Modifier = Modifier,
    login: String,
    onLoginChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    authType: AuthType,
    onMainButtonClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SignTextFieldWithText(
            modifier = Modifier.fillMaxWidth(),
            headline = stringResource(R.string.login),
            placeholder = stringResource(R.string.enter_login),
            text = login,
            onTextChange = onLoginChange
        )
        Spacer(Modifier.height(12.dp))
        SignTextFieldWithText(
            modifier = Modifier.fillMaxWidth(),
            headline = stringResource(R.string.password),
            placeholder = stringResource(R.string.enter_password),
            text = password,
            onTextChange = onPasswordChange
        )
        Spacer(Modifier.height(24.dp))
        AppButton(
            modifier = Modifier.fillMaxWidth(),
            text = when (authType) {
                AuthType.Login -> stringResource(R.string.log_in)
                AuthType.Register -> stringResource(R.string.sign_up)
            },
            onClick = onMainButtonClick
        )
    }
}

@Composable
fun SignTextFieldWithText(
    modifier: Modifier = Modifier,
    headline: String,
    placeholder: String,
    text: String,
    onTextChange: (String) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = headline,
            fontSize = 14.sp,
            color = dark_gray,
            fontWeight = FontWeight.SemiBold,
            fontFamily = jakarta
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text,
            onValueChange = onTextChange,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = red,
                unfocusedBorderColor = Color(0xFFE1E1E1),
                focusedTextColor = dark_gray,
                unfocusedTextColor = dark_gray,
                cursorColor = red
            ),
            textStyle = TextStyle(
                fontSize = 16.sp,
                color = dark_gray,
                fontWeight = FontWeight.SemiBold,
                fontFamily = jakarta
            ),
            placeholder = {
                Text(
                    text = placeholder,
                    fontSize = 16.sp,
                    color = Color(0xFF999999),
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Italic,
                    fontFamily = jakarta
                )
            }
        )
    }
}