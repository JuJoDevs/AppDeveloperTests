package com.jujodevs.appdevelopertests.ui.screens.users

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.jujodevs.appdevelopertests.R
import com.jujodevs.appdevelopertests.ui.DeveloperTestsAppState
import com.jujodevs.appdevelopertests.ui.common.AutoFocusingTextField
import com.jujodevs.appdevelopertests.ui.common.dropdownmenu.DropdownMenu0VerticalPadding

const val UsersMenuTag = "UsersMenu"
const val UsersBackTag = "UsersBack"
const val UsersSearchTag = "UsersSearch"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersTopBar(
    stateApp: DeveloperTestsAppState,
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {}
) {
    var expand by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.contacts),
                fontWeight = FontWeight.Bold,
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onNavigateBack,
                modifier = Modifier.testTag(UsersBackTag)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                )
            }
        },
        actions = {
            IconButton(
                onClick = { expand = true },
                modifier = Modifier.testTag(UsersMenuTag)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.submenu),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            DropdownMenu0VerticalPadding(
                expanded = expand,
                onDismissRequest = { expand = false },
            ) {
                AutoFocusingTextField(
                    value = stateApp.findText.value,
                    onValueChange = { stateApp.findText.value = it },
                    label = { Text(text = stringResource(R.string.find)) },
                    singleLine = true,
                    keyboardActions = KeyboardActions(onDone = { expand = false }),
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                stateApp.findText.value = ""
                                expand = false
                            },
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = null)
                        }
                    },
                    modifier = Modifier.testTag(UsersSearchTag),
                )
            }
        },
        modifier = modifier,
    )
}
