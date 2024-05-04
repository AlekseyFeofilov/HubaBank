package ru.hits.hubabank.presentation.credit.adding

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hits.hubabank.R
import ru.hits.hubabank.presentation.core.CollectAction
import ru.hits.hubabank.presentation.core.LocalSnackbarController
import ru.hits.hubabank.presentation.credit.adding.model.CreditAddingAction
import ru.hits.hubabank.presentation.ui.components.AppButton
import ru.hits.hubabank.presentation.ui.components.AppDateDialog
import ru.hits.hubabank.presentation.ui.components.AppTextField
import ru.hits.hubabank.presentation.ui.components.TextFieldWithDropdownMenu
import java.time.format.DateTimeFormatter

@Composable
fun CreditAddingScreen(
    onNavigateBack: () -> Unit,
    onOpenCreditInfoScreen: (creditId: String) -> Unit,
    viewModel: CreditAddingViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsState()

    val snackbarController = LocalSnackbarController.current
    viewModel.action.CollectAction { action ->
        when (action) {
            CreditAddingAction.NavigateBack -> onNavigateBack()
            is CreditAddingAction.OpenCreditInfoScreen -> onOpenCreditInfoScreen(action.creditId)
            is CreditAddingAction.ShowError -> snackbarController.show(action.errorRes)
        }
    }

    if (state.isDateDialogOpen) {
        AppDateDialog(
            onDateChange = { viewModel.newDate(it) },
            onDismiss = { viewModel.changeDateDialogVisible(false) },
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            IconButton(
                onClick = viewModel::navigateBack,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterStart),
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                )
            }
            Text(
                text = stringResource(R.string.credit_adding_screen_title),
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
        }
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.common_refresh),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W400),
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        viewModel.fetchCreditTerms()
                        viewModel.fetchBills()
                    },
                    modifier = Modifier.size(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldWithDropdownMenu(
                currentText = (state.selectedTerms?.title ?: "Выберите ставку") +
                        (state.selectedTerms?.let { (it.interestRate * 100).toString() + '%' } ?: ""),
                isMenuOpen = state.isCreditTermsMenuOpen,
                menuItemsText = state.allCreditTerms.map { (it.title ?: "") + " ${it.interestRate * 100} " + '%' },
                onFieldClick = { viewModel.changeTermsMenuVisible(true) },
                onCloseMenu = { viewModel.changeTermsMenuVisible(false) },
                onSelectValue = { viewModel.onTermsClick(it) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextFieldWithDropdownMenu(
                currentText = state.selectedBill?.id ?: "Выберите счёт",
                isMenuOpen = state.isBillsMenuOpen,
                menuItemsText = state.allBills.map { it.id + "" },
                onFieldClick = { viewModel.changeBillsMenuVisible(true) },
                onCloseMenu = { viewModel.changeBillsMenuVisible(false) },
                onSelectValue = { viewModel.onBillClick(it) },
            )
            Spacer(modifier = Modifier.height(16.dp))
            AppTextField(
                value = state.creditDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { viewModel.changeDateDialogVisible(true) },
                enabled = false,
            )
            Spacer(modifier = Modifier.height(16.dp))
            AppTextField(
                value = state.creditSum,
                onValueChange = viewModel::creditSumChange,
                modifier = Modifier.fillMaxWidth(),
                placeholderText = "Введите сумму кредита",
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(16.dp))
        AppButton(
            text = stringResource(R.string.credit_adding_screen_action),
            onClick = viewModel::addNewCredit,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
        )
    }
}
