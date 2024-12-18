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
    @State var tabViewTempleteActive: Bool = true
    @ObservedObject var sLVM: SpaceListViewModel = SpaceListViewModel()
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    var body: some Scene {
        WindowGroup {
            TabViewTemplte(isLogin: $isLogin, userId: $userId, tabViewTempleteActive: tabViewTempleteActive, sLVM: sLVM)
        }
    }
}
