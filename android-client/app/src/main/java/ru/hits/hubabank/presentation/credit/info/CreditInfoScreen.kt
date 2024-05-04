package ru.hits.hubabank.presentation.credit.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.model.Currency
import ru.hits.hubabank.presentation.common.getSymbol
import ru.hits.hubabank.presentation.common.getTitleRes
import ru.hits.hubabank.presentation.core.CollectAction
import ru.hits.hubabank.presentation.core.LocalSnackbarController
import ru.hits.hubabank.presentation.credit.info.model.CreditInfoAction
import java.time.format.DateTimeFormatter

@Composable
fun CreditInfoScreen(
    onNavigateBack: () -> Unit,
    viewModel: CreditInfoViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsState()

    val snackbarController = LocalSnackbarController.current
    viewModel.action.CollectAction { action ->
        when (action) {
            CreditInfoAction.NavigateBack -> onNavigateBack()
            is CreditInfoAction.ShowError -> snackbarController.show(action.errorRes)
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchCredit()
        viewModel.fetchCreditPayments()
    }

    Column {
        IconButton(
            onClick = viewModel::navigateBack,
            modifier = Modifier
                .size(40.dp),
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = null,
                modifier = Modifier.size(32.dp),
            )
        }
        state.credit?.let { credit ->
            Text(
                text = stringResource(R.string.main_screen_credit_number, credit.id),
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.credit_screen_sum),
                    modifier = Modifier.fillMaxWidth(0.35f),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(
                        R.string.main_screen_balance_with_kopecks,
                        credit.principal / 100,
                        credit.principal % 100,
                        Currency.RUB.getSymbol(),
                    ),
                    modifier = Modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.credit_screen_current_accounts_payable),
                    modifier = Modifier.fillMaxWidth(0.35f),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(
                        R.string.main_screen_balance_with_kopecks,
                        credit.currentAccountsPayable / 100,
                        credit.currentAccountsPayable % 100,
                        Currency.RUB.getSymbol(),
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.credit_screen_arrears),
                    modifier = Modifier.fillMaxWidth(0.35f),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(
                        R.string.main_screen_balance_with_kopecks,
                        credit.arrears / 100,
                        (credit.arrears) % 100,
                        Currency.RUB.getSymbol(),
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopEnd,
            ) {
                Text(
                    text = stringResource(R.string.credit_screen_close),
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .clickable(onClick = viewModel::closeCredit)
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(R.string.credit_screen_payments),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W400),
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    IconButton(
                        onClick = { viewModel.fetchCreditPayments() },
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
            }
            items(state.creditPayments) { payment ->
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.credit_screen_payment_day),
                        modifier = Modifier.fillMaxWidth(0.35f),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = payment.paymentDay.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.credit_screen_payment_status),
                        modifier = Modifier.fillMaxWidth(0.35f),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = stringResource(payment.paymentStatus.getTitleRes()),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = stringResource(R.string.credit_screen_payment_amount),
                        modifier = Modifier.fillMaxWidth(0.35f),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                    Text(
                        text = stringResource(
                            R.string.main_screen_balance_with_kopecks,
                            payment.paymentAmount / 100,
                            (payment.paymentAmount) % 100,
                            Currency.RUB.getSymbol(),
                        ),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
        }
    }
}
