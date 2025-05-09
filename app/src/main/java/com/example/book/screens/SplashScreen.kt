package com.example.book.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.book.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.platform.LocalContext
import android.content.res.Resources
import androidx.core.content.res.ResourcesCompat

@Composable
fun SplashScreen(onGetStartedClick: () -> Unit) {
    val context = LocalContext.current
    val resources = context.resources

    val drawableId = if (ResourcesCompat.getDrawable(resources, R.drawable.splash_image, null) != null) {
        R.drawable.splash_image
    } else {
        android.R.drawable.ic_dialog_alert
    }

    val painter = painterResource(id = drawableId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Hình minh họa
        Image(
            painter = painter,
            contentDescription = "Splash Image",
            modifier = Modifier
                .height(220.dp)
                .padding(bottom = 24.dp)
        )

        // Tiêu đề
        Text(
            text = "Đọc sách trực tuyến với Book",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Mô tả
        Text(
            text = "Đọc sách để học tập, thư giãn và cho chính bản thân bạn.",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Nút "Get Started"
        Button(
            onClick = onGetStartedClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF697D5B))
        ) {
            Text(text = "BẮT ĐẦU", color = Color.White, fontWeight = FontWeight.Bold)
        }
    }
}