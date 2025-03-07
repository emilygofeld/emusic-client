package org.emily.music.presentation.home.components

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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.emily.music.presentation.utils.MoreOptionButton
import org.emily.project.secondaryColor

@Composable
fun PlaylistIconMoreOptionsComponent(
    onEditDetails: () -> Unit,
    onAddToQueue: () -> Unit,
    onDelete: () -> Unit,
    onDismiss: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxWidth(0.2f)
            .fillMaxHeight(0.4f)
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
                text = "Edit Details",
                icon = Icons.Default.Edit,
                color = Color.White,
                onClick = {
                    onEditDetails()
                    onDismiss()
                }
            )

            Spacer(modifier = Modifier.weight(0.2f))

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
                text = "Delete",
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

