package io.github.patricksfoster.vocabularytrainer.ui.dialogs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.vocabularytrainer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDialog(title: String, body: String, onCancel: () -> Unit, onConfirm: () -> Unit) {
    BasicAlertDialog(onDismissRequest = onCancel) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            border = BorderStroke(
                dimensionResource(R.dimen.card_border_width),
                MaterialTheme.colorScheme.outline
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = dimensionResource(R.dimen.card_horizontal_padding),
                        vertical = dimensionResource(R.dimen.card_vertical_padding)
                    ),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.confirm_dialog_spacing))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge
                    )

                    IconButton(onClick = onCancel) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.dialog_button_close_content_description)
                        )
                    }
                }

                Text(
                    text = body,
                    style = MaterialTheme.typography.bodyLarge
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        dimensionResource(R.dimen.card_horizontal_padding),
                        Alignment.End
                    ),
                ) {
                    TextButton(onClick = onCancel) {
                        Text(stringResource(R.string.confirm_dialog_cancel))
                    }

                    FilledTonalButton(onClick = onConfirm) {
                        Text(stringResource(R.string.confirm_dialog_confirm))
                    }
                }
            }
        }
    }
}