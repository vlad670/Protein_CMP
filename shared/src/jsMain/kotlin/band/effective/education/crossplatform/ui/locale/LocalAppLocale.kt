package band.effective.education.crossplatform.ui.locale

import androidx.compose.runtime.*

actual object LocalAppLocale {
    private val LocalAppLocaleCompositionLocal = staticCompositionLocalOf<String?> { null }

    actual val current: String
        @Composable get() = LocalAppLocaleCompositionLocal.current 
            ?: js("typeof navigator !== 'undefined' && navigator.language ? navigator.language : 'en'") as String

    @Composable
    actual infix fun provides(value: String?): ProvidedValue<*> {
        return LocalAppLocaleCompositionLocal provides value
    }
}