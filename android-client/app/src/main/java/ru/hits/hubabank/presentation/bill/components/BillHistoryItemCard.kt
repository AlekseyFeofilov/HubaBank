package ru.hits.hubabank.presentation.bill.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.model.BillHistoryItem
import ru.hits.hubabank.presentation.common.getSign
import ru.hits.hubabank.presentation.common.getTitleRes
import kotlin.math.absoluteValue

@Composable
fun BillHistoryItemCard(
    item: BillHistoryItem,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
        ) {
            Text(
                text = stringResource(item.billChange.getTitleRes()),
                color = MaterialTheme.colorScheme.background,
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.W600),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = item.billChange.getSign() + stringResource(
                    R.string.main_screen_balance_with_kopecks,
                    item.changeSum.absoluteValue / 100,
                    item.changeSum.absoluteValue % 100,
                ),
                modifier = Modifier.weight(1f),
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
