package app.futured.academyproject.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import app.futured.academyproject.R
import app.futured.academyproject.data.model.local.TouristPlace
import app.futured.academyproject.ui.theme.Grid
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun TouristPlaceCard(
    touristPlace: TouristPlace,
    onClick: (placeId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable { onClick(touristPlace.id) }
            .padding(vertical = Grid.d2, horizontal = Grid.d4)
            .fillMaxWidth(),
    ) {
        Card(
            colors = CardDefaults.cardColors(),
            modifier = Modifier
                .size(Grid.d15),
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    ImageRequest.Builder(LocalContext.current)
                        .data(touristPlace.image1Url)
                        .placeholder(R.drawable.no_image_placeholder)
                        .error(R.drawable.no_image_placeholder)
                        .crossfade(true)
                        .build(),
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .aspectRatio(1f),
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .padding(vertical = Grid.d2, horizontal = Grid.d4),
        ) {
            Text(
                text = touristPlace.name,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.height(Grid.d1))
            touristPlace.street?.let { street ->
                Text(
                    text = street,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}