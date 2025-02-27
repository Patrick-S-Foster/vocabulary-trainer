package com.example.vocabularytrainer.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vocabularytrainer.R

@Composable
fun TitledComposable(text: String, content: @Composable () -> Unit) {
    Column {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.titled_content_horizontal_padding)),
            thickness = dimensionResource(R.dimen.titled_content_divider_thickness)
        )

        Text(
            modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.titled_content_title_spacing),
                    bottom = dimensionResource(R.dimen.titled_content_content_spacing)
                )
                .padding(horizontal = dimensionResource(R.dimen.titled_content_horizontal_padding)),
            text = text,
            style = MaterialTheme.typography.labelSmall
        )

        content()
    }
}

@Preview(showBackground = true)
@Composable
fun TitledComposablePreview() {
    TitledComposable("Title") {
        Text("CONTENT")
    }
}