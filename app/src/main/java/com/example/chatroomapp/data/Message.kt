package com.example.chatroomapp.data

data class Message(
    val text: String,
    val isSentByCurrentUser: Boolean=false,
    val senderFirstName:String,
    val timestamp:Long=System.currentTimeMillis(),
    val senderId:String
)
