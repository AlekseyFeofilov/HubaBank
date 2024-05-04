package ru.hits.hubabank.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.hits.hubabank.R
import java.time.Instant
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDateDialog(
    onDateChange: (millis: Long) -> Unit,
    onDismiss: () -> Unit,
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = getCurrentMillisWithSystemZone()
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    datePickerState.selectedDateMillis?.let {
                        onDateChange(it)
                    }
                }
            ) {
                Text(text = stringResource(R.string.date_picker_confirm))
            }
        },
        modifier = Modifier.fillMaxWidth(),
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = stringResource(R.string.date_picker_cancel),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        tonalElevation = 0.dp
    ) {
        DatePicker(state = datePickerState)
    }
}

private fun getCurrentMillisWithSystemZone(): Long =
    Instant.now().plusSeconds(
        ZoneId.systemDefault().rules.getOffset(Instant.now()).totalSeconds.toLong()
    ).toEpochMilli()
