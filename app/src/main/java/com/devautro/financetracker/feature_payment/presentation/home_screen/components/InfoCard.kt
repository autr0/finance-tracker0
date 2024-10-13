package com.devautro.financetracker.feature_payment.presentation.home_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.DarkGreenCircle
import com.devautro.financetracker.ui.theme.DarkRedCircle
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme
import com.devautro.financetracker.ui.theme.OnAccentBlue

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    text: String,
    amount: String,
    color: Color
) {

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(
            containerColor = AccentBlue,
            contentColor = OnAccentBlue
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Spacer(modifier = Modifier.height(30.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp
            )

            Box(modifier = Modifier
                .clip(CircleShape)
                .background(color)
                .size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = amount,
                    color = MaterialTheme.colorScheme.primaryContainer
                )
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
    }
}

@Preview
@Composable
fun InfoCardPreview() {
    FinanceTrackerTheme {

        LazyColumn(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)
        ) {
            item {
                InfoCard(
                    modifier = Modifier.fillMaxHeight(0.3f),
                    text = "Incomes",
                    amount = "2300$",
                    color = DarkGreenCircle
                )
            }
            item {
                InfoCard(
                    modifier = Modifier.fillMaxHeight(0.3f),
                    text = "Expenses",
                    amount = "1000$",
                    color = DarkRedCircle
                )
            }
            item {
                InfoCard(
                    modifier = Modifier.fillMaxHeight(0.3f),
                    text = "Budget",
                    amount = "-1300$",
                    color = MaterialTheme.colorScheme.background
                )
            }
        }

    }
}