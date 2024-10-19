package com.devautro.financetracker.feature_moneySource.presentation.money_sorces.components

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.devautro.financetracker.ui.theme.FinanceTrackerTheme

@Composable
fun SingleItemDeleteDialog(
    onDismissDialog: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissDialog
    ) {
        Card(
            modifier = Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Min),
            shape = RoundedCornerShape(15.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = Color.Black
            )
        ) {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    append("You can ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("not ")
                    }
                    append("delete it!")
                },
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    append("There is must be at least ")
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("one ")
                    }
                    append("source of money")
                },
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview
@Composable
fun DialogPreview() {
    FinanceTrackerTheme {
        SingleItemDeleteDialog {

        }
    }
}