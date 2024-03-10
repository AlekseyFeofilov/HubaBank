package ru.hits.hubabank.presentation.bill

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
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
import ru.hits.hubabank.domain.bill.model.BillChange
import ru.hits.hubabank.presentation.bill.components.BillHistory
import ru.hits.hubabank.presentation.bill.components.ChangeBillDialog
import ru.hits.hubabank.presentation.bill.model.BillInfoAction
import ru.hits.hubabank.presentation.core.CollectAction

@Composable
fun BillInfoScreen(
    onNavigateBack: () -> Unit,
    viewModel: BillInfoViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsState()

    viewModel.action.CollectAction { action ->
        when (action) {
            BillInfoAction.NavigateBack -> onNavigateBack()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchBill()
        viewModel.fetchBillHistory()
    }

    if (state.isChangeBillDialogOpen) {
        ChangeBillDialog(
            billChange = state.howChange,
            currentSum = state.changeSum,
            onSumChange = viewModel::changeSum,
            onCloseDialog = viewModel::closeDialog,
            onChangeClick = viewModel::changeBillBalance,
        )
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
        state.bill?.let { bill ->
            Text(
                text = stringResource(R.string.main_screen_bill_number, bill.id),
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(
                    R.string.main_screen_balance_with_kopecks,
                    bill.balance / 100,
                    bill.balance % 100,
                ),
                modifier = Modifier.padding(start = 16.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.bill_screen_do_refill),
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { viewModel.openDialog(BillChange.REFILL) }
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge,
                )
                Text(
                    text = stringResource(R.string.bill_screen_do_transfer),
                    modifier = Modifier
                        .clip(RoundedCornerShape(6.dp))
                        .clickable { viewModel.openDialog(BillChange.TRANSFER) }
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = stringResource(R.string.bill_screen_history),
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W600),
            )
            BillHistory(billHistory = state.billHistory, today = state.today)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = stringResource(R.string.bill_screen_close_bill),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = viewModel::closeBill),
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelLarge,
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}


