package band.effective.education.crossplatform.ui.locale

import androidx.compose.runtime.*

var customAppLocale by mutableStateOf<String?>(null) // null = взять из системы

expect object LocalAppLocale {
    val current: String @Composable get
    @Composable infix fun provides(value: String?): ProvidedValue<*>
}

@Composable
fun AppEnvironment(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalAppLocale provides customAppLocale) {
        key(customAppLocale) { content() }
    }
}