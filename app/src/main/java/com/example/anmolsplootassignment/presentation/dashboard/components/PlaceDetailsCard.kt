package com.example.anmolsplootassignment.presentation.dashboard.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.anmolsplootassignment.R
import com.example.anmolsplootassignment.utils.MultiplePermissionStateExt.hasAllPermissions
import com.example.anmolsplootassignment.utils.navigation.Screen
import kotlin.text.Typography.bullet

@Composable
fun PlaceDetailsCard(
    locationName: String,
    rating: String,
    noOfReview: String,
    address: String,
    onDirectionClick: () -> Unit,
    types: List<String>,
    businessStatus: String
) {
    Card(
        shape = RoundedCornerShape(
            topEnd = 12.dp,
            topStart = 12.dp,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),

        ) {
        Column(
            modifier = Modifier
                .background(color = Color.White)
                .padding(vertical = 12.dp, horizontal = 22.dp)
        ) {
            Row() {
                Image(
                    painter = painterResource(id = R.drawable.location),
                    contentDescription = "location image",
                    modifier = Modifier
                        .clip(shape = MaterialTheme.shapes.medium)
                        .weight(1F)
                        .fillMaxWidth()
                        .height(100.dp),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column(Modifier.weight(2F)) {
                    Text(
                        text = locationName,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.onSecondary
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "$rating/5 in $noOfReview reviews.",
                        color = MaterialTheme.colors.onSecondary,
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = address,
                        color = Color.Gray,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick =
                onDirectionClick,
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.onBackground)
            ) {
                Text(text = "Direction", color = MaterialTheme.colors.surface)
            }

            Spacer(modifier = Modifier.height(10.dp))
            var type = ""
            types.forEach {
                type += it.capitalize(Locale.current).replace("_", " ") + ","
                type += " "
            }

            Points(type, "Type")
            Points(businessStatus, "Business Status")

        }
    }
}


@Composable
fun Points(text: String, header: String) {
    Row {
        Canvas(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp)
                .size(6.dp)
                .align(Alignment.CenterVertically)

        ) {
            drawCircle(Color.Black)
        }
        Text(
            text = "$header $text",
            style = MaterialTheme.typography.body2,
            color = Color.Gray,
        )

    }
    Spacer(modifier = Modifier.height(8.dp))
}

