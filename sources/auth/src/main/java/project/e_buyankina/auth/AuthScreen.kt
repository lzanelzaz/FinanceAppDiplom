package project.e_buyankina.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import project.e_buyankina.common_ui.loadingbutton.LoadingButton
import project.e_buyankina.common_ui.preview.DayNightPreviews
import project.e_buyankina.common_ui.theme.AppTheme

@Composable
fun AuthScreen() {
    val viewModel = remember { AuthViewModel() }
    val state = viewModel.uiState.collectAsState()
    AuthScreenContent(
        state = state.value,
        onPasswordIconClick = { viewModel.onPasswordIconClick() },
        onPrimaryButtonClick = { viewModel.onPrimaryButtonClick() },
        onSecondaryButtonClick = { viewModel.onSecondaryButtonClick() },
        onEmailTextUpdated = { viewModel.onEmailTextUpdated(it) },
        onPasswordTextUpdated = { viewModel.onPasswordTextUpdated(it) },
        onNameTextUpdated = { viewModel.onNameTextUpdated(it) },
    )
}

@Composable
internal fun AuthScreenContent(
    state: UiState,
    onEmailTextUpdated: (String) -> Unit,
    onPasswordTextUpdated: (String) -> Unit,
    onNameTextUpdated: (String) -> Unit,
    onPasswordIconClick: () -> Unit,
    onPrimaryButtonClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                interactionSource = MutableInteractionSource(),
                indication = null,
            ) {
                focusManager.clearFocus()
            },
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderBlock(state)
        TextFieldsBlock(
            state = state,
            onEmailTextUpdated = onEmailTextUpdated,
            onPasswordTextUpdated = onPasswordTextUpdated,
            onNameTextUpdated = onNameTextUpdated,
            onPasswordIconClick = onPasswordIconClick,
        )
    }
    ButtonsBlock(
        state = state,
        onPrimaryButtonClick = onPrimaryButtonClick,
        onSecondaryButtonClick = onSecondaryButtonClick,
    )
}

@Composable
private fun HeaderBlock(state: UiState) {
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
private fun TextFieldsBlock(
    state: UiState,
    onEmailTextUpdated: (String) -> Unit,
    onPasswordTextUpdated: (String) -> Unit,
    onNameTextUpdated: (String) -> Unit,
    onPasswordIconClick: () -> Unit,
) {
    OutlinedTextField(
        value = state.emailText,
        onValueChange = onEmailTextUpdated,
        enabled = !state.isLoading,
        modifier = Modifier.padding(vertical = 10.dp),
        label = {
            Text(
                text = stringResource(R.string.email)
            )
        }
    )
    OutlinedTextField(
        value = state.passwordText,
        onValueChange = onPasswordTextUpdated,
        enabled = !state.isLoading,
        modifier = Modifier.padding(vertical = 10.dp),
        label = {
            Text(
                text = stringResource(R.string.password)
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(state.passwordIcon),
                contentDescription = null,
                modifier = Modifier.clickable(onClick = onPasswordIconClick),
            )
        }
    )
    if (state.isNameFieldVisible) {
        OutlinedTextField(
            value = state.nameText,
            onValueChange = onNameTextUpdated,
            enabled = !state.isLoading,
            modifier = Modifier.padding(vertical = 10.dp),
            label = {
                Text(
                    text = stringResource(R.string.name)
                )
            }
        )
    }
}

@Composable
private fun ButtonsBlock(
    state: UiState,
    onPrimaryButtonClick: () -> Unit,
    onSecondaryButtonClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        LoadingButton(
            onClick = onPrimaryButtonClick,
            modifier = Modifier
                .padding(top = 72.dp, bottom = 20.dp)
                .size(width = 230.dp, height = 56.dp),
            isLoading = state.isLoading,
            content = {
                Text(
                    text = stringResource(state.primaryButtonText),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        )
        Button(
            onClick = onSecondaryButtonClick,
            enabled = !state.isLoading,
            modifier = Modifier
                .padding(top = 20.dp, bottom = 80.dp)
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            ),
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
    val state = UiState(
        title = R.string.log_into_account,
        isNameFieldVisible = true,
        primaryButtonText = R.string.log_in,
        secondaryButtonText = R.string.me_new_user,
        passwordIcon = R.drawable.visibility_lock_24dp,
        isLoading = false,
        emailText = "",
        passwordText = "",
        nameText = "",
    )
    AppTheme {
        AuthScreenContent(
            state = state,
            onPasswordIconClick = {},
            onPrimaryButtonClick = {},
            onSecondaryButtonClick = {},
            onEmailTextUpdated = {},
            onPasswordTextUpdated = {},
            onNameTextUpdated = {},
        )
    }
}

@DayNightPreviews
@Composable
private fun PreviewAuth() {
    val state = UiState(
        title = R.string.log_into_account,
        isNameFieldVisible = false,
        primaryButtonText = R.string.log_in,
        secondaryButtonText = R.string.me_new_user,
        passwordIcon = R.drawable.visibility_lock_24dp,
        isLoading = false,
        emailText = "",
        passwordText = "",
        nameText = "",
    )
    AppTheme {
        AuthScreenContent(
            state = state,
            onPasswordIconClick = {},
            onPrimaryButtonClick = {},
            onSecondaryButtonClick = {},
            onEmailTextUpdated = {},
            onPasswordTextUpdated = {},
            onNameTextUpdated = {},
        )
    }
}

@DayNightPreviews
@Composable
private fun PreviewLoading() {
    val state = UiState(
        title = R.string.log_into_account,
        isNameFieldVisible = true,
        primaryButtonText = R.string.log_in,
        secondaryButtonText = R.string.me_new_user,
        passwordIcon = R.drawable.visibility_lock_24dp,
        isLoading = true,
        emailText = "",
        passwordText = "",
        nameText = "",
    )
    AppTheme {
        AuthScreenContent(
            state = state,
            onPasswordIconClick = {},
            onPrimaryButtonClick = {},
            onSecondaryButtonClick = {},
            onEmailTextUpdated = {},
            onPasswordTextUpdated = {},
            onNameTextUpdated = {},
        )
    }
}