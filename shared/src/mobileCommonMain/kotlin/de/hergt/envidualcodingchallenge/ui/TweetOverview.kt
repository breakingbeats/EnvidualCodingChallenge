package de.hergt.envidualcodingchallenge.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import de.hergt.envidualcodingchallenge.core.constants.TextKeys
import de.hergt.envidualcodingchallenge.dtos.TweetDTO

@Composable
fun TweetOverview(tweets: List<TweetDTO>) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = {
                    //TODO open Edit Screen
                }
            ) {
                Icon(Icons.Default.Add, null)
                Text(TextKeys.AddTweet)
            }
        }
    ) {
        LazyColumn {
            items(tweets) {
                TweetCard(it)
            }
        }
    }
}