package band.effective.education.crossplatform.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import band.effective.education.crossplatform.resources.Res
import band.effective.education.crossplatform.resources.app_title
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import band.effective.education.crossplatform.ui.locale.customAppLocale
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import band.effective.education.crossplatform.resources.action_toggle_lang
import band.effective.education.crossplatform.resources.action_toggle_theme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    navigationIcon: @Composable () -> Unit = {}, 
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (Modifier) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = stringResource(Res.string.app_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    ) 
                },
                navigationIcon = navigationIcon,
                actions = {
                    actions()
                    IconButton(onClick = {
                        customAppLocale = if (customAppLocale == "en") "ru" else "en"
                    }) {
                        Icon(
                            imageVector = Icons.Default.Language,
                            contentDescription = stringResource(Res.string.action_toggle_lang)
                        )
                    }
                    IconButton(onClick = { isDarkTheme = !isDarkTheme }) {
                        Icon(
                            imageVector = if (isDarkTheme) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = stringResource(Res.string.action_toggle_theme)
                        )
                    }
                }
            )
        },
    ) { insets ->
        Box(Modifier.padding(insets)) {
            content(Modifier.fillMaxSize())
        }
    }
}