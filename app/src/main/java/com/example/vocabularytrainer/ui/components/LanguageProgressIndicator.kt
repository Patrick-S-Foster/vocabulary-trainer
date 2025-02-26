package com.example.vocabularytrainer.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vocabularytrainer.R
import com.example.vocabularytrainer.service.db.LanguageLevel

@Composable
fun LanguageProgressIndicator(languageLevel: LanguageLevel, completedCount: Int, totalCount: Int) {
    val progress = completedCount.toFloat() / totalCount.toFloat();

    Box {
        CircularProgressIndicator(
            modifier = Modifier
                .aspectRatio(1F)
                .fillMaxSize(),
            progress = { progress },
            strokeWidth = dimensionResource(R.dimen.language_progress_indicator_stroke_width),
            gapSize = dimensionResource(R.dimen.language_progress_indicator_gap_size),
            strokeCap = StrokeCap.Round
        )
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = languageLevel.title,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = "$completedCount/$totalCount",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(widthDp = 125, heightDp = 125)
@Composable
fun LanguageProgressIndicatorPreview() {
    LanguageProgressIndicator(LanguageLevel.B1, 123, 456)
}