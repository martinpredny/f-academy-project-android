package app.futured.academyproject.tools.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import timber.log.Timber

fun Context.openUrl(url: String) = try {
    CustomTabsIntent.Builder().run {
        setShowTitle(true)
        build().launchUrl(this@openUrl, Uri.parse(url))
    }
} catch (e: ActivityNotFoundException) {
    Timber.d(e)
}