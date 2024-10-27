package com.wgv.ampliacionbuttonnavigation

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wgv.ampliacionbuttonnavigation.ui.theme.AmpliacionButtonNavigationTheme
import com.wgv.ampliacionbuttonnavigation.ui.theme.Pink40
import com.wgv.ampliacionbuttonnavigation.ui.theme.Pink80

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AmpliacionButtonNavigationTheme {
                val navController = rememberNavController()
                val tabs = listOf(
                    NavItem(
                        label = "Home",
                        icon = Icons.Filled.Home,
                        screen = Screen.HomeScreen
                    ),
                    NavItem(
                        label = "Settings",
                        icon = Icons.Filled.Settings,
                        screen = Screen.SettingScreen
                    ),
                    NavItem(
                        label = "Profile",
                        icon = Icons.Filled.Person,
                        screen = Screen.ProfileScreen("newText")
                    )
                )

                val itemButton = Item(
                    screen = Screen.SettingScreen
                )

                var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
                var texto by rememberSaveable { mutableStateOf("") }
                var textoImprimir by remember{ mutableStateOf("")}

                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = topAppBarColors(
                                containerColor = Pink80,
                                titleContentColor = Pink40
                            ),
                            title = {
                                Text("Top app bar")
                            }
                        )
                    },
                    bottomBar = {
                        NavigationBar(
                            containerColor = Pink80,
                            contentColor = Pink40
                        ) {
                            tabs.mapIndexed { index, navItem ->
                                NavigationBarItem(
                                    selected = selectedTabIndex == index,
                                    onClick = {
                                        selectedTabIndex = index
                                        navController.navigate(navItem.screen)
                                    },
                                    icon = {
                                        Icon(
                                            tint = Pink40,
                                            imageVector = navItem.icon,
                                            contentDescription = null
                                        )
                                    },
                                    label = {
                                        Text(
                                            color = Pink40,
                                            text = navItem.label
                                        )
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->

                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = Screen.HomeScreen
                        ) {
                            composable<Screen.HomeScreen> {
                                Column(modifier = Modifier.fillMaxSize()) {
                                    Text(
                                        text = "Pantalla 1",
                                        color = Color.Red,
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(vertical = 80.dp),
                                        fontSize = 30.sp
                                    )
                                    TextField(
                                        value = texto,
                                        onValueChange = { texto = it },
                                        label = { Text("Introduzca Texto") },
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(vertical = 80.dp)
                                    )
                                    Button(
                                        onClick = {
                                            // Navegar a SettingScreen pasando el texto como argumento
                                            navController.navigate("settings_screen/$texto")
                                        },
                                        modifier = Modifier
                                            .align(Alignment.CenterHorizontally)
                                            .padding(vertical = 80.dp),
                                        colors = ButtonDefaults.buttonColors(Color.White),
                                        enabled = true

                                    )
                                    {
                                        Text("Enviar", color = Color.Black)
                                    }
                                }

                            }
                            composable(
                                route = "settings_screen/{texto}",
                                arguments = listOf(navArgument("texto") { type = NavType.StringType })
                            ) { backStackEntry ->
                                val textoRecibido = backStackEntry.arguments?.getString("texto") ?: ""

                                // Mostrar el texto recibido en la pantalla de settings
                                Box(modifier = Modifier.fillMaxSize()) {
                                    Text(
                                        text = textoRecibido,
                                        modifier = Modifier.align(Alignment.Center),
                                        fontSize = 50.sp
                                    )
                                }
                            }
                            composable<Screen.ProfileScreen> {
                                textoImprimir = ""
                                Text("PROFILE: add your screen here")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AmpliacionButtonNavigationTheme {
    }
}



fun EnviarDato(texto: String){


}

data class NavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)



data class Item(
    val screen: Screen
)