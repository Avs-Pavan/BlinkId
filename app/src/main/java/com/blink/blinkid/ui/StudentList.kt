package com.blink.blinkid.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.blink.blinkid.User


@Composable
fun StudentList(list: List<User>, onUserClick: (User) -> Unit) {

    LazyColumn {
        items(list.size) {
            UserRow(user = list[it], onUserClick = onUserClick)
        }
    }

}

@Composable
fun UserRow(user: User, onUserClick: (User) -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(androidx.compose.ui.graphics.Color.Cyan, RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Column(
                Modifier.clickable { onUserClick(user) }
            ) {
                Row(Modifier.fillMaxWidth()){
                    Text(text = "ID: ${user.id}  ")
                    Text(text = user.username, fontWeight = FontWeight.Bold)
                }
                Text(text = user.email)
            }
        }
    }
}


@Preview(showBackground = false)
@Composable
fun UserRowPreview() {
    UserRow(
        user = User(
            email = "pavan@gmail.com",
            id = 1,
            images = emptyList(),
            password = "password",
            roles = emptyList(),
            status = "active",
            username = "Pavan"
        )
    ) {}
}