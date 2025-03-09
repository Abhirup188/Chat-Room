package com.example.chatroomapp.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.chatroomapp.viewmodel.AuthState
import com.example.chatroomapp.viewmodel.AuthViewModel

@Composable
fun SignUpSreen(authViewModel: AuthViewModel,onNavigationToLogIn:()->Unit,navController: NavController){
    var firstName by remember{ mutableStateOf("") }
    var lastName by remember{ mutableStateOf("") }
    var email by remember{ mutableStateOf("") }
    var password by remember{ mutableStateOf("") }
    val context = LocalContext.current
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value){
        when(authState.value){
            is AuthState.Authenticated -> { navController.navigate(Screen.ChatRoomScreen.route) }
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
            label = { Text(text = "Email")}, modifier = Modifier.padding(8.dp))
        OutlinedTextField(value = password, onValueChange = {password=it},
            label = { Text(text = "Password")},modifier = Modifier.padding(8.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        OutlinedTextField(value = firstName, onValueChange = {firstName = it},
            label = { Text(text = "Firstname")},modifier = Modifier.padding(8.dp))
        OutlinedTextField(value = lastName, onValueChange = {lastName=it},
            label = { Text(text = "Lastname")},modifier = Modifier.padding(8.dp))

        Button(onClick = {
            authViewModel.signUp(email, password, firstName, lastName)
        }, modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Sign Up")
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Already have an account? Log in",
            modifier = Modifier.clickable {
                onNavigationToLogIn()
            }
        )
    }

}

//@Preview(showBackground = true)
//@Composable
//fun SignUpSreenPreview(){
//    SignUpSreen({})
//}
