package com.example.chatroomapp.data

import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.chatroomapp.viewmodel.AuthState
import com.example.chatroomapp.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository(private val auth: FirebaseAuth,
                     private val firestore: FirebaseFirestore
){
    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState
    suspend fun signUp(email:String,password:String,firstName:String, lastName:String):Result<Boolean> =
        try {
            if(email.isEmpty()||password.isEmpty()){
                _authState.value = AuthState.Error("Email and Password Cannot Be Empty!")
            }
            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    if(task.isSuccessful){
                        _authState.value = AuthState.Authenticated
                    }else{
                        _authState.value = AuthState.Error(task.exception?.message?:"Something Went Wrong")
                    }
                }
                .await()
            val user = User(firstName,lastName,email)
            saveUserToFireStore(user)
            Result.Success(true)
        }catch (e:Exception){
            Result.Error(e)
        }
    private suspend fun saveUserToFireStore(user:User){
        firestore.collection("users").document(user.email).set(user).await()
    }
    suspend fun login(email: String,password: String):Result<Boolean> =
        try {
            if(email.isEmpty()||password.isEmpty()){
                _authState.value = AuthState.Error("Email and Password Cannot Be Empty!")
            }
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener {task->
                    if(task.isSuccessful){
                        _authState.value = AuthState.Authenticated
                    }else{
                        _authState.value = AuthState.Error(task.exception?.message?:"Something Went Wrong!")
                    }
                }
                .await()
            Result.Success(true)
        }catch (e:Exception){
            Result.Error(e)
        }
    suspend fun getCurrentUser(): Result<User> {
        val currentUser = auth.currentUser
        return if (currentUser != null) {
            // Fetch user data from Firestore or another source
            val userDocument = firestore.collection("users").document(currentUser.uid).get().await()
            val user = userDocument.toObject(User::class.java)
            if (user != null) {
                Result.Success(user)
            } else {
                Result.Error(Exception("User not found"))
            }
        } else {
            Result.Error(Exception("No logged-in user"))
        }
    }

}