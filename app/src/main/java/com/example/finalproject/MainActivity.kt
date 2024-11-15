package com.example.finalproject

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
/*import com.example.finalproject.ui.theme.FinalProjectTheme*/
import androidx.compose.material3.*

import androidx.compose.material3.FilledTonalButton

import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


import com.example.finalproject.ModelView.SessionViewModel
import androidx.room.Room
import com.example.finalproject.DB.AppDatabase
import com.example.finalproject.Model.Users
import com.example.finalproject.Model.Post

class MainActivity : ComponentActivity() {
    private val SessionVM by viewModels<SessionViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
/*
            val intent = Intent(this, LoginActivity::class.java)
*/


            MaterialTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HomePaeg(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun HomePaeg(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id=R.drawable.background),
            contentDescription = null,
            modifier=Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        );
        Column(
            modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            Text(
                text = "Welcome To App",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(16.dp)
            )

            Box(){
                Column {
                    FilledTonalButton(
                        onClick = {
                            val intent1 = Intent(context, GuestActivity::class.java)
                            context.startActivity(intent1)
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Guest ")
                    }
                    Button(onClick ={
                        val intent = Intent(context, LoginActivity::class.java)
                        context.startActivity(intent)
                    },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Login")
                    }


                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MaterialTheme {
    }
}