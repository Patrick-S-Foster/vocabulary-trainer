package io.github.patricksfoster.vocabularytrainer.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.vocabularytrainer.R
import io.github.patricksfoster.vocabularytrainer.ui.dialogs.InformationDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterAlignedTopBar(
    title: String,
    canNavigateBack: Boolean,
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
    var dialogOpen by rememberSaveable { mutableStateOf(false) }

    CenterAlignedTopAppBar(title = {
        Text(
            text = title,
            modifier = Modifier.testTag("CenterAlignedTopAppBarTitle")
        )
    },
        modifier = modifier.testTag("CenterAlignedTopAppBar"),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp,
                    modifier = Modifier.testTag("BackIconButton")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.center_aligned_top_bar_back_content_description),
                        modifier = Modifier.testTag("BackIcon")
                    )
                }
            }
        },
        actions = {
            if (!canNavigateBack) {
                IconButton(
                    onClick = { dialogOpen = true },
                    modifier = Modifier.testTag("InfoIconButton")
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = stringResource(R.string.center_aligned_top_bar_info_content_description),
                        modifier = Modifier.testTag("InfoIcon")
                    )
                }
            }
        })

    if (dialogOpen) {
        InformationDialog(modifier = Modifier.testTag("InformationDialog")) {
            dialogOpen = false
        }
    }
}

@Preview
@Composable
fun CenterAlignedTopBarNoNavigationPreview() {
    CenterAlignedTopBar("Title", false)
}

@Preview
@Composable
fun CenterAlignedTopBarWithNavigationPreview() {
    CenterAlignedTopBar("Title", true)
}