package com.example.anmolsplootassignment.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.anmolsplootassignment.utils.Range.getLocationTypeRangeData
import com.example.anmolsplootassignment.utils.Range.getRadiusRangeData


@Composable
fun DropDownMenus(
    radiusRange: String,
    locationType: String,
    onRadiusChange: (String) -> Unit,
    onLocationTypeChange: (String) -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            Modifier
                .weight(1f)
                .padding(start = 30.dp, end = 5.dp)
        ) {
            DropDownMenuComponent(
                initialText = "Radius",
                icon = Icons.Default.LocationOn,
                getRadiusRangeData(),
                radiusRange,
                onRadiusChange

            )
        }
        Box(
            Modifier
                .weight(1f)
                .padding(end = 30.dp, start = 5.dp)
        ) {
            DropDownMenuComponent(
                initialText = "Location Type",
                icon = Icons.Default.Menu,
                getLocationTypeRangeData(),
                locationType,
                onLocationTypeChange
            )
        }

    }


}


@Composable
fun DropDownMenuComponent(
    initialText: String,
    icon: ImageVector,
    listData: List<String>,
    range: String,
    onChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
    ) {

        Row(
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = MaterialTheme.shapes.large
                )
                .padding(horizontal = 8.dp, vertical = 10.dp)
                .fillMaxWidth()
                .clickable {
                    expanded = !expanded
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(
                imageVector = icon,
                contentDescription = initialText,
                tint = Color.Gray,
                modifier = Modifier.padding(end = 5.dp)
            )

            Text(
                text = if (range == "") initialText
                else range,
                color = Color.Gray,
                modifier = Modifier.padding(end = 12.dp),
                style = MaterialTheme.typography.caption
            )
        }

        Box(Modifier.padding(top = 52.dp)) {
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.padding(end = 42.dp)
            ) {
                listData.forEach { item ->
                    DropdownMenuItem(
                        onClick = { onChange(item) }
                    ) {
                        Text(
                            text = item,
                            style = MaterialTheme.typography.caption,
                            color = Color.Gray
                        )

                    }
                }

            }
        }


    }
}
