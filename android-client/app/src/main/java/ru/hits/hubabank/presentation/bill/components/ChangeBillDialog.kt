package ru.hits.hubabank.presentation.bill.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.hits.hubabank.R
import ru.hits.hubabank.domain.bill.model.BillChange
import ru.hits.hubabank.presentation.common.getActionRes
import ru.hits.hubabank.presentation.common.getTitleRes
import ru.hits.hubabank.presentation.ui.components.AppButton
import ru.hits.hubabank.presentation.ui.components.AppTextField

@Composable
fun ChangeBillDialog(
    billChange: BillChange,
    currentSum: String,
    onSumChange: (String) -> Unit,
    onCloseDialog: () -> Unit,
    onChangeClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(
        onDismissRequest = onCloseDialog
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, RoundedCornerShape(16.dp))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = stringResource(billChange.getTitleRes()),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(16.dp))
            AppTextField(
                value = currentSum,
                onValueChange = onSumChange,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                placeholderText = stringResource(R.string.bill_screen_input_sum),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
            )
            Spacer(modifier = Modifier.height(16.dp))
            AppButton(
                text = stringResource(billChange.getActionRes()),
                onClick = onChangeClick,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp),
            )
        }
    }
}
