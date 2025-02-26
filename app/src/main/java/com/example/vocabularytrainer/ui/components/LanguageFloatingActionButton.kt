package com.example.vocabularytrainer.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.db.LanguageLevel
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun LanguageFloatingActionButton(
    onExpanded: (close: () -> Unit) -> Unit,
    onClosed: () -> Unit,
    onClick: (languageLevel: LanguageLevel) -> Unit
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box {
        for ((index, languageLevel) in LanguageLevel.entries.withIndex()) {
            val angle = Math.toRadians(index * 90.0 / (LanguageLevel.entries.size - 1))

            val paddingEnd by animateDpAsState(
                if (expanded) cos(angle) * dimensionResource(
                    R.dimen.language_floating_action_button_expanded_radius
                ) else 0.dp
            )
            val paddingBottom by animateDpAsState(
                if (expanded) sin(angle) * dimensionResource(
                    R.dimen.language_floating_action_button_expanded_radius
                ) else 0.dp
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = paddingEnd, bottom = paddingBottom)
            ) {
                FilledIconButton(
                    onClick = { onClick(languageLevel) },
                ) {
                    Text(languageLevel.title)
                }
            }
        }

        FloatingActionButton(modifier = Modifier.align(Alignment.BottomEnd), onClick = {
            expanded = !expanded

            if (expanded) {
                onExpanded { expanded != expanded }
            } else {
                onClosed()
            }
        }) {
            Icon(
                if (expanded) Icons.Filled.Close else Icons.AutoMirrored.Outlined.MenuBook,
                stringResource(R.string.floating_action_button_content_description)
            )
        }
    }
}

@Preview
@Composable
fun LanguageFloatingActionButtonPreview() {
    LanguageFloatingActionButton({}, {}, {})
}