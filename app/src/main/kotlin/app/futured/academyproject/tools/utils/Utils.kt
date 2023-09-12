package app.futured.academyproject.tools.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
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

fun Context.sendMail(to: String) {
    try {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "vnd.android.cursor.item/email"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
        startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Timber.d(e)
    } catch (t: Throwable) {
        Timber.d(t)
    }
}

fun Context.dial(phone: String) {
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        startActivity(intent)
    } catch (t: Throwable) {
        Timber.d(t)
    }
}