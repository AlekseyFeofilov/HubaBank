package ru.hits.hubabank.presentation.credit.info

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hits.hubabank.R
import ru.hits.hubabank.presentation.core.CollectAction
import ru.hits.hubabank.presentation.credit.info.model.CreditInfoAction

@Composable
fun CreditInfoScreen(
    onNavigateBack: () -> Unit,
    viewModel: CreditInfoViewModel = hiltViewModel(),
) {
    val state by viewModel.screenState.collectAsState()

    viewModel.action.CollectAction { action ->
        when (action) {
            CreditInfoAction.NavigateBack -> onNavigateBack()
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchCredit()
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
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
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
                        credit.sum / 100,
                        credit.sum % 100,
                    ),
                    modifier = Modifier.padding(start = 16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.credit_screen_paid_out),
                    modifier = Modifier.fillMaxWidth(0.35f),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(
                        R.string.main_screen_balance_with_kopecks,
                        credit.paidOut / 100,
                        credit.paidOut % 100,
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = stringResource(R.string.credit_screen_remains),
                    modifier = Modifier.fillMaxWidth(0.35f),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = stringResource(
                        R.string.main_screen_balance_with_kopecks,
                        (credit.sum - credit.paidOut) / 100,
                        (credit.sum - credit.paidOut) % 100,
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
    }
}
