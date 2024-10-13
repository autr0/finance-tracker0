package com.devautro.financetracker.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.devautro.financetracker.core.presentation.util.AutoResizedText
import com.devautro.financetracker.ui.theme.UnChosenTextColor

@Composable
fun TopTabsSorting(
    modifier: Modifier = Modifier,
    tabItems: List<String>,
    defaultTabIndex: Int
) {
    var selectedTabIndex by remember {
        mutableIntStateOf(defaultTabIndex)
    }

    TabRow(
        modifier = Modifier.clip(RoundedCornerShape(15.dp)),
        selectedTabIndex = selectedTabIndex,
        containerColor = MaterialTheme.colorScheme.primary,
        indicator = {
            SecondaryIndicator(color = Color.Transparent)
        },
        divider = {
            HorizontalDivider(color = Color.Transparent)
        }
    ) {
        tabItems.forEachIndexed { index, text ->
            Tab(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .background(
                        if (index == selectedTabIndex) {
                            MaterialTheme.colorScheme.secondary
                        } else {
                            MaterialTheme.colorScheme.primary
                        }
                    ),
                selected = index == selectedTabIndex,
                onClick = {
                    selectedTabIndex = index
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = UnChosenTextColor
            ) {
                Box(
                    modifier = Modifier.height(64.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AutoResizedText(
                        text = text,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }
        }
    }
}