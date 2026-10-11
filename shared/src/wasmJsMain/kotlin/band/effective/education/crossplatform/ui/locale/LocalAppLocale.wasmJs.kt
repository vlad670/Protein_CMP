package band.effective.education.crossplatform.ui.locale

import androidx.compose.runtime.*

private external val window: JsAny?

private fun getBrowserLanguage(): String? = js("window.navigator.language")

actual object LocalAppLocale {
    private val LocalAppLocaleCompositionLocal = staticCompositionLocalOf<String?> { null }

    actual val current: String
        @Composable get() = LocalAppLocaleCompositionLocal.current ?: (try {
            getBrowserLanguage()
        } catch (e: Throwable) {
            null
        } ?: "en")

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        return LocalAppLocaleCompositionLocal provides value
    }
}
