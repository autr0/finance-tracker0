package com.devautro.financetracker.feature_moneySource.presentation.money_sorces.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.devautro.financetracker.core.presentation.components.ActionIcon
import com.devautro.financetracker.core.util.Const
import com.devautro.financetracker.feature_moneySource.domain.model.MoneySource
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme

@Composable
fun MoneySourceCard(
    modifier: Modifier = Modifier,
    cardPaleColor: Color,
    cardAccentColor: Color,
    sourceName: String,
    index: Int,
//    includedInTotal: Boolean,
    amount: String,
    onEditIconClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
//        border = BorderStroke(
//            width = 2.dp,
//            color = if (includedInTotal) AccentBlue else Color.Transparent
//        ),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.background,
            containerColor = cardPaleColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp,
            pressedElevation = 6.dp
        )
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = sourceName,
            style = MaterialTheme.typography.headlineMedium
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(0.77f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(cardAccentColor),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier,
                        text = (index + 1).toString(),
                        style = MaterialTheme.typography.headlineMedium
                    )
                }

                Text(
                    text = amount,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.background,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            ActionIcon(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(15.dp))
                    .clip(RoundedCornerShape(15.dp)),
                onClick = onEditIconClick,
                backgroundColor = cardAccentColor,
                icon = Icons.Filled.Edit,
                tint = MaterialTheme.colorScheme.background
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

//@Preview
//@Composable
//fun CardPreview() {
//    FinanceTrackerTheme {
//        MoneySourceCard(
//            cardPaleColor = Const.sourcePaleColors[1],
//            cardAccentColor = Const.sourceAccentColors[1],
//            sourceName = "Card #1",
//            index = 2,
//            includedInTotal = true,
//            amount = "17894.89"
//        ) {
//
//        }
//    }
//}