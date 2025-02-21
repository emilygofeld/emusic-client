package org.emily.music.presentation.wrapperbar.screen

import PlayingSongState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SearchOff
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
import org.emily.music.presentation.playlist.component.formatDuration
import org.emily.project.Fonts
import org.emily.project.black
import org.emily.project.primaryColor
import org.emily.project.secondaryColor

@Composable
fun SearchBarScreen(
    songs: List<Song>
) {
    var selectedSongId by remember { mutableStateOf<ID?>(null) }
    var isTopSongPlaying by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        if (songs.isEmpty()) {
            Text(
                "No matches found",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Fonts.montserratFontFamily,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = "Not found",
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        } else {
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    "Top result",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Fonts.montserratFontFamily,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(secondaryColor)
                        .padding(16.dp)
                ) {
                    songs.firstOrNull()?.let { topSong ->

                        isTopSongPlaying = PlayingSongState.isPlaying.value && PlayingSongState.currentPlayingSong.value?.id == topSong.id

                        Text(
                            topSong.title,
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            "Song • ${topSong.artists.joinToString()}",
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        IconButton(
                            onClick = {
                                selectedSongId = topSong.id
                                PlayingSongState.togglePlayingState(topSong)
                                isTopSongPlaying = !isTopSongPlaying
                            },
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(primaryColor)
                        ) {
                            Icon(
                                if (isTopSongPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isTopSongPlaying) "Pause" else "Play",
                                tint = Color.Black,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }

            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Songs",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(songs) { song ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selectedSongId == song.id) secondaryColor else Color.Transparent)
                                .padding(8.dp)
                                .clickable {
                                    selectedSongId = song.id
                                    PlayingSongState.playSong(song)
                                    isTopSongPlaying = PlayingSongState.isPlaying.value && PlayingSongState.currentPlayingSong.value?.id == songs.firstOrNull()?.id
                                },
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(secondaryColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.MusicNote,
                                    contentDescription = "music note",
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

                            Text(
                                formatDuration(song.length),
                                color = Color.White.copy(alpha = 0.7f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}