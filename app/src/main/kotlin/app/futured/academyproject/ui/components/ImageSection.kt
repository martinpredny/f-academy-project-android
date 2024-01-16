package app.futured.academyproject.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.futured.academyproject.R
import app.futured.academyproject.ui.theme.Grid
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun ImageSection(
    imageUrl: String?,
    description: String?,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(),
        modifier = modifier.padding(Grid.d4),
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .placeholder(R.drawable.no_image_detail_placeholder)
                    .error(R.drawable.no_image_detail_placeholder)
                    .crossfade(true)
                    .build(),
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
        )
        if (!description.isNullOrBlank()) {
            Text(text = description, modifier = Modifier.padding(Grid.d2), textAlign = TextAlign.Center)
        }
    }
}