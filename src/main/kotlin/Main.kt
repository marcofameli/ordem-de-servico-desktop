import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.screen.MainScreen

fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Sistema OS - Kotlin Compose") {
        MainScreen()
    }
}