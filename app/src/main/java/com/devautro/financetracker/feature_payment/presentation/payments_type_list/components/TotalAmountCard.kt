package com.devautro.financetracker.feature_payment.presentation.payments_type_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme

@Composable
fun TotalAmountCard(
    modifier: Modifier = Modifier,
    amount: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        shape = CircleShape,
        border = BorderStroke(1.dp, AccentBlue)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(0.5f).padding(start = 20.dp),
                textAlign = TextAlign.Start,
                text = "Total amount:",
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                modifier = Modifier.fillMaxWidth().padding(end = 20.dp),
                textAlign = TextAlign.End,
                text = amount,
                fontStyle = FontStyle.Italic,
                style = MaterialTheme.typography.headlineMedium
            )
        }
    }
}

@Preview
@Composable
fun TotalAmountCardPreview() {
    FinanceTrackerTheme {
        TotalAmountCard(amount = "156 $")
    }
}