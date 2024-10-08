package com.example.randomsuperhero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.randomsuperhero.ui.theme.RandomSuperheroTheme
import com.example.randomsuperhero.ui.theme.backRound1
import com.example.randomsuperhero.ui.theme.backRound2
import com.example.randomsuperhero.ui.theme.backRound3
import com.example.randomsuperhero.ui.theme.backRoundColouri
import com.example.randomsuperhero.ui.theme.fontColouri
import com.example.randomsuperhero.ui.theme.neonGreenText
import com.example.randomsuperhero.ui.theme.neonOrangeText
import com.example.randomsuperhero.ui.theme.text1
import com.example.randomsuperhero.ui.theme.whiteText

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RandomSuperheroTheme {
                Routes()
            }
        }
    }
}

@Composable
fun Routes () {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "Home"
    ) {
        composable(route = "Home") {
            RandomSuperhero(navController)
        }
        composable(route = "Info") {
            InfoScreen(navController)
        }
        composable(route = "Settings") {
            SettingsScreen(navController)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopBar(title: String, navController: NavController) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = Modifier.background(backRoundColouri)) {
        TopAppBar(
            title = { Text(title, color = fontColouri) },
            actions = {
                IconButton(onClick = {
                    expanded = !expanded
                }) {
                    Icon(Icons.Filled.MoreVert, contentDescription = null, tint = fontColouri)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(backRoundColouri)
                        .border(4.dp, fontColouri, RectangleShape)
                ) {
                    DropdownMenuItem(
                        text = {ThemedText(stringResource(R.string.info),16)},
                        onClick = { navController.navigate("info") }
                    )
                    DropdownMenuItem(
                        text = { ThemedText(stringResource(R.string.settings), 16) },
                        onClick = { navController.navigate("settings") }
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = backRoundColouri,
            ),
        )
        Divider(
            color = fontColouri,
            thickness = 4.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 60.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenTopBar(title: String, navController: NavController) {
    Box(modifier = Modifier.background(backRoundColouri)) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = backRoundColouri),
            title = { Text(title, color = fontColouri )  },
            navigationIcon = {
                IconButton(onClick = {navController.navigateUp()}) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, tint = fontColouri )
                }
            },
        )
        Divider(
            color = fontColouri,
            thickness = 4.dp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(top = 60.dp)
        )
    }
}

@Composable
fun RandomSuperhero(navController: NavController, SuperHeroViewModel: SuperHeroViewModel = viewModel()) {
    Scaffold(
        topBar = { MyTopBar(stringResource(R.string.title), navController) },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .background(backRoundColouri),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ThemedText(stringResource(R.string.title),30)
                if (SuperHeroViewModel.error===""){
                    if (SuperHeroViewModel.loading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(16.dp)
                                .size(50.dp),
                            color = fontColouri
                        )
                    } else {
                        if (SuperHeroViewModel.name.isEmpty()) {
                            ThemedText(stringResource(R.string.instructions),24)
                        } else {
                            ThemedText(stringResource(R.string.randomheroTitle,SuperHeroViewModel.name),16)
                            ThemedText(stringResource(R.string.publisher,SuperHeroViewModel.publisher),16)
                            ThemedText(stringResource(R.string.firstAppearance,SuperHeroViewModel.firstAppearance),16)
                            ThemedText(stringResource(R.string.alignment, SuperHeroViewModel.alignment),16)
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(SuperHeroViewModel.imgUrl)
                                    .placeholder(R.drawable.loading)
                                    .error(R.drawable.no_image)
                                    .build(),
                                contentDescription = "Superhero Image",
                                modifier = Modifier
                                    .size(209.dp,280.dp)
                                    .padding(top = 8.dp)
                                    .border(width = 4.dp, color = fontColouri),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                    Button(
                        modifier = Modifier.padding(top = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = fontColouri,
                            contentColor = backRoundColouri
                        ),
                        onClick = { SuperHeroViewModel.handleButtonPress() }
                    ) {
                        Text(text = stringResource(R.string.button))
                    }
                }else{
                    ThemedText(SuperHeroViewModel.error,30)
                }
            }
        }
    )
}

@Composable
fun InfoScreen(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { ScreenTopBar(stringResource(R.string.info), navController) },
        content = { innerPadding ->
            Column(modifier =modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(backRoundColouri),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                ThemedText(stringResource(R.string.info1),18)
                ThemedText(stringResource(R.string.info2),18)
                ThemedText(stringResource(R.string.info3),18)
                ThemedText(stringResource(R.string.info4),18)
            }
        },
    )
}

@Composable
fun RadioButtonGroup(selectedTheme: Int, onThemeSelected: (Int) -> Unit) {
    Column(modifier = Modifier.padding(top = 16.dp)){
        (1..4).forEach { index ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (selectedTheme == index),
                    onClick = { onThemeSelected(index) }
                )
                Text(color = fontColouri, fontSize = 24.sp,modifier = Modifier.padding(top = 16.dp),text = stringResource(id = when (index) {
                    1 -> R.string.teema1
                    2 -> R.string.teema2
                    3 -> R.string.teema3
                    4 -> R.string.teema4
                    else -> R.string.teema1
                }))
            }
        }
    }
}

@Composable
fun SettingsScreen(navController: NavController, modifier: Modifier = Modifier) {
    var selectedTheme by remember { mutableIntStateOf(1) }
    Scaffold(
        topBar = { ScreenTopBar(stringResource(R.string.settings), navController) },
        content = { innerPadding ->
            Column(modifier = modifier
                .padding(innerPadding)
                .background(backRoundColouri)
                .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ThemedText(stringResource(R.string.teemaInfo),30)
                RadioButtonGroup(
                    selectedTheme = selectedTheme,
                    onThemeSelected = { newTheme ->
                        selectedTheme = newTheme
                        when (newTheme) {
                            1 -> {
                                backRoundColouri = backRound1
                                fontColouri = text1
                            }
                            2 -> {
                                backRoundColouri = backRound2
                                fontColouri = neonGreenText
                            }
                            3 -> {
                                backRoundColouri = backRound2
                                fontColouri = neonOrangeText
                            }
                            4 -> {
                                backRoundColouri = backRound3
                                fontColouri = whiteText
                            }
                        }
                    }
                )
            }
        },
    )
}

@Composable
fun ThemedText(text: String, fontSize: Int, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        color = fontColouri,
        modifier = modifier,
        textAlign = TextAlign.Center
    )
}
@Preview(showBackground = true)
@Composable
fun RandomSuperHeroPreview() {
    RandomSuperheroTheme {
        Routes()
    }
}