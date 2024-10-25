package com.devautro.financetracker.feature_payment.presentation.payments_type_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.secondary

@Composable
fun SelectedMonthContainer(
    modifier: Modifier = Modifier,
    monthTag: String
) {
    Card(
        modifier = modifier,
        shape = CircleShape,
        border = BorderStroke(1.dp, AccentBlue),
        colors = CardDefaults.cardColors(
            containerColor = secondary,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            modifier = Modifier.padding(5.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = monthTag,
                modifier = Modifier.padding(5.dp),
                color = AccentBlue
            )
        }
    }
}