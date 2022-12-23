//
//  GyeSeoYoApp.swift
//  GyeSeoYo
//
//  Created by imseonghyeon on 2022/12/18.
//

import SwiftUI
import CoreData

@main
struct GyeSeoYoApp: App {
    @State var isLogin: Bool = false
    @State var userId: String = ""
    var body: some Scene {
        WindowGroup {
            MainView(isLogin: $isLogin, userId: $userId)
        }
    }
}
