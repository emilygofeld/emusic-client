package org.emily.auth.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import org.emily.auth.presentation.UiEvent
import org.emily.auth.presentation.auth.viewmodel.AuthEvent
import org.emily.auth.presentation.auth.viewmodel.AuthViewModel
import org.emily.core.Screen
import org.emily.project.black
import org.emily.project.primaryColor
import org.emily.project.secondaryColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignInScreen(
    vm: AuthViewModel,
    onNavigate: (to: Screen) -> Unit
) {
    val state = vm.state
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(vm) {
        vm.uiEvent.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
                is UiEvent.Navigate -> {
                    vm.onEvent(AuthEvent.Clear)
                    onNavigate(event.to)
                }
            }
        }
    }

    val gradientBackground = Brush.verticalGradient(
        colors = listOf(primaryColor, black)
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    )  { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(brush = gradientBackground)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome Back",
                    color = secondaryColor,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Log in to continue your musical journey",
                    color = Color.White,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(80.dp))

                OutlinedTextField(
                    value = state.signInUsername,
                    onValueChange = {
                        vm.onEvent(AuthEvent.SignInUsernameChanged(it))
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .width(400.dp),
                    placeholder = {
                        Text(
                            text = "Enter username...",
                            color = Color.White
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Person,
                            tint = Color.White,
                            contentDescription = "Username"
                        )
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = secondaryColor,
                        unfocusedBorderColor = Color.White
                    ),
                    shape = RoundedCornerShape(4.dp),
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = state.signInPassword,
                    onValueChange = {
                        vm.onEvent(AuthEvent.SignInPasswordChanged(it))
                    },
                    modifier = Modifier
                        .height(56.dp)
                        .width(400.dp),
                    placeholder = {
                        Text(
                            text = "Enter password...",
                            color = Color.White
                        )
                    },
                    leadingIcon = {
                        Icon(
                            Icons.Default.Lock,
                            tint = Color.White,
                            contentDescription = "Password",
                        )
                    },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = secondaryColor,
                        unfocusedBorderColor = Color.White
                    ),
                    shape = RoundedCornerShape(4.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        vm.onEvent(AuthEvent.SignIn)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = secondaryColor,
                        contentColor = primaryColor
                    ),
                    shape = RoundedCornerShape(32.dp),
                    modifier = Modifier
                        .height(48.dp)
                        .width(240.dp)
                ) {
                    Text(
                        text = "Log in",
                        style = MaterialTheme.typography.h6,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account?",
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "Sign up",
                        fontSize = 16.sp,
                        color = secondaryColor,
                        modifier = Modifier.onClick {
                            vm.onEvent(
                                AuthEvent.ToSignUp
                            )
                        }
                    )
                }
            }
        }
    }
}