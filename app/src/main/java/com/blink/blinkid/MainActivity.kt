package com.blink.blinkid

import android.os.Bundle
import android.util.Log
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
import com.blink.blinkid.ui.AddStudentScreen
import com.blink.blinkid.ui.ExamDetailsScreen
import com.blink.blinkid.ui.theme.BlinkIdTheme
import com.blink.blinkid.ui.ExamListScreen
import com.blink.blinkid.ui.HeaderText
import com.blink.blinkid.ui.HomeScreen
import com.blink.blinkid.ui.LoginScreen
import com.blink.blinkid.ui.StudentExamVerificationScreen
import com.blink.blinkid.ui.StudentListScreen
import com.blink.blinkid.ui.student.StudentDashBoard
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
    val examViewModel: ExamViewModel = hiltViewModel()

    NavHost(
        navController = navController,
        startDestination = Navigation.Routes.LOGIN
    ) {
        composable(Navigation.Routes.LOGIN) {
            LoginScreen(navController, loginViewModel = hiltViewModel())
        }
        composable(Navigation.Routes.HOME) {
            HomeScreen(navController, loginViewModel = hiltViewModel())
        }
        composable(Navigation.Routes.EXAMS) {
            ExamListScreen(navController, examViewModel)
        }
        composable(Navigation.Routes.ADD_EXAM) {
            AddExamScreen(navController)
        }
        composable(Navigation.Routes.EXAM_DETAIL + "/{examId}") {
            ExamDetailsScreen(navController, examViewModel)
        }
        composable(Navigation.Routes.STUDENT_LIST) {
            StudentListScreen(navController, examViewModel)
        }
        composable(Navigation.Routes.ADD_STUDENT) {
            AddStudentScreen(navController)
        }
        composable(Navigation.Routes.STUDENT_DASHBOARD) {
            StudentDashBoard(navController, loginViewModel = hiltViewModel())
        }
        composable(Navigation.Routes.STUDENT_EXAM_VERIFICATION) {
            StudentExamVerificationScreen(navController, examViewModel)
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
        const val STUDENT_LIST = "student_list"
        const val ADD_STUDENT = "add_student"
        const val STUDENT_DASHBOARD = "student_dashboard"
        const val STUDENT_EXAM_VERIFICATION = "student_exam_verification"
    }

}


