package com.argz.cryptoapp.pages.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfilePage(
    onBackPress: () -> Unit,
    onLogout: () -> Unit,
) {
    val profileViewModel = koinViewModel<ProfileViewModel>()
    val userInfo by profileViewModel.userDetails.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onBackPress()
                        }
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            if(userInfo.isSuccess()){
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = userInfo.getSuccessData().name ?: ""
                )
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = userInfo.getSuccessData().email ?: ""
                )
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )
            Button(
                shape = RoundedCornerShape(10),
                onClick = { onLogout() },
                modifier = Modifier.fillMaxWidth().padding(top = 16.dp, start = 16.dp, end = 16.dp),
            ) {
                Text(text = "Logout")
            }
        }
    }
}