package ru.hits.hubabank.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hits.hubabank.R
import ru.hits.hubabank.presentation.core.CollectAction
import ru.hits.hubabank.presentation.main.components.BillCard
import ru.hits.hubabank.presentation.main.components.CreditCard
import ru.hits.hubabank.presentation.main.model.MainAction

@Composable
fun MainScreen(
    onOpenProfileScreen: () -> Unit,
    onOpenBillInfoScreen: (billId: String) -> Unit,
    onOpenCreditInfoScreen: (creditId: String) -> Unit,
    onOpenCreditAddingScreen: () -> Unit,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsState()

    viewModel.action.CollectAction { action ->
        when (action) {
            MainAction.OpenProfileScreen -> onOpenProfileScreen()
            is MainAction.OpenBillInfoScreen -> onOpenBillInfoScreen(action.billId)
            is MainAction.OpenCreditInfoScreen -> onOpenCreditInfoScreen(action.creditId)
            MainAction.OpenOpenCreditAddingScreen -> onOpenCreditAddingScreen()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchBills()
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium,
            )
            IconButton(
                onClick = viewModel::openProfile,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface),
            ) {
                Icon(
                    bitmap = ImageBitmap.imageResource(R.drawable.ic_profile),
                    contentDescription = null,
                )
            }
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
        ) {
            item {
                Text(
                    text = stringResource(R.string.main_screen_your_bills),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W400),
                )
            }
            itemsIndexed(state.bills) { index, bill ->
                Spacer(modifier = Modifier.height(16.dp))
                BillCard(
                    bill = bill,
                    index = index + 1,
                    onCardClick = { viewModel.openBillInfo(bill) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    Text(
                        text = stringResource(R.string.main_screen_open_bill),
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable(onClick = viewModel::addNewBill)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = stringResource(R.string.main_screen_your_credit),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.W400),
                )
            }
            itemsIndexed(state.credits) { index, credit ->
                Spacer(modifier = Modifier.height(16.dp))
                CreditCard(
                    credit = credit,
                    index = index + 1,
                    onCreditClick = { viewModel.openCreditInfo(credit) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.TopEnd,
                ) {
                    Text(
                        text = stringResource(R.string.main_screen_open_credit),
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .clickable(onClick = viewModel::addNewCredit)
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.labelLarge,
                    )
                }
            }
        }
    }
}
