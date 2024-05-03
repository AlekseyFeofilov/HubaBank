package ru.hits.hubabank.presentation.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.model.Bill
import ru.hits.hubabank.presentation.common.getSymbol

@Composable
fun BillCard(
    bill: Bill,
    index: Int,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onCardClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 8.dp, end = 16.dp, bottom = 8.dp),
        ) {
            Text(
                text = stringResource(
                    R.string.main_screen_balance_with_kopecks,
                    bill.balance / 100,
                    bill.balance % 100,
                    bill.currency.getSymbol(),
                ),
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .drawWithContent {
                        drawContent()
                        if (bill.isHidden) {
                            drawRect(color = Color.Black)
                        }
                    },
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
            )
            Column(
                modifier = Modifier.align(Alignment.TopEnd),
                horizontalAlignment = Alignment.End,
            ) {
                Text(
                    text = stringResource(R.string.main_screen_bill_number, index.toString()),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.main_screen_about),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.W300),
                )
            }
        }
    }
}
