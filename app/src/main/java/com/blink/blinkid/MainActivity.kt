package com.blink.blinkid

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.blink.blinkid.commons.NetworkResult
import com.blink.blinkid.ui.AddExamScreen
import com.blink.blinkid.ui.ExamDetailsScreen
import com.blink.blinkid.ui.theme.BlinkIdTheme
import com.blink.blinkid.ui.ExamListScreen
import com.blink.blinkid.viewmodel.ExamViewModel
import com.blink.blinkid.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlinkIdTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyApp()
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = hiltViewModel()
    val examViewModel: ExamViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = if (loginViewModel.isLoggedIn()) Navigation.Routes.HOME else Navigation.Routes.LOGIN
    ) {
        composable(Navigation.Routes.LOGIN) {
            LoginScreen(navController, loginViewModel)
        }
        composable(Navigation.Routes.HOME) {
            HomeScreen(navController)
        }
        composable(Navigation.Routes.EXAMS) {
            ExamListScreen(navController, examViewModel)
        }
        composable(Navigation.Routes.ADD_EXAM) {
            AddExamScreen(navController, examViewModel)
        }
        composable(Navigation.Routes.EXAM_DETAIL+"/{examId}") {
            ExamDetailsScreen(navController, examViewModel)
        }
    }
}


object Navigation {

    object Args {
        const val USER_ID = "user_id"
    }

    object Routes {
        const val HOME = "home"
        const val LOGIN = "login"
        const val EXAMS = "exams"
        const val EXAM_DETAIL = "examDetail"
        const val ADD_EXAM = "add_exam"
    }

}


@Composable
fun LoginScreen(navController: NavController, loginViewModel: LoginViewModel) {

    val context = LocalContext.current

    var toastMessage by remember { mutableStateOf("") }

    LaunchedEffect(toastMessage) {
        toastMessage.takeIf { it.isNotEmpty() }?.let { msg ->
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
            toastMessage = ""
        }
    }

    val loginResponse by loginViewModel.loginResponse.collectAsState(initial = NetworkResult.Initial)

    LaunchedEffect(loginResponse) {
        when (loginResponse) {
            is NetworkResult.Initial -> {

            }

            is NetworkResult.Success -> {
                toastMessage = "Login successful"
                navController.navigate(Navigation.Routes.HOME)
            }

            is NetworkResult.Error -> {
                toastMessage =
                    "Login failed: ${(loginResponse as NetworkResult.Error).errorMessage}"
            }

            else -> {
                toastMessage = "Loading..."
            }
        }
    }


    // UI code for login screen
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Login",
            fontSize = 24.sp,
            modifier = Modifier.padding(20.dp)
        )
        TextField(
            value = email,
            onValueChange = {
                email = it
            },
            label = {
                Text("Email")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Password")
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    toastMessage = "Email and password cannot be empty"
                    return@Button
                }
                loginViewModel.login(email, password)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Login")
        }

    }

}


@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                navController.navigate(Navigation.Routes.EXAMS)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Exams")
        }

        Button(
            onClick = {
                navController.navigate(Navigation.Routes.ADD_EXAM)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Add exam")
        }
    }
}
