// LoginScreen.kt
package com.example.book.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.book.utils.ValidationUtils
import com.example.book.viewmodel.LoginViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var loginError by remember { mutableStateOf<String?>(null) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val loginResult by viewModel.loginResult.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle(initialValue = false)

    LaunchedEffect(loginResult) {
        loginResult?.fold(
            onSuccess = { 
                // Navigate to main screen on success
                navController.navigate("main") {
                    popUpTo("login") { inclusive = true }
                }
            },
            onFailure = { 
                showError = true
                errorMessage = it.message ?: "An error occurred"
            }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Chào mừng trở lại!",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            OutlinedTextField(
                value = email,
                onValueChange = { 
                    email = it
                    emailError = ValidationUtils.getEmailError(it)
                    loginError = null
                },
                placeholder = { Text("Nhập email của bạn") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                shape = MaterialTheme.shapes.medium,
                isError = emailError != null,
                supportingText = {
                    if (emailError != null) {
                        Text(text = emailError!!, color = MaterialTheme.colorScheme.error)
                    }
                },
                enabled = !isLoading
            )
            
            OutlinedTextField(
                value = password,
                onValueChange = { 
                    password = it
                    passwordError = if (it.isEmpty()) "Mật khẩu không được để trống" else null
                    loginError = null
                },
                placeholder = { Text("Nhập mật khẩu của bạn") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                shape = MaterialTheme.shapes.medium,
                isError = passwordError != null,
                supportingText = {
                    if (passwordError != null) {
                        Text(text = passwordError!!, color = MaterialTheme.colorScheme.error)
                    }
                },
                enabled = !isLoading
            )
            
            if (showError) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
            
            TextButton(
                onClick = { /* TODO: Implement forgot password navigation */ },
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(bottom = 16.dp),
                enabled = !isLoading
            ) {
                Text("Quên mật khẩu?", color = Color(0xFF8A956E))
            }
            
            Button(
                onClick = { 
                    // Validate fields before proceeding
                    emailError = ValidationUtils.getEmailError(email)
                    passwordError = if (password.isEmpty()) "Mật khẩu không được để trống" else null
                    
                    // Only proceed if there are no errors
                    if (emailError == null && passwordError == null) {
                        viewModel.login(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A956E)),
                shape = MaterialTheme.shapes.medium,
                enabled = !isLoading && email.isNotEmpty() && password.isNotEmpty()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text("ĐĂNG NHẬP", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
            
            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Chưa có tài khoản? ")
                TextButton(
                    onClick = { navController.navigate("registration") },
                    enabled = !isLoading
                ) {
                    Text("Đăng ký", color = Color(0xFF8A956E))
                }
            }
        }
    }
}
