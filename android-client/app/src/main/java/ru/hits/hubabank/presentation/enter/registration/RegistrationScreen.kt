package ru.hits.hubabank.presentation.enter.registration

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
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hits.hubabank.R
import ru.hits.hubabank.presentation.core.CollectAction
import ru.hits.hubabank.presentation.core.LocalSnackbarController
import ru.hits.hubabank.presentation.enter.registration.model.RegistrationAction
import ru.hits.hubabank.presentation.ui.components.AppButton
import ru.hits.hubabank.presentation.ui.components.AppTextField

@Composable
fun RegistrationScreen(
    onOpenLoginScreen: () -> Unit,
    onOpenMainScreen: () -> Unit,
    viewModel: RegistrationViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsState()

    val snackbarController = LocalSnackbarController.current
    viewModel.action.CollectAction { action ->
        when (action) {
            RegistrationAction.OpenLoginScreen -> onOpenLoginScreen()
            RegistrationAction.OpenMainScreen -> onOpenMainScreen()
            is RegistrationAction.ShowError -> snackbarController.show(action.errorRes)
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = stringResource(R.string.registration_screen_title),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleLarge,
        )
        Spacer(modifier = Modifier.height(48.dp))
        AppTextField(
            value = state.firstName,
            onValueChange = viewModel::firstNameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholderText = stringResource(R.string.registration_screen_first_name),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            value = state.secondName,
            onValueChange = viewModel::secondNameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholderText = stringResource(R.string.registration_screen_second_name),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
        AppTextField(
            value = state.thirdName,
            onValueChange = viewModel::thirdNameChange,
            modifier = Modifier.fillMaxWidth(),
            placeholderText = stringResource(R.string.registration_screen_third_name),
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
            singleLine = true,
        )
        Spacer(modifier = Modifier.height(16.dp))
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
            text = stringResource(R.string.registration_screen_already_have_account),
            modifier = Modifier.align(Alignment.CenterHorizontally).clickable { viewModel.openLoginScreen() },
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(8.dp))
        AppButton(
            text = stringResource(R.string.registration_screen_register),
            onClick = viewModel::register,
            modifier = Modifier.fillMaxWidth(),
        )
    }
}
