package com.devautro.financetracker.feature_payment.presentation.payments_type_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.devautro.financetracker.core.presentation.components.ActionIcon

@Composable
fun YearPicker(
    modifier: Modifier = Modifier,
    yearsList: List<Int>,
    onYearChanged: (Int) -> Unit,
    yearIndex: Int
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        ActionIcon(
            modifier = Modifier.clip(RoundedCornerShape(15.dp)),
            onClick = {
                onYearChanged(
                    yearIndex - 1
                )
            },
            backgroundColor = Color.Transparent,
            icon = Icons.AutoMirrored.Default.ArrowLeft,
            tint = MaterialTheme.colorScheme.onBackground,
            isEnabled = yearIndex > 0
        )

        Text(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Min),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.SemiBold,
            text = yearsList[yearIndex].toString()
        )
//        AnimatedContent(
//            modifier = Modifier.width(IntrinsicSize.Max),
//            targetState = currentIndex,
//            transitionSpec = {
//                slideInHorizontally(
//                    initialOffsetX = {
//                        -it
//                    }
//                ) togetherWith slideOutHorizontally(
//                    targetOffsetX = {
//                        it
//                    }
//                )
//            },
//            label = "year change"
//        ) { currentIndex ->
//
//        }

        ActionIcon(
            modifier = Modifier.clip(RoundedCornerShape(15.dp)),
            onClick = {
                onYearChanged(
                    yearIndex + 1
                )
            },
            backgroundColor = Color.Transparent,
            icon = Icons.AutoMirrored.Default.ArrowRight,
            tint = MaterialTheme.colorScheme.onBackground,
            isEnabled = yearIndex < yearsList.lastIndex
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun YearChangingPreview() {
//    FinanceTrackerTheme {
//        Column(modifier = Modifier.fillMaxWidth()
//            .background(MaterialTheme.colorScheme.background)) {
//            YearPicker(yearsList = listOf(2020, 2021, 2022, 2023, 2024))
//        }
//    }
//}