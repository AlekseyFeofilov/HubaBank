package ru.hits.hubabank.presentation.bill.components

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ColumnScope.BillHistory(
    billHistory: Map<LocalDate, List<BillHistoryItem>>,
    today: LocalDate,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .padding(horizontal = 16.dp),
    ) {
        billHistory.forEach { (date, billHistoryItems) ->
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (today == date) {
                        stringResource(R.string.bill_screen_today)
                    } else {
                        date.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.getDefault()))
                    },
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            items(billHistoryItems) { item ->
                Spacer(modifier = Modifier.height(16.dp))
                BillHistoryItemCard(item = item, modifier = Modifier.fillMaxWidth())
            }
        }
    }
}
