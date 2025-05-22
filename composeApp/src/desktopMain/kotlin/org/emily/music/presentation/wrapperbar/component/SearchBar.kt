package org.emily.music.presentation.wrapperbar.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import org.emily.core.Screen
import org.emily.core.utils.UiEvent
import org.emily.music.presentation.wrapperbar.viewmodel.WrapperBarEvent
import org.emily.music.presentation.wrapperbar.viewmodel.WrapperBarViewModel
import org.emily.project.Fonts
import org.emily.project.black
import org.emily.project.primaryColor
import org.emily.project.secondaryColor

@Composable
fun SearchBar(
    vm: WrapperBarViewModel,
    onNavigate: (to: Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = vm.state

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

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(black)
            .padding(vertical = 16.dp, horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

//        IconButton(
//            onClick = {}
//        ) {
//            Icon(
//                imageVector = Icons.AutoMirrored.Filled.Logout,
//                contentDescription = "logout",
//                tint = Color.White.copy(alpha = 0.7f)
//            )
//        }

        OutlinedTextField(
            value = state.searchBarText,
            onValueChange = {
                vm.onEvent(WrapperBarEvent.OnSearchBarChange(it))
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .width(400.dp),
            placeholder = {
                Text(
                    text = "What would you like to play?",
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
                focusedBorderColor = primaryColor,
                unfocusedBorderColor = Color.White,
                backgroundColor = secondaryColor
            ),
            shape = RoundedCornerShape(40.dp),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(4.dp).background(secondaryColor))
    }
}
