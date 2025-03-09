package com.example.chatroomapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatroomapp.isNetworkAvailable

@Composable
fun RoomItem(room: Room,onJoinClicked:()->Unit){
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ){
        Text(text = room.name, fontSize = 16.sp, fontWeight = FontWeight.Normal)
        OutlinedButton(onClick = {
            if(isNetworkAvailable(context = context)){
                onJoinClicked()
            }
            else{
                Toast.makeText(context,"Not Connected to internet",Toast.LENGTH_LONG).show()
            }
        }) {
            Text(text = "Join")
        }
    }
}
@Preview(showBackground = true)
@Composable
fun RoomItemPreview(){
    RoomItem(room = Room(),{})
}