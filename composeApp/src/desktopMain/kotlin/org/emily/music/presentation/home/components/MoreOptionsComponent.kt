package org.emily.music.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.unit.dp
import org.emily.project.primaryColor
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MoreOptionButton(
    text: String,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit
) {

    var isHovered by remember { mutableStateOf(false) }

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        elevation = null,
        modifier = Modifier.
            fillMaxWidth()
            .onPointerEvent(PointerEventType.Enter) {
                isHovered = true
            }
            .onPointerEvent(PointerEventType.Exit) {
                isHovered = false
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = if (isHovered) primaryColor else color
            )
            Spacer(modifier = Modifier.weight(0.3f))
            Text(
                text = text,
                color = if (isHovered) primaryColor else color
            )
        }
    }
}