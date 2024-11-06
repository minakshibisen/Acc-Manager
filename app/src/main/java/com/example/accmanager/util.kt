package com.example.accmanager

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun EmptyScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp), // Optional padding if needed
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Image(
                painter = painterResource(id = R.drawable.empty),
                contentDescription = "Account Empty Image",
                modifier = Modifier.size(
                    width = 150.dp,
                    height = 150.dp
                ) // specify width and height here
            )
            Text(
                text = "No Account Added Yet.",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

    }

}