package com.example.book.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.book.utils.ValidationUtils
import com.example.book.viewmodel.RegistrationState
import com.example.book.viewmodel.RegistrationViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    navController: NavHostController,
    viewModel: RegistrationViewModel
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    
    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var confirmPasswordError by remember { mutableStateOf<String?>(null) }
    
    var showPasswordRequirements by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    // Collect registration state
    LaunchedEffect(key1 = true) {
        viewModel.registrationState.collectLatest { state ->
            when (state) {
                is RegistrationState.Loading -> {
                    isLoading = true
                }
                is RegistrationState.Success -> {
                    isLoading = false
                    // Show success message
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Đăng ký thành công! Đang chuyển đến trang đăng nhập...",
                            duration = SnackbarDuration.Short
                        )
                        // Wait for a short delay to show the message
                        delay(1500)
                        // Navigate to login screen
                        navController.navigate("login") {
                            popUpTo("registration") { inclusive = true }
                        }
                    }
                }
                is RegistrationState.Error -> {
                    isLoading = false
                    // Show error message
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = state.message,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
                else -> {
                    isLoading = false
                }
            }
        }
    }

    val title = buildAnnotatedString {
        append("Chào mừng đến với ")
        pushStyle(SpanStyle(color = Color(0xFF8A956E), fontWeight = FontWeight.Bold))
        append("Book!")
        pop()
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
                text = title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Hãy để chúng tôi giúp bạn hoàn thành công việc.",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            
            OutlinedTextField(
                value = fullName,
                onValueChange = { 
                    fullName = it
                    fullNameError = ValidationUtils.getFullNameError(it)
                },
                placeholder = { Text("Nhập họ tên của bạn") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                shape = MaterialTheme.shapes.medium,
                isError = fullNameError != null,
                supportingText = {
                    if (fullNameError != null) {
                        Text(text = fullNameError!!, color = MaterialTheme.colorScheme.error)
                    }
                },
                enabled = !isLoading
            )
            
            OutlinedTextField(
                value = email,
                onValueChange = { 
                    email = it
                    emailError = ValidationUtils.getEmailError(it)
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
                    passwordError = ValidationUtils.getPasswordError(it)
                    showPasswordRequirements = true
                },
                placeholder = { Text("Nhập mật khẩu của bạn") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
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
            
            if (showPasswordRequirements) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, bottom = 8.dp)
                ) {
                    ValidationUtils.getPasswordRequirements().forEach { requirement ->
                        Text(
                            text = "• $requirement",
                            fontSize = 12.sp,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
            }
            
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { 
                    confirmPassword = it
                    confirmPasswordError = if (it != password) "Mật khẩu không khớp" else null
                },
                placeholder = { Text("Xác nhận mật khẩu của bạn") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                shape = MaterialTheme.shapes.medium,
                isError = confirmPasswordError != null,
                supportingText = {
                    if (confirmPasswordError != null) {
                        Text(text = confirmPasswordError!!, color = MaterialTheme.colorScheme.error)
                    }
                },
                enabled = !isLoading
            )
            
            Button(
                onClick = { 
                    // Validate all fields before proceeding
                    fullNameError = ValidationUtils.getFullNameError(fullName)
                    emailError = ValidationUtils.getEmailError(email)
                    passwordError = ValidationUtils.getPasswordError(password)
                    confirmPasswordError = if (confirmPassword != password) "Mật khẩu không khớp" else null
                    
                    // Only proceed if there are no errors
                    if (fullNameError == null && emailError == null && 
                        passwordError == null && confirmPasswordError == null) {
                        viewModel.register(fullName, email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8A956E)),
                shape = MaterialTheme.shapes.medium,
                enabled = !isLoading && fullName.isNotEmpty() && email.isNotEmpty() && 
                         password.isNotEmpty() && confirmPassword.isNotEmpty()
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White
                    )
                } else {
                    Text("ĐĂNG KÝ", fontWeight = FontWeight.Bold, color = Color.White)
                }
            }
            
            Row(
                modifier = Modifier.padding(top = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Đã có tài khoản? ")
                TextButton(
                    onClick = { navController.navigate("login") },
                    enabled = !isLoading
                ) {
                    Text("Đăng nhập", color = Color(0xFF8A956E))
                }
            }
        }
    }
}
