package band.effective.education.crossplatform

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.compose.resources.stringResource
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.app_title

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        // заголовок окна — тоже подпись интерфейса, значит живёт в ресурсах
        title = stringResource(Res.string.app_title),
    ) {
        App()
    }
}
