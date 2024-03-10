package ru.hits.hubabank.presentation.enter.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hits.hubabank.R
import ru.hits.hubabank.presentation.core.CollectAction
import ru.hits.hubabank.presentation.enter.login.model.LoginAction
import ru.hits.hubabank.presentation.ui.components.AppButton
import ru.hits.hubabank.presentation.ui.components.AppTextField

@Composable
fun LoginScreen(
    onOpenRegistrationScreen: () -> Unit,
    onOpenMainScreen: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state by viewModel.screenState.collectAsState()

    viewModel.action.CollectAction { action ->
        when (action) {
            LoginAction.OpenRegistrationScreen -> onOpenRegistrationScreen()
            LoginAction.OpenMainScreen -> onOpenMainScreen()
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
        Spacer(modifier = Modifier.height(48.dp))
        AppTextField(
            value = state.phone,
            onValueChange = viewModel::phoneChange,
            modifier = Modifier.fillMaxWidth(),
            placeholderText = stringResource(R.string.login_screen_phone),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            value = state.password,
            onValueChange = viewModel::passwordChange,
            modifier = Modifier.fillMaxWidth(),
            placeholderText = stringResource(R.string.login_screen_password),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.login_screen_no_account),
            modifier = Modifier.align(Alignment.CenterHorizontally).clickable { viewModel.openRegistrationScreen() },
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppButton(
            text = stringResource(R.string.login_screen_enter),
            onClick = viewModel::login,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
