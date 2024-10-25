package com.devautro.financetracker.feature_settings.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devautro.financetracker.R
import com.devautro.financetracker.core.util.Const

@Composable
fun DropDownLanguageMenu(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    onDismissMenu: () -> Unit,
    selectedItem: Int,
    onSelectedItemIdChange: (Int) -> Unit,
) {
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = { onDismissMenu() },
        scrollState = rememberScrollState(),
        modifier = modifier
            .height(IntrinsicSize.Max)
            .background(MaterialTheme.colorScheme.background)
    ) {
        Const.flags.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    onDismissMenu()
                    onSelectedItemIdChange(item.imageResource)
                },
                text = {
                    Text(
                        text = stringResource(id = item.language),
                        fontSize = 18.sp,
                        color = when {
                            item.imageResource == selectedItem -> MaterialTheme.colorScheme.secondary
                            else -> MaterialTheme.colorScheme.onBackground
                        }
                    )
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        painter = painterResource(id = item.imageResource),
                        contentDescription = stringResource(id = R.string.icon_flag_description),
                        tint = Color.Unspecified // for correct drawable color
                    )
                }
            )
        }
    }
}