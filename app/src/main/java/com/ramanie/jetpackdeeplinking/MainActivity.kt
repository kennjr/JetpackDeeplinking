package com.ramanie.jetpackdeeplinking

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.ramanie.jetpackdeeplinking.ui.theme.JetpackDeeplinkingTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackDeeplinkingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = Destinations.HOME_SCREEN.destination){
                        composable(route = Destinations.HOME_SCREEN.destination){
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Button(onClick = { navController.navigate(Destinations.DETAIL_SCREEN.destination) }) {
                                    Text(text = "Go to detail")
                                }
                            }
                        }
                        composable(route = Destinations.DETAIL_SCREEN.destination,
                            deepLinks = listOf(
                                // this is how we'll allow other apps to call our app
                                navDeepLink {
                                    // when a uri with the pattern below is 'called' then our app will
                                    // be one of the apps that can open it, so it'll(the app) appear as an option and the user can choose it
                                    uriPattern = "https://ramanie.com/{id}"
                                    // this is what the user can do, since for this example we'd like
                                    // the user to be able to view the screen and see the id that was passed for the 'call'
                                    action = Intent.ACTION_VIEW
                                } ), arguments = listOf( navArgument(ArgumentKeys.ARG_ID.key){
                                    type = NavType.IntType
                                    defaultValue = -1
                            } )){ backstack ->
                            val id = backstack.arguments?.getInt(ArgumentKeys.ARG_ID.key)
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Here's the content\n$id",
                                    textAlign = TextAlign.Center,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackDeeplinkingTheme {
        Greeting("Android")
    }
}