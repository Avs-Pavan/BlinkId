package com.blink.blinkid.ui.theme

import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.blink.blinkid.Exam
import com.blink.blinkid.commons.NetworkResult
import com.blink.blinkid.viewmodel.ExamViewModel

@Composable
fun ExamListScreen(navController: NavController, examViewModel: ExamViewModel) {
    var examList by remember { mutableStateOf<List<Exam>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }

    val result by examViewModel.exams.collectAsState(NetworkResult.Initial)

    LaunchedEffect(true) {
        examViewModel.getExams()
    }
    when (result) {
        is NetworkResult.Success -> {
            isLoading = false
            Log.e(
                "ExamListScreen",
                "ExamListScreen: ${result as NetworkResult.Success<List<Exam>>}"
            )
            examList = (result as NetworkResult.Success<List<Exam>>).body!!
        }

        is NetworkResult.Error -> {
            isLoading = false

            Log.e(
                "ExamListScreen",
                "ExamListScreen: ${result as NetworkResult.Error<List<Exam>>}"
            )
        }

        is NetworkResult.Loading -> {
            Log.e("ExamListScreen", "ExamListScreen: loading...")
            isLoading = true
        }

        else -> {
            Log.e("ExamListScreen", "ExamListScreen: else...")
        }
    }


    Box(modifier = Modifier.padding(10.dp)) {

        if (isLoading) {
            ProgressBar()
        } else {
            ExamList(examList) {
                navController.navigate("examDetail/${it.id}")
            }
        }
    }

}


@Composable
fun ExamRow(exam: Exam, onUserClick: (Exam) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Cyan, RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Column(
                Modifier.clickable { onUserClick(exam) }
            ) {
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "ID: ${exam.id}  ")
                    Text(text = exam.name, fontWeight = FontWeight.Bold)
                }
                Text(text = exam.description)
                Text(text = exam.examDate + " " + exam.examTime)
                Text(text = exam.examLocation)

            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview
@Composable
fun ExamRowPreview() {
    ExamRow(
        exam = Exam(
            id = 1,
            name = "Math",
            description = "Math exam",
            examDate = "2022-12-12",
            examTime = "12:00",
            examDuration = "2 hours",
            examLocation = "BlinkID"
        ),
        onUserClick = {}
    )
}

@Composable
fun ExamList(exams: List<Exam>, onUserClick: (Exam) -> Unit) {
    Log.e("ExamList", "ExamList: $exams")
    Column {
        exams.forEach { exam ->
            ExamRow(exam, onUserClick)
        }
    }

}


@Preview
@Composable
fun ProgressBar() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
    }
}