package io.github.patricksfoster.vocabularytrainer.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.vocabularytrainer.R
import io.github.patricksfoster.vocabularytrainer.service.db.LanguageLevel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun LanguageFloatingActionButton(
    expanded: MutableState<Boolean>,
    onClick: (languageLevel: LanguageLevel) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(
                end = WindowInsets.systemBars.asPaddingValues()
                    .calculateRightPadding(LocalLayoutDirection.current)
            )
            .testTag("FloatingActionButtonOuterBox")
    ) {
        for ((index, languageLevel) in LanguageLevel.entries.withIndex()) {
            val angle = Math.toRadians(index * 90.0 / (LanguageLevel.entries.size - 1))

            val paddingEnd by animateDpAsState(
                if (expanded.value) cos(angle) * dimensionResource(
                    R.dimen.language_floating_action_button_expanded_radius
                ) else 0.dp
            )
            val paddingBottom by animateDpAsState(
                if (expanded.value) sin(angle) * dimensionResource(
                    R.dimen.language_floating_action_button_expanded_radius
                ) else 0.dp
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = paddingEnd, bottom = paddingBottom)
                    .testTag("FloatingActionButtonInnerBox#$index")
            ) {
                FilledIconButton(
                    onClick = { onClick(languageLevel) },
                    modifier = Modifier.testTag("FilledActionButton#$index")
                ) {
                    Text(
                        text = languageLevel.title,
                        modifier = Modifier.testTag("FilledActionButtonText#$index")
                    )
                }
            }
        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .testTag("FloatingActionButton"),
            onClick = { expanded.value = !expanded.value }
        ) {
            Icon(
                imageVector = if (expanded.value) Icons.Filled.Close else Icons.AutoMirrored.Outlined.MenuBook,
                contentDescription = stringResource(R.string.floating_action_button_content_description),
                modifier = Modifier.testTag("FloatingActionButtonIcon")
            )
        }
    }
}

@Preview
@Composable
fun LanguageFloatingActionButtonPreview() {
    LanguageFloatingActionButton(rememberSaveable { mutableStateOf(false) }) {}
}