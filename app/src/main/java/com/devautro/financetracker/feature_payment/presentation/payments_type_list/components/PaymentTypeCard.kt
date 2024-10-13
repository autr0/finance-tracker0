package com.devautro.financetracker.feature_payment.presentation.payments_type_list.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devautro.financetracker.ui.theme.ExpenseRed
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme
import com.devautro.financetracker.ui.theme.IncomeGreen

@Composable
fun PaymentTypeCard(
    modifier: Modifier = Modifier, // need it to make each item clickable
    description: String,
    amount: String,
    monthTag: String,
    date: String,
    color: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = color,
            contentColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier.padding(28.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(0.5f)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = description,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = monthTag,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineLarge,

                )
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = amount,
                    textAlign = TextAlign.End,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = date,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        }
    }
}

@Preview
@Composable
fun PaymentTypeCardPreview() {
    FinanceTrackerTheme {
        Column(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
            PaymentTypeCard(
                description = "Rest payment for soup and more long message",
                amount = "211217.99 $",
                monthTag = "October",
                date = "12.10.24",
                color = IncomeGreen
            )
            PaymentTypeCard(
                description = "Rest payment for soup and more long message",
                amount = "211217.99 $",
                monthTag = "October",
                date = "12.10.24",
                color = ExpenseRed
            )
            PaymentTypeCard(
                description = "Rest payment for soup and more long message",
                amount = "217.99 $",
                monthTag = "October",
                date = "12.10.24",
                color = IncomeGreen
            )
            PaymentTypeCard(
                description = "Rest payment for soup and more long message",
                amount = "211217.99 $",
                monthTag = "October",
                date = "12.10.24",
                color = ExpenseRed
            )
        }
    }
}
