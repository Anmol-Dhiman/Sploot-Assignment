package com.example.anmolsplootassignment.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp


@Composable
fun Instructions(onClick: () -> Unit) {

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
            _instruction(
                heading = "Step 1",
                content = "Select the catagory of locations which you want to search nearby"
            )
            _instruction(
                heading = "Step 2",
                content = "Select the radius inside which you want to search the location"
            )
            _instruction(
                heading = "Step 3",
                content = "Tap any red pin on the map to know more details about that location"
            )
            Spacer(modifier = Modifier.height(height = 25.dp))

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.onBackground)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(text = "Got it", color = MaterialTheme.colors.surface)
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "right",
                        tint = MaterialTheme.colors.surface
                    )
                }

            }
        }
    }


}

@Composable
fun _instruction(heading: String, content: String) {
    Text(
        text = heading, style = MaterialTheme.typography.h6, color = MaterialTheme.colors.onSurface
    )
    Spacer(modifier = Modifier.height(height = 25.dp))
    Text(
        text = content,
        style = MaterialTheme.typography.body2,
        color = MaterialTheme.colors.onSecondary,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.height(height = 25.dp))
}


