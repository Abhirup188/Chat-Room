package com.example.chatroomapp.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatroomapp.Screen
import com.example.chatroomapp.viewmodel.AuthViewModel
import com.example.chatroomapp.data.Result
import com.example.chatroomapp.viewmodel.AuthState


@Composable
fun LogInScreen(authViewModel: AuthViewModel,onNavigationToSignUp:()->Unit,
                navController:NavController
){
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value){
        when(authState.value){
            is AuthState.Authenticated -> {
                navController.navigate(Screen.ChatRoomScreen.route)
            }
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        OutlinedTextField(value = email, onValueChange = {email=it},
            label = { Text(text = "Email")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp))
        OutlinedTextField(value = password, onValueChange = {password=it},
            label = { Text(text = "Password")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(onClick = {
            authViewModel.login(email,password)
        }, modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Log In")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Dont have an account? Sign Up", Modifier.clickable {
            onNavigationToSignUp()
        })
    }

}

//@Preview(showBackground = true)
//@Composable
//fun LogInScreenPreview(){
//    LogInScreen({})
//}