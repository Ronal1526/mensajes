package com.example.mensajes

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue

import com.example.mensajes.ui.theme.MensajesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MensajesTheme{
                Conversation(MessageData.MessageList)

            }
        }
    }
}
data class Message(val sender: String, val body:String)
@Composable
fun Conversation(messageList : List<Message>){
    LazyColumn{
        items(messageList) {
                message -> MessageBox(message)
        }
    }
}

@Composable
fun MessageBox(msg:Message ) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(painter = painterResource(id = R.drawable.usuario),
            contentDescription = "profile picture",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, color = Color.Blue, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        var msgExpanded by remember{ mutableStateOf(false) }
        val surfaceColorChange : Color by animateColorAsState(
            if (msgExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )

        Column(modifier = Modifier.clickable { msgExpanded =!msgExpanded }) {
            //esta variable define los estilos del tipo de letra
            val mySubtitle2Style = TextStyle(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                letterSpacing = 0.15.sp
            )

            Text( text =msg.sender,
                color = Color.Blue,
                style = mySubtitle2Style
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                color = surfaceColorChange,
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text( msg.body,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if(msgExpanded) Int.MAX_VALUE else 1
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES,
showBackground = true,
name = "Dark mode"
    )
@Composable
fun PreviewMessageBox(){
    MensajesTheme {
        Conversation(messageList = MessageData.MessageList )
    }
}
@Preview(name = "Light Mode")
@Composable
fun PreviewLightMessageBox(){
    MensajesTheme {

     Conversation(messageList = MessageData.MessageList )

    }
}