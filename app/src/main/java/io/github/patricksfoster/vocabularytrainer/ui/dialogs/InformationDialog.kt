package io.github.patricksfoster.vocabularytrainer.ui.dialogs

import android.content.Intent
import android.net.Uri
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.vocabularytrainer.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InformationDialog(modifier: Modifier, onDismissRequest: () -> Unit) {
    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        modifier = modifier.testTag("InformationDialogAlertDialog")
    ) {
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
                verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.information_dialog_content_spacing))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.testTag("InformationDialogTitle"),
                        text = stringResource(R.string.information_dialog_credit_title),
                        style = MaterialTheme.typography.titleLarge
                    )

                    IconButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.testTag("InformationDialogCloseButton")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = stringResource(R.string.dialog_button_close_content_description)
                        )
                    }
                }

                Text(
                    modifier = Modifier.testTag("InformationDialogOxfordBody"),
                    text = stringResource(R.string.information_dialog_oxford_body),
                    style = MaterialTheme.typography.bodyLarge
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(stringResource(R.string.oxford_5000_uri))
                    )
                    val context = LocalContext.current

                    TextButton(
                        onClick = { context.startActivity(intent) },
                        modifier = Modifier.testTag("InformationDialogOxfordButton")
                    ) {
                        Text(text = stringResource(R.string.information_dialog_button_oxford_5000))
                    }
                }

                Text(
                    modifier = Modifier.testTag("InformationDialogApiBody"),
                    text = stringResource(R.string.information_dialog_credit_body),
                    style = MaterialTheme.typography.bodyLarge
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(stringResource(R.string.dictionary_api_uri))
                    )
                    val context = LocalContext.current

                    TextButton(
                        onClick = { context.startActivity(intent) },
                        modifier = Modifier.testTag("InformationDialogApiButton")
                    ) {
                        Text(text = stringResource(R.string.information_dialog_button_view_api))
                    }
                }
            }
        }
    }
}