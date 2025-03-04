package org.emily.music.presentation.wrapperbar.component

import org.emily.music.presentation.playingsong.PlayingSongState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import org.emily.core.Screen
import org.emily.core.utils.UiEvent
import org.emily.music.presentation.wrapperbar.viewmodel.WrapperBarEvent
import org.emily.music.presentation.wrapperbar.viewmodel.WrapperBarViewModel
import org.emily.project.Fonts
import org.emily.project.secondaryColor

@Composable
fun BottomBar(
    vm: WrapperBarViewModel,
    onNavigate: (to: Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val playingSong by PlayingSongState.currentPlayingSong.collectAsState()
    val isPlaying by PlayingSongState.isPlaying.collectAsState()

    LaunchedEffect(vm) {
        vm.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {}
                is UiEvent.Navigate -> {
                    onNavigate(event.to)
                }
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(secondaryColor)
            .padding(vertical = 20.dp, horizontal = 32.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            if (playingSong != null) {
                Text(
                    text = playingSong!!.title,
                    color = Color.White,
                    fontFamily = Fonts.montserratFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = playingSong!!.artists.joinToString(),
                    color = Color.White,
                    fontFamily = Fonts.montserratFontFamily,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            } else {
                Text(
                    text = "------",
                    color = Color.White,
                    fontFamily = Fonts.montserratFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "---",
                    color = Color.White,
                    fontFamily = Fonts.montserratFontFamily,
                    fontSize = 12.sp
                )
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center)
        ) {
            IconButton(
                onClick = { vm.onEvent(WrapperBarEvent.OnPlayPreviousSong) }
            ) {
                Icon(
                    imageVector = Icons.Default.SkipPrevious,
                    contentDescription = "Previous Song",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = { PlayingSongState.togglePlayingState() }
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Rounded.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White
                )
            }

            IconButton(
                onClick = { vm.onEvent(WrapperBarEvent.OnSkipSong) }
            ) {
                Icon(
                    imageVector = Icons.Default.SkipNext,
                    contentDescription = "Next Song",
                    tint = Color.White,
                )
            }
        }

        IconButton(
            modifier = Modifier.align(Alignment.CenterEnd),
            onClick = { vm.onEvent(WrapperBarEvent.OnOpenHome) }
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home Icon",
                tint = Color.White,
            )
        }
    }
}
