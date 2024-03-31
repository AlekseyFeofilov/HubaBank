package ru.hits.hubabank.presentation.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.model.Currency

@Composable
fun CreatingBillDialog(
    onCloseDialog: () -> Unit,
    onCurrencyClick: (Currency) -> Unit,
) {
    Dialog(
        onDismissRequest = onCloseDialog
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.main_screen_choose_currency),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Currency.entries.forEach {
                Row(
                    modifier = Modifier
                        .clickable { onCurrencyClick(it) }
                        .padding(8.dp)
                ) {
                    Text(
                        text = it.name,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
            }
        }
    }
}
