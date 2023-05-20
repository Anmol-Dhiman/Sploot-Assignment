package com.example.anmolsplootassignment.presentation.starter

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.anmolsplootassignment.R
import com.example.anmolsplootassignment.utils.navigation.Screen
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavHostController, isFirstTime: Boolean) {


    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 3000
        )
    )

    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(2000)
        navController.popBackStack()
        if (isFirstTime)
            navController.navigate(Screen.Instruction.route)
        else
            navController.navigate(Screen.Dashboard.route)
    }
    Splash(alpha = alphaAnim.value)

}

@Composable
fun Splash(alpha: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.sploot),
            contentDescription = "Sploot Logo",
            modifier = Modifier.clip(shape = RoundedCornerShape(90.dp))
        )
    }
}