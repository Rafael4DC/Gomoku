package isel.game.gomoku.ui.screens.about.components

import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import isel.pdm.gomoku.R

/**
 * Composable used to display information about the author of the application
 */
@Composable
fun Author(authorInfo: AuthorInfo, onSendEmailRequested: (String) -> Unit = { }, onOpenUrlRequested: (Uri) -> Unit) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.clickable { onSendEmailRequested(authorInfo.email) }
        ) {
            Image(
                painter = painterResource(id = authorInfo.imageId),
                contentDescription = null,
                modifier = Modifier.sizeIn(150.dp, 150.dp, 150.dp, 150.dp)
            )
            Text(text = authorInfo.name, style = MaterialTheme.typography.titleLarge)
        }

        Social(link = authorInfo.git, onOpenUrlRequested = onOpenUrlRequested)
    }
}

@Composable
private fun Social(link: Uri, onOpenUrlRequested: (Uri) -> Unit = { }, @DrawableRes id: Int = R.drawable.ic_github) {
    Image(
        painter = painterResource(id = id),
        contentDescription = null,

        modifier = Modifier
            .sizeIn(maxWidth = 64.dp)
            .clickable { onOpenUrlRequested(link) }
    )
}