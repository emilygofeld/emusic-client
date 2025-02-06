package org.emily.music.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.emily.music.domain.models.Playlist
import org.emily.project.black
import org.emily.project.primaryColor
import org.emily.project.secondaryColor

@Composable
fun PlaylistIcon(
    playlist: Playlist,
    isHovered: Boolean,
    onMoreOptionsClick: () -> Unit
) {

    var isMoreOptionsVisible by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(if (isHovered) secondaryColor.copy(alpha = 0.6f) else black)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(secondaryColor)
                .size(56.dp)
                .padding(8.dp)
        ) {
            Icon(
                modifier = Modifier.size(32.dp).align(Alignment.Center),
                imageVector = if (isHovered) Icons.Default.PlayArrow else Icons.Default.MusicNote,
                contentDescription = "Music note or play arrow",
                tint = if (isHovered) primaryColor else Color.White
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = playlist.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "Playlist • ${playlist.ownerName}",
                color = Color.White,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        IconButton(
            onClick = { onMoreOptionsClick() }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More options",
                tint = Color.White,
            )
        }

        if (isMoreOptionsVisible) {
            Box(
                modifier = Modifier
                    .padding(top = 56.dp)
                    .align(Alignment.Top)
            ) {
                PlaylistIconMoreOptionsComponent(
                    onEditDetails = {},
                    onAddToQueue = {},
                    onDelete = {},
                    onDismiss = { isMoreOptionsVisible = false }
                )
            }
        }
    }
}




