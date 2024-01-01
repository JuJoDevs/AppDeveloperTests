package com.jujodevs.appdevelopertests.ui.screens.users

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.jujodevs.appdevelopertests.R
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.ui.DeveloperTestsAppState
import com.jujodevs.appdevelopertests.ui.common.RowSpacer
import com.jujodevs.appdevelopertests.ui.theme.IsAppearanceLightStatusBars

const val UsersScreenTag = "UsersScreen"
const val UsersLazyColumnTag = "UsersLazyColumn"
const val UserRowTag = "RowUser"

@Composable
fun UsersScreen(
    appState: DeveloperTestsAppState,
    modifier: Modifier = Modifier,
    viewModel: UsersViewModel = hiltViewModel(),
) {
    IsAppearanceLightStatusBars()

    val users = viewModel.userPagingFlow.collectAsLazyPagingItems()
    var blockOnce by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = appState.findText.value) {
        if (blockOnce) {
            blockOnce = false
            return@LaunchedEffect
        }
        viewModel.findUsers(appState.findText.value)
        users.refresh()
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .testTag(UsersScreenTag),
    ) {
        when (users.loadState.refresh) {
            is LoadState.Error ->
                Text(text = "Error" + (users.loadState.refresh as LoadState.Error).error.message)

            LoadState.Loading -> CircularProgressIndicator()
            is LoadState.NotLoading -> {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .testTag(UsersLazyColumnTag),
                ) {
                    items(
                        count = users.itemCount,
                        key = users.itemKey { it.id },
                    ) { index ->
                        users[index]?.let { user ->
                            UserItem(
                                user = user,
                                onNavigateToDetail = {
                                    blockOnce = true
                                    appState.currentUser.value = user
                                    appState.navToDetail(it)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(
    user: User,
    modifier: Modifier = Modifier,
    onNavigateToDetail: (User) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onNavigateToDetail(user) }
            .testTag(UserRowTag+user.id),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .sizeIn(minWidth = 84.dp, minHeight = 78.dp)
                .padding(16.dp),
        ) {
            AsyncImage(
                model = user.picture,
                contentDescription = user.name,
                modifier = Modifier
                    .clip(CircleShape),
            )
        }
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .weight(1f),
                ) {
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.arrow_small),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .padding(16.dp),
                )
            }
            RowSpacer()
        }
    }
}
