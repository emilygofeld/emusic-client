package org.emily.music.presentation.home

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import org.emily.core.Screen
import org.emily.core.utils.UiEvent
import org.emily.music.domain.models.Playlist
import org.emily.music.presentation.home.components.CreateEditPlaylistBar
import org.emily.music.presentation.home.components.PlaylistIcon
import org.emily.music.presentation.home.components.PlaylistIconMoreOptionsComponent
import org.emily.music.presentation.home.components.RecentCard
import org.emily.music.presentation.home.viewmodel.HomeEvent
import org.emily.music.presentation.home.viewmodel.HomeViewModel
import org.emily.music.presentation.playingsong.PlayingSongState
import org.emily.music.presentation.playingsong.QueueStateManager
import org.emily.project.Fonts
import org.emily.project.black
import org.emily.project.primaryColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun HomeScreen(
    vm: HomeViewModel,
    onNavigate: (to: Screen) -> Unit,
) {
    val state = vm.state

    LaunchedEffect(vm) {
        vm.onEvent(HomeEvent.OnGetGlobalLikedSongs)

        vm.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {}
                is UiEvent.Navigate -> {
                    onNavigate(event.to)
                }
            }
        }
    }

    var isCreatePlaylistBarVisible by remember { mutableStateOf(false) }
    var selectedPlaylist by remember { mutableStateOf<Playlist?>(null) }
    var isMoreOptionsVisible by remember { mutableStateOf(false) }
    var isEditDetailsVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(black)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LibraryMusic,
                        contentDescription = "Music Library",
                        tint = primaryColor,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.weight(0.1f))
                    Text(
                        text = "Your Musical Library",
                        fontFamily = Fonts.montserratFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White.copy(0.7f),
                        fontSize = 16.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Visible
                    )

                    Spacer(modifier = Modifier.weight(0.5f))

                    IconButton(
                        onClick = { isCreatePlaylistBarVisible = true }
                    ) {
                        Icon(
                            Icons.Default.Add,
                            contentDescription = "Create Playlist",
                            tint = Color.White.copy(0.7f)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                LazyColumn(
                    modifier = Modifier.padding(8.dp)
                ) {
                    items(state.recentPlaylists) { playlist ->

                        var isHovered by remember { mutableStateOf(false) }

                        Box(
                            modifier = Modifier
                                .onPointerEvent(PointerEventType.Enter) {
                                    isHovered = true
                                }
                                .onPointerEvent(PointerEventType.Exit) {
                                    isHovered = false
                                }
                                .clickable { vm.onEvent(HomeEvent.OnClickPlaylist(playlist = playlist)) }
                        ) {
                            PlaylistIcon(
                                playlist = playlist,
                                isHovered = isHovered,
                                onMoreOptionsClick = {
                                    selectedPlaylist = playlist
                                    isMoreOptionsVisible = true
                                }
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                    }
                }
            }

            Column(
                modifier = Modifier.weight(3f).padding(16.dp)
            ) {
                LazyColumn {

                    item {
                        Text(
                            text = "Recent Songs",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                        )
                    }

                    items(QueueStateManager.getRecentSongs().reversed().chunked(4)) { songGroup ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            songGroup.forEach { song ->
                               RecentCard(
                                   title = song.title,
                                   artists = song.artists,
                                   id = song.id,
                                   onPlay = { PlayingSongState.togglePlayingState(song) }
                               )
                            }
                        }
                    }

                    item {
                        Text(
                            text = "Most Liked Songs",
                            color = Color.White,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp),
                        )
                    }

                    items(state.globalFavoriteSongs.chunked(4)) { faveSongGroup ->
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            faveSongGroup.forEach { faveSong ->
                                RecentCard(
                                    title = faveSong.title,
                                    artists = faveSong.artists,
                                    id = faveSong.id,
                                    onPlay = { PlayingSongState.togglePlayingState(faveSong) }
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isCreatePlaylistBarVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CreateEditPlaylistBar(
                    isVisible = isCreatePlaylistBarVisible,
                    onCreatePlaylist = { title ->
                        vm.onEvent(HomeEvent.OnCreatePlaylist(title))
                        isCreatePlaylistBarVisible = false
                    },
                    onDismiss = { isCreatePlaylistBarVisible = false },
                    actionText = "Create"
                )
            }
        }

        if (isMoreOptionsVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
                    .clickable { isMoreOptionsVisible = false },
            contentAlignment = Alignment.Center
            ) {
                PlaylistIconMoreOptionsComponent(
                    onEditDetails = {
                        isEditDetailsVisible = true
                        isMoreOptionsVisible = false
                    },
                    onAddToQueue = {
                        selectedPlaylist?.songs?.forEach { song ->
                            QueueStateManager.addSongToQueue(song)
                        }
                    },
                    onDelete = {
                        selectedPlaylist?.let {
                            HomeEvent.OnDeletePlaylist(it.id)
                        } ?.let { vm.onEvent(it) }
                    },
                    onDismiss = { isMoreOptionsVisible = false }
                )
            }
        }

        if (isEditDetailsVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f)),
                contentAlignment = Alignment.Center
            ) {
                CreateEditPlaylistBar(
                    isVisible = isEditDetailsVisible,
                    onCreatePlaylist = { title ->
                        selectedPlaylist?.copy(title = title)
                            ?.let { HomeEvent.OnEditPlaylistDetails(it) }
                            ?.let { vm.onEvent(it) }
                        isEditDetailsVisible = false
                    },
                    onDismiss = { isEditDetailsVisible = false },
                    actionText = "Edit"
                )
            }
        }
    }
}