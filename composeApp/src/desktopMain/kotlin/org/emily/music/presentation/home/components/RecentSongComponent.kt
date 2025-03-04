package org.emily.music.presentation.home.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.emily.music.domain.models.Song
import org.emily.music.presentation.playingsong.PlayingSongState
import org.emily.music.presentation.utils.WaveAnimation
import org.emily.project.Fonts
import org.emily.project.primaryColor
import org.emily.project.secondaryColor

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RecentCard(
    song: Song,
    onPlay: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isHovered by remember { mutableStateOf(false) }

    val scale by animateFloatAsState(if (isHovered) 1.05f else 1f)
    val iconScale by animateFloatAsState(if (isHovered) 1.2f else 1f)

    val currentPlayingSong by PlayingSongState.currentPlayingSong.collectAsState()
    val isPlaying by PlayingSongState.isPlaying.collectAsState()

    val isThisSongPlaying = isPlaying && currentPlayingSong?.id == song.id

    Column(
        modifier = modifier
            .width(180.dp)
            .scale(scale)
            .clip(RoundedCornerShape(12.dp))
            .onPointerEvent(PointerEventType.Enter) {
                isHovered = true
            }
            .onPointerEvent(PointerEventType.Exit) {
                isHovered = false
            }
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
                .background(secondaryColor.copy(alpha = 0.5f)),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { onPlay() }
            ) {
                if (isThisSongPlaying)
                    WaveAnimation()
                else {
                    Icon(
                        imageVector = if (isHovered) Icons.Default.PlayArrow else Icons.Default.MusicNote,
                        contentDescription = "Song",
                        tint = if (isHovered) primaryColor else secondaryColor.copy(alpha = 0.8f),
                        modifier = Modifier
                            .size(64.dp)
                            .scale(iconScale)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = song.title,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontFamily = Fonts.montserratFontFamily
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = song.artists.joinToString(),
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 12.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            fontFamily = Fonts.montserratFontFamily,
            lineHeight = 20.sp
        )
    }
}