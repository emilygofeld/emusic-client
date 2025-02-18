package org.emily.music.presentation.playlist.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.emily.core.constants.ID
import org.emily.music.domain.models.Song
import org.emily.project.Fonts
import org.emily.project.black
import org.emily.project.primaryColor
import org.emily.project.secondaryColor

@Composable
fun AddSongToPlaylistBar(
    searchBarText: String,
    songs: List<Song>,
    onSearchChanged: (String) -> Unit,
    onAddSong: (ID) -> Unit,
    onDismiss: () -> Unit
) {

    var selectedSongId by remember { mutableStateOf<ID?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.4f)
            .fillMaxHeight(0.7f)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(secondaryColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(black)
                .padding(vertical = 16.dp, horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = searchBarText,
                onValueChange = { updatedSearchText -> onSearchChanged(updatedSearchText) },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .width(400.dp),
                placeholder = {
                    Text(
                        text = "Search song",
                        fontFamily = Fonts.montserratFontFamily,
                        color = Color.White,
                        fontSize = 16.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        tint = Color.White,
                        contentDescription = "Search"
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = Color.White,
                    focusedBorderColor = Color.White,
                    unfocusedBorderColor = Color.White,
                    backgroundColor = secondaryColor
                ),
                shape = RoundedCornerShape(40.dp),
                singleLine = true,
                maxLines = 1
            )
        }

        Spacer(modifier = Modifier.height(4.dp).background(secondaryColor))

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(songs) { song ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(Color.Transparent)
                        .clickable { selectedSongId = song.id }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(secondaryColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MusicNote,
                            contentDescription = "Music note",
                            tint = primaryColor
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            song.title,
                            color = if (selectedSongId == song.id) primaryColor else Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            song.artists.joinToString(),
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 12.sp
                        )
                    }

                    IconButton(
                        onClick = {
                            onAddSong(song.id)
                            onDismiss()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.AddCircle,
                            tint = Color.White,
                            contentDescription = "Add"
                        )
                    }
                }
            }
        }
    }
}