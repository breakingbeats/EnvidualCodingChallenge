package de.hergt.envidualcodingchallenge.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import de.hergt.envidualcodingchallenge.utils.TimeUtils
import de.hergt.envidualcodingchallenge.dtos.TweetDTO

@Composable
fun TweetCard(tweet: TweetDTO) {
    val timeUtils = TimeUtils()

    Card(Modifier
        .fillMaxWidth()
        .padding(Dp(8f))
        .clickable {  }
    ) {
        Column(Modifier
            .fillMaxWidth()
            .padding(Dp(8f))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(timeUtils.convertTimeMillisToDateString(tweet.mTimestamp))
            }
            Text(tweet.mMessage)
        }
    }
}