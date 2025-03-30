package io.github.patricksfoster.vocabularytrainer.ui.components

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vocabularytrainer.R
import io.github.patricksfoster.vocabularytrainer.service.db.LanguageLevel

@Composable
fun LanguageProgressIndicator(
    modifier: Modifier,
    languageLevel: LanguageLevel,
    completedCount: Int,
    totalCount: Int
) {
    // This line sets the progress, and prevents a divide-by-zero error, defaulting to zero
    val progress = if (totalCount == 0) 0F else completedCount.toFloat() / totalCount.toFloat()

    Box(modifier = modifier.testTag("LanguageProgressIndicatorBox")) {
        CircularProgressIndicator(
            modifier = Modifier
                .aspectRatio(1F)
                .fillMaxSize()
                .align(Alignment.Center)
                .testTag("LanguageProgressIndicatorCircularIndicator"),
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
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.testTag("LanguageProgressIndicatorTitle")
            )
            Text(
                text = "$completedCount/$totalCount",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.testTag("LanguageProgressIndicatorBody")
            )
        }
    }
}

@Preview(widthDp = 125, heightDp = 125)
@Composable
fun LanguageProgressIndicatorPreview() {
    LanguageProgressIndicator(Modifier, LanguageLevel.B1, 123, 456)
}