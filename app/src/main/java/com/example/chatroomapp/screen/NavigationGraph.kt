package com.example.chatroomapp.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.chatroomapp.Screen
import com.example.chatroomapp.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NaviagtionGraph(authViewModel: AuthViewModel,navController: NavHostController){
    NavHost(navController = navController,
        startDestination = Screen.SignUpScreen.route
    ){
        composable(route = Screen.SignUpScreen.route){
            SignUpSreen(
                authViewModel = authViewModel,
                onNavigationToLogIn = {
                    navController.navigate(Screen.LoginScreen.route)
                },navController
            )
        }
        composable(route = Screen.LoginScreen.route){
            LogInScreen(
                authViewModel = authViewModel,
                onNavigationToSignUp = {
                    navController.navigate(Screen.SignUpScreen.route)
                },navController
            )
        }
        composable(route = Screen.ChatRoomScreen.route){
            ChatRoomListScreen(onJoinClicked =
            {
                navController.navigate("${Screen.ChatScreen.route}/${it.id}")
            },
                authViewModel = authViewModel, navController = navController
            )
        }
        composable("${Screen.ChatScreen.route}/{roomId}") {
            val roomId: String = it.arguments?.getString("roomId") ?: ""
            ChatScreen(roomId = roomId)
        }
    }
}