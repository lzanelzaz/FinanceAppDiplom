package project.e_buyankina.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
internal fun ProfileScreen(state: State) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HeaderBlock(state, modifier = Modifier.fillMaxHeight(0.5f))
        Spacer(modifier = Modifier.fillMaxHeight(0.25f))
        ButtonBlock(state, modifier = Modifier.fillMaxHeight(0.5f))
    }
}

@Composable
internal fun HeaderBlock(state: State, modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.cat_with_bulb),
            contentDescription = null,
            modifier = Modifier
                .size(256.dp)
                .padding(top = 32.dp)
        )
        Text(
            text = state.userName,
            modifier = Modifier.padding(vertical = 16.dp),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
internal fun ButtonBlock(state: State, modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        LoadingButton(
            onClick = {},
            modifier = Modifier
                .padding(top = 72.dp, bottom = 20.dp)
                .size(width = 246.dp, height = 56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError,
            ),
            loadingColor = MaterialTheme.colorScheme.onError,
            isLoading = state.isLoading,
            content = {
                Icon(
                    painter = painterResource(R.drawable.delete_24dp),
                    contentDescription = null
                )
                Text(
                    text = stringResource(R.string.log_out),
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        )
    }
}

@DayNightPreviews
@Composable
private fun Preview() {
    val state = State(
        userName = "Елизавета",
        isLoading = false,
    )
    AppTheme {
        ProfileScreen(state = state)
    }
}

@DayNightPreviews
@Composable
private fun PreviewLoading() {
    val state = State(
        userName = "Елизавета",
        isLoading = true,
    )
    AppTheme {
        ProfileScreen(state = state)
    }
}