package project.e_buyankina.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import project.e_buyankina.common_ui.loadingbutton.LoadingButton
import project.e_buyankina.common_ui.preview.DayNightPreviews
import project.e_buyankina.common_ui.theme.AppTheme

@Composable
internal fun AuthScreen(state: State) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderBlock(state)
        TextFieldsBlock(state)
    }
    ButtonsBlock(state)
}

@Composable
private fun HeaderBlock(state: State) {
    Image(
        painter = painterResource(R.drawable.cat_group),
        contentDescription = null,
        modifier = Modifier
            .size(256.dp)
            .padding(top = 32.dp)
    )
    Text(
        text = stringResource(state.title),
        modifier = Modifier.padding(vertical = 16.dp),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.onSurface,
        fontWeight = FontWeight.Bold,
    )
}

@Composable
private fun TextFieldsBlock(state: State) {
    OutlinedTextField(
        modifier = Modifier.padding(vertical = 10.dp),
        state = TextFieldState(),
        label = {
            Text(
                text = stringResource(R.string.email)
            )
        }
    )
    OutlinedTextField(
        modifier = Modifier.padding(vertical = 10.dp),
        state = TextFieldState(),
        label = {
            Text(
                text = stringResource(R.string.password)
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(state.passwordIcon),
                contentDescription = null,
                modifier = Modifier.clickable(onClick = {}),
            )
        }
    )
    if (state.isNameFieldVisible) {
        OutlinedTextField(
            modifier = Modifier.padding(vertical = 10.dp),
            state = TextFieldState(),
            label = {
                Text(
                    text = stringResource(R.string.name)
                )
            }
        )
    }
}

@Composable
private fun ButtonsBlock(state: State) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoadingButton(
            onClick = {},
            modifier = Modifier
                .padding(top = 72.dp, bottom = 20.dp)
                .size(width = 230.dp, height = 56.dp),
            isLoading = state.isPrimaryButtonLoading,
            content = {
                Text(
                    text = stringResource(state.primaryButtonText),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        )
        LoadingButton(
            onClick = {},
            modifier = Modifier
                .padding(top = 20.dp, bottom = 80.dp)
                .height(56.dp),
            isLoading = state.isSecondaryButtonLoading,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
            loadingColor = MaterialTheme.colorScheme.onSurface,
            content = {
                Text(
                    text = stringResource(state.secondaryButtonText),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(10.dp)
                )
            }
        )
    }
}

@DayNightPreviews
@Composable
private fun PreviewRegister() {
    val state = State(
        title = R.string.log_into_account,
        isNameFieldVisible = true,
        primaryButtonText = R.string.log_in,
        secondaryButtonText = R.string.me_new_user,
        passwordIcon = R.drawable.visibility_lock_24dp,
        isPrimaryButtonLoading = false,
        isSecondaryButtonLoading = false,
    )
    AppTheme {
        AuthScreen(state = state)
    }
}

@DayNightPreviews
@Composable
private fun PreviewAuth() {
    val state = State(
        title = R.string.log_into_account,
        isNameFieldVisible = false,
        primaryButtonText = R.string.log_in,
        secondaryButtonText = R.string.me_new_user,
        passwordIcon = R.drawable.visibility_lock_24dp,
        isPrimaryButtonLoading = false,
        isSecondaryButtonLoading = false,
    )
    AppTheme {
        AuthScreen(state = state)
    }
}

@DayNightPreviews
@Composable
private fun PreviewLoading() {
    val state = State(
        title = R.string.log_into_account,
        isNameFieldVisible = true,
        primaryButtonText = R.string.log_in,
        secondaryButtonText = R.string.me_new_user,
        passwordIcon = R.drawable.visibility_lock_24dp,
        isPrimaryButtonLoading = true,
        isSecondaryButtonLoading = true,
    )
    AppTheme {
        AuthScreen(state = state)
    }
}