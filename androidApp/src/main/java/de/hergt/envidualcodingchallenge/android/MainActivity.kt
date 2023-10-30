package de.hergt.envidualcodingchallenge.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.hergt.envidualcodingchallenge.android.tweet.overview.TweetOverviewView
import de.hergt.envidualcodingchallenge.android.tweet.overview.TweetOverviewViewModel
import de.hergt.envidualcodingchallenge.core.DriverFactory
import de.hergt.envidualcodingchallenge.navigation.NavRoutes
import de.hergt.envidualcodingchallenge.repositories.TweetRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Navigation()
            }
        }
    }
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val driver = DriverFactory(context)
    val tweetRepository = TweetRepository(driver)

    NavHost(navController = navController, startDestination = NavRoutes.TweetOverview) {
        composable(NavRoutes.TweetOverview) {
            TweetOverviewView(TweetOverviewViewModel(tweetRepository))
        }
    }
}