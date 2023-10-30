//
//  TweetOverviewViewModel.swift
//  iosApp
//
//  Created by Leon Hergt - Privat on 29.10.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

class TweetOverviewViewModel: ObservableObject {
    
    @Published var mTweets: [TweetDTO] = []
    @Published var mSearchText: String = ""
    
    let mTweetRepository = TweetRepository(driverFactory: DriverFactory())
    
    func onAppear() async {
        try? await mTweetRepository.fetchTweets()
        await collectFlow(keyword: mSearchText)
    }
    
    func collectFlow(keyword: String) async {
        try? await self.mTweetRepository.getTweetsByKeyword(keyword: keyword).collect(collector: Collector<[Tweet]>{ tweets in
            self.mTweets = tweets.map({ TweetDTO(mId: $0.mId, mMessage: $0.mMessage, mTimestamp: $0.mTimestamp) })
        })
    }
    
}
