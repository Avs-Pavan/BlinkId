package com.blink.blinkid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.blink.blinkid.commons.NetworkResult
import com.blink.blinkid.model.Exam
import com.blink.blinkid.model.User
import com.blink.blinkid.ui.theme.Purple40
import com.blink.blinkid.viewmodel.ExamViewModel
import retrofit2.http.Header


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamDetailsScreen(navController: NavController, viewModel: ExamViewModel) {

    val exam by viewModel.selectedExam.collectAsState()

    var students by remember { mutableStateOf<List<User>>(emptyList()) }

    var isLoading by remember { mutableStateOf(false) }


    val studentsRes by viewModel.students.collectAsState()

    LaunchedEffect(true) {
        viewModel.getStudents()
    }

    when (studentsRes) {
        is NetworkResult.Success -> {
            isLoading = false
            students = (studentsRes as NetworkResult.Success<List<User>>).body!!
        }

        is NetworkResult.Error -> {
            isLoading = false
        }

        is NetworkResult.Loading -> {
            isLoading = true
        }

        else -> {

        }
    }


    Column {

        HeaderText("Exam Details")

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {


            Column(modifier = Modifier.fillMaxSize()) {

                exam?.let {
                    ExamCard(exam = it) {

                    }
                }

                Spacer(modifier = Modifier.padding(5.dp))

                Text(
                    text = "Invigilators",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(start = 0.dp, top = 10.dp, bottom = 10.dp)
                )

                StudentList(false, exam?.admins ?: emptyList()) {

                }

                Spacer(modifier = Modifier.padding(5.dp))

                Text(
                    text = "Enrolled Students",
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    modifier = Modifier.padding(start = 0.dp, top = 10.dp, bottom = 10.dp)
                )

                LazyColumn {
                    exam?.users?.let { list ->
                        items(list.size) { student ->
                            UserCardWithDelete(list[student]) { userId ->
                                viewModel.deleteStudentFromExam(exam?.id!!, userId)
                            }
                        }
                    }
                }

            }
            var sheetVisible by remember { mutableStateOf(false) }

            CircularButton(modifier = Modifier.align(Alignment.BottomEnd)) {
                sheetVisible = true
            }

            if (sheetVisible) {
                ModalBottomSheet(onDismissRequest = { sheetVisible = false }) {

                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Select student to add",
                            style = MaterialTheme.typography.headlineSmall,
                            modifier = Modifier.padding(16.dp)
                        )

                        if (isLoading) {
                            // show loading
                            ProgressBar()
                        } else {
                            StudentList(true, students) { user ->
                                // add invigilator
                                exam?.id?.let { examId ->
                                    viewModel.addStudentToExam(examId = examId, user.id)
                                }
                                sheetVisible = false
                            }
                        }

                        Spacer(modifier = Modifier.height(25.dp))
                    }
                }
            }

        }
    }
}

@Composable
fun CircularButton(
    modifier: Modifier = Modifier, onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary)
            .clickable {
                onClick()
            }
            .padding(10.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Favorite Icon",
            tint = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Composable
fun HeaderText(text: String) {
    Column(
        modifier = Modifier
            .background(Purple40)
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = text,
            fontFamily = FontFamily.Default,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            modifier = Modifier.padding(start = 5.dp, top = 10.dp, bottom = 10.dp)
        )
    }

}