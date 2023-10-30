//
//  TweetOverviewView.swift
//  iosApp
//
//  Created by Leon Hergt - Privat on 29.10.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct TweetOverviewRepresentable: UIViewControllerRepresentable {
    
    @Binding var mTweets: [TweetDTO]
    
    func makeUIViewController(context: Context) -> UIViewController {
        return SharedViewControllers().statusComposable {}
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        SharedViewControllers().updateTweetOverviewComposable(tweets: mTweets)
    }
}
