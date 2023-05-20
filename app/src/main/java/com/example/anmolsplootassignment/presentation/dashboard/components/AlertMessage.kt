package com.example.anmolsplootassignment.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun AlertMessage(message: String, buttonText: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(horizontal = 30.dp), contentAlignment = Alignment.Center
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(
                    MaterialTheme.colors.surface, shape = MaterialTheme.shapes.medium
                )
                .padding(vertical = 70.dp, horizontal = 60.dp)

        ) {

            Text(
                text = message,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(height = 25.dp))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.onBackground)
            ) {
                Text(text = buttonText, color = MaterialTheme.colors.surface)
            }
        }
    }
}