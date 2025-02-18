package org.emily.music.presentation.playlist.component

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shuffle
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
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.emily.music.domain.models.Song
import org.emily.music.presentation.playlist.viewmodel.PlaylistEvent
import org.emily.music.presentation.playlist.viewmodel.PlaylistViewModel
import org.emily.project.black
import org.emily.project.primaryColor
import org.emily.project.secondaryColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlaylistScreen(
    vm: PlaylistViewModel,
    songsForTesting: List<Song> = emptyList()
) {
    val state = vm.state

    var isSongMoreOptionsVisible by remember { mutableStateOf(false) }
    var selectedSong by remember { mutableStateOf<Song?>(null) }

    var isAddSongBarVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(224.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(secondaryColor)
            ) {
                Icon(
                    modifier = Modifier.size(120.dp).align(Alignment.Center),
                    imageVector = if (state.playlist.title == "Favorites") Icons.Default.Favorite else Icons.Default.MusicNote,
                    contentDescription = "Music note",
                    tint = primaryColor
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Public Playlist",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 12.sp
                )

                Text(
                    text = state.playlist.title,
                    color = Color.White,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(secondaryColor)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "person",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp).align(Alignment.Center)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "${state.playlist.ownerName} • ${state.playlist.songs.size} songs • ${formatPlaylistDuration(state.playlist.songs.sumOf { it.length })}",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(56.dp)
                    .background(primaryColor, RoundedCornerShape(28.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                    tint = black,
                    modifier = Modifier.size(32.dp)
                )
            }

            IconButton(
                onClick = {
                    isAddSongBarVisible = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Shuffle,
                    contentDescription = "Shuffle",
                    tint = Color.White.copy(alpha = 0.7f)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = {
                    isAddSongBarVisible = true
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "More options",
                    tint = Color.White.copy(alpha = 0.7f),
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(state.playlist.songs) { song ->
                var isHovered by remember { mutableStateOf(false) }

                Box(
                    modifier = Modifier
                        .onPointerEvent(PointerEventType.Enter) {
                            isHovered = true
                        }
                        .onPointerEvent(PointerEventType.Exit) {
                            isHovered = false
                        }
                ) {
                    SongComponent(
                        song = song,
                        onPlayClick = {
                            PlayingSongState.playSong(song)
                        },
                        onTogglePlayButton = {
                            PlayingSongState.togglePlayingState()
                        },
                        onFavoriteClick = {
                            vm.onEvent(PlaylistEvent.OnFavoriteChange(song))
                        },
                        onMoreClick = {
                            selectedSong = song
                            isSongMoreOptionsVisible = true
                        },
                        isHovered = isHovered
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }

    if (isSongMoreOptionsVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable { isSongMoreOptionsVisible = false },
            contentAlignment = Alignment.Center
        ) {
            SongMoreOptionsBar(
                onAddToQueue = { /* Handle Add */ },
                onDelete = {
                    selectedSong?.id?.let {
                        vm.onEvent(PlaylistEvent.OnDeleteSong(state.playlist.id, it))
                    }
                    isSongMoreOptionsVisible = false
                },
                onDismiss = { isSongMoreOptionsVisible = false }
            )
        }
    }

    if (isAddSongBarVisible) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.3f))
                .clickable { isAddSongBarVisible = false },
            contentAlignment = Alignment.Center
        ) {
            AddSongToPlaylistBar(
                state.searchSongBarText,
                state.searchedSongs,
                onSearchChanged = { updatedSearchText ->
                    vm.onEvent(PlaylistEvent.OnSearchSong(updatedSearchText))
                },
                onAddSong = { songId ->
                    vm.onEvent(PlaylistEvent.OnAddSong(state.playlist.id, songId))
                },
                onDismiss = { isAddSongBarVisible = false }
            )
        }
    }
}


fun formatPlaylistDuration(durationInSeconds: Int): String {
    val hours = durationInSeconds / 3600
    val minutes = (durationInSeconds % 3600) / 60
    val seconds = durationInSeconds % 60

    return when {
        hours > 0 -> "%d hr %02d min %02d sec".format(hours, minutes, seconds)
        minutes > 0 -> "%d min %02d sec".format(minutes, seconds)
        else -> "%d sec".format(seconds)
    }
}


