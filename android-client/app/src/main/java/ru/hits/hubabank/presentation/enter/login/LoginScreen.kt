package ru.hits.hubabank.presentation.enter.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hits.hubabank.R
import ru.hits.hubabank.presentation.core.CollectAction
import ru.hits.hubabank.presentation.core.LocalSnackbarController
import ru.hits.hubabank.presentation.enter.login.model.LoginAction
import ru.hits.hubabank.presentation.ui.components.AppButton

@Composable
fun LoginScreen(
    onOpenRegistrationScreen: () -> Unit,
    onOpenMainScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.screenState.collectAsState()
    val context = LocalContext.current

    val snackbarController= LocalSnackbarController.current
    viewModel.action.CollectAction { action ->
        when (action) {
            LoginAction.OpenRegistrationScreen -> onOpenRegistrationScreen()
            LoginAction.OpenMainScreen -> onOpenMainScreen()
            is LoginAction.ShowError -> snackbarController.show(action.errorRes)
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(R.string.login_screen_title),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.weight(1f))
        AppButton(
            text = stringResource(R.string.login_screen_enter),
            onClick = { viewModel.openAuthPage(context) },
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.login_screen_no_account),
            modifier = Modifier.align(Alignment.CenterHorizontally).clickable { viewModel.openRegistrationScreen() },
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}
