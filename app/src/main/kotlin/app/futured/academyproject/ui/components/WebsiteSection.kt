package app.futured.academyproject.ui.components

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.futured.academyproject.R

@Composable
fun WebsiteSection(
    url: String,
    context: Context
) {
    RowTitleValueWebsite(title = stringResource(R.string.website_title), url = url, context = context)
}