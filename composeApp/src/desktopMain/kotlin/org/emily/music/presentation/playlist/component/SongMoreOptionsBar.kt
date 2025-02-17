package org.emily.music.presentation.playlist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.emily.music.presentation.utils.MoreOptionButton
import org.emily.project.secondaryColor

@Composable
fun SongMoreOptionsBar(
    onAddToQueue: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth(0.2f)
            .fillMaxHeight(0.3f)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(secondaryColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            MoreOptionButton(
                text = "Add to Queue",
                icon = Icons.Default.Add,
                color = Color.White,
                onClick = {
                    onAddToQueue()
                    onDismiss()
                }
            )

            Spacer(modifier = Modifier.weight(0.2f))

            MoreOptionButton(
                text = "Delete Song",
                icon = Icons.Default.Delete,
                color = Color.Red,
                onClick = {
                    onDelete()
                    onDismiss()
                }
            )
        }
    }
}

