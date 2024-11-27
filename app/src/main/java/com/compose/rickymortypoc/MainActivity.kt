package com.compose.rickymortypoc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.compose.presentation.navigation.RMNavGraph
import com.compose.presentation.ui.theme.RMAppTheme
import com.compose.presentation.viewmodel.CharacterViewModel
import com.compose.presentation.viewmodel.ViewModelFactoryProvider
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    @Inject lateinit var viewModelFactoryProvider: ViewModelFactoryProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as BaseApplication).appComponent.inject(this)
        enableEdgeToEdge()
        setContent {
            RMAppTheme {
                val characterViewModel: CharacterViewModel =
                    ViewModelProvider(
                        LocalContext.current as ComponentActivity,
                        viewModelFactoryProvider
                    )[CharacterViewModel::class.java]
                val navController = rememberNavController()
                //CharacterListScreen(characterViewModel)
                RMNavGraph(characterViewModel)
            }
        }
    }
}
