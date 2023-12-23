package com.jujodevs.appdevelopertests.ui.screens.userdetail

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.jujodevs.appdevelopertests.R
import com.jujodevs.appdevelopertests.data.remote.FakeUsers
import com.jujodevs.appdevelopertests.domain.User
import com.jujodevs.appdevelopertests.ui.DeveloperTestsScreen
import com.jujodevs.appdevelopertests.ui.providers.BackgroudPhotoProvider
import com.jujodevs.appdevelopertests.ui.theme.Neutral95
import java.util.Locale
import kotlinx.coroutines.delay

private const val ImageDelay = 1500L

@Composable
fun UserDetailTopBar(
    user: User,
    modifier: Modifier = Modifier,
    onBackDetail: () -> Unit
) {
    var showUserImage by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = Unit) {
        delay(ImageDelay)
        showUserImage = true
    }

    Box(modifier = modifier) {
        Column {
            BackgroundImage(user)
            BottomBar()
        }
        TopBar(onBackDetail, user)
        UserAsyncImage(boxScope = this, user = user, showUserImage = showUserImage)
    }
}

@Composable
private fun BackgroundImage(user: User) {
    Image(
        painter = painterResource(
            id = BackgroudPhotoProvider.getPhoto(user.getPhotoNumber()),
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun BottomBar(modifier: Modifier = Modifier) {
    TopAppBar(
        title = { },
        actions = {
            BottomAction(R.drawable.camera)
            Spacer(modifier = Modifier.width(8.dp))
            BottomAction(R.drawable.edit)
        },
        colors = topAppBarColors(MaterialTheme.colorScheme.onSurface),
        windowInsets = WindowInsets(top = 4.dp),
        modifier = modifier.height(42.dp),
    )
}

@Composable
private fun BottomAction(@DrawableRes drawable: Int, modifier: Modifier = Modifier) {
    IconButton(
        onClick = { },
        modifier = modifier,
    ) {
        Icon(painter = painterResource(id = drawable), contentDescription = null)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(
    onBackDetail: () -> Unit,
    user: User,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = onBackDetail) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = null,
                )
            }
        },
        title = { Text(text = user.name.uppercase(Locale.ROOT)) },
        colors = topAppBarColors(Neutral95),
        actions = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.submenu),
                    contentDescription = null,
                )
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun UserAsyncImage(
    boxScope: BoxScope,
    user: User,
    modifier: Modifier = Modifier,
    showUserImage: Boolean = false
) {
    with(boxScope) {
        AnimatedVisibility(
            visible = showUserImage,
            modifier = modifier.align(Alignment.BottomStart),
        ) {
            AsyncImage(
                model = user.picture,
                contentDescription = user.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .size(82.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.background, CircleShape),
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun topAppBarColors(contentColor: Color = MaterialTheme.colorScheme.inverseOnSurface) =
    TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        navigationIconContentColor = contentColor,
        titleContentColor = contentColor,
        actionIconContentColor = contentColor,
    )

@Preview
@Composable
private fun UserDetailTopBarPreview() {
    DeveloperTestsScreen {
        Scaffold(
            topBar = { UserDetailTopBar(FakeUsers.users.first()) {} },
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
            )
        }
    }
}
