package app.futured.academyproject.ui.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import app.futured.academyproject.R
import app.futured.academyproject.navigation.NavigationDestinations
import app.futured.academyproject.tools.utils.openUrl
import app.futured.academyproject.tools.utils.sendMail
import app.futured.academyproject.ui.theme.Grid

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(
    navigation: NavigationDestinations,
    modifier: Modifier = Modifier,
) {
    val url = stringResource(R.string.futured_website_value)
    val developerMail = stringResource(R.string.developer_email_value)
    val futuredMail = stringResource(R.string.futured_email_value)
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.app_info)) },
                navigationIcon = {
                    IconButton(onClick = { navigation.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
        content = { contentPadding ->
            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(bottom = Grid.d2),
                )
                val context = LocalContext.current
                val version = context.packageManager.getPackageInfo(context.packageName, 0).versionName
                Text(
                    text = stringResource(R.string.app_version, version),
                    modifier = Modifier.padding(bottom = Grid.d2),
                )
                Text(
                    text = stringResource(R.string.app_description),
                    modifier = Modifier.padding(horizontal = Grid.d4, vertical = Grid.d2),
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = stringResource(R.string.contact_developer),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = Grid.d2, bottom = Grid.d2),
                )
                Text(
                    text = stringResource(R.string.developer_email),
                    modifier = Modifier
                        .clickable {
                            context.sendMail(developerMail)
                        }
                        .padding(bottom = Grid.d4),
                )
                Text(
                    text = stringResource(R.string.contact_futured),
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(top = Grid.d2, bottom = Grid.d2),
                )
                Text(
                    text = stringResource(R.string.futured_email),
                    modifier = Modifier
                        .clickable {
                            context.sendMail(futuredMail)
                        }
                        .padding(bottom = Grid.d4),
                )
                Text(
                    text = stringResource(R.string.futured_website),
                    modifier = Modifier
                        .clickable {
                            context.openUrl(url)
                        }
                        .padding(bottom = Grid.d4),
                )
                Card(
                    colors = CardDefaults.cardColors(),
                    modifier = Modifier.padding(Grid.d4),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.futured_logo),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        },
    )
}
