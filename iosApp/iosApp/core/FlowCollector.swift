//
//  FlowCollector.swift
//  iosApp
//
//  Created by Leon Hergt - Privat on 29.10.23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared

class Collector<T>: Kotlinx_coroutines_coreFlowCollector {
    
    let callback:(T) -> Void

    init(callback: @escaping (T) -> Void) {
        self.callback = callback
    }

    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        DispatchQueue.main.async {
            self.callback(value as! T)
        }
        completionHandler(nil)
    }
}
