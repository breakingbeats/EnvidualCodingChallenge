//
//  TweetOverviewView.swift
//  iosApp
//
//  Created by Leon Hergt - Privat on 29.10.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import SwiftUI

struct TweetOverviewView: View {
    
    @StateObject var mViewModel = TweetOverviewViewModel()
    
    var body: some View {
        TweetOverviewRepresentable(mTweets: $mViewModel.mTweets)
            .searchable(text: $mViewModel.mSearchText)
            .task {
                await mViewModel.onAppear()
            }
            .onChange(of: mViewModel.mSearchText, perform: { value in
                Task {
                    await mViewModel.collectFlow(keyword: value)
                }
            })
    }
}

#Preview {
    TweetOverviewView()
}
