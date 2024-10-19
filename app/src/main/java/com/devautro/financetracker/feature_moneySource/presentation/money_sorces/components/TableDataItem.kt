package com.devautro.financetracker.feature_moneySource.presentation.money_sorces.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.devautro.financetracker.core.presentation.components.ActionIcon
import com.devautro.financetracker.core.util.Const
import com.devautro.financetracker.ui.theme.AccentBlue
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme

@Composable
fun TableDataItem(
    modifier: Modifier = Modifier,
    index: Int,
    sourceName: String,
    amount: String,
//    includedInTotal: Boolean,
    onEditClick: () -> Unit,
    backgroundColor: Color
) {
    var sizeInDp by remember { mutableStateOf(DpSize.Zero) }
    val density = LocalDensity.current

    val heightCalculatingModifier = Modifier
        .onSizeChanged {
            sizeInDp = density.run {
                DpSize(
                    it.width.toDp(),
                    it.height.toDp()
                )
            }
        }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(horizontal = 2.dp)
            .clip(RoundedCornerShape(15.dp))
//            .border(
//                width = 2.dp,
//                color = if (includedInTotal) AccentBlue else Color.Transparent,
//                shape = RoundedCornerShape(15.dp)
//            )
            .background(backgroundColor),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // index
        Column(
            modifier = Modifier
                .weight(0.1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = (index + 1).toString(),
                color = MaterialTheme.colorScheme.background
            )
        }
//        VerticalDivider(
//            modifier = Modifier.fillMaxHeight(),
//            color = MaterialTheme.colorScheme.background
//        )
        // name
        Column(
            modifier = Modifier
                .weight(0.35f)
                .padding(10.dp)
                .then(heightCalculatingModifier),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = sourceName,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.background,
                textAlign = TextAlign.Center
            )
        }
//        VerticalDivider(
//            modifier = Modifier.fillMaxHeight(),
//            color = MaterialTheme.colorScheme.background
//        )
        // amount
        Column(
            modifier = Modifier
                .weight(0.37f)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = amount,
                fontWeight = FontWeight.SemiBold,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.background
            )
        }
//        VerticalDivider(
//            modifier = Modifier.fillMaxHeight(),
//            color = MaterialTheme.colorScheme.background
//        )
        // edit option
        Column(
            modifier = Modifier
                .weight(0.15f)
                .height(sizeInDp.height),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ActionIcon(
                modifier = Modifier.fillMaxSize(),
                onClick = onEditClick,
                backgroundColor = Color.Transparent,
                icon = Icons.Default.Edit,
                tint = MaterialTheme.colorScheme.background
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier
            .fillMaxWidth()
            .height(2.dp),
        color = Color.Transparent
    )

}


@Preview(showBackground = true)
@Composable
fun TableDataItemPreview() {
    FinanceTrackerTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            TableDataItem(
                modifier = Modifier.fillMaxWidth(),
                index = 0,
                sourceName = "Debit cakrfnkcfvnck fbggvv Igooooooooooooor kjfg kfdjjb dfljoc fv dfkkcbn rd Express Bank",
                amount = "112 236 234 478 487 498.56",
//                includedInTotal = false,
                onEditClick = {},
                backgroundColor = Const.sourcePaleColors[3]
            )
        }
    }
}