package com.argz.cryptoapp.pages.login

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.argz.cryptoapp.domain.RequestState
import com.argz.cryptoapp.pages.components.ErrorDialog

@Composable
fun LoginPage(
    onLoginSuccess: () -> Unit,
    loginViewModel: LoginViewModel,
) {
    val currentUser by loginViewModel.currentUser.collectAsState()
    val username = remember { mutableStateOf("test@test.com") }
    val password = remember { mutableStateOf("123456") }

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedContent(
                targetState = currentUser.isLoading()
            ) { loading ->
                Column {
                    if(loading){
                        CircularProgressIndicator()
                    } else {
                        LoginContent(
                            username,
                            password,
                            onLoginClick = { loginViewModel.login(username.value, password.value) },
                        )
                    }
                }
            }

            if(currentUser.isSuccess()) {
                onLoginSuccess()
            }

            if(currentUser.isError()) {
                ErrorDialog(
                    icon = Icons.AutoMirrored.Filled.Login,
                    dialogTitle = "Login error",
                    dialogText = if (currentUser is RequestState.Error) (currentUser as RequestState.Error).message else "",
                    onDismissRequest = {
                        loginViewModel.resetState()
                    },
                )
            }
        }
    }
}

@Composable
fun LoginContent(
    username: MutableState<String>,
    password: MutableState<String>,
    onLoginClick: () -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp),
        label = { Text(text = "Email") },
        value = username.value,
        onValueChange = { value -> username.value = value }
    )
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
        label = { Text(text = "Password") },
        value = password.value,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        onValueChange = { value -> password.value = value }
    )
    Button(
        shape = RoundedCornerShape(10),
        onClick = { onLoginClick() },
        modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(text = "Login")
    }
}