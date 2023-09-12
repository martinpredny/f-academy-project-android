package app.futured.academyproject.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import app.futured.academyproject.tools.utils.dial
import app.futured.academyproject.tools.utils.openUrl
import app.futured.academyproject.tools.utils.sendMail
import app.futured.academyproject.ui.theme.Grid

@Composable
fun RowTitleValue(
    title: String,
    value: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = Grid.d2, horizontal = Grid.d4)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(text = value, textAlign = TextAlign.End)
    }
}

@Composable
fun RowTitleValueWebsite(
    title: String,
    url: String,
    context: Context,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = Grid.d2, horizontal = Grid.d4)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(
            text = url, textAlign = TextAlign.End,
            modifier = Modifier
                .clickable {
                    context.openUrl(url)
                },
        )
    }
}

@Composable
fun RowTitleValuePhone(
    title: String,
    phoneNumber: String,
    context: Context,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = Grid.d2, horizontal = Grid.d4)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(
            text = phoneNumber, textAlign = TextAlign.End,
            modifier = Modifier
                .clickable {
                    context.dial(phoneNumber)
                },
        )
    }
}

@Composable
fun RowTitleValueEmail(
    title: String,
    email: String,
    context: Context,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = Grid.d2, horizontal = Grid.d4)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = title, fontWeight = FontWeight.Bold)
        Text(
            text = email, textAlign = TextAlign.End,
            modifier = Modifier
                .clickable {
                    context.sendMail(email)
                },
        )
    }
}