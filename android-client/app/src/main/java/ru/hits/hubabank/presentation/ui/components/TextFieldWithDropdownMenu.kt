package ru.hits.hubabank.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldWithDropdownMenu(
    currentText: String,
    isMenuOpen: Boolean,
    menuItemsText: List<String>,
    onFieldClick: () -> Unit,
    onCloseMenu: () -> Unit,
    onSelectValue: (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        AppTextField(
            value = currentText,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .clickable(onClick = onFieldClick),
            enabled = false,
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background,
                )
            },
        )

        val width = LocalConfiguration.current.screenWidthDp

        DropdownMenu(
            expanded = isMenuOpen,
            onDismissRequest = onCloseMenu,
            modifier = Modifier
                .width(width.dp - 32.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(4.dp),
                ),
        ) {
            menuItemsText.forEachIndexed { index, textRes ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = textRes,
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    },
                    onClick = { onSelectValue(index) },
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                )
            }
        }
    }
}
