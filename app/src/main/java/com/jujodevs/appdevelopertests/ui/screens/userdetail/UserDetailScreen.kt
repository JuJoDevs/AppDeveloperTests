package com.jujodevs.appdevelopertests.ui.screens.userdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jujodevs.appdevelopertests.R
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.ui.common.RowSpacer
import com.jujodevs.appdevelopertests.ui.common.WebViewMap
import com.jujodevs.appdevelopertests.ui.common.buildIFrameMap
import com.jujodevs.appdevelopertests.ui.screens.userdetail.model.getGender
import com.jujodevs.appdevelopertests.ui.theme.IsAppearanceLightStatusBars

@Composable
fun UserDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: UserDetailViewModel = hiltViewModel()
) {
    IsAppearanceLightStatusBars(true)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        when {
            uiState.loading -> {
                CircularProgressIndicator()
            }

            uiState.user != null -> {
                UserDetailBody(user = uiState.user!!)
            }
        }
    }
}

@Composable
fun UserDetailBody(
    user: User,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        item {
            UserDetailRow(
                label = stringResource(R.string.first_and_last_name),
                text = user.name,
                painter = painterResource(id = R.drawable.user),
            )
            UserDetailRow(
                label = stringResource(R.string.email),
                text = user.email,
                painter = painterResource(id = R.drawable.mail),
            )
            UserDetailRow(
                label = stringResource(R.string.gender),
                text = getGender(user.gender).showText,
                imageVector = getGender(user.gender).icon,
            )
            UserDetailRow(
                label = stringResource(R.string.registration_date),
                text = user.registered.changeFormatDate(),
                painter = painterResource(id = R.drawable.calendar),
            )
            UserDetailRow(
                label = stringResource(R.string.phone),
                text = user.cell,
                painter = painterResource(id = R.drawable.phone),
            )
            UserDetailRow(label = "Dirección") {
                WebViewMap(iFrame = buildIFrameMap(user.latitude, user.longitude))
            }
        }
    }
}

@Composable
fun UserDetailRow(
    label: String,
    text: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier
) {
    UserDetailRow(
        label = label,
        text = text,
        icon = { Icon(imageVector = imageVector, contentDescription = null) },
        modifier = modifier,
    )
}

@Composable
fun UserDetailRow(
    label: String,
    text: String,
    painter: Painter,
    modifier: Modifier = Modifier
) {
    UserDetailRow(
        label = label,
        text = text,
        icon = { Icon(painter = painter, contentDescription = null) },
        modifier = modifier,
    )
}

@Composable
fun UserDetailRow(
    label: String,
    text: String,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    UserDetailRow(
        label = label,
        leftContent = { icon() },
        content = {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        modifier = modifier,
    )
}

@Composable
fun UserDetailRow(
    label: String,
    modifier: Modifier = Modifier,
    leftContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(42.dp),
        ) { leftContent() }
        Column(modifier = Modifier.padding(start = 12.dp, top = 8.dp, bottom = 8.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
            )
            Box(modifier = Modifier.padding(vertical = 8.dp)) {
                content()
            }
            RowSpacer()
        }
    }
}
