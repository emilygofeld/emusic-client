package org.emily.music.presentation.home.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.emily.project.Fonts
import org.emily.project.primaryColor
import org.emily.project.secondaryColor

@Composable
fun CreatePlaylistBar(
    isVisible: Boolean,
    onCreatePlaylist: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var playlistTitle by remember { mutableStateOf("") }

    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it })
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .fillMaxHeight(0.3f)
                .padding(16.dp)
                .background(secondaryColor)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Create New Playlist",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Fonts.montserratFontFamily
                )

                Spacer(modifier = Modifier.weight(0.2f))

                TextField(
                    value = playlistTitle,
                    onValueChange = { playlistTitle = it },
                    placeholder = {
                        Text(
                            "Enter playlist name",
                            color = Color.White.copy(alpha = 0.6f),
                            fontFamily = Fonts.montserratFontFamily
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.White.copy(alpha = 0.1f),
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.weight(0.1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = { onDismiss() },
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        elevation = ButtonDefaults.elevation(0.dp)
                    ) {
                        Text(
                            "Cancel",
                            color = Color.White,
                            fontFamily = Fonts.montserratFontFamily
                        )
                    }
                    Button(
                        onClick = {
                            if (playlistTitle.isNotBlank()) {
                                onCreatePlaylist(playlistTitle)
                                playlistTitle = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = primaryColor)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Create Playlist",
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            "Create",
                            color = Color.Black,
                            fontFamily = Fonts.montserratFontFamily
                        )
                    }
                }
            }
        }
    }
}