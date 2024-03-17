package com.blink.blinkid.ui.staff

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.blink.blinkid.Navigation
import com.blink.blinkid.ui.teacher.HeaderText
import com.blink.blinkid.viewmodel.LoginViewModel


@Composable
fun StaffHomeScreen(navController: NavController, loginViewModel: LoginViewModel) {

    LaunchedEffect(true) {
        if (!loginViewModel.isLoggedIn()) {
            navController.navigate(Navigation.Routes.LOGIN)
        }
        Log.e("StaffHomeScreen", "HomeScreen: ${loginViewModel.getUser()}")
    }
    Column {
        HeaderText(text = "Welcome ${loginViewModel.getUser()?.username ?: ""}")

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navController.navigate(Navigation.Routes.GROUPS)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Groups")
            }

            Button(
                onClick = {
                    navController.navigate(Navigation.Routes.ADD_GROUP)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Add group")
            }

            Button(
                onClick = {
                    navController.navigate(Navigation.Routes.STUDENT_LIST)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Student list")
            }

            Button(
                onClick = {
                    navController.navigate(Navigation.Routes.ADD_STUDENT)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Add student")
            }

            Button(
                onClick = {
                    loginViewModel.logout()
                    navController.navigate(Navigation.Routes.LOGIN) {
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Logout")
            }
        }
    }
}
