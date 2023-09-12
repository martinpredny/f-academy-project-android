package app.futured.academyproject

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import app.futured.academyproject.ui.AppUI
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // draw app content behind the system bars
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            AppUI()
        }
    }
}