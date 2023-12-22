package com.jujodevs.appdevelopertests.ui.screens.users

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.jujodevs.appdevelopertests.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersTopBar(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.contacts),
                fontWeight = FontWeight.Bold,
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = null)
            }
        },
        modifier = modifier,
    )
}
